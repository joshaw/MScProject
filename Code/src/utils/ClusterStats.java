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
		int s = (int) status;
		checkSize(status);
		stats.get(s).addPointsNum(size);
	}

	public void addNodeArea(byte status, String code) {

	}

	public void sort(String order) {
		if (order.equals("desc")) {
		}
	}

	public int size() {
		return stats.size();
	}

	private void checkSize(byte status) {
		while (status > stats.size()) {
			stats.add(new ClusterStatsDatum());
		}
	}

	public int getNumPoints(int i) {
		return stats.get(i).getNumPoints();
	}

}
