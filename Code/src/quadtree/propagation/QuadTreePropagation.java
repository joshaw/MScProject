/** Created: Fri 11 Jul 2014 12:28 PM
 * Modified: Fri 11 Jul 2014 12:28 PM
 * @author Josh Wainwright
 * File name : QuadTreePropagation.java
 */
package quadtree.propagation;

import utils.Coordinate;
import utils.Sutils;
import utils.PropogationDatum;

import java.util.HashMap;

public class QuadTreePropagation {

	private HashMap<String, PropogationDatum> hashmap;
	private final String start;
	private final int depthRange = 0;
	private int searches = 0;

	public QuadTreePropagation(HashMap<String, PropogationDatum> hashmap){
		this.hashmap = hashmap;
		this.start = getStart();
		System.out.println("Start: " + start);
	}

	private String getStart() {
		boolean cont = true;
		String s = "00";
		while (cont) {
			if (hashmap.containsKey(s)) {
				cont = false;
			} else {
				s += "00";
			}
		}
		return s;
	}

	public void propagate(String cell) {
		String[] neighbours = getNeighbours(cell);
		int nulls = 0;
		for (String c : neighbours) {
			if (c != null) {
				searches++;
				propagate(c);
			} else {
				nulls++;
			}
		}

		if (nulls == 4) {
			searches--;
		}

		while (searches > 0) {

		}
	}

	/** Returns true if the two nodes are adjacent.
	 * @param qt1 code for first quadtree
	 * @param qt2 code for second quadtree
	 * @param dRange depth range to check for neighbours. If the dRange is
	 * zero, then the two cells must be neighbours on the same level. For
	 * dRange of 1, the cells can be 1 level apart and still be considered
	 * neighbours.
	 */
	public static boolean adjacent(String qt1, String qt2, int dRange) {
		System.out.println("1: " + qt1 + ", 2: " + qt2 + ",  R: " + dRange);

		// These are the same node.
		if (qt1.equals(qt2)) {
			return true;
		}

		// One location is inside the other.
		if (qt1.startsWith(qt2) || qt2.startsWith(qt1)) {
			return true;
		}

		if (dRange == 0 || qt1.length() == qt2.length()) {
			Coordinate qt1Coord = getCoordinate(qt1);
			Coordinate qt2Coord = getCoordinate(qt2);
			double qt1x = qt1Coord.getX();
			double qt1y = qt1Coord.getY();
			double qt2x = qt2Coord.getX();
			double qt2y = qt2Coord.getY();

			/* If the two coordinates are the same in the x-axis and differ by
			 * 1 in the y-axis, or are the same in the y-axis and differ by 1
			 * in the x-axis, then they are adjacent. */
			return ((qt1x == qt2x) && (Math.abs(qt1y-qt2y) == 1) ) ||
				  ( (qt1y == qt2y) && (Math.abs(qt1x-qt2x) == 1));
		}

		String sqt = Sutils.shortest(qt1,qt2);
		String lqt = Sutils.longest(qt1, qt2);
		lqt = Sutils.longest(qt1, qt2).substring(0, lqt.length()-2);

		return adjacent(sqt, lqt, dRange-1);
	}

	/** Returns true if the two nodes are adjacent.
	 */
	public static boolean adjacent(String qt1, String qt2) {
		return adjacent(qt1, qt2, 0);
	}

	/** Produces the decimal coordinates of the code specifying a single
	 * quadtree element using Morton's order.
	 *
	 * @param code node code of a single element in base 4.
	 */
	public static Coordinate getCoordinate(String code) {

		/* For the bits in the code, de-interleave the row and column bits, row
		 * first. This gives the coordinates in binary, then convert to decimal
		 * to give quadtree coordinate of node. */
		short count = 0;
		String rowBits = "";
		String columnBits = "";
		for (char c: code.toCharArray()) {
			if (count%2 == 0) {
				// Column Bits
				columnBits += c;
			} else {
				// Row Bits
				rowBits += c;
			}
			count++;
		}

		int row    = Integer.parseInt(rowBits, 2);
		int column = Integer.parseInt(columnBits, 2);

		return new Coordinate(row,column);
	}

	public static String getCode(Coordinate coord, int codelength) {

		if (coord.getX() < 0 || coord.getY() < 0) {
			return null;
		}

		String x = Integer.toBinaryString((int) coord.getX());
		String y = Integer.toBinaryString((int) coord.getY());

		while (x.length() != y.length()) {
			if (x.length() < y.length()) {
				x = 0 + x;
			} else {
				y = 0 + y;
			}
		}

		String code = Sutils.interleave(y, x);

		if (code.length() > codelength) {
			return null;
		}

		while (code.length() < codelength) {
			code = 0 + code;
		}

		return code;
	}

	public String[] getNeighbours(String code) {
		Coordinate c = getCoordinate(code);
		String[] neighbours = new String[4];
		int cl = code.length();

		neighbours[0] = getCode(new Coordinate(c.getX()-1, c.getY()), cl);
		neighbours[1] = getCode(new Coordinate(c.getX(), c.getY()-1), cl);
		neighbours[2] = getCode(new Coordinate(c.getX()+1, c.getY()), cl);
		neighbours[3] = getCode(new Coordinate(c.getX(), c.getY()+1), cl);

		for (int i = 0; i < 4; i++) {
			if (! hashmap.containsKey(neighbours[i])) {
				neighbours[i] = null;
			}
		}

		return neighbours;
	}

}
