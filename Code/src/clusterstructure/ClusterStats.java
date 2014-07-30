/** Created: Mon 28 Jul 2014 04:43 PM
 * Modified: Wed 30 Jul 2014 01:03 PM
 * @author Josh Wainwright
 * filename: ClusterStats.java
 */
package clusterstructure;

import clusterstructure.ClusterStatsDatum;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/** A class which holds a list of ClusterStatsDatum objects, one for each of
 * the clusters that is found in a data set. This class provides a simple way
 * of adding and retrieving information from those objects without having the
 * interact with them directly.
 */
public class ClusterStats {

	private List<ClusterStatsDatum> stats;

	public ClusterStats() {
		stats = new ArrayList<ClusterStatsDatum>(40);
	}

	/** Increase the count of points for the given cluster.
	 * @param status cluster to alter infomation for
	 * @param size number to increase information by
	 */
	public void addClusterPoints(byte status, int size) {
		checkSize(status);
		stats.get(status).addClusterPoints(size);
		stats.get(status).setStatus(status);
	}

	/** Increase the area of the given cluster.
	 * @param status cluster to alter infomation for
	 * @param code code of the node that represents the increase in area. From
	 * this, the size of area that should be added is calculated.
	 */
	public void addClusterArea(byte status, String code) {
		checkSize(status);
		double area = Math.pow(4, -code.length()/2);
		stats.get(status).addClusterArea(area);
	}

	/** Increase the perimeter of the given cluster. The value added to the
	 * perimeter will be the length of one side, calculated from the code,
	 * multiplied by the number of sides.
	 * @param status cluster to alter infomation for
	 * @param code code of the node that represents the increase in perimeter.
	 * From this, the size of perimeter that should be added is calculated.
	 * @param sides number of sides of the node that contribute to the
	 * perimeter.
	 */
	public void addClusterPerimeter(byte status, String code, int sides) {
		checkSize(status);
		if (sides < 0 || sides > 4) {
			throw new IllegalArgumentException("Sides is between 1 and 4");
		}
		double perimeter = sides * Math.pow(2, -code.length()/2);
		stats.get(status).addClusterPerimeter(perimeter);
	}

	/** Sorts the list of cluster information objects.
	 */
	public void sort() {
		Collections.sort(stats);
	}

	public int size() {
		return stats.size();
	}

	/** Ensures there are enough objects in the list of clusters.
	 */
	private void checkSize(byte status) {
		while (status >= stats.size()) {
			stats.add(new ClusterStatsDatum());
		}
	}

	public int getClusterPoints(int i) {
		return stats.get(i).getClusterPoints();
	}

	public double getClusterArea(int i) {
		return stats.get(i).getClusterArea();
	}

	public double getClusterPerimeter(int i) {
		return stats.get(i).getClusterPerimeter();
	}

	public byte getStatus(int i) {
		return stats.get(i).getStatus();
	}

}
