package origin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @param <Double> The type of the elements to be held in this node.
 */

public class KDNode<E> {

    private int maxSize;
    private int cSize;
    private int numDims;
    //TODO heuristic for determining dim to split on.
    //Unsure what to use atm. Will likely choose widest dimension for now to save processing time.
    //private List<IKDElement> dimVariance;
    private List<Double> upperBound;
    private List<Double> lowerBound;
    private List<KDElement> elements;

    private boolean isLeaf;
    private int splitDim;
    private KDNode<Double> upperNode;
    private KDNode<Double> lowerNode;
    private double boundValue; //ahhhh this is bad. Should have just designed this thing to work exclusively with double primitives from the start..



    public KDNode(int size, int numDims) {
        this.maxSize = size;
        this.cSize = 0;

        //dimVariance = new ArrayList<IKDElement>(numDims);
        upperBound = new ArrayList<Double>(numDims);
        lowerBound = new ArrayList<Double>(numDims);
        elements   = new ArrayList<KDElement>(maxSize);

        //These will be set/changed if/when this node splits:
        isLeaf = true;
        splitDim = -1;
        upperNode = null;
        lowerNode = null;
        
    }

    public void add(KDElement element) {
        if (isLeaf) {//if node is not full
            if (cSize >= maxSize)
            {
                split(getWidestDim());
                sendToChildNode(element);
            }
            elements.add(element);

            List<Double> vals = element.getKDValues();
            //Iterate through dimensions and update node bounds as needed:
            for (int i = 0; i < numDims; i++) {
                if (vals.get(i).doubleValue() > upperBound.get(i).doubleValue()) {//if greater than upper bound
                    upperBound.set(i, vals.get(i));
                } else if (vals.get(i).doubleValue() < lowerBound.get(i).doubleValue()) {//if less than lower bound
                    lowerBound.set(i, vals.get(i));
                }
            }
        } else {
            sendToChildNode(element);
        }
    }
    
    private void sendToChildNode(KDElement element) {
        if (element.getKDValues().get(splitDim).doubleValue() > this.boundValue) {
            upperNode.add(element);
        } else {
            lowerNode.add(element);
        }
    }

    private void split(int dimensionIndex) {

        elements.sort(new Comparator<KDElement>() {
            public int compare(KDElement e1, KDElement e2)
            {
                return  ((int)e2.getKDValues().get(dimensionIndex).doubleValue()
                        -(int)e1.getKDValues().get(dimensionIndex).doubleValue());
            }
        });

        this.upperNode = new KDNode<Double>(maxSize, numDims);
        this.lowerNode = new KDNode<Double>(maxSize, numDims);
        
        for (int i = 0; i < maxSize; i++)
        {
            if (i < maxSize/2) {
                lowerNode.add(elements.get(i));
            } else if (i == maxSize/2) {
                lowerNode.add(elements.get(i));
                this.boundValue = elements.get(i).getKDValues().get(dimensionIndex).doubleValue();
            } else {
                upperNode.add(elements.get(i));
            }
        }
        elements = null;
        upperBound = null;
        lowerBound = null;
    }

    private int getWidestDim() {
        int widestIndex = -1;
        double widestWidth = -1;
        for (int i = 0; i < numDims; i++) {
            double cWidth = upperBound.get(i).doubleValue()-lowerBound.get(i).doubleValue();
            if (cWidth > widestWidth)
            {
                widestIndex = i;
                widestWidth = cWidth;
            }
        }
        return widestIndex;
    }

    public List<KDElement> getKNN(KDElement target, int k) {
        List<KDElement> out = null;
        if (isLeaf) {
            //iterate through and test distances to find nearest
            double[] minDists = new double[k];
            ArrayList<KDElement> minElements = new ArrayList<KDElement>(k);
            double  maxMinDist  = Double.MAX_VALUE;
            int     maxMinIndex = -1;
            int cIndex = 0;
            for (int i=0; i < elements.size(); i++) {
                KDElement e = elements.get(i);
                double cDist = e.kdDistanceTo(target); // TODO This changes the value of 'lastDist' within the KDElement object
                if (maxMinIndex == -1 && cDist <= maxMinDist) {
                    minDists[cIndex] = cDist;
                    minElements.add(cIndex, e);
                    cIndex++;
                } else if (maxMinIndex != -1 && cDist <= maxMinDist) {
                    minDists[maxMinIndex] = cDist;
                    minElements.set(maxMinIndex, e);
                }

                if (cIndex+1 == cSize)
                {
                    minElements.sort(new Comparator<KDElement>() {
                    
                        public int compare(KDElement e1, KDElement e2)
                        {
                            return (int)(e2.getLastKDDist() - e1.getLastKDDist());
                        }
                    });
                }
                out = minElements;
            }
        } else {
            //traverse kd tree
            if (target.getKDValues().get(splitDim).doubleValue() > this.boundValue) {
                out = upperNode.getKNN(target, k);
            } else {
                out = lowerNode.getKNN(target, k);
            }
        }

        return out;
    }
}
