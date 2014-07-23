/** Created: Tue 22 Jul 2014 12:17 PM
 * Modified: Wed 23 Jul 2014 03:55 PM
 * @author Josh Wainwright
 * File name : DrawQuadTreeMapIJ.java
 */
package quadtree;

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
	private final double scaleVal;
	private int points1[][];
	private int points2[][];
	private int addedCount = 0;
	private boolean incPoints;
	private boolean incLines;

	public DrawQuadTreeMapIJ(String filepath, QuadTreeMap qtm, double maxX,
			double maxY, double scaleVal) {
		this.filepath = filepath;
		this.qtm = qtm;
		this.scaleVal = scaleVal;
		this.gridX = (int)(scaleVal * maxX);
		this.gridY = (int)(scaleVal * maxY);
		this.globalMaxX = qtm.getMaxX();
		this.globalMaxY = qtm.getMaxY();
		points1 = new int[gridX+1][gridY+1];
		points2 = new int[gridX+1][gridY+1];

		for (int i = 0; i < gridX+1; i++) {
			for (int j = 0; j < gridY+1; j++) {
				points1[i][j] = rgb(255, 255, 255);
				points2[i][j] = rgb(255, 255, 255);
			}
		}
	}

	public void draw(boolean incLines, boolean incPoints) {
		this.incLines = incLines;
		this.incPoints = incPoints;

		ImagePlus imp = NewImage.createRGBImage(filepath, gridX, gridY, 2,
				NewImage.FILL_WHITE);
		// ImageProcessor imgpro = imp.getProcessor();
		ImageStack ims = imp.getStack();

		traverseMap();

		int[] pixels1 = new int[gridX * gridY];
		int[] pixels2 = new int[gridX * gridY];
		int[] pixels3 = new int[gridX * gridY];
		for (int j = 0; j < gridY; j++) {
			for (int i = 0; i < gridX; i++) {
				pixels1[j*gridX + i] = points1[i][j];
			}
		}
		for (int j = 0; j < gridY; j++) {
			for (int i = 0; i < gridX; i++) {
				pixels2[j*gridX + i] = points2[i][j];
			}
		}
		// for (int j = 0; j < gridY; j++) {
		// 	for (int i = 0; i < gridX; i++) {
		// 		if (points1[i][j] == rgb(255, 255, 255)) {
		// 			pixels3[j*gridX + i] = points2[i][j];
		// 		} else {
		// 			pixels3[j*gridX + i] = points1[i][j];
		// 		}
		// 	}
		// }

		ims.setPixels(pixels1, 1);
		ims.setPixels(pixels2, 2);
		// ims.setPixels(pixels3, 3);
		imp.setStack("Clusters", ims);
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

			int x = (int)(scaleVal*minX);
			int X = (int)(scaleVal*maxX);
			int y = (int)(scaleVal*minY);
			int Y = (int)(scaleVal*maxY);

			for (int i = x; i < X; i++) {
				for (int j = y; j < Y; j++) {
					if (e.getValue().status()%10 == 1) {
						points1[i][j] = hexrgb("#fce94f");
						points2[i][j] = hexrgb("#fce94f");
					}
					if (e.getValue().status()%10 == 2) {
						points1[i][j] = hexrgb("#fcaf3e");
						points2[i][j] = hexrgb("#fcaf3e");
					}
					if (e.getValue().status()%10 == 3) {
						points1[i][j] = hexrgb("#e9b96e");
						points2[i][j] = hexrgb("#e9b96e");
					}
					if (e.getValue().status()%10 == 4) {
						points1[i][j] = hexrgb("#8ae234");
						points2[i][j] = hexrgb("#8ae234");
					}
					if (e.getValue().status()%10 == 5) {
						points1[i][j] = hexrgb("#729fcf");
						points2[i][j] = hexrgb("#729fcf");
					}
					if (e.getValue().status()%10 == 6) {
						points1[i][j] = hexrgb("#ad7fa8");
						points2[i][j] = hexrgb("#ad7fa8");
					}
					if (e.getValue().status()%10 == 7) {
						points1[i][j] = hexrgb("#ef2929");
						points2[i][j] = hexrgb("#ef2929");
					}
					if (e.getValue().status()%10 == 8) {
						points1[i][j] = hexrgb("#d3d7cf");
						points2[i][j] = hexrgb("#d3d7cf");
					}
					if (e.getValue().status()%10 == 9) {
						points1[i][j] = hexrgb("#888a85");
						points2[i][j] = hexrgb("#888a85");
					}
				}
			}

			if (incLines) {
				for (int i = x; i < X; i++) {
					points2[i][y] = rgb(0, 0, 0);
				}
				for (int i = x; i < X; i++) {
					points2[i][Y] = rgb(0, 0, 0);
				}
				for (int i = y; i < Y; i++) {
					points2[x][i] = rgb(0, 0, 0);
				}
				for (int i = y; i < Y; i++) {
					points2[X][i] = rgb(0, 0, 0);
				}
			}

			if (incPoints) {
				for(Coordinate c: e.getValue().points()) {
					int xpoint = (int)(scaleVal * c.getX());
					int ypoint = (int)(scaleVal * c.getY());
					points1[xpoint][ypoint] = rgb(0, 0, 0);
				}
			}
		}
	}

	/** Convert a trio of 8 bit colour values to a single bit shifted colour
	 * decimal.
	 * @param r red component 0-255
	 * @param b blue component 0-255
	 * @param g green component 0-255
	 */
	private int rgb(int r, int g, int b) {
		if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
			throw new IllegalArgumentException(
					"RGB colours must be between 0 and 255.");
		}

		return ((r & 0xff)<<16) + ((g & 0xff)<<8) + (b & 0xff);
	}

	/** Convert a hex colour string to a decimal representation.
	 * @param hex hex colour string, eg #ff00ff
	 * @return decimal colour integer, eg 25500255
	 */
	private int hexrgb(String hex) {
		int r = Integer.valueOf(hex.substring(1, 3), 16);
		int g = Integer.valueOf(hex.substring(3, 5), 16);
		int b = Integer.valueOf(hex.substring(5, 7), 16);
		return rgb(r, g, b);
	}
}
