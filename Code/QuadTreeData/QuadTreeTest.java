/** Created: Tue 17 Jun 2014 12:00 PM
 * Modified: Fri 20 Jun 2014 03:56 PM
 * @author Josh Wainwright
 * File name : QuadtreeTest.java
 */
import org.junit.Test;
import static org.junit.Assert.*;

public class QuadTreeTest {
	public static void main(String[] args) {

		QuadTree main = new QuadTree(41000, 41000, 20, 0.022, "../sampledata/1.txt");

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
