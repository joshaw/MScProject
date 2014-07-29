/** Created: Tue 17 Jun 2014 12:02 PM
 * Modified: Wed 23 Jul 2014 05:45 PM
 * @author Josh Wainwright
 * File name : QuadTree.java
 */
package quadtree;

import quadtree.DrawQuadTree;
import quadtree.QuadTreeMap;
import utils.Coordinate;
import utils.ClusterStructure;
import utils.FileDescriptor;
import utils.Sutils;
import utils.PropogationDatum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Map.Entry;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class QuadTree implements ClusterStructure {

	private final String ORDER = "morton";

	private String filepath;
	private boolean root = false;
	private FileDescriptor fileDesc;
	private int depth;
	private double minX;
	private double maxX;
	private double minY;
	private double maxY;
	private int maxDensity;

	private QuadTree tl;
	private QuadTree tr;
	private QuadTree bl;
	private QuadTree br;
	private Set<Coordinate> points;
	private boolean leaf;
	private String pos;
	private String code = "";
	private boolean drawing;
	private int countFile = 0;

	private QuadTreeMap hashmap;

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
		this.fileDesc = new FileDescriptor(colX, colY, separator);
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
		this.depth       = depth + 1;
		this.pos         = pos;
		this.minX        = minX;
		this.maxX        = maxX;
		this.minY        = minY;
		this.maxY        = maxY;
		this.points      = new HashSet<Coordinate>();
	}

	private QuadTree() {
		this.leaf = true;
	}

	/** Decide if the given point should be placed in the current node, a
	 * child of the current node, or if the current node has reached maximum
	 * capacity and must be split into sub nodes.
	 * @param c coordinate to be added to the quadtree.
	 */
	public boolean addPoint(Coordinate c) {
		if (!Coordinate.checkValid(c, maxX, maxY)) {
			return false;
		}

		/* If we have reached a leaf node, then c needs to be added here,
		 * otherwise, we need to find the correct sub tree to go down.*/
		if (leaf) {

			/* If this leaf can still take more points, then simply add it to
			 * the list of points, otherwise we need to split the list into new
			 * subtrees. */
			if (points.size() <= maxDensity || depth >= 9) {
				points.add(c);
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
		leaf = false;

		for (Coordinate c: points) {
			addPoint(c);
		}

		/* The number of points in this node must be < maxDensity. These are no
		 * longer needed as there should not be any points at a non-leaf node
		 * anyway - remove them. */
		points = null;
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
		if (ORDER.equals("modgray")) {
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
		} else if (ORDER.equals("morton")) {
				newCode[0] = code+"00";
				newCode[1] = code+"01";
				newCode[2] = code+"10";
				newCode[3] = code+"11";

		}
		this.tl=new QuadTree(
				minX, minY, maxX/2+minX/2, maxY/2+minY/2, newCode[0],
				maxDensity, depth, "tl");
		this.tr=new QuadTree(
				maxX/2+minX/2, minY, maxX, maxY/2+minY/2, newCode[1],
				maxDensity, depth, "tr");
		this.bl=new QuadTree(
				minX, maxY/2+minY/2, maxX/2+minX/2, maxY, newCode[2],
				maxDensity, depth, "bl");
		this.br=new QuadTree(
				maxX/2+minX/2, maxY/2+minY/2, maxX, maxY, newCode[3],
				maxDensity, depth, "br");
	}

	/** Returns a HashMap representation of the current quadtree. This can
	 * often be faster to access multiple entries from than the quadtree.
	 *
	 * The key is the code for the leaf node, the value is the arraylist of the
	 * points that were added to the quadtree.
	 * @return HashMap representation of this quadtree.
	 */
	public QuadTreeMap toQuadTreeMap() {
		int maxLeaves = (int) getMaxNumberOfLeaves();

		QuadTreeMap qtm = new QuadTreeMap(maxX, maxY);

		return toQuadTreeMap(qtm);
	}
	private QuadTreeMap toQuadTreeMap(QuadTreeMap qtm) {

		if (leaf) {
			qtm.put(this.code, new PropogationDatum(this.points, (byte)0));
		} else {
			tl.toQuadTreeMap(qtm);
			tr.toQuadTreeMap(qtm);
			bl.toQuadTreeMap(qtm);
			br.toQuadTreeMap(qtm);
		}
		return qtm;
	}

	public void addQuadTreeMap() {
		this.hashmap = toQuadTreeMap();
	}

	public QuadTreeMap getQuadTreeMap() {
		return hashmap;
	}

	/** Returns the maximum depth of a child node in the quadtree.
	 */
	public int getDepth() {
		return getDepth(0);
	}
	private int getDepth(int d) {
		if (leaf) {
			return Math.max(this.depth, d);
		}

		return Sutils.max(tl.getDepth(d), tr.getDepth(d),
						  bl.getDepth(d), br.getDepth(d));
	}

	/** Calculates the maximum number of leaf nodes that would be in the
	 * quadtree if the quadtree is complete.
	 */
	private double getMaxNumberOfLeaves() {
		return Math.pow(4, code.length()/2);
	}

	public String getCode() {
		// String codeString = Integer.toBinaryString(code & 0b11);
		// if (codeString.length() < 2) { codeString = 0 + codeString; }

		// for (int i = 1; i < depth; i++) {
		// 	String tempString = Integer.toBinaryString((code >> (2*i)) & 0b11);
		// 	if (tempString.length() < 2) { tempString = 0 + tempString; }
		// 	codeString += tempString;
		// }
		// return codeString;
		return code;
	}

	public Set<Coordinate> getPoints() { return points; }
	public boolean isLeaf()     { return leaf; }
	public QuadTree getTL()     { return tl; }
	public QuadTree getTR()     { return tr; }
	public QuadTree getBL()     { return bl; }
	public QuadTree getBR()     { return br; }
	public double getMinX()     { return minX; }
	public double getMaxX()     { return maxX; }
	public double getMinY()     { return minY; }
	public double getMaxY()     { return maxY; }
	public int getCountFile()   { return countFile; }
	public String getFilepath() { return filepath; }

	public static String toString(HashMap<String, PropogationDatum> hm) {

		StringBuilder sb = new StringBuilder();

		for(Entry<String, PropogationDatum> leaf : hm.entrySet()) {
			String key = leaf.getKey();
			PropogationDatum value = leaf.getValue();
			sb.append(key + " (" + value.size() + "), ");
		}

		return sb.toString();
	}

	/** Returns the quadtree with the code given. If a leaf node exists with a
	 * code which is a prefix of the given code, then that is returned. If the
	 * code cannot be found, then a new, empty tree is returned.
	 *
	 * @param findCode Code string to locate in the current tree
	 * @return the child node with the code given.
	 */
	public QuadTree getNode(String findCode) {
		return getNode(findCode, findCode);
	}
	private QuadTree getNode(String findCode, String orig) {
		if (leaf || findCode.equals(code)) {
			return this;
		} else {
			try {
				String nibble = findCode.substring(0,2);
				if (nibble.equals("00")) {
					return tl.getNode(findCode.substring(2), orig);

				} else if (nibble.equals("01")) {
					return tr.getNode(findCode.substring(2), orig);

				} else if (nibble.equals("10")){
					return bl.getNode(findCode.substring(2), orig);

				} else if (nibble.equals("11")){
					return br.getNode(findCode.substring(2), orig);

				} else {
					throw new IllegalArgumentException("Something went wrong.");
				}
			} catch(StringIndexOutOfBoundsException e){
				return this;
			} catch(NullPointerException e){
				return new QuadTree();
			}
		}
	}

	/** Creates a list of the codes of the nodes that are children of the
	 * current node.
	 * @return arraylist of codes of the children of this node.
	 */
	public ArrayList<String> getAllChildrenCodes() {
		ArrayList<String> childCodes = new ArrayList<String>();

		if (leaf) {
			if (code != "") {
				childCodes.add(code);
			}
			return childCodes;
		} else {
			childCodes.addAll(tl.getAllChildrenCodes());
			childCodes.addAll(tr.getAllChildrenCodes());
			childCodes.addAll(bl.getAllChildrenCodes());
			childCodes.addAll(br.getAllChildrenCodes());
		}

		return childCodes;
	}

	@Override
	public String toString() {
		if (leaf) {
			return code + "" ;
		} else {
			return "[" + tl.toString() + ", " + tr.toString() + ", "
				+ bl.toString() + ", " + br.toString() + "]";
		}
	}

	/** Draw the current tree to the screen.
	 */
	public void draw(boolean incLines, boolean incPoints, double scaleFactor) {
		System.out.println(filepath);
		DrawQuadTree d = new DrawQuadTree(this, scaleFactor);
		d.draw(incLines, incPoints);
	}

	/** Reads the given data file and interprets the contents as coordinates.
	 * Each coordinate is added to the quadtree.
	 *
	 * @return true if file was read succssfully.
	 */
	public boolean readDataFile() {
		if (!filepath.equals("")) {
			BufferedReader reader = null;

			try {
				reader = new BufferedReader(new FileReader(filepath));
				String line = null;

				reader.readLine();
				while ((line = reader.readLine()) != null) {

					String[] entries = line.split(fileDesc.getSeparator());
					double[] xyDouble = new double[2];

					xyDouble[0] = Double.parseDouble(
							entries[fileDesc.getColX()]);
					xyDouble[1] = Double.parseDouble(
							entries[fileDesc.getColY()]);

					addPoint(new Coordinate(xyDouble[0], xyDouble[1]));

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

}
