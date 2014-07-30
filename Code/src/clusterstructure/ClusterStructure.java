/** Created: Wed 09 Jul 2014 02:57 PM
 * Modified: Wed 30 Jul 2014 11:03 AM
 * @author Josh Wainwright
 * File name : ClusterStructure.java
 */
package clusterstructure;

import utils.Coordinate;

public interface ClusterStructure {

	public int getCountFile();
	public String getFilepath();
	public boolean addPoint(Coordinate c);
	public boolean readDataFile();

}
