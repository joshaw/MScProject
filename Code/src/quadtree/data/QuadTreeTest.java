/** Created: Tue 17 Jun 2014 12:00 PM
 * Modified: Tue 24 Jun 2014 10:06 AM
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
		if (args.length == 7) {
			double xdim = Double.parseDouble(args[0]);
			double ydim = Double.parseDouble(args[1]);
			int density = Integer.parseInt(args[2]);
			double scale = Double.parseDouble(args[3]);
			boolean incLines = Boolean.parseBoolean(args[5]);
			boolean incPoints = Boolean.parseBoolean(args[6]);
			main = new QuadTree(xdim, ydim, density, scale, args[4], incLines, incPoints);
		} else {
			main = new QuadTree(41000.0, 41000.0, 20, 0.012, "../sampledata/palm_1.txt", true, true);
		}

		main.draw();
	}

}
