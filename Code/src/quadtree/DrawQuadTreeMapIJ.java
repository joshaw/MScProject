/** Created: Tue 22 Jul 2014 12:17 PM
 * Modified: Tue 22 Jul 2014 12:17 PM
 * @author Josh Wainwright
 * File name : DrawQuadTreeMapIJ.java
 */
package simplegrid;

import ij.*;
import ij.io.*;
import ij.gui.*;
import ij.plugin.*;
import ij.process.*;

import utils.Coordinate;
import quadtree.QuadTreeMap;
import utils.PropogationDatum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map.Entry;

public class DrawQuadTreeMapIJ {

	private String filepath;
	private QuadTreeMap qtm;
	private int gridX;
	private int gridY;
	private double globalMaxX;
	private double globalMaxY;
	private final double scaleFactor = 0.01;
	private float points[][];
	private int addedCount = 0;
	private boolean incPoints;
	private boolean incLines;

	public DrawQuadTreeMapIJ(String filepath, QuadTreeMap qtm, int gridX, int gridY) {
		this.filepath = filepath;
		this.qtm = qtm;
		this.gridX = gridX;
		this.gridY = gridY;
	}

	public void draw() {
		ImagePlus imp = NewImage.createFloatImage(filepath, gridX, gridY, 1,
				NewImage.FILL_WHITE);
		ImageProcessor imgpro = imp.getProcessor();

		float[] pixels = new float[gridX * gridY];
		for (int j = 0; j < gridY; j++) {
			for (int i = 0; i < gridX; i++) {
				pixels[j*gridX + i] = points[i][j];
			}
		}

		imgpro.setPixels(pixels);
		imp.show();
	}

	/** Recursivly go through the quadtree and draw sqare on screen based on
	 * the minimum and maximum limits of the node are.
	 */
	private void traverseMap() {

		for (Entry<String, PropogationDatum> e : qtm.entrySet()) {

			String code = e.getKey();
			double minX = 0.0;
			double maxX = globalMaxX;
			double minY = 0.0;
			double maxY = globalMaxY;
			String nibble;

			while (code.length() > 0) {
				nibble = code.substring(0, 2);
				if (nibble.equals("00")) {
					minX = minX;
					minY = minY;
					maxX = maxX/2+minX/2;
					maxY = maxY/2+minY/2;

				} else if (nibble.equals("01")) {
					minX = maxX/2+minX/2;
					minY = minY;
					maxX = maxX;
					maxY = maxY/2+minY/2;

				} else if (nibble.equals("10")) {
					minX = minX;
					minY = maxY/2+minY/2;
					maxX = maxX/2+minX/2;
					maxY = maxY;

				} else if (nibble.equals("11")) {
					minX = maxX/2+minX/2;
					minY = maxY/2+minY/2;
					maxX = maxX;
					maxY = maxY;
				}

				code = code.substring(2);
			}

			double x = scaleFactor*minX;
			double X = scaleFactor*maxX;
			double y = scaleFactor*minY;
			double Y = scaleFactor*maxY;

			if (e.getValue().status() == 1) {
			// 	g2.setPaint(Color.RED);
			// 	g2.fill(new Rectangle2D.Double(10+x, 10+y, X-x, Y-y));
			}
			// g2.setPaint(Color.BLACK);

			// if (incLines) {
			// 	g2.draw(new Rectangle2D.Double(10+x, 10+y, X-x, Y-y));
			// 	boxCount++;
			// }

			if (incPoints) {
				for(Coordinate c: e.getValue().points()) {
					points[(int)c.getX()][(int)c.getY()] = 0;
					// addedCount++;
				}
			}
		}
	}

}
