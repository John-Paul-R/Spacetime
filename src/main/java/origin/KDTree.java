package origin;
/**
 *
 * @param <E> The type of the elements to be put in this tree.
 */


public class KDTree<E extends Comparable<E>> {

    private int binSize;
    private int numDims;
    private KDNode root;

    public KDTree(int binSize, int numDims) throws Exception {
        if (binSize >= 1) {
            this.binSize = binSize;
            this.numDims = numDims;
        } else {
            throw new Exception("KDTree bin size and number of dimensions must both be greater than or equal to one.");
        }
        this.root = new KDNode<E>(binSize, numDims);
    }

}