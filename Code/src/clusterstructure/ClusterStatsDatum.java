/** Created: Mon 28 Jul 2014 05:07 PM
 * Modified: Mon 04 Aug 2014 10:26 AM
 * @author Josh Wainwright
 * filename: ClusterStatsDatum.java
 */

package clusterstructure;

import utils.Coordinate;

import java.util.Set;
import java.util.HashSet;

public class ClusterStatsDatum extends ClusterStats
	implements Comparable<ClusterStatsDatum> {

	private byte   status;
	private int    clusterPoints;
	private double clusterArea;
	private double clusterPerimeter;
	private int    numPoints;
	private double area;
	private double perimeter;
	private Set<Coordinate> points;

	public ClusterStatsDatum() {
		status           = 0;
		clusterPoints    = 0;
		clusterArea      = 0;
		clusterPerimeter = 0;
		area             = -1;
		perimeter        = 0;
		points = new HashSet<Coordinate>(50);
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public byte getStatus() {
		return status;
	}

	public void addClusterPoints(int size) {
		clusterPoints += size;
	}

	public int getClusterPoints() {
		return clusterPoints;
	}

	public void addClusterArea(double area) {
		clusterArea += area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public double getArea() {
		return area;
	}

	public double getClusterArea() {
		return clusterArea;
	}

	public void addClusterPerimeter(double perimeter) {
		clusterPerimeter += perimeter;
	}

	public void setPerimeter(double perimeter) {
		this.perimeter = perimeter;
	}

	public double getPerimeter() {
		return perimeter;
	}

	public double getClusterPerimeter() {
		return clusterPerimeter;
	}

	public void addClusterCoords(Set<Coordinate> points) {
		if (points != null) {
			this.points.addAll(points);
		}
	}

	public Set<Coordinate> getClusterCoords() {
		return points;
	}

	public int compareTo(ClusterStatsDatum c) {
		// return this.clusterPoints - c.getClusterPoints();
		return c.getClusterPoints() - this.clusterPoints;
	}
}
