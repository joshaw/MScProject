/** Created: TIMESTAMP
 * Modified: TIMESTAMP
 * @author Josh Wainwright
 * File name : QuadTreeMap.java
 */
package quadtree;

import utils.PropogationDatum;

import java.util.HashMap;

public class QuadTreeMap extends HashMap<String, PropogationDatum> {

	private static final long serialVersionUID = 1L;
	double maxX;
	double maxY;

	public QuadTreeMap(double maxX, double maxY) {
		super(100);
		this.maxX = maxX;
		this.maxY = maxY;
	}

	public double getMaxX() { return maxX; }
	public double getMaxY() { return maxY; }
}
