/** Created: Tue 17 Jun 2014 12:02 PM
 * Modified: Tue 17 Jun 2014 06:08 PM
 * @author Josh Wainwright
 * File name : QuadTree.java
 */
import java.util.ArrayList;

public class QuadTree {

	private boolean root = false;
	private int maxX;
	private int maxY;

	private QuadTree tl = new QuadTree();
	private QuadTree tr = new QuadTree();
	private QuadTree bl = new QuadTree();
	private QuadTree br = new QuadTree();
	private ArrayList<Coordinate> points;
	private boolean leaf;

	public QuadTree(int maxX, int maxY){
		this.root = true;
		this.maxX = maxX;
		this.maxY = maxY;
	}

	/** Creates a leaf node of a quadtree.
	 */
	public QuadTree() {
		this.leaf = true;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public ArrayList<Coordinate> getPoints() {
		return points;
	}

	public void addPoint(Coordinate c) {
		this.points.add(c);
	}

	public QuadTree getTL() {
		return tl;
	}

	public void setTL(QuadTree t) {
		this.tl = t;
	}

	public QuadTree getTR() {
		return tr;
	}

	public void setTR(QuadTree t) {
		this.tr = t;
	}

	public QuadTree getBL() {
		return bl;
	}

	public void setBL(QuadTree t) {
		this.bl = t;
	}

	public QuadTree getBR() {
		return br;
	}

	public void setBR(QuadTree t) {
		this.br = t;
	}

}
