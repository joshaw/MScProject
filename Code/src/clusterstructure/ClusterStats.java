/** Created: Mon 28 Jul 2014 04:43 PM
 * Modified: Mon 04 Aug 2014 05:19 PM
 * @author Josh Wainwright
 * filename: ClusterStats.java
 */
package clusterstructure;

import clusterstructure.ClusterStatsDatum;
import utils.Coordinate;

import java.util.Collections;
import java.util.Set;
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

	public void excludeCluster(int status) {
		stats.get(status).setStatus(0);
	}

	/** Increase the count of points for the given cluster.
	 * @param status cluster to alter infomation for
	 * @param size number to increase information by
	 */
	public void addClusterPoints(int status, int size) {
		checkSize(status);
		stats.get(status).addClusterPoints(size);
		stats.get(status).setStatus(status);
	}

	public int getClusterPoints(int i) {
		return stats.get(i).getClusterPoints();
	}

	/** Increase the area of the given cluster.
	 * @param status cluster to alter infomation for
	 * @param code code of the node that represents the increase in area. From
	 * this, the size of area that should be added is calculated.
	 */
	public void addClusterArea(int status, String code) {
		checkSize(status);
		double area = Math.pow(4, -code.length()/2);
		stats.get(status).addClusterArea(area);
	}

	public void setArea(int status, double area) {
		stats.get(status).setArea(area);
	}

	public double getArea(int status) {
		return stats.get(status).getArea();
	}

	public double getClusterArea(int status) {
		return stats.get(status).getClusterArea();
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
	public void addClusterPerimeter(int status, String code, int sides) {
		checkSize(status);
		if (sides < 0 || sides > 4) {
			throw new IllegalArgumentException("Sides is between 1 and 4");
		}
		double perimeter = sides * Math.pow(2, -code.length()/2);
		stats.get(status).addClusterPerimeter(perimeter);
	}

	public void setPerimeter(int status, double perimeter) {
		stats.get(status).setPerimeter(perimeter);
	}

	public double getPerimeter(int status) {
		return stats.get(status).getPerimeter();
	}

	public double getClusterPerimeter(int status) {
		return stats.get(status).getClusterPerimeter();
	}

	public void addClusterCoords(int status, Set<Coordinate> points) {
		stats.get(status).addClusterCoords(points);
	}

	public Set<Coordinate> getClusterCoords(int status) {
		return stats.get(status).getClusterCoords();
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
	private void checkSize(int status) {
		while (status >= stats.size()) {
			stats.add(new ClusterStatsDatum());
		}
	}

	public int getStatus(int i) {
		if (stats.size() > 0) {
			return stats.get(i).getStatus();
		}
		return 0;
	}

}
