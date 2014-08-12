/** Created: Wed 16 Jun 2014 9:02 AM
 * @author Josh Wainwright
 * File name : QuadTree.java
 */
package clusterstructure.quadtree.propagation;

import utils.Coordinate;
import utils.Sutils;

public class QuadTree {

	/** Returns true if the two nodes are adjacent.
	 * @param qt1 code for first quadtree
	 * @param qt2 code for second quadtree
	 * @param dRange depth range to check for neighbours. If the dRange is
	 * zero, then the two cells must be neighbours on the same level. For
	 * dRange of 1, the cells can be 1 level apart and still be considered
	 * neighbours.
	 */
	public static boolean adjacent(String qt1, String qt2, int dRange) {

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

	// public static int[] getNeighbours(String code) {

	// 	int[] neighbours = new int[4];

	// 	int codeInt = Integer.parseInt(code, 2);

	// 	// for (int i = 0; i < code.length(); i++) {
	// 	// 	int mask = 1 << i;
	// 	// 	neighbours[i] = codeInt ^ mask;
	// 	// }

	// 	neighbours[0] = codeInt ^ 0b1;
	// 	neighbours[1] = codeInt ^ 0b10;

	// 	// 2 LSB == 11
	// 	if ((codeInt & 0b11) == 0b11) {
	// 		neighbours[2] = codeInt ^ 0b100;
	// 		neighbours[3] = codeInt ^ 0b1000;
	// 		return neighbours;
	// 	}

	// 	// 2 LSB == 00
	// 	if ((codeInt & 0b11) == 0b00) {
	// 		int tmpCode = codeInt;
	// 		int count = 0;
	// 		while ((tmpCode & 0b11) != 0b11 ) {
	// 			tmpCode = tmpCode >> 2;
	// 			count++;
	// 		}
	// 		neighbours[2] = codeInt ^ (0b1 << 2*count+2);
	// 		neighbours[3] = codeInt ^ (0b1 << 2*count+3);
	// 		return neighbours;

	// 	}

	// 	// 2 LSB == 01
	// 	if ((codeInt & 0b11) == 0b01) {
	// 		neighbours[2] = codeInt ^ 0b100;

	// 	}

	// 	// 2 LSB == 10
	// 	if ((codeInt & 0b11) == 0b10) {
	// 		neighbours[2] = codeInt ^ 0b1000;

	// 	}

	// 	return neighbours;
	// }

	// public static int[] getNeighbours2(String code) {
	// 	int[] neighbours = new int[4];
	// 	int codeInt = Integer.parseInt(code,2);
	// 	System.out.println("Code: " + code + ", " + codeInt);

	// 	neighbours[0] = codeInt ^ 0b1;
	// 	neighbours[1] = codeInt ^ 0b10;

	// 	int[] possN = new int[code.length()-2];
	// 	for (int n = 2; n < code.length(); n++) {
	// 		int mask = 1 << n;
	// 		possN[n-2] = codeInt ^ mask;
	// 		System.out.println("\tPoss: " + possN[n-2]);
	// 	}

	// 	int[] def1Neighbours = new int[code.length()-2];
	// 	for (int n = 2; n < code.length(); n++) {
	// 		int mask = 1 << n;
	// 		def1Neighbours[n-2] = neighbours[0] ^ mask;
	// 		System.out.println("\tDef : " + def1Neighbours[n-2]);
	// 	}

	// 	for (int i = 0; i < code.length()-2; i++) {

	// 		int poss1 = possN[i];
	// 		int[] poss1Neighbours = new int[code.length()];
	// 		for (int n = 0; n < code.length(); n++) {
	// 			int mask = 1 << n;
	// 			poss1Neighbours[n] = poss1 ^ mask;
	// 		}

	// 		for (int d : def1Neighbours) {
	// 			for (int p : poss1Neighbours) {
	// 				System.out.println(poss1 + ": " + d + "\t" + p);

	// 				if (p != codeInt && (d == p)) {
	// 					System.out.println("\t\t\tMatch: " + p);
	// 				}
	// 			}
	// 		}

			// for (int d = 0; d < code.length()-2; d++) {
			// 	for (int p = 0; p < code.length()-2; p++) {

			// 		System.out.println(def1Neighbours[d] + "\t" + poss1Neighbours[p]);

			// 		if (poss1Neighbours[p] != codeInt &&
			// 				(def1Neighbours[d] == poss1Neighbours[p])) {
			// 			System.out.println("\t\t\tMatch: " + poss1Neighbours[p]);
			// 		}
			// 	}
			// }
		// }

		// for (int i = 0; i < code.length()-2; i++) {

		// 	int poss1 = possN[i];
		// 	int[] poss1Neighbours = new int[code.length()-2];
		// 	for (int n = 2; n < code.length(); n++) {
		// 		int mask = 1 << n;
		// 		poss1Neighbours[n-2] = poss1 ^ mask;
		// 	}

		// 	for (int j = i+1; j < code.length()-2; j++) {

		// 		int poss2 = possN[j];
		// 		int[] poss2Neighbours = new int[code.length()-2];
		// 		System.out.println("\t\t" + poss1 + "\t" + poss2 + "##########");
		// 		for (int n = 2; n < code.length(); n++) {
		// 			int mask = 1 << n;
		// 			poss2Neighbours[n-2] = poss2 ^ mask;
		// 			System.out.println("\t\t" + poss1Neighbours[n-2] + "\t" + poss2Neighbours[n-2]);
		// 		}

		// 		for (int p1 = 0; p1 < code.length()-2; p1++) {
		// 			for (int p2 = 0; p2 < code.length()-2; p2++) {

		// 				// System.out.println(poss1 + ", " + poss2);
		// 				if (poss1Neighbours[p1] != codeInt &&
		// 						(poss1Neighbours[p1] == poss2Neighbours[p2])) {
		// 					System.out.println("\t\t\tMatch: " + poss1Neighbours[p1]);
		// 				}

		// 			}
		// 		}

		// 	}
		// }
		// return new int[1];
	// }

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

	/** Return the coordinates of the 4 neighbours to this node when this
	 * node's location is given as a coordinate.      1
	 *                                              0 a 2
	 *                                                3                */
	public static Coordinate[] getNeighboursCoordinates(Coordinate coord) {
		Coordinate[] neighbours = new Coordinate[4];

		neighbours[0] = new Coordinate(coord.getX()-1, coord.getY());
		neighbours[1] = new Coordinate(coord.getX(), coord.getY()-1);
		neighbours[2] = new Coordinate(coord.getX()+1, coord.getY());
		neighbours[3] = new Coordinate(coord.getX(), coord.getY()+1);

		for (int i = 0; i < 4; i++) {
			neighbours[i] = neighbours[i].checkCoord();
		}

		return neighbours;
	}

	public static Coordinate[] getNeighboursCoordinates(String code) {
		Coordinate coord = getCoordinate(code);
		return getNeighboursCoordinates(coord);
	}

	public static String[] getNeighboursCodes(String code) {
		Coordinate c = getCoordinate(code);
		String[] neighbours = new String[4];
		int cl = code.length();

		neighbours[0] = getCode(new Coordinate(c.getX()-1, c.getY()), cl);
		neighbours[1] = getCode(new Coordinate(c.getX(), c.getY()-1), cl);
		neighbours[2] = getCode(new Coordinate(c.getX()+1, c.getY()), cl);
		neighbours[3] = getCode(new Coordinate(c.getX(), c.getY()+1), cl);

		return neighbours;
	}

}
