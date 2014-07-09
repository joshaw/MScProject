/** Created: Tue 08 Jul 2014 02:55 PM
 * Modified: Wed 09 Jul 2014 02:55 PM
 * @author Josh Wainwright
 * File name : DrawSimpleGrid.java
 */
package simplegrid;

import ij.*;
import ij.io.*;
import ij.gui.*;
import ij.plugin.*;
import ij.process.*;

import java.io.BufferedReader;
import java.io.FileReader;

public class DrawSimpleGrid {

	private String filepath;
	private int[][] points;
	private int gridX;
	private int gridY;

	public DrawSimpleGrid(String filepath, int[][] points, int gridX, int gridY) {
		this.filepath = filepath;
		this.points = points;
		this.gridX = gridX;
		this.gridY = gridY;
	}

	public void draw() {
		ImagePlus imp = NewImage.createFloatImage(filepath, gridX, gridY, 1,
				NewImage.FILL_BLACK);
		ImageProcessor imgpro = imp.getProcessor();

		float[] pixels = new float[gridX * gridY];
		for (int j = 0; j < gridY; j++) {
			for (int i = 0; i < gridX; i++) {
				pixels[j*gridX + i] = points[i][j];
			}
		}

		imgpro.setPixels(pixels);
		IJ.run(imp, "Enhance Contrast", "saturated=0.35");
		imp.show();

	}

}
