/** Created: Tue 17 Jun 2014 12:00 PM
 * Modified: Wed 18 Jun 2014 11:51 AM
 * @author Josh Wainwright
 * File name : QuadtreeTest.java
 */
import org.junit.Test;
import static org.junit.Assert.*;

public class QuadTreeTest {
	public static void main(String[] args) {

		QuadTree main = new QuadTree(100, 100, 2);
		// main.addPoint(new Coordinate(55, 34));
		// main.addPoint(new Coordinate(73, 23));
		// main.addPoint(new Coordinate(89, 19));
		// main.addPoint(new Coordinate(58, 23));
		// main.addPoint(new Coordinate(41, 82));
		// main.addPoint(new Coordinate(11, 59));
		// main.addPoint(new Coordinate(35, 84));
		// main.addPoint(new Coordinate(28, 73));
		// main.addPoint(new Coordinate(1, 1));
		// main.addPoint(new Coordinate(2, 2));
		// main.addPoint(new Coordinate(3, 3));
		// main.addPoint(new Coordinate(4, 4));
		// main.addPoint(new Coordinate(5, 5));
		// main.addPoint(new Coordinate(6, 6));
		// main.addPoint(new Coordinate(7, 7));
		// main.addPoint(new Coordinate(8, 8));
		// main.addPoint(new Coordinate(9, 9));

		// main.addPoint(new Coordinate(99, 1));
		// main.addPoint(new Coordinate(98, 2));
		// main.addPoint(new Coordinate(97, 3));
		// main.addPoint(new Coordinate(96, 4));
		// main.addPoint(new Coordinate(95, 5));
		// main.addPoint(new Coordinate(94, 6));
		// main.addPoint(new Coordinate(93, 7));
		// main.addPoint(new Coordinate(92, 8));
		// main.addPoint(new Coordinate(91, 9));

		// main.addPoint(new Coordinate(1, 99));
		// main.addPoint(new Coordinate(2, 98));
		// main.addPoint(new Coordinate(3, 97));
		// main.addPoint(new Coordinate(4, 96));
		// main.addPoint(new Coordinate(5, 95));
		// main.addPoint(new Coordinate(6, 94));
		// main.addPoint(new Coordinate(7, 93));
		// main.addPoint(new Coordinate(8, 92));
		// main.addPoint(new Coordinate(9, 91));

		main.addPoint(new Coordinate(99, 99));
		main.addPoint(new Coordinate(98, 98));
		main.addPoint(new Coordinate(97, 97));
		main.addPoint(new Coordinate(96, 96));
		// main.addPoint(new Coordinate(95, 95));
		// main.addPoint(new Coordinate(94, 94));
		// main.addPoint(new Coordinate(93, 93));
		// main.addPoint(new Coordinate(92, 92));
		// main.addPoint(new Coordinate(91, 91));

		System.out.println(main);
		Draw d = new Draw(main);
		d.DrawQuadTree();
	}

	@Test
	public void testAddPoint() {
		QuadTree main = new QuadTree(100, 100, 2);
		// main.addPoint(new Coordinate(20, 20));
		// main.addPoint(new Coordinate(25, 25));
		// main.addPoint(new Coordinate(75, 25));
		// main.addPoint(new Coordinate(25, 75));
		// main.addPoint(new Coordinate(75, 75));
		// main.addPoint(new Coordinate(80, 80));
		main.addPoint(new Coordinate(1, 1));
		main.addPoint(new Coordinate(2, 2));
		main.addPoint(new Coordinate(3, 3));
		main.addPoint(new Coordinate(4, 4));
		main.addPoint(new Coordinate(5, 5));
		main.addPoint(new Coordinate(6, 6));
		main.addPoint(new Coordinate(7, 7));
		main.addPoint(new Coordinate(8, 8));
		main.addPoint(new Coordinate(9, 9));

		System.out.println(main);
	}
}
