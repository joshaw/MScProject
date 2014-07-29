// Created:  Mon 28 Jul 2014 05:07 PM
// Modified: Mon 28 Jul 2014 05:07 PM
// @author Josh Wainwright
// filename: ClusterStatsDatum.java

package utils;

public class ClusterStatsDatum implements Comparable<ClusterStatsDatum>{

	private int clusterPoints;
	private double clusterArea;

	public ClusterStatsDatum() {

	}

	public void addPointsNum(int size) {
		clusterPoints += size;
	}

	public void addClusterArea(double area) {
		clusterArea += area;
	}

	public int getClusterPoints() {
		return clusterPoints;
	}

	public double getClusterArea() {
		return clusterArea;
	}

	public int compareTo(ClusterStatsDatum c) {
		// return this.clusterPoints - c.getClusterPoints();
		return c.getClusterPoints() - this.clusterPoints;
	}
}
