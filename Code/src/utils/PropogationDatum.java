/** Created: Fri 11 Jul 2014 10:05 AM
 * Modified: Fri 11 Jul 2014 10:05 AM
 * @author Josh Wainwright
 * File name : PropogationDatum.java
 */
package utils;

import utils.Coordinate;
import java.util.Set;

public class PropogationDatum {

	private Set<Coordinate> points;
	private byte status;

	public PropogationDatum(Set<Coordinate> points, byte status) {
		this.points = points;
		this.status = status;
	}

	public void setPoints(Set<Coordinate> points) {
		this.points = points;
	}

	public Set<Coordinate> points() {
		return points;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public byte status() {
		return status;
	}

	public int size() {
		return points.size();
	}

}
