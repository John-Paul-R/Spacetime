package origin;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @param <E> The type of the elements to be held in this node.
 */

public class KDNode<E extends Comparable<E>> {

    private int maxSize;
    private int cSize;
    private int numDims;
    //TODO heuristic for determining dim to split on.
    //Unsure what to use atm. Will likely choose widest dimension for now to save processing time.
    //private List<IKDElement<E>> dimVariance;
    private List<E> upperBound;
    private List<E> lowerBound;
    private List<IKDElement<E>> elements;

    public KDNode(int size, int numDims) {
        this.maxSize = size;
        this.cSize = 0;

        //dimVariance = new ArrayList<IKDElement<E>>(numDims);
        
        upperBound = new ArrayList<E>(numDims);
        lowerBound = new ArrayList<E>(numDims);
        elements    = new ArrayList<IKDElement<E>>(maxSize);
    }

    public void add(IKDElement<E> element) {
        if (cSize < maxSize) {//if node is not full
            elements.add(element);

            List<E> vals = element.getKDValues();
            //Iterate through dimensions and update node bounds as needed:
            for (int i = 0; i < numDims; i++) {
                if (vals.get(i).compareTo(upperBound.get(i)) > 0) {//if greater than upper bound
                    upperBound.set(i, vals.get(i));
                } else if (vals.get(i).compareTo(lowerBound.get(i)) < 0) {//if less than lower bound
                    lowerBound.set(i, vals.get(i));
                }

            }
        }
    }
}