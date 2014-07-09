/** Created: Wed 16 Jun 2014 9:02 AM
 * Modified: Wed 09 Jul 2014 02:50 PM
 * @author Josh Wainwright
 * File name : QuadTreeTest.java
 */
package quadtree.integer;

import utils.Coordinate;
import quadtree.integer.QuadTree;

import org.junit.Test;
import static org.junit.Assert.*;

public class QuadTreeTest {

	public static void main(String[] args) {
		QuadTree.adjacent("0010", "0001");
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
		assertFalse(QuadTree.adjacent("0010", "0001"));
	}

	@Test
	public void testAdjacncy4() {
		assertTrue(QuadTree.adjacent("1011","1110"));
	}

	@Test
	public void testAdjacncy5() {
		System.out.println(QuadTree.getCoordinate("01101010"));
		System.out.println(QuadTree.getCoordinate("1100"));
		assertTrue(QuadTree.adjacent("01100101","0111", 2));
	}

	@Test
	public void testGetCoordinate1() {
		assertEquals(QuadTree.getCoordinate("01"), new Coordinate(1, 0));
	}

	@Test
	public void testGetCoordinate2() {
		assertEquals(QuadTree.getCoordinate("110001"), new Coordinate(5, 4));
	}

	@Test
	public void testGetCoordinate3() {
		assertEquals(QuadTree.getCoordinate("101101100001"), new Coordinate(25,52));
	}

	@Test
	public void testGetCode1() {
		assertEquals(QuadTree.getCode(new Coordinate(1, 0), 2), "01");
	}

	@Test
	public void testGetCode2() {
		assertEquals(QuadTree.getCode(new Coordinate(5, 4), 6), "110001");
	}

	@Test
	public void testGetCode3() {
		assertEquals(QuadTree.getCode(new Coordinate(25, 52), 12), "101101100001");
	}

	@Test
	public void testGetNeighbours1() {
		Coordinate[] c = new Coordinate[4];
		c[0] = new Coordinate(0,2);
		c[1] = new Coordinate(1,1);
		c[2] = new Coordinate(2,2);
		c[3] = new Coordinate(1,3);

		assertArrayEquals(QuadTree.getNeighboursCoordinates("1001"), c);
	}

	@Test
	public void testGetNeighboursCodes1() {
		String[] c = new String[4];
		c[0] = "1000";
		c[1] = "0011";
		c[2] = "1100";
		c[3] = "1011";

		assertArrayEquals(QuadTree.getNeighboursCodes("1001"), c);
	}

	@Test
	public void testGetNeighboursCodes2() {
		String[] c = new String[4];
		c[0] = null;
		c[1] = "1000";
		c[2] = "1011";
		c[3] = null;

		assertArrayEquals(QuadTree.getNeighboursCodes("1010"), c);
	}
}
