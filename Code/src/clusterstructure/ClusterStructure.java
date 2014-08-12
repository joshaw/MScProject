/** Created: Wed 09 Jul 2014 02:57 PM
 * @author Josh Wainwright
 * File name: ClusterStructure.java
 */
package clusterstructure;

import utils.Coordinate;

/** Defines a number of methods that a cluster structure must implement.
 * Currently cluster structures are either SimpleGrid's or QuadTree's.
 */
public interface ClusterStructure {

	public int getCountFile();
	public String getFilepath();
	public boolean addPoint(Coordinate c);
	public boolean readDataFile();

}
