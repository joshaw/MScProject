/** Created: Fri 11 Jul 2014 12:28 PM
 * Modified: Wed 23 Jul 2014 03:55 PM
 * @author Josh Wainwright
 * File name : QuadTreePropagate.java
 */
package quadtree.propagation;

import utils.Coordinate;
import utils.Sutils;
import utils.PropogationDatum;

import java.util.HashMap;
import java.util.ArrayList;

public class QuadTreePropagate {

	private HashMap<String, PropogationDatum> hashmap;
	private String start;
	private final int depthRange = 2*2;

	public QuadTreePropagate(HashMap<String, PropogationDatum> hashmap){
		this.hashmap = hashmap;
		propagate();
	}

	private String getStart() {
		// boolean cont = true;
		// String s = "00";
		// while (cont) {
		// 	if (hashmap.containsKey(s)) {
		// 		cont = false;
		// 	} else {
		// 		s += "00";
		// 	}
		// }

		int lmax = 4;
		String smax = "";
		for (String node : hashmap.keySet()) {
			if (node.length() > lmax && hashmap.get(node).status() == 0) {
				lmax = node.length();
				smax = node;
			}
		}

		return smax;
	}

	private void propagate() {
		for (int k = 1; k < 40; k++) {
			this.start = getStart();
			System.out.println("Start " + k + ": " + start);
			if (start == "") {
				return;
			}
			propagate(start, k);
		}
	}

	private void propagate(String cell, int k) {
		ArrayList<String> neighbours = getNeighbours(cell);

		for (String c : neighbours) {

			if (c != null && hashmap.get(c).status() == 0) {
				// System.out.println("Hit: " + c);
				hashmap.get(c).setStatus((byte)k);
				propagate(c, k);
			}
		}
		// propagate();
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

	public ArrayList<String> getNeighbours(String code) {
		Coordinate c = getCoordinate(code);
		ArrayList<String> neighbours = new ArrayList<String>();
		int cl = code.length();

		neighbours.add(getCode(new Coordinate(c.getX()-1, c.getY()), cl));
		neighbours.add(getCode(new Coordinate(c.getX(), c.getY()-1), cl));
		neighbours.add(getCode(new Coordinate(c.getX()+1, c.getY()), cl));
		neighbours.add(getCode(new Coordinate(c.getX(), c.getY()+1), cl));

		neighbours.add(getCode(new Coordinate(c.getX()-1, c.getY()-1), cl));
		neighbours.add(getCode(new Coordinate(c.getX()-1, c.getY()+1), cl));
		neighbours.add(getCode(new Coordinate(c.getX()+1, c.getY()-1), cl));
		neighbours.add(getCode(new Coordinate(c.getX()+1, c.getY()+1), cl));

		int numNeighbours = neighbours.size();

		for (int i = 0; i < numNeighbours; i++) {

			String s = neighbours.get(i);
			// System.out.println("s: " + s);

			if (s != null) {

				// Check up the tree for valid neighbours
				while (s != null &&
						start.length()-s.length() < depthRange &&
						s.length() >= 4) {

					if (hashmap.containsKey(s)) {
						// System.out.println("Add: " + s);
						neighbours.add(s);
						break;
					} else {
						s = s.substring(0, s.length()-2);
						// System.out.println("Sub: " + s);
					}
						}

				// Check down the tree for valid neighbours
				s = neighbours.get(i);
				neighbours.addAll(addSuffixes(s));
			}

			if (!hashmap.containsKey(neighbours.get(i))) {
				neighbours.set(i, null);
			}
		}

		return neighbours;
	}

	private ArrayList<String> addSuffixes(String code) {
		String[] suffixes = {"00", "01", "10", "11"};
		ArrayList<String> codesWithSuff = new ArrayList<String>();
		ArrayList<String> codesWithSuffTmp = new ArrayList<String>();
		codesWithSuff.add(code);

		for (int i = 1; i < start.length()/2; i++) {
			for (String s : codesWithSuff) {
				for (String suff : suffixes) {
					if (hashmap.containsKey(s+suff)) {
						codesWithSuffTmp.add(s+suff);
					}
				}
			}
			codesWithSuff.addAll(codesWithSuffTmp);
		}

		if (!hashmap.containsKey(code)) {
			codesWithSuff.remove(0);
		}
		return codesWithSuff;
	}
}
