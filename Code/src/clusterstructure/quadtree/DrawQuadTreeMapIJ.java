/** Created: Tue 22 Jul 2014 12:17 PM
 * Author:   Josh Wainwright
 * Filename: DrawQuadTreeMapIJ.java
 */
package clusterstructure.quadtree;

import ij.*;
import ij.io.*;
import ij.gui.*;
import ij.plugin.*;
import ij.process.*;
import ij.measure.ResultsTable;

import clusterstructure.quadtree.QuadTreeMap;
import clusterstructure.simplegrid.SimpleGrid;
import clusterstructure.ClusterStats;
import utils.Coordinate;
import utils.FileHandler;
import utils.PropogationDatum;
// import hull.ConcaveHull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.Set;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.awt.Window;

public class DrawQuadTreeMapIJ {

	private final int GRID_SIZE = 20;

	private String      filepath;
	private QuadTreeMap qtm;
	private int         gridX;
	private int         gridY;
	private double      globalMaxX;
	private double      globalMaxY;
	private final       double scaleVal;
	private int         points1[][];
	private int         points2[][];
	private int         addedCount = 0;

	private boolean incPoints;
	private boolean incLines;
	private boolean colourize;
	private double  minClusterSize;

	private final String colours[] = {  "#ffffff",
										"#c4a000",
										"#ce5c00",
										"#8f5902",
										"#4e9a06",
										"#204a87",
										"#5c3566",
										"#a40000",
										"#babdb6",
										"#2e3436",
										"#edd400",
										"#f57900",
										"#c17d11",
										"#73d216",
										"#3465a4",
										"#75507b",
										"#cc0000",
										"#d3d7cf",
										"#555753",
										"#fce94f",
										"#fcaf3e",
										"#e9b96e",
										"#8ae234",
										"#729fcf",
										"#ad7fa8",
										"#ef2929",
										"#eeeeec",
										"#888a85"};
	private ClusterStats clusters = new ClusterStats();

	public DrawQuadTreeMapIJ(String filepath, QuadTree qt, double maxX,
			double maxY, double scaleVal) {
		this.filepath = filepath;
		this.qtm = qt.getQuadTreeMap();
		this.scaleVal = scaleVal;
		this.gridX = (int)(scaleVal * maxX);
		this.gridY = (int)(scaleVal * maxY);
		this.globalMaxX = qtm.getMaxX();
		this.globalMaxY = qtm.getMaxY();

		initPointsArrays();
	}

	private void initPointsArrays() {
		points1 = new int[gridX+1][gridY+1];
		points2 = new int[gridX+1][gridY+1];

		for (int i = 0; i < gridX+1; i++) {
			for (int j = 0; j < gridY+1; j++) {
				points1[i][j] = rgb(255, 255, 255);
				points2[i][j] = rgb(255, 255, 255);
			}
		}
	}

	public void draw(boolean incLines, boolean incPoints, boolean colourize,
			double minClusterSize) {

		this.incLines       = incLines;
		this.incPoints      = incPoints;
		this.colourize      = colourize;
		this.minClusterSize = minClusterSize;
		ImageStack ims;
		ImagePlus imp;

		Window wind = WindowManager.getWindow(FileHandler.removeExt(filepath));
		if (wind == null) {

			imp = NewImage.createRGBImage(filepath, gridX, gridY, 2,
					NewImage.FILL_WHITE);
			// ImageProcessor imgpro = imp.getProcessor();

		} else {
			ImageWindow imgwind = (ImageWindow)wind;
			imp = imgwind.getImagePlus();
			initPointsArrays();
		}

		ims = imp.getStack();

		clusters = qtm.getClusterStats();
		for (int i = 1; i < clusters.size(); i++) {
			calculateClusterArea(i);

			// ConcaveHull ch = new ConcaveHull();
			// ch.concaveHull(clusters.getClusterCoords(i), 4);
		}
		traverseMap();

		int[] pixels1 = new int[gridX * gridY];
		int[] pixels2 = new int[gridX * gridY];
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

		ims.setPixels(pixels1, 1);
		ims.setPixels(pixels2, 2);
		imp.setStack(FileHandler.removeExt(filepath), ims);
		imp.show();

		ResultsTable rt = new ResultsTable();
		for (int i = 0; i < clusters.size(); i++) {
			if (clusters.getStatus(i) > 0) {
				rt.incrementCounter();
				rt.addValue("Cluster", clusters.getStatus(i));
				rt.addValue("No. of Points", clusters.getClusterPoints(i));
				rt.addValue("Area", clusters.getArea(i));
				rt.addValue("Perimeter", clusters.getPerimeter(i));
				rt.addValue("Roundness", Math.sqrt(4*Math.PI*clusters.getArea(i)/
						Math.pow(clusters.getPerimeter(i), 2)));
			}
		}
		rt.showRowNumbers(true);
		rt.show("Clusters Results");

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

					int status = e.getValue().status();
					if (clusters.getStatus(status) == 0) {
						status = 0;
					}
					if (colourize) {
						int cn = colours.length;
						points1[i][j] = hexrgb(colours[status%cn]);
						points2[i][j] = hexrgb(colours[status%cn]);
					} else {
						int c = (status == 0) ? 0 : 27;
						points1[i][j] = hexrgb(colours[c]);
						points2[i][j] = hexrgb(colours[c]);
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

	private void calculateClusterArea(int status) {
		Set<Coordinate> cluster = clusters.getClusterCoords(status);
		double maxX = 0;
		double maxY = 0;
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		for (Coordinate c : cluster) {

			if (c.getX() > maxX) {
				maxX = c.getX();
			}
			if (c.getX() < minX) {
				minX = c.getX();
			}

			if (c.getY() > maxY) {
				maxY = c.getY();
			}
			if (c.getY() < minY) {
				minY = c.getY();
			}
		}
		SimpleGrid sg = new SimpleGrid(maxX-minX, maxY-minY, GRID_SIZE);

		for (Coordinate c : cluster) {
			double newX = c.getX() - minX;
			double newY = c.getY() - minY;
			sg.addPoint(new Coordinate(newX, newY));
		}

		ImagePlus sgimp = sg.draw(false);

		Prefs.blackBackground = false;

		int dilateTimes = 10;
		IJ.run(sgimp, "Make Binary", "");
		for (int i = 0; i < dilateTimes; i++) {
			IJ.run(sgimp, "Dilate", "");
		}
		IJ.run(sgimp, "Fill Holes", "");
		for (int i = 0; i < dilateTimes+1; i++) {
			IJ.run(sgimp, "Erode", "");
		}
		IJ.run(sgimp, "Dilate", "");

		double area = countBlackPixels(sgimp);
		IJ.run(sgimp, "Outline", "");
		double perimeter = countBlackPixels(sgimp);

		area = area - perimeter;

		// Take into account size of grid
		// area = (int) (Math.pow(GRID_SIZE, 2)*area);
		// perimeter = GRID_SIZE*perimeter;

		// Scale so result is fraction of whole image
		perimeter = perimeter / points1.length;
		area = area / Math.pow(points1.length, 2);

		clusters.setArea(status, area);
		clusters.setPerimeter(status, perimeter);
		if (area <= minClusterSize) {
			clusters.excludeCluster(status);
		}

	}

	private int countBlackPixels(ImagePlus imp) {
		int xdim = imp.getWidth();
		int ydim = imp.getHeight();
		int count = 0;
		for (int i = 0; i < xdim; i++) {
			for (int j = 0; j < ydim; j++) {
				int pix = imp.getPixel(i,j)[0];
				if (pix > 0) {
					count++;
				}
			}
		}
		return count;
	}
}
