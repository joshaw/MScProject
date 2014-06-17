/**
 *
 * @author Josh Wainwright
 * UID       : 1079596
 * Worksheet : 5
 * Exercise  : 4
 * File name : QuadtreeTest.java
 * @version 2013-12-07
 */
import org.junit.Test;
import static org.junit.Assert.*;

public class QuadTreeTest {
	public static void main(String[] args) {

<<<<<<< HEAD
		// /* Create a QuadTree as described on the sheet. */
		QuadTree<Integer> qt0 = new QuadTree<Integer>(0);
		QuadTree<Integer> qt1 = new QuadTree<Integer>(1);
		QuadTree<Integer> qt2 = new QuadTree<Integer>(2);
		QuadTree<Integer> qt3 = new QuadTree<Integer>(3);
		QuadTree<Integer> qt4 = new QuadTree<Integer>(4);
		QuadTree<Integer> qt5 = new QuadTree<Integer>(5);
		QuadTree<Integer> qt6 = new QuadTree<Integer>(6);
		QuadTree<Integer> qt7 = new QuadTree<Integer>(7);
		QuadTree<Integer> qt8 = new QuadTree<Integer>(8);
		QuadTree<Integer> qt9 = new QuadTree<Integer>(9);
		QuadTree<Integer> qt10 = new QuadTree<Integer>(10);
		QuadTree<Integer> qt11 = new QuadTree<Integer>(11);
		QuadTree<Integer> qt12 = new QuadTree<Integer>(12);
		QuadTree<Integer> qt13 = new QuadTree<Integer>(13);
		QuadTree<Integer> qt14 = new QuadTree<Integer>(14);
		QuadTree<Integer> qt15 = new QuadTree<Integer>(15);
		QuadTree<Integer> qt16 = new QuadTree<Integer>(16);
		QuadTree<Integer> qt17 = new QuadTree<Integer>(17);
		QuadTree<Integer> qt18 = new QuadTree<Integer>(18);
		QuadTree<Integer> qt19 = new QuadTree<Integer>(19);
		QuadTree<Integer> qt20 = new QuadTree<Integer>(20);

		QuadTree<Integer> qtBig1 = new QuadTree<Integer>(qt11, qt10, qt12, qt9);
		QuadTree<Integer> qtBig2 = new QuadTree<Integer>(qt6, qtBig1, qt7, qt8);
		QuadTree<Integer> qtBig4 = new QuadTree<Integer>(qt13, qt14, qt16, qt15);
		QuadTree<Integer> qtBig3 = new QuadTree<Integer>(qt5, qtBig4, qtBig2, qt3);
		QuadTree<Integer> qtBig5 = new QuadTree<Integer>(qt17, qt18, qt19, qt20);

		QuadTree<Integer> qtBig = new QuadTree<Integer>(qt0, qtBig3, qtBig5, qt2);
		/* End Creation. */

		System.out.println(qtBig.toDot());

		/* Create a QuadTree as described on the sheet. */
=======
		// QuadTree<Integer> qt0 = new QuadTree<Integer>(0);
		// QuadTree<Integer> qt1 = new QuadTree<Integer>(1);
		// QuadTree<Integer> qt2 = new QuadTree<Integer>(2);
		// QuadTree<Integer> qt3 = new QuadTree<Integer>(3);
		// QuadTree<Integer> qt4 = new QuadTree<Integer>(4);
		// QuadTree<Integer> qt5 = new QuadTree<Integer>(5);
		// QuadTree<Integer> qt6 = new QuadTree<Integer>(6);
		// QuadTree<Integer> qt7 = new QuadTree<Integer>(7);
		// QuadTree<Integer> qt8 = new QuadTree<Integer>(8);
		// QuadTree<Integer> qt9 = new QuadTree<Integer>(9);
		// QuadTree<Integer> qt10 = new QuadTree<Integer>(10);
		// QuadTree<Integer> qt11 = new QuadTree<Integer>(11);
		// QuadTree<Integer> qt12 = new QuadTree<Integer>(12);
		// QuadTree<Integer> qt13 = new QuadTree<Integer>(13);
		// QuadTree<Integer> qt14 = new QuadTree<Integer>(14);
		// QuadTree<Integer> qt15 = new QuadTree<Integer>(15);
		// QuadTree<Integer> qt16 = new QuadTree<Integer>(16);
		// QuadTree<Integer> qt17 = new QuadTree<Integer>(17);
		// QuadTree<Integer> qt18 = new QuadTree<Integer>(18);
		// QuadTree<Integer> qt19 = new QuadTree<Integer>(19);
		// QuadTree<Integer> qt20 = new QuadTree<Integer>(20);

		// QuadTree<Integer> qtBig1 = new QuadTree<Integer>(qt11, qt10, qt12, qt9);
		// QuadTree<Integer> qtBig2 = new QuadTree<Integer>(qt6, qtBig1, qt7, qt8);
		// QuadTree<Integer> qtBig4 = new QuadTree<Integer>(qt13, qt14, qt16, qt15);
		// QuadTree<Integer> qtBig3 = new QuadTree<Integer>(qt5, qtBig4, qtBig2, qt3);
		// QuadTree<Integer> qtBig5 = new QuadTree<Integer>(qt17, qt18, qt19, qt20);

		// QuadTree<Integer> qtBig = new QuadTree<Integer>(qt0, qtBig3, qtBig5, qt2);
		// /* End Creation. */

		// System.out.println(qtBig.toDot());

>>>>>>> 8a495997044c83014a6f29b75bf122b5a31155c2
		// QuadTree<Integer> qt1 = new QuadTree<Integer>(1);
		// QuadTree<Integer> qt2 = new QuadTree<Integer>(2);
		// QuadTree<Integer> qt3 = new QuadTree<Integer>(3);
		// QuadTree<Integer> qt4 = new QuadTree<Integer>(4);
		// QuadTree<Integer> qt5 = new QuadTree<Integer>(5);
		// QuadTree<Integer> qt6 = new QuadTree<Integer>(6);
		// QuadTree<Integer> qt7 = new QuadTree<Integer>(7);

		// QuadTree<Integer> qtBig = new QuadTree<Integer>(qt2, qt3, qt4, qt5);

		// QuadTree<Integer> qt = new QuadTree<Integer>(qt1, qtBig, qt6, qt7);
		// /* End Creation. */

		// System.out.println(qt.toDot());

		if (args.length == 1) {
			for (int i = 0; i < 4; i++) {
				System.out.println(QuadTree.getNeighboursCoordinates(args[0])[i]);
			}
		} else if (args.length == 2) {
			int a = Integer.parseInt(args[0]);
			int b = Integer.parseInt(args[1]);
			Coordinate c = new Coordinate(a, b);
			for (int i = 0; i < 4; i++) {
				System.out.println(QuadTree.getNeighboursCoordinates(c)[i]);
			}
		}
	}

	@Test
	public void testAdjacency1() {
		assertTrue(QuadTree.adjacent("0", "01"));
	}

	@Test
	public void testAdjacency2() {
		assertTrue(QuadTree.adjacent("0", "000"));
	}

	@Test
	public void testAdjacency3() {
		assertFalse(QuadTree.adjacent("0", "3"));
	}

	@Test
	public void testAdjacncy4() {
		assertTrue(QuadTree.adjacent("23","32"));
	}

	@Test
	public void testAdjacncy5() {
		System.out.println(QuadTree.getCoordinate("1221"));
		System.out.println(QuadTree.getCoordinate("13"));
		// assertTrue(QuadTree.adjacent("1211","13"));
	}

	@Test
	public void testGetCoordinate1() {
		assertTrue(QuadTree.getCoordinate("231201").equals(new Coordinate(52,25)));
	}

	@Test
	public void testGetNeighbours1() {
		Coordinate[] c = new Coordinate[4];
		c[0] = new Coordinate(1,1);
		c[1] = new Coordinate(2,2);
		c[2] = new Coordinate(3,1);
		c[3] = new Coordinate(2,0);

		assertArrayEquals(QuadTree.getNeighboursCoordinates("21"), c);
	}

	@Test
	public void testGetNeighboursCodes1() {
		String[] c = new String[4];
		c[0] = "20";
		c[1] = "21";
		c[2] = "30";
		c[3] = "23";

		// assertArrayEquals(QuadTree.getNeighboursCodes("21"), c);
	}

	@Test
	public void testGetCode() {
		System.out.println(QuadTree.getCode(new Coordinate(52, 25)));
	}
}
