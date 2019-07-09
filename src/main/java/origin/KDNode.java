package origin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @param <Double> The type of the elements to be held in this node.
 */

public class KDNode<E> {

    private KDTree<E> tree;
    private KDNode<E> parent;
    private int maxSize;
    private int numDims;
    private int depth;
    //TODO heuristic for determining dim to split on.
    //Unsure what to use atm. Will likely choose widest dimension for now to save processing time.
    //private List<IKDElement> dimVariance;
    private List<Double> upperBound;
    private List<Double> lowerBound;
    private List<KDElement> elements;

    private boolean isLeaf;
    private int splitDim;
    private KDNode<E> upperNode;
    private KDNode<E> lowerNode;
    private double boundValue; //ahhhh this is bad. Should have just designed this thing to work exclusively with double primitives from the start..



    public KDNode(int size, int numDims, KDNode<E> parent, int depth, KDTree<E> tree) {
        this.maxSize = size;
        //this.elements.size() = 0;
        this.numDims = numDims;

        //dimVariance = new ArrayList<IKDElement>(numDims);
        upperBound = new ArrayList<Double>(numDims);
        lowerBound = new ArrayList<Double>(numDims);
        for (int i=0; i<numDims; i++)
        {
            upperBound.add(i, -Double.MAX_VALUE/4D);
            lowerBound.add(i, Double.MAX_VALUE/4D);
        }
        elements   = new ArrayList<KDElement>(maxSize);

        this.tree = tree;
        this.depth = depth;
        this.parent = parent;

        //These will be set/changed if/when this node splits:
        isLeaf = true;
        splitDim = -1;
        upperNode = null;
        lowerNode = null;
    }

    public void add(KDElement element) {
        
            if (isLeaf && elements.size() >= maxSize)
        {
            split(getWidestDim());
            System.out.println("Node split\n\n\n");
            isLeaf = false;
        }
        
        
        if (isLeaf) {//if node is not full
            
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
            System.out.println("Added Element to "+ this.toString() +". elements.size() " + elements.size() + "\n\n\n");
        
        } else {
            try {
            sendToChildNode(element);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        this.splitDim = dimensionIndex;
        elements.sort(new Comparator<KDElement>() {
            public int compare(KDElement e1, KDElement e2)
            {
                if (e1.getKDValues() == null) {
                    return (e2.getKDValues() == null) ? 0 : 1;
                } else if (e2.getKDValues() == null) {
                    return -1;
                }

                return  (int)Math.signum(e1.getKDValues().get(dimensionIndex).doubleValue()
                        -e2.getKDValues().get(dimensionIndex).doubleValue());
            }
        });
        for (KDElement elem : elements) {
            System.out.println("KD Value (Heading): " + elem.getKDValues().get(0));
        }
        this.upperNode = new KDNode<E>(maxSize, numDims, this, depth+1, tree);
        this.lowerNode = new KDNode<E>(maxSize, numDims, this, depth+1, tree);
        
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

        tree.onSplit(upperNode, lowerNode, depth+1);
    }

    private int getWidestDim() {
        int widestIndex = -1;
        double widestWidth = -1;
        for (int i = 0; i < numDims; i++) {
            double cWidth = Math.abs(upperBound.get(i).doubleValue()-lowerBound.get(i).doubleValue());
            System.out.println(cWidth);
            if (cWidth > widestWidth)
            {
                widestIndex = i;
                widestWidth = cWidth;
            }
            
        }
        System.out.println(widestIndex);
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
                
                if (e != target && e != ((EnemyState) target).getPrevState()) {////WARN this condition is janky af
                    if (maxMinIndex == -1 && cDist <= maxMinDist) {
                        minDists[cIndex] = cDist;
                        minElements.add(cIndex, e);
                        cIndex++;
                    } else if (maxMinIndex != -1 && cDist <= maxMinDist) {
                        minDists[maxMinIndex] = cDist;
                        minElements.set(maxMinIndex, e);
                    }
    
                    if (cIndex+1 >= k)
                    {
                        minElements.sort(new Comparator<KDElement>() {
                        
                            public int compare(KDElement e1, KDElement e2)
                            {
                                
                                return (int)Math.signum(e1.getLastKDDist() - e2.getLastKDDist());
                            }
                        });
                        maxMinIndex = minElements.size()-1;
                        maxMinDist = minElements.get(maxMinIndex).getLastKDDist();
                        
                    }
                }
            }
            for (KDElement elem : minElements) {
                System.out.println("KD Value (Heading): " + elem.getKDValues().get(0));
                System.out.println("KD Dist: " + elem.getLastKDDist());
            }
            System.out.println("END LOOP\n\n\n");
            out = minElements;
            System.out.println(out.get(0));
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

    int size() {
        int out = -1;
        if (isLeaf) {
            out = elements.size();
        }
        return out;
    }
    KDNode<E> getUpper() {
        return upperNode;
    }
    KDNode<E> getLower() {
        return lowerNode;
    }
    KDNode<E> getParent() {
        return parent;
    }
}
