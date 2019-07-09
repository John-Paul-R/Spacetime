package origin;

import java.util.List;
import java.util.LinkedList;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Point2D;
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

    //Painting
    public void paint(Graphics2D g, int x, int y)
    {
        g.setColor(new Color(255, 255, 255, 55));
        paintNode(g, x, y, root, 0, 0, 0, 0);
        
    }

    private int rowOffset(int depth, int hSpacing, int nodeW) {
        return depth*(hSpacing + nodeW)/2;
    }
    void paintNode(Graphics2D g, int x, int y, KDNode<E> n, int depth, int hLoc, double parentX, double parentY) {
        
        Point2D.Double location = drawNode(g, x, y, n, depth, hLoc, parentX, parentY);

        if (n.getLower() != null) {
            g.setColor(new Color(255, 0, 0, 55));
            paintNode(g,x,y,n.getLower(), depth+1, hLoc*2, location.getX(), location.getY());

        } else {
            drawNode(g,x,y,n.getLower(), depth+1, hLoc*2, location.getX(), location.getY());
        }
        if (n.getUpper() != null) {
            g.setColor(new Color(0, 255, 0, 55));
            paintNode(g,x,y,n.getUpper(), depth+1, hLoc*2+1, location.getX(), location.getY());//hLoc+(maxDepth)/(depth+1));

        } else {
            drawNode(g,x,y,n.getUpper(), depth+1, hLoc*2+1, location.getX(), location.getY());
        }

        
        
    }

    Point2D.Double drawNode(Graphics2D g, int x, int y, KDNode<E> n, int depth, int hLoc, double parentX, double parentY) {
        final int hSpacing = 1; //18/4;
        final int vSpacing = 18/4;
        final int nodeH = 40/4;
        final int nodeW = 36/9;

        int abs_hLoc = hLoc*(nodeW+hSpacing);
        int centerOffset = ((int)Math.pow(2, depth)*(nodeW+hSpacing)/2);
        int xLoc = x+abs_hLoc -centerOffset;
        int yLoc = y+(nodeH+vSpacing)*depth;
        if (n != null)
        {
            if (n.size() == -1) {
                g.setColor(new Color(255, 255, 0, 55));
                g.fillRect(xLoc +nodeW/4,  yLoc +nodeH/4, nodeW/2, nodeH/2);
                g.drawLine(xLoc+nodeW/2, yLoc+nodeH/4, (int)(parentX+nodeW/2), (int)(parentY+nodeH/4*3));
            } else {
                g.drawRect(xLoc, yLoc, nodeW, nodeH);
                g.fillRect(xLoc, yLoc, nodeW, (int)((double)nodeH/(double)binSize*n.size()));
                g.drawLine(xLoc+nodeW/2, yLoc, (int)(parentX+nodeW/2), (int)(parentY+nodeH/4*3));
                //g.drawLine(x+abs_hLoc , yLoc, x+abs_hLoc+nodeW/2-((nodeW+hSpacing))*(hLoc%2==0 ? 0 : 1),  y+(nodeH+vSpacing)*(depth-1)+nodeH/2);
            }
            //System.out.println(n.size());
        } //else {
            //g.drawRect(xLoc, yLoc, nodeW, nodeH);
        //}
        return new Point2D.Double(xLoc, yLoc);
    }
}
