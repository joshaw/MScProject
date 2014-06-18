/** Created: Tue 17 Jun 2014 12:02 PM
 * Modified: Wed 18 Jun 2014 11:59 AM
 * @author Josh Wainwright
 * File name : QuadTree.java
 */
import java.util.ArrayList;

public class QuadTree {

	private boolean root = false;
	private int minX;
	private int maxX;
	private int minY;
	private int maxY;
	private int maxDensity;

	private QuadTree tl;
	private QuadTree tr;
	private QuadTree bl;
	private QuadTree br;
	private ArrayList<Coordinate> points;
	private boolean leaf;
	private int depth;

	/** Creates a new root node for a new quadtree.
	 */
	public QuadTree(int maxX, int maxY, int maxDensity){
		this.root    = true;
		this.depth   = 0;
		this.leaf    = false;
		this.maxDensity = maxDensity;
		this.minX    = 0;
		this.maxX    = maxX;
		this.minY    = 0;
		this.maxY    = maxY;
		createSubTrees();
	}

	private QuadTree(int minX, int maxX, int minY, int maxY, int depth,
			int maxDensity) {
		this.root   = false;
		this.leaf   = true;
		this.depth  = depth + 1;
		this.maxDensity = maxDensity;
		this.minX   = minX;
		this.maxX   = maxX;
		this.minY   = minY;
		this.maxY   = maxY;
		this.points = new ArrayList<Coordinate>();
	}

	public boolean addPoint(Coordinate c) {
		checkValid(c);
		if (this.leaf) {
			System.out.println("Points: " + this.points.size() + " maxDensity " + this.maxDensity);
			if (this.points.size() <= this.maxDensity) {
				System.out.println("AddPoint " + c + " minX: " + minX + ", minY: " + minY
						+ ", maxX: " + maxX + ", maxY: " + maxY);
				this.points.add(c);
				return true;
			} else {
				System.out.println("Deleafing .............");
				deleaf();
			}
		}
		return newPointLocation(c).addPoint(c);
	}

	private void deleaf() {
		createSubTrees();
		this.leaf = false;

		for (Coordinate c: points) {
			addPoint(c);
		}
	}

	private QuadTree newPointLocation(Coordinate c) {
		System.out.println("NewLocation " + c + " minX: " + minX + ", minY: " + minY
				+ ", maxX: " + maxX + ", maxY: " + maxY);
		int x = c.getX();
		int y = c.getY();
		if ( (x >= minX && x <= maxX/2) && (y >= minY && y <= maxY/2)) {
			System.out.println("return 0");
			return tl;
		}
		if ( (x >= maxX/2 && x <= maxX) && (y >= minY && y <= maxY/2)) {
			System.out.println("return 1");
			return tr;
		}
		if ( (x >= minX && x <= maxX/2) && (y >= maxY/2 && y <= maxY)) {
			System.out.println("return 2");
			return bl;
		}
		if ( (x >= maxX/2 && x <= maxX) && (y >= maxY/2 && y <= maxY)) {
			System.out.println("return 3");
			return br;
		}

		throw new IllegalArgumentException("Don't know where to place point");
	}

	private void createSubTrees() {
		this.tl = new QuadTree(minX, maxX/2, minY, maxY/2, depth, maxDensity);
		this.tr = new QuadTree(maxX/2, maxX, minY, maxY/2, depth, maxDensity);
		this.bl = new QuadTree(minX, maxX/2, maxY/2, maxY, depth, maxDensity);
		this.br = new QuadTree(maxX/2, maxX, maxY/2, maxY, depth, maxDensity);
	}

	private boolean checkValid(Coordinate c) {
		int x = c.getX();
		int y = c.getY();
		if (y < 0)    throw new IllegalArgumentException("y too small");
		if (y > maxY) throw new IllegalArgumentException("y too large");
		if (x < 0)    throw new IllegalArgumentException("x too small");
		if (x > maxX) throw new IllegalArgumentException("x too large");
		return true;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public ArrayList<Coordinate> getPoints() {
		return points;
	}

	public QuadTree getTL() { return tl; }
	public QuadTree getTR() { return tr; }
	public QuadTree getBL() { return bl; }
	public QuadTree getBR() { return br; }
	public int getMinX() { return minX; }
	public int getMaxX() { return maxX; }
	public int getMinY() { return minY; }
	public int getMaxY() { return maxY; }

	@Override
	public String toString() {
		if (leaf) {
			return points.toString();
		} else {
			return "[" + tl.toString() + ", " + tr.toString() + ", "
				+ bl.toString() + ", " + br.toString() + "]";
		}
	}

	/* Method to create a graphical representation of a quadtree using the
	 * Graphviz "dot" program. Either write the generate code to a file and
	 * read the file with the dot program or write to stdout and pipe.
	 *
	 * javac testQuadTree | dot -Tps -o graph.ps
	 */
	public String toDot() {
		String result = "digraph g {\n";
		result += toDot("t");
		return result + "}";
	}

	private String toDot(String s) {
		String result = "";
		if (!this.isLeaf()) {
			result += s + " [label=\"X\\n\\N\"]\n";
			result += s + "0 [label=\"" + this.getTL().getPoints().get(0) + "\\n\\N\"]\n";
			result += s + "->" + s + "0\n";
			result += this.getTL().toDot(s+"0");
			result += s + "1 [label=\"" + this.getTR().getPoints().get(0) + "\\n\\N\"]\n";
			result += s + "->" + s + "1\n";
			result += this.getTR().toDot(s+"1");
			result += s + "2 [label=\"" + this.getBL().getPoints().get(0) + "\\n\\N\"]\n";
			result += s + "->" + s + "2\n";
			result += this.getBL().toDot(s+"2");
			result += s + "3 [label=\"" + this.getBR().getPoints().get(0) + "\\n\\N\"]\n";
			result += s + "->" + s + "3\n";
			result += this.getBR().toDot(s+"3");
		}
		return result;
  }

}
