/** Created: Tue 17 Jun 2014 12:02 PM
 * Modified: Thu 07 Aug 2014 12:24 PM
 * @author Josh Wainwright
 * File name : QuadTreeNode.java
 */
package clusterstructure.quadtree;

import clusterstructure.quadtree.DrawQuadTree;
import clusterstructure.quadtree.QuadTreeMap;
import utils.Coordinate;
import utils.Sutils;
import utils.PropogationDatum;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class QuadTreeNode {

	private final String ORDER     = "morton";
	private int    maxDepth;

	private boolean root = false;
	private int     depth;
	private double  minX;
	private double  maxX;
	private double  minY;
	private double  maxY;
	private int     maxDensity;

	private QuadTreeNode    tl;
	private QuadTreeNode    tr;
	private QuadTreeNode    bl;
	private QuadTreeNode    br;
	private Set<Coordinate> points;
	private boolean         leaf;
	private String          pos;
	private String          code = "";

	/** Creates a new root node for a new quadtree.
	 *
	 * @param maxX dimension in the x-axis
	 * @param maxY dimension in the y-axis
	 * @param maxDensity maximum number of points that are allowed in a leaf
	 * node before it is split into 4 subtrees.
	 */
	public QuadTreeNode(double maxX, double maxY, int maxDensity, int maxDepth) {
		this.root  = true;
		this.pos   = "tl";
		this.code  = "";
		this.leaf  = false;
		this.minX  = 0;
		this.maxX  = maxX;
		this.minY  = 0;
		this.maxY  = maxY;
		this.depth = 0;
		this.maxDepth = maxDepth;

		if (maxDensity > 0) {
			this.maxDensity = maxDensity;
		} else {
			throw new IllegalArgumentException(
					"Maximum density must be greater than 0");
		}

		createSubTrees();
	}

	/** Create a new leaf node.
	 */
	private QuadTreeNode(double minX, double minY, double maxX, double maxY,
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

	private QuadTreeNode() {
		this.leaf = true;
	}

	/** Decide if the given point should be placed in the current node, a
	 * child of the current node, or if the current node has reached maximum
	 * capacity and must be split into sub nodes.
	 * @param c coordinate to be added to the quadtree.
	 */
	public boolean addPoint(Coordinate c) {
		if (!c.checkValid(maxX, maxY)) {
			return false;
		}

		/* If we have reached a leaf node, then c needs to be added here,
		 * otherwise, we need to find the correct sub tree to go down.*/
		if (leaf) {

			/* If this leaf can still take more points, then simply add it to
			 * the list of points, otherwise we need to split the list into new
			 * subtrees. */
			if (points.size() <= maxDensity || depth >= MAX_DEPTH) {
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
	private QuadTreeNode newPointLocation(Coordinate c) {
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
		this.tl=new QuadTreeNode(
				minX, minY, maxX/2+minX/2, maxY/2+minY/2, newCode[0],
				maxDensity, depth, "tl");
		this.tr=new QuadTreeNode(
				maxX/2+minX/2, minY, maxX, maxY/2+minY/2, newCode[1],
				maxDensity, depth, "tr");
		this.bl=new QuadTreeNode(
				minX, maxY/2+minY/2, maxX/2+minX/2, maxY, newCode[2],
				maxDensity, depth, "bl");
		this.br=new QuadTreeNode(
				maxX/2+minX/2, maxY/2+minY/2, maxX, maxY, newCode[3],
				maxDensity, depth, "br");
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
	protected double getMaxNumberOfLeaves() {
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
	public QuadTreeNode getTL()     { return tl; }
	public QuadTreeNode getTR()     { return tr; }
	public QuadTreeNode getBL()     { return bl; }
	public QuadTreeNode getBR()     { return br; }
	public double getMinX()     { return minX; }
	public double getMaxX()     { return maxX; }
	public double getMinY()     { return minY; }
	public double getMaxY()     { return maxY; }

	protected QuadTreeMap toQuadTreeMap(QuadTreeMap qtm) {

		if (leaf) {
			qtm.put(this.code, new PropogationDatum(this.points, 0));
		} else {
			tl.toQuadTreeMap(qtm);
			tr.toQuadTreeMap(qtm);
			bl.toQuadTreeMap(qtm);
			br.toQuadTreeMap(qtm);
		}
		return qtm;
	}

	/** Returns the quadtree with the code given. If a leaf node exists with a
	 * code which is a prefix of the given code, then that is returned. If the
	 * code cannot be found, then a new, empty tree is returned.
	 *
	 * @param findCode Code string to locate in the current tree
	 * @return the child node with the code given.
	 */
	protected QuadTreeNode getNode(String findCode) {
		if (leaf || findCode.equals(code)) {
			return this;
		} else {
			try {
				String nibble = findCode.substring(0,2);
				if (nibble.equals("00")) {
					return tl.getNode(findCode.substring(2));

				} else if (nibble.equals("01")) {
					return tr.getNode(findCode.substring(2));

				} else if (nibble.equals("10")){
					return bl.getNode(findCode.substring(2));

				} else if (nibble.equals("11")){
					return br.getNode(findCode.substring(2));

				} else {
					throw new IllegalArgumentException("Something went wrong.");
				}
			} catch(StringIndexOutOfBoundsException e){
				return this;
			} catch(NullPointerException e){
				return new QuadTreeNode();
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

}
