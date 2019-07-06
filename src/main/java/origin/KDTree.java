package origin;

import java.util.List;
import java.util.LinkedList;
import java.awt.Graphics2D;
import java.awt.Color;

/**
 *
 * @param <E> The type of the elements to be put in this tree.
 */

//TODO Rebalance tree at the start of each round?
public class KDTree<E> {

    private int binSize;
    private int numDims;
    //this is the CURRENT max depth (Not a limit).
    private int maxDepth;
    private KDNode<E> root;
    private LinkedList<KDNode<E>> nodes;
    public KDTree(int binSize, int numDims) {
        if (binSize >= 1) {
            this.binSize = binSize;
            this.numDims = numDims;
        } else {
            System.out.println("KDTree bin size and number of dimensions must both be greater than or equal to one.");
        }
        this.root = new KDNode<E>(binSize, numDims, null, 0,  this);
        nodes = new LinkedList<KDNode<E>>();
        nodes.add(this.root);
    }

    public void add(KDElement element) {
        root.add(element);
    }

    public List<KDElement> getKNN(KDElement target, int k) {
        return root.getKNN(target, k);
    }

    void onSplit(KDNode<E> upperNode, KDNode<E> lowerNode, int depth) {
        nodes.add(upperNode);
        nodes.add(lowerNode);
        if (maxDepth < depth) {
            maxDepth = depth;
        }
    }
    public void paint(Graphics2D g, int x, int y)
    {
        g.setColor(new Color(255, 255, 255, 55));
        paintNode(g, x, y, root, 0, 0);
        
    }

    private int rowOffset(int depth, int hSpacing, int nodeW) {
        return depth*(hSpacing + nodeW)/2;
    }
    int paintNode(Graphics2D g, int x, int y, KDNode<E> n, int depth, int hLoc) {
        final int hSpacing = 18;
        final int vSpacing = 18;
        final int nodeH = 40;
        final int nodeW = 36;
        
        int minX, maxX;
        if (n.getLower() != null) {
            g.setColor(new Color(255, 0, 0, 55));
            paintNode(g,x,y,n.getLower(), depth+1, hLoc);

        }
        if (n.getUpper() != null) {
            g.setColor(new Color(0, 255, 0, 55));
            paintNode(g,x,y,n.getUpper(), depth+1, hLoc+maxDepth/(depth+1));

        }
        
        

        int abs_hLoc = hLoc*(nodeW+hSpacing);
        if (n != null)
        {
            if (n.size() == -1) {
                g.fillRect(x+abs_hLoc  +nodeW/4,  y+(nodeH+vSpacing)*depth+nodeH/4, nodeW/2, nodeH/2);
            } else {
                g.drawRect(x+abs_hLoc , y+(nodeH+vSpacing)*depth, nodeW, nodeH);
                g.fillRect(x+abs_hLoc , y+(nodeH+vSpacing)*depth, nodeW, nodeH/binSize*n.size());
                //g.drawLine(x+abs_hLoc , y+(nodeH+vSpacing)*depth, x+abs_hLoc+nodeW/2-((nodeW+hSpacing))*(hLoc%2==0 ? 0 : 1),  y+(nodeH+vSpacing)*(depth-1)+nodeH/2);
            }
            System.out.println(n.size());
        }

        return abs_hLoc;
        
    }
}
