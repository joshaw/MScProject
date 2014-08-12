/** Created: Fri 11 Jul 2014 10:05 AM
 * @author Josh Wainwright
 * File name : PropogationDatum.java
 */
package utils;

import utils.Coordinate;
import java.util.Set;

/** Represents a single piece of data to hold information about the nodes that
 * are analysed when propagaing a quadtree to find clusters.
 */
public class PropogationDatum {

	private Set<Coordinate> points;
	private int status;
	private int perimeter = 4;

	public PropogationDatum(Set<Coordinate> points, int status) {
		this.points = points;
		this.status = status;
	}

	public void setPoints(Set<Coordinate> points) {
		this.points = points;
	}

	public Set<Coordinate> points() {
		return points;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int status() {
		return status;
	}

	public int size() {
		return points.size();
	}

	/** Value from 1 to 4 representing how many of this cell's sides contribute
	 * to the perimeter of the cluster.
	 */
	public int perimeter() {
		return perimeter;
	}

	public void reducePerimeter() {
		this.perimeter--;
	}
}
