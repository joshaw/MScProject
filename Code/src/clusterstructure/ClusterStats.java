/** Created: Mon 28 Jul 2014 04:43 PM
 * Modified: Wed 30 Jul 2014 10:59 AM
 * @author Josh Wainwright
 * filename: ClusterStats.java
 */
package clusterstructure;

import clusterstructure.ClusterStatsDatum;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class ClusterStats {

	private List<ClusterStatsDatum> stats;

	public ClusterStats() {
		stats = new ArrayList<ClusterStatsDatum>(40);
	}

	public void addClusterPoints(byte status, int size) {
		checkSize(status);
		stats.get(status).addClusterPoints(size);
		stats.get(status).setStatus(status);
	}

	public void addClusterArea(byte status, String code) {
		checkSize(status);
		double area = Math.pow(4, -code.length()/2);
		stats.get(status).addClusterArea(area);
	}

	public void addClusterPerimeter(byte status, String code, int sides) {
		checkSize(status);
		if (sides < 0 || sides > 4) {
			throw new IllegalArgumentException("Sides is between 1 and 4");
		}
		double perimeter = sides * Math.pow(2, -code.length()/2);
		stats.get(status).addClusterPerimeter(perimeter);
	}

	public void sort() {
		Collections.sort(stats);
	}

	public int size() {
		return stats.size();
	}

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
