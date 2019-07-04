package origin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @param <E> The type of the elements to be held in this node.
 */

public class KDNode<E extends Number> {

    private int maxSize;
    private int cSize;
    private int numDims;
    //TODO heuristic for determining dim to split on.
    //Unsure what to use atm. Will likely choose widest dimension for now to save processing time.
    //private List<IKDElement<E>> dimVariance;
    private List<E> upperBound;
    private List<E> lowerBound;
    private List<KDElement<E>> elements;

    private boolean isLeaf;
    private int splitDim;
    private KDNode<E> upperNode;
    private KDNode<E> lowerNode;
    private double boundValue; //ahhhh this is bad. Should have just designed this thing to work exclusively with double primitives from the start..



    public KDNode(int size, int numDims) {
        this.maxSize = size;
        this.cSize = 0;

        //dimVariance = new ArrayList<IKDElement<E>>(numDims);
        upperBound = new ArrayList<E>(numDims);
        lowerBound = new ArrayList<E>(numDims);
        elements   = new ArrayList<KDElement<E>>(maxSize);

        //These will be set/changed if/when this node splits:
        isLeaf = true;
        splitDim = -1;
        upperNode = null;
        lowerNode = null;
        
    }

    public void add(KDElement<E> element) {
        if (isLeaf) {//if node is not full
            if (cSize < maxSize)
            {
                split(getWidestDim());
                if (element.getKDValues().get(splitDim).doubleValue() > this.boundValue) {
                    upperNode.add(element);
                } else {
                    lowerNode.add(element);
                }
            }
            elements.add(element);

            List<E> vals = element.getKDValues();
            //Iterate through dimensions and update node bounds as needed:
            for (int i = 0; i < numDims; i++) {
                if (vals.get(i).doubleValue() > upperBound.get(i).doubleValue()) {//if greater than upper bound
                    upperBound.set(i, vals.get(i));
                } else if (vals.get(i).doubleValue() < lowerBound.get(i).doubleValue()) {//if less than lower bound
                    lowerBound.set(i, vals.get(i));
                }
            }
        } else {
            
        }
    }
    
    private void split(int dimensionIndex) {

        elements.sort(new Comparator<KDElement<E>>() {
            public int compare(KDElement<E> e1, KDElement<E> e2)
            {
                return  ((int)e2.getKDValues().get(dimensionIndex).doubleValue()
                        -(int)e1.getKDValues().get(dimensionIndex).doubleValue());
            }
        });

        this.upperNode = new KDNode<E>(maxSize, numDims);
        this.lowerNode = new KDNode<E>(maxSize, numDims);
        
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
}
