/** Created: Tue 17 Jun 2014 12:00 PM
 * Modified: Wed 18 Jun 2014 04:30 PM
 * @author Josh Wainwright
 * File name : Coordinate.java
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

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Coordinate) {
			Coordinate c = (Coordinate) o;
			return this.x == c.x && this.y == c.y;
		}
		return false;
	}

}
