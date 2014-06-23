/** Created: Tue 17 Jun 2014 12:00 PM
 * Modified: Mon 23 Jun 2014 06:07 PM
 * @author Josh Wainwright
 * File name : QuadtreeTest.java
 */
package quadtree.data;

import utils.Coordinate;
import quadtree.data.*;

import org.junit.Test;
import static org.junit.Assert.*;

public class QuadTreeTest {
	public static void main(String[] args) {

		QuadTree main;
		if (args.length == 5) {
			double xdim = Double.parseDouble(args[0]);
			double ydim = Double.parseDouble(args[1]);
			int density = Integer.parseInt(args[2]);
			double scale = Double.parseDouble(args[3]);
			main = new QuadTree(xdim, ydim, density, scale, args[4]);
		} else if (args.length == 3) {
			int density = Integer.parseInt(args[0]);
			double scale = Double.parseDouble(args[1]);
			main = new QuadTree(41000.0, 41000.0, density, scale, args[2]);
		} else if (args.length == 1) {
			main = new QuadTree(41000.0, 41000.0, 20, 0.02, args[0]);
		} else {
			main = new QuadTree(41000.0, 41000.0, 20, 0.012, "../sampledata/palm_1.txt");
		}

		main.draw(false, true);
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
