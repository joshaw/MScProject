/** Created: Tue 17 Jun 2014 12:00 PM
 * Modified: Wed 30 Jul 2014 12:51 PM
 * @author Josh Wainwright
 * File name : Coordinate.java
 */
package utils;

/** Represnts a two dimensional cartesian coordinate. Provides some methods to
 * manipulate coordinates.
 */
public class Coordinate {

	private double x;
	private double y;

	public Coordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	/** Check if a Coordinate is sane for this usage, ie, not negative.
	 */
	public static Coordinate checkCoord(Coordinate c) {
		if (c.getX() < 0 || c.getY() < 0) {
			return null;
		}
		return c;
	}
	public Coordinate checkCoord() {
		if (x < 0 || y < 0) {
			return null;
		}
		return this;
	}

	/** Checks that a coordinate is valid, ie exists in the quadtree-space of
	 * the current quadtree.
	 */
	public static boolean checkValid(Coordinate c, double maxX, double maxY) {
		double x = c.getX();
		double y = c.getY();
		if (y < 0 || y > maxY || x < 0 || x > maxX) {
			if (y < 0)
				System.err.println("[" + y + "] y too small");
			if (y > maxY)
				System.err.println("[" + y + "] y too large");
			if (x < 0)
				System.err.println("[" + x + "] x too small");
			if (x > maxX)
				System.err.println("[" + x + "] x too large");
			return false;
		}
		return true;
	}
	public boolean checkValid(double maxX, double maxY) {
		return checkValid(this, maxX, maxY);
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}

	/** A coordinate is equal to another coordinate if both the x and the y
	 * coordinates are equal.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Coordinate) {
			Coordinate c = (Coordinate) o;
			return this.x == c.x && this.y == c.y;
		}
		return false;
	}

}
