/** Created: Wed 16 Jun 2014 9:02 AM
 * Modified: Fri 27 Jun 2014 10:34 AM
 * @author Josh Wainwright
 * File name : Coordinate.java
 */
package quadtree.integer;

import utils.Coordinate;

public class QuadTree<E> {

	private QuadTree<E> tl;
	private QuadTree<E> tr;
	private QuadTree<E> bl;
	private QuadTree<E> br;
	private E value;
	private boolean leaf;

	/** Creates a leaf node of a quadtree with the given value.
	 */
	public QuadTree(E value) {
		this.value = value;
		this.leaf = true;
	}

	/** Creates a quadtree which is not a leaf and so contains 4 sub-trees.
	 */
	public QuadTree(QuadTree<E> tl, QuadTree<E>tr, QuadTree<E> bl,
			QuadTree<E> br) {
		this.tl = tl;
		this.tr = tr;
		this.bl = bl;
		this.br = br;
		this.leaf = false;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public E getValue() {
		return value;
	}

	public QuadTree<E> getTL() {
		return tl;
	}

	public QuadTree<E> getTR() {
		return tr;
	}

	public QuadTree<E> getBL() {
		return bl;
	}

	public QuadTree<E> getBR() {
		return br;
	}

	// /** Returns true if the two nodes are adjacent.
	//  */
	// public static boolean adjacent(String qt1, String qt2) {

	// 	// These are the same node.
	// 	if (qt1.equals(qt2)) {
	// 		return true;
	// 	}

	// 	// One location is inside the other.
	// 	if (qt1.contains(qt2) || qt2.contains(qt1)) {
	// 		return true;
	// 	}

	// 	Coordinate qt1Coord = getCoordinate(qt1);
	// 	Coordinate qt2Coord = getCoordinate(qt2);
	// 	double qt1x = qt1Coord.getX();
	// 	double qt1y = qt1Coord.getY();
	// 	double qt2x = qt2Coord.getX();
	// 	double qt2y = qt2Coord.getY();

	// 	/* If the two coordinates are the same in the x-axis and differ by 1 in
	// 	 * the y-axis, or are the same in the y-axis and differ by 1 in the
	// 	 * x-axis, then they are adjacent. */
	// 	if ( ((qt1x == qt2x) && (Math.abs(qt1y-qt2y) == 1) ) ||
	// 		( (qt1y == qt2y) && (Math.abs(qt1x-qt2x) == 1)) ) {

	// 		return true;
	// 	}

	// 	return false;
	// }

	public static boolean adjacent2(String qt1, String qt2) {
		if (qt1.equals(qt2)) {
			return true;
		}

		if (qt1.startsWith(qt2) || qt2.startsWith(qt1)) {
			return true;
		}

		return false;
	}

	public static int[] getNeighbours(String code) {
		// TODO edge cases

		int[] neighbours = new int[4];

		int codeInt = Integer.parseInt(code, 2);

		// for (int i = 0; i < code.length(); i++) {
		// 	int mask = 1 << i;
		// 	neighbours[i] = codeInt ^ mask;
		// }

		neighbours[0] = codeInt ^ 0b1;
		neighbours[1] = codeInt ^ 0b10;

		// 2 LSB == 11
		if ((codeInt & 0b11) == 0b11) {
			neighbours[2] = codeInt ^ 0b100;
			neighbours[3] = codeInt ^ 0b1000;
			return neighbours;
		}

		// 2 LSB == 00
		if ((codeInt & 0b11) == 0b00) {
			int tmpCode = codeInt;
			int count = 0;
			while ((tmpCode & 0b11) != 0b11 ) {
				tmpCode = tmpCode >> 2;
				count++;
			}
			neighbours[2] = codeInt ^ (0b1 << 2*count+2);
			neighbours[3] = codeInt ^ (0b1 << 2*count+3);
			return neighbours;

		}

		// 2 LSB == 01
		if ((codeInt & 0b11) == 0b01) {
			neighbours[2] = codeInt ^ 0b100;

		}

		// 2 LSB == 10
		if ((codeInt & 0b11) == 0b10) {
			neighbours[2] = codeInt ^ 0b1000;

		}

		return neighbours;
	}

	// /** Produces the decimal coordinates of the code specifying a single
	//  * quadtree element using Morton's order.
	//  *
	//  * @param code node code of a single element in base 4.
	//  */
	// public static Coordinate getCoordinate(String code)
	// 	throws IllegalArgumentException {

	// 	/* Convert the node code from base 4 to binary. */
	// 	String bits = "";
	// 	for (char c: code.toCharArray()) {
	// 		if (c == '0')        { bits += "00";
	// 		} else if (c == '1') { bits += "01";
	// 		} else if (c == '2') { bits += "10";
	// 		} else if (c == '3') { bits += "11";
	// 		} else {
	// 			throw new IllegalArgumentException();
	// 		}
	// 	}

	// 	/* For the bits, de-interleave the row and column bits, row first. This
	// 	 * gives the coordinates in binary, then convert to decimal to give
	// 	 * quadtree coordinate of node. */
	// 	short count = 0;
	// 	String rowBits = "";
	// 	String columnBits = "";
	// 	for (char c: bits.toCharArray()) {
	// 		if (count%2 == 0) {
	// 			// Column Bits
	// 			columnBits += c;
	// 		} else {
	// 			// Row Bits
	// 			rowBits += c;
	// 		}
	// 		count++;
	// 	}

	// 	int row    = Integer.parseInt(rowBits, 2);
	// 	int column = Integer.parseInt(columnBits, 2);

	// 	return new Coordinate(row,column);
	// }

	// public static String getCode(Coordinate coord) {

	// 	if (coord.getX() < 0 || coord.getY() < 0) {
	// 		return null;
	// 	}

	// 	String code = "";
	// 	String x = Integer.toBinaryString((int) coord.getX());
	// 	String y = Integer.toBinaryString((int) coord.getY());

	// 	while (x.length() != y.length()) {
	// 		if (x.length() < y.length()) {
	// 			x = 0 + x;
	// 		} else {
	// 			y = 0 + y;
	// 		}
	// 	}

	// 	String bits = interleave(y, x);

	// 	while (bits.length() > 0) {
	// 		String couple = bits.substring(0, 2);

	// 		if (couple.equals("00"))      { code += "0"; }
	// 		else if (couple.equals("01")) { code += "1"; }
	// 		else if (couple.equals("10")) { code += "2"; }
	// 		else if (couple.equals("11")) { code += "3"; }
	// 		else {
	// 			throw new IllegalArgumentException();
	// 		}

	// 		bits = bits.substring(2);
	// 	}

	// 	return code;
	// }

	// private static String interleave(String s1, String s2) {
	// 	if (s1.length() == 0) {
	// 		return s2;
	// 	}
	// 	if (s2.length() == 0) {
	// 		return s1;
	// 	}
	// 	return "" + s1.charAt(0) + s2.charAt(0) +
	// 		interleave(s1.substring(1), s2.substring(1));
	// }

	// private static Coordinate checkCoord(Coordinate c) {
	// 	if (c.getX() < 0 || c.getY() < 0) {
	// 		return null;
	// 	}
	// 	return c;
	// }

	// /** Return the coordinates of the 4 neighbours to this node when this
	//  * node's location is given as a coordinate.      1
	//  *                                              0 a 2
	//  *                                                3
	//  */
	// public static Coordinate[] getNeighboursCoordinates(Coordinate coord) {
	// 	Coordinate[] neighbours = new Coordinate[4];

	// 	neighbours[0] = new Coordinate(coord.getX()-1, coord.getY());
	// 	neighbours[1] = new Coordinate(coord.getX(), coord.getY()-1);
	// 	neighbours[2] = new Coordinate(coord.getX()+1, coord.getY());
	// 	neighbours[3] = new Coordinate(coord.getX(), coord.getY()+1);

	// 	for (int i = 0; i < 4; i++) {
	// 		neighbours[i] = checkCoord(neighbours[i]);
	// 	}

	// 	return neighbours;
	// }

	// public static Coordinate[] getNeighboursCoordinates(String code) {
	// 	Coordinate coord = getCoordinate(code);
	// 	return getNeighboursCoordinates(coord);
	// }

	// public static String[] getNeighboursCodes(String code) {
	// 	Coordinate coord = getCoordinate(code);
	// 	String[] neighbours = new String[4];

	// 	neighbours[0] = getCode(new Coordinate(coord.getX()-1, coord.getY()));
	// 	neighbours[1] = getCode(new Coordinate(coord.getX(), coord.getY()-1));
	// 	neighbours[2] = getCode(new Coordinate(coord.getX()+1, coord.getY()));
	// 	neighbours[3] = getCode(new Coordinate(coord.getX(), coord.getY()+1));

	// 	return neighbours;
	// }

	@Override
	public String toString() {
		if (leaf) {
			return value.toString() + "";
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
			result += s + "0 [label=\"" + this.getTL().getValue() + "\\n\\N\"]\n";
			result += s + "->" + s + "0\n";
			result += this.getTL().toDot(s+"0");
			result += s + "1 [label=\"" + this.getTR().getValue() + "\\n\\N\"]\n";
			result += s + "->" + s + "1\n";
			result += this.getTR().toDot(s+"1");
			result += s + "2 [label=\"" + this.getBL().getValue() + "\\n\\N\"]\n";
			result += s + "->" + s + "2\n";
			result += this.getBL().toDot(s+"2");
			result += s + "3 [label=\"" + this.getBR().getValue() + "\\n\\N\"]\n";
			result += s + "->" + s + "3\n";
			result += this.getBR().toDot(s+"3");
		}
		return result;
  }

}
