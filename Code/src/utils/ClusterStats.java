// Created:  Mon 28 Jul 2014 04:43 PM
// Modified: Mon 28 Jul 2014 04:43 PM
// @author Josh Wainwright
// filename: ClusterStats.java

package utils;

import utils.ClusterStatsDatum;

import java.util.List;
import java.util.ArrayList;

public class ClusterStats {

	private List<ClusterStatsDatum> stats;

	public ClusterStats() {
		stats = new ArrayList<ClusterStatsDatum>(40);
	}

	public void addPointsNum(byte status, int size) {
		checkSize(status);
		stats.get(status).addPointsNum(size);
	}

	public void addClusterArea(byte status, String code) {
		checkSize(status);
		double area = 1.0/Math.pow(4, code.length()/2);
		stats.get(status).addClusterArea(area);
	}

	public void sort(String order) {
		if (order.equals("desc")) {
		}
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

}
