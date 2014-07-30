/** Created: Mon 28 Jul 2014 05:07 PM
 * Modified: Wed 30 Jul 2014 10:59 AM
 * @author Josh Wainwright
 * filename: ClusterStatsDatum.java
 */

package clusterstructure;

public class ClusterStatsDatum extends ClusterStats
	implements Comparable<ClusterStatsDatum> {

	private byte status;
	private int clusterPoints;
	private double clusterArea;
	private double clusterPerimeter;

	public ClusterStatsDatum() { }

	public void setStatus(byte status) {
		this.status = status;
	}

	public byte getStatus() {
		return status;
	}

	public void addClusterPoints(int size) {
		clusterPoints += size;
	}

	public void addClusterArea(double area) {
		clusterArea += area;
	}

	public void addClusterPerimeter(double perimeter) {
		clusterPerimeter += perimeter;
	}

	public int getClusterPoints() {
		return clusterPoints;
	}

	public double getClusterArea() {
		return clusterArea;
	}

	public double getClusterPerimeter() {
		return clusterPerimeter;
	}

	public int compareTo(ClusterStatsDatum c) {
		// return this.clusterPoints - c.getClusterPoints();
		return c.getClusterPoints() - this.clusterPoints;
	}
}
