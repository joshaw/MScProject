/** Created: Wed 09 Jul 2014 02:57 PM
 * Modified: Wed 09 Jul 2014 02:57 PM
 * @author Josh Wainwright
 * File name : ClusterStructure.java
 */
package utils;

public interface ClusterStructure {

	public int getCountFile();
	public String getFilepath();
	public boolean addPoint(Coordinate c);
	public boolean readDataFile();

}
