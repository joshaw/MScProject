/** Created: Mon 28 Jul 2014 05:07 PM
 * Modified: Fri 01 Aug 2014 03:36 PM
 * @author Josh Wainwright
 * filename: ClusterStatsDatum.java
 */

package clusterstructure;

import utils.Coordinate;

import java.util.Set;
import java.util.HashSet;

public class ClusterStatsDatum extends ClusterStats
	implements Comparable<ClusterStatsDatum> {

	private byte status;
	private int clusterPoints;
	private double clusterArea;
	private double clusterPerimeter;
	private Set<Coordinate> points;

	public ClusterStatsDatum() {
		status = 0;
		clusterPoints = 0;
		clusterArea = 0;
		clusterPerimeter = 0;
		points = new HashSet<Coordinate>(100);
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

	public double getClusterArea() {
		return clusterArea;
	}

	public void addClusterPerimeter(double perimeter) {
		clusterPerimeter += perimeter;
	}

	public double getClusterPerimeter() {
		return clusterPerimeter;
	}

	public void addClusterCoords(Set<Coordinate> points) {
		System.out.println("AddClusterCoords");
		// this.points.addAll(points);
		this.points.addAll(null);
	}

	public int compareTo(ClusterStatsDatum c) {
		// return this.clusterPoints - c.getClusterPoints();
		return c.getClusterPoints() - this.clusterPoints;
	}
}
