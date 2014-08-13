/**
 * Created:  Wed 13 Aug 2014 08:54 am
 * Author:   Josh Wainwright
 * Filename: ConcaveHullTest.java
 */
package hull;

import utils.Coordinate;

import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;

public class ConcaveHullTest {

	public static void main(String[] args) {

		Set<Coordinate> ts = new HashSet<Coordinate>();

		// points {{{
		// randomly generated data points between 1 and 10
		ts.add(new Coordinate(9,2));
		ts.add(new Coordinate(5,6));
		ts.add(new Coordinate(3,4));
		ts.add(new Coordinate(7,2));
		ts.add(new Coordinate(4,9));
		ts.add(new Coordinate(1,2));
		ts.add(new Coordinate(6,4));
		ts.add(new Coordinate(7,7));
		ts.add(new Coordinate(1,5));
		ts.add(new Coordinate(9,7));
		ts.add(new Coordinate(3,3));
		ts.add(new Coordinate(2,5));
		ts.add(new Coordinate(4,7));
		ts.add(new Coordinate(9,1));
		ts.add(new Coordinate(2,4));
		ts.add(new Coordinate(9,9));
		ts.add(new Coordinate(2,7));
		ts.add(new Coordinate(1,3));
		ts.add(new Coordinate(3,8));
		ts.add(new Coordinate(1,7));
		ts.add(new Coordinate(9,5));
		ts.add(new Coordinate(2,8));
		ts.add(new Coordinate(6,6));
		ts.add(new Coordinate(1,6));
		ts.add(new Coordinate(4,5));
		ts.add(new Coordinate(5,2));
		ts.add(new Coordinate(7,4));
		ts.add(new Coordinate(5,7));
		ts.add(new Coordinate(6,9));
		ts.add(new Coordinate(2,1));
		ts.add(new Coordinate(7,8));
		ts.add(new Coordinate(3,1));
		ts.add(new Coordinate(8,7));
		ts.add(new Coordinate(2,6));
		ts.add(new Coordinate(4,6));
		ts.add(new Coordinate(8,1));
		ts.add(new Coordinate(5,4));
		ts.add(new Coordinate(6,7));
		ts.add(new Coordinate(2,3));
		ts.add(new Coordinate(9,4));
		ts.add(new Coordinate(8,8));
		ts.add(new Coordinate(9,6));
		ts.add(new Coordinate(7,9));
		ts.add(new Coordinate(7,3));
		ts.add(new Coordinate(8,6));
		ts.add(new Coordinate(5,5));
		ts.add(new Coordinate(7,5));
		ts.add(new Coordinate(8,9));
		ts.add(new Coordinate(9,8));
		ts.add(new Coordinate(8,3));
		ts.add(new Coordinate(2,9));
		ts.add(new Coordinate(3,9));
		ts.add(new Coordinate(1,4));
		ts.add(new Coordinate(2,2));
		// }}}

		ConcaveHull ch = new ConcaveHull();
		int num = Integer.parseInt(args[0]);
		ch.concaveHull(ts, num);

	}

}
