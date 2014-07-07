/** Created: Tue 17 Jun 2014 12:02 PM
 * Modified: Wed 02 Jul 2014 06:23 PM
 * @author Josh Wainwright
 * File name : QuadTree.java
 */
package quadtree.data;

import utils.Coordinate;
import quadtree.DrawQuadTree;
import utils.ClusterStructure;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class QuadTree implements ClusterStructure {

	private String filepath;
	private int colX;
	private int colY;
	private String separator;
	private boolean root = false;
	private int depth;
	private double minX;
	private double maxX;
	private double minY;
	private double maxY;
	private int maxDensity;
	private Coordinate maxCoord;

	public QuadTree tl;
	public QuadTree tr;
	public QuadTree bl;
	public QuadTree br;
	private ArrayList<Coordinate> points;
	private boolean leaf;
	private String pos;
	private String code;
	private boolean drawing;
	private int countFile = 0;

	public QuadTree(String filepath, int colX, int colY, String separator) {
		this.filepath  = filepath;
		this.colX      = colX;
		this.colY      = colY;
		this.separator = separator;
		this.drawing   = false;
		readDataFile();
	}

	/** Creates a new root node for a new quadtree.
	 *
	 * @param maxX dimension in the x-axis
	 * @param maxY dimension in the y-axis
	 * @param maxDensity maximum number of points that are allowed in a leaf
	 * node before it is split into 4 subtrees.
	 */
	public QuadTree(double maxX, double maxY, int maxDensity, String filepath,
			int colX, int colY, String separator) {
		this.root      = true;
		this.pos       = "tl";
		this.code      = "";
		this.leaf      = false;
		this.minX      = 0;
		this.maxX      = maxX;
		this.minY      = 0;
		this.maxY      = maxY;
		this.depth     = 0;
		this.filepath  = filepath;
		this.colX      = colX;
		this.colY      = colY;
		this.separator = separator;
		this.drawing   = true;

		if (maxDensity > 0) {
			this.maxDensity = maxDensity;
		} else {
			throw new IllegalArgumentException(
					"Maximum density must be greater than 0");
		}

		createSubTrees();
		readDataFile();
	}

	/** Create a new leaf node.
	 */
	private QuadTree(double minX, double minY, double maxX, double maxY,
			String code, int maxDensity, int depth, String pos) {
		this.root        = false;
		this.leaf        = true;
		this.code        = code;
		this.maxDensity  = maxDensity;
		this.depth       = depth;
		this.pos         = pos;

		this.minX        = minX;
		this.maxX        = maxX;
		this.minY        = minY;
		this.maxY        = maxY;
		this.points      = new ArrayList<Coordinate>();
	}

	/** Decide if the given point should be placed in the current node, a
	 * child of the current node, or if the current node has reached maximum
	 * capacity and must be split into sub nodes.
	 * @param c coordinate to be added to the quadtree.
	 */
	public boolean addPoint(Coordinate c) {
		if (!checkValid(c)) {
			return false;
		}

		/* If we have reached a leaf node, then c needs to be added here,
		 * otherwise, we need to find the correct sub tree to go down.*/
		if (this.leaf) {

			/* If this leaf can still take more points, then simply add it to
			 * the list of points, otherwise we need to split the list into new
			 * subtrees. */
			if (this.points.size() <= this.maxDensity) {
				this.points.add(c);
				return true;
			} else {
				deleaf();
				return addPoint(c);
			}
		}
		return newPointLocation(c).addPoint(c);
	}

	/** For a leaf node, remove all points that have been added previously,
	 * create a set of 4 new subquadtrees and add the points back in in the
	 * correct location.
	 */
	private void deleaf() {
		createSubTrees();
		this.leaf = false;

		for (Coordinate c: points) {
			addPoint(c);
		}

		/* The number of points in this node must be < maxDensity. These are no
		 * longer needed as there should not be any points at a non-leaf node
		 * anyway - remove them. */
		this.points = null;
	}

	/** Calculate which quadrant a point exists in and return a the relevant
	 * quadtree for that quadrant.
	 */
	private QuadTree newPointLocation(Coordinate c) {
		double x = c.getX();
		double y = c.getY();

		if ( (x >= minX && x <= maxX/2+minX/2) &&
			 (y >= minY && y <= maxY/2+minY/2)) {
			return tl;                             // Top Left - 0
		}
		if ( (x >= maxX/2+minX/2 && x <= maxX) &&
			 (y >= minY && y <= maxY/2+minY/2)) {
			return tr;                             // Top Right - 1
		}
		if ( (x >= minX && x <= maxX/2+minX/2) &&
			 (y >= maxY/2+minY/2 && y <= maxY)) {
			return bl;                             // Bottom Left - 2
		}
		if ( (x >= maxX/2+minX/2 && x <= maxX) &&
			 (y >= maxY/2+minY/2 && y <= maxY)) {
			return br;                             // Bottom Right - 3
		}

		throw new IllegalArgumentException("Don't know where to place point");
	}

	/** This node has been newly created and requires 4 subquadtrees. Create
	 * them with the correct limits based on the limits of the parent.
	 */
	private void createSubTrees() {
		String[] newCode = new String[4];
		if (pos.equals("tl") ) {
			newCode[0] = code+"00";
			newCode[1] = code+"01";
			newCode[2] = code+"10";
			newCode[3] = code+"11";

		} else if (pos.equals("tr")) {
			newCode[0] = code+"01";
			newCode[1] = code+"00";
			newCode[2] = code+"11";
			newCode[3] = code+"10";

		} else if (pos.equals("bl")) {
			newCode[0] = code+"10";
			newCode[1] = code+"11";
			newCode[2] = code+"00";
			newCode[3] = code+"01";

		} else if (pos.equals("br")){
			newCode[0] = code+"11";
			newCode[1] = code+"10";
			newCode[2] = code+"01";
			newCode[3] = code+"00";

		} else {
			System.exit(1);
		}
		this.tl=new QuadTree(
				minX, minY, maxX/2+minX/2, maxY/2+minY/2, newCode[0], maxDensity, depth+1, "tl");
		this.tr=new QuadTree(
				maxX/2+minX/2, minY, maxX, maxY/2+minY/2, newCode[1], maxDensity, depth+1, "tr");
		this.bl=new QuadTree(
				minX, maxY/2+minY/2, maxX/2+minX/2, maxY, newCode[2], maxDensity, depth+1, "bl");
		this.br=new QuadTree(
				maxX/2+minX/2, maxY/2+minY/2, maxX, maxY, newCode[3], maxDensity, depth+1, "br");
	}

	/** Checks that a coordinate is valid, ie exists in the quadtree-space of
	 * the current quadtree.
	 */
	private boolean checkValid(Coordinate c) {
		double x = c.getX();
		double y = c.getY();
		if (y < 0 || y > maxY || x < 0 || x > maxX) return false;
		return true;
	}

	public boolean isLeaf() { return leaf; }
	public ArrayList<Coordinate> getPoints() { return points; }

	public String getCode() {
		// String codeString = Integer.toBinaryString(code & 0b11);
		// if (codeString.length() < 2) { codeString = 0 + codeString; }

		// for (int i = 1; i < depth; i++) {
		// 	String tempString = Integer.toBinaryString((code >> (2*i)) & 0b11);
		// 	if (tempString.length() < 2) { tempString = 0 + tempString; }
		// 	codeString += tempString;
		// }
		// System.out.println("depth: " + depth + ", code: " + Integer.toBinaryString(code) + ", codeString: " + codeString);
		// return codeString;
		return code;
	}

	public QuadTree getTL() { return tl; }
	public QuadTree getTR() { return tr; }
	public QuadTree getBL() { return bl; }
	public QuadTree getBR() { return br; }
	public double getMinX() { return minX; }
	public double getMaxX() { return maxX; }
	public double getMinY() { return minY; }
	public double getMaxY() { return maxY; }

	@Override
	public String toString() {
		if (leaf) {
			return code + "" ;
		} else {
			return "[" + tl.toString() + ", " + tr.toString() + ", "
				+ bl.toString() + ", " + br.toString() + "]";
		}
	}

	public void draw(boolean incLines, boolean incPoints, double scaleFactor) {
		System.out.println(filepath);
		DrawQuadTree d = new DrawQuadTree(this, scaleFactor);
		d.draw(incLines, incPoints);
	}

	/** Reads the given data file and interprets the contents as coordinates.
	 * Each coordinate is added to the quadtree.
	 */
	private boolean readDataFile() {
		maxCoord = new Coordinate(0,0);
		if (!filepath.equals("")) {
			BufferedReader reader = null;

			try {
				reader = new BufferedReader(new FileReader(filepath));
				String line = null;

				reader.readLine();
				while ((line = reader.readLine()) != null) {

					String[] entries = line.split(separator);
					double[] xyDouble = new double[2];
					xyDouble[0] = Double.parseDouble(entries[colX]);
					xyDouble[1] = Double.parseDouble(entries[colY]);

					if (xyDouble[0] > maxCoord.getX()) {
						maxCoord.setX(xyDouble[0]);
						System.out.println("Max x: " + xyDouble[0]);
					}
					if (xyDouble[1] > maxCoord.getY()) {
						maxCoord.setY(xyDouble[1]);
						System.out.println("Max y: " + xyDouble[1]);
					}

					if (drawing) {
						addPoint(new Coordinate(xyDouble[0], xyDouble[1]));
					}
					countFile++;
				}
				System.out.println("Total read from file: " + countFile);
			} catch (IOException e) {
				System.err.println("Error: Could not open file " + filepath);
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch(IOException e){
						e.printStackTrace();
					}
				}
			}
		}
		return true;
	}

	public double getMaxCoordX() { return maxCoord.getX(); }
	public double getMaxCoordY() { return maxCoord.getY(); }
	public int getCountFile() { return countFile; }
	public String getFilepath() { return filepath; }

}
