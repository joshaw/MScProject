/** Created: Tue 29 Jul 2014 10:14 AM
 * Modified: Mon 04 Aug 2014 10:21 AM
 * @author Josh Wainwright
 * File name : QuadTreeMap.java
 */
package clusterstructure.quadtree;

import clusterstructure.quadtree.QuadTree;
import utils.PropogationDatum;
import clusterstructure.ClusterStats;

import java.util.HashMap;
import java.util.Map.Entry;

public class QuadTreeMap extends HashMap<String, PropogationDatum> {

	private static final long serialVersionUID = 1L;
	private QuadTree quadtree;
	private double maxX;
	private double maxY;
	private ClusterStats clusters = new ClusterStats();

	public QuadTreeMap(double maxX, double maxY, QuadTree qt) {
		super(100);
		this.maxX = maxX;
		this.maxY = maxY;
		this.quadtree = qt;
	}

	public double getMaxX() { return maxX; }
	public double getMaxY() { return maxY; }

	public void generateClusterStats() {
		for (Entry<String, PropogationDatum> e : super.entrySet()) {
			int status = e.getValue().status();
			if (status > 0) {
				clusters.addClusterPoints(status, e.getValue().size());
				clusters.addClusterArea(status, e.getKey());
				clusters.addClusterPerimeter(status, e.getKey(), e.getValue().perimeter());
				clusters.addClusterCoords(status, quadtree.getNode(e.getKey()).getPoints());
			}
		}
	}

	public ClusterStats getClusterStats() {
		generateClusterStats();
		return clusters;
	}
}
