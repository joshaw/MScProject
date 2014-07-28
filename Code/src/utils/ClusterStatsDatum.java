// Created:  Mon 28 Jul 2014 05:07 PM
// Modified: Mon 28 Jul 2014 05:07 PM
// @author Josh Wainwright
// filename: ClusterStatsDatum.java

package utils;

public class ClusterStatsDatum {

	private int numPoints;
	private int nodeArea;

	public ClusterStatsDatum() {

	}

	public void addPointsNum(int size) {
		numPoints += size;
	}

	public void addNodeArea(int area) {
		nodeArea += area;
	}

	public int getNumPoints() {
		return numPoints;
	}

	public int getNodeArea() {
		return nodeArea;
	}
}
