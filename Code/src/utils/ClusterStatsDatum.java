// Created:  Mon 28 Jul 2014 05:07 PM
// Modified: Mon 28 Jul 2014 05:07 PM
// @author Josh Wainwright
// filename: ClusterStatsDatum.java

package utils;

public class ClusterStatsDatum {

	private int numPoints;
	private double nodeArea;

	public ClusterStatsDatum() {

	}

	public void addPointsNum(int size) {
		numPoints += size;
	}

	public void addClusterArea(double area) {
		nodeArea += area;
	}

	public int getClusterPoints() {
		return numPoints;
	}

	public double getClusterArea() {
		return nodeArea;
	}
}
