/** Created: Tue 29 Jul 2014 10:14 AM
 * Modified: Wed 30 Jul 2014 11:03 AM
 * @author Josh Wainwright
 * File name : QuadTreeMap.java
 */
package clusterstructure.quadtree;

import utils.PropogationDatum;
import clusterstructure.ClusterStats;

import java.util.HashMap;
import java.util.Map.Entry;

public class QuadTreeMap extends HashMap<String, PropogationDatum> {

	private static final long serialVersionUID = 1L;
	private double maxX;
	private double maxY;
	private ClusterStats clusters = new ClusterStats();

	public QuadTreeMap(double maxX, double maxY) {
		super(100);
		this.maxX = maxX;
		this.maxY = maxY;
	}

	public double getMaxX() { return maxX; }
	public double getMaxY() { return maxY; }

	public void generateClusterStats() {
		for (Entry<String, PropogationDatum> e : super.entrySet()) {
			byte status = e.getValue().status();
			clusters.addClusterPoints(status, e.getValue().size());
			clusters.addClusterArea(status, e.getKey());
			clusters.addClusterPerimeter(status, e.getKey(), e.getValue().perimeter());
		}
	}

	public ClusterStats getClusterStats() {
		return clusters;
	}
}
