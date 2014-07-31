/** Created: Thu 31 Jul 2014 12:33 PM
 * Modified: Thu 31 Jul 2014 01:47 PM
 * @author Josh Wainwright
 * filename: QuadTree.java
 */

package clusterstructure.quadtree;

import clusterstructure.quadtree.QuadTreeNode;
import utils.FileDescriptor;
import utils.PropogationDatum;
import utils.Coordinate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class QuadTree {

	private String filepath;
	private FileDescriptor fileDesc;
	private int countFile = 0;

	private QuadTreeMap hashmap;

	private QuadTreeNode root;

	private double maxX;
	private double maxY;

	/** Create a new root node
	 */
	public QuadTree(double maxX, double maxY, int maxDensity,
			String filepath, int colX, int colY, String separator) {

		this.root     = new QuadTreeNode(maxX, maxY, maxDensity);
		this.maxX     = maxX;
		this.maxY     = maxY;
		this.filepath = filepath;
		this.fileDesc = new FileDescriptor(colX, colY, separator);

		readDataFile();
	}

	public QuadTreeNode getNode(String findCode) {
		return root.getNode(findCode, findCode);
	}

	/** Returns a HashMap representation of the current quadtree. This can
	 * often be faster to access multiple entries from than the quadtree.
	 *
	 * The key is the code for the leaf node, the value is the arraylist of the
	 * points that were added to the quadtree.
	 * @return HashMap representation of this quadtree.
	 */
	public QuadTreeMap toQuadTreeMap() {
		int maxLeaves = (int) root.getMaxNumberOfLeaves();

		QuadTreeMap qtm = new QuadTreeMap(maxX, maxY);

		return root.toQuadTreeMap(qtm);
	}

	public void addQuadTreeMap() {
		this.hashmap = toQuadTreeMap();
	}

	public QuadTreeMap getQuadTreeMap() {
		return hashmap;
	}

	public static String toString(HashMap<String, PropogationDatum> hm) {

		StringBuilder sb = new StringBuilder();

		for(Entry<String, PropogationDatum> leaf : hm.entrySet()) {
			String key = leaf.getKey();
			PropogationDatum value = leaf.getValue();
			sb.append(key + " (" + value.size() + "), ");
		}

		return sb.toString();
	}

	public int getCountFile()   { return countFile; }
	public String getFilepath() { return filepath; }

	/** Draw the current tree to the screen.
	 */
	public void draw(boolean incLines, boolean incPoints, double scaleFactor) {
		System.out.println(filepath);
		DrawQuadTree d = new DrawQuadTree(this, scaleFactor);
		d.draw(incLines, incPoints);
	}

	/** Reads the given data file and interprets the contents as coordinates.
	 * Each coordinate is added to the quadtree.
	 *
	 * @return true if file was read succssfully.
	 */
	public boolean readDataFile() {
		if (!filepath.equals("")) {
			BufferedReader reader = null;

			try {
				reader = new BufferedReader(new FileReader(filepath));
				String line = null;

				reader.readLine();
				while ((line = reader.readLine()) != null) {

					String[] entries = line.split(fileDesc.getSeparator());
					double[] xyDouble = new double[2];

					xyDouble[0] = Double.parseDouble(
							entries[fileDesc.getColX()]);
					xyDouble[1] = Double.parseDouble(
							entries[fileDesc.getColY()]);

					root.addPoint(new Coordinate(xyDouble[0], xyDouble[1]));

					countFile++;
				}
				System.out.println("Total read from file: " + countFile);
			} catch (IOException e) {
				System.err.println("Error: Could not open file " + filepath);
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch(IOException e){
						e.printStackTrace();
					}
				}
			}
		}
		return true;
	}

}
