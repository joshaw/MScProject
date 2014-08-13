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

		ts.add(new Coordinate(2,4));
		ts.add(new Coordinate(5,2));
		ts.add(new Coordinate(7,8));
		ts.add(new Coordinate(3,4));
		ts.add(new Coordinate(6,7));
		ts.add(new Coordinate(2,3));
		ts.add(new Coordinate(6,4));
		ts.add(new Coordinate(2,6));
		ts.add(new Coordinate(4,9));
		ts.add(new Coordinate(2,5));

		ConcaveHull ch = new ConcaveHull();
		ch.concaveHull(ts, 4);

	}

}
