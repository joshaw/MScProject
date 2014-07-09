/** Created: Tue 01 Jul 2014 03:39 PM
 * Modified: Mon 07 Jul 2014 05:36 PM
 * @author Josh Wainwright
 * File name : SimpleGrid.java
 */
package simplegrid;

import utils.Coordinate;
import utils.ClusterStructure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class SimpleGrid implements ClusterStructure {

	private double maxX;
	private double maxY;
	private int gridX;
	private int gridY;

	private int points[][];
	private int cellSize;
	private int maxCapacity;
	private String filepath;
	private int colX;
	private int colY;
	private String separator;
	private int threshold = 0;
	private int countFile = 0;
	private boolean drawing;

	public SimpleGrid(double maxX, double maxY, int cellSize, String filepath,
			int colX, int colY, String separator) {
		this.maxX = maxX;
		this.maxY = maxY;
		this.cellSize = cellSize;
		this.filepath = filepath;
		this.colX = colX;
		this.colY = colY;
		this.separator = separator;

		this.gridX = (int) maxX / cellSize + 1;
		this.gridY = (int) maxY / cellSize + 1;

		this.points = new int[gridX][gridY];

		for (int i = 0; i < gridX; i++) {
			for (int j = 0; j < gridY; j++) {
				points[i][j] = 0;
			}
		}

		if (!readDataFile()) {
			System.err.println("Error: Could not read input file.");
			System.exit(1);
		}
	}

	// TODO remove nessessity for parameters - required because of interface
	public void draw(boolean incLines, boolean incPoints, double other) {
		DrawSimpleGrid d = new DrawSimpleGrid(filepath, points, gridX, gridY);
		d.draw();
	}

	public int addPoint(Coordinate c) {
		checkValid(c);
		int posX = (int) c.getX()/cellSize;
		int posY = (int) c.getY()/cellSize;

		points[posX][posY] ++;
		return points[posX][posY];
	}

	/** Checks that a coordinate is valid, ie exists in the quadtree-space of
	 * the current quadtree.
	 */
	private boolean checkValid(Coordinate c) {
		double x = c.getX();
		double y = c.getY();
		if (y < 0)
			throw new IllegalArgumentException("[" + y + "] y too small");
		if (y > maxY)
			throw new IllegalArgumentException("[" + y + "] y too large");
		if (x < 0)
			throw new IllegalArgumentException("[" + x + "] x too small");
		if (x > maxX)
			throw new IllegalArgumentException("[" + x + "] x too large");
		return true;
	}

	/** Find the maximum value in the array of pixel values.
	 */
	private int getMaxCapacity() {
		int maxCapacity = 0;
		for (int j = 0; j < gridY; j++) {
			for (int i = 0; i < gridX; i++) {

				if (points[i][j] > maxCapacity) {
					maxCapacity = points[i][j];
				}
			}
		}
		this.maxCapacity = maxCapacity;
		return maxCapacity;
	}

	/** Reads the given data file and interprets the contents as coordinates.
	 * Each coordinate is added to the grid.
	 */
	private boolean readDataFile() {
		if (!filepath.equals("")) {
			BufferedReader reader = null;

			try {
				reader = new BufferedReader(new FileReader(filepath));
				String line = null;

				reader.readLine();
				while ((line = reader.readLine()) != null) {

					String[] entries = line.split(separator);
					double[] xyDouble = new double[2];
					xyDouble[0] = Double.parseDouble(entries[colX]);
					xyDouble[1] = Double.parseDouble(entries[colY]);

					addPoint(new Coordinate(xyDouble[0], xyDouble[1]));
					countFile++;
				}
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
			return true;
		}
		return false;
	}

	/** Write the pixel data contained in the points array to the given file.
	 * If the SimpleGrid object has not been initialised with a data file, the
	 * output will be all zeros.
	 *
	 * @param output file to write data to, will end up as a pnm image file.
	 * @return true if the file was written successfully.
	 */
	public boolean writeToFile(String output) {
		getMaxCapacity();
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(output), "utf-8"));

			String headerInfo = "P2\n" + gridX + " " + gridY + "\n" +
				maxCapacity + "\n";

			writer.write(headerInfo);
			writer.write(pixelLoop());

		} catch (IOException ex) {
			return false;
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
				return false;
			}
		}
		return true;
	}

	private String pixelLoop() {
		StringBuilder gridString = new StringBuilder();

		for (int j = 0; j < gridY; j++) {
			for (int i = 0; i < gridX; i++) {
				int pixelValue = (points[i][j]);

				int above = 0;
				int right = 0;
				int below = 0;
				int left  = 0;
				try {
					above = (points[i][j-1]);
					right = (points[i+1][j]);
					below = (points[i][j+1]);
					left  = (points[i-1][j]);
				} catch(ArrayIndexOutOfBoundsException e) {}

				/* Only writes pixels that have neighbours with a value greater
				 * than threshold/2. */
				if (pixelValue > threshold &&
						above > threshold/2 && right > threshold/2 &&
						below > threshold/2 && left > threshold/2) {
					gridString.append(maxCapacity-pixelValue + " ");
				} else {
					gridString.append(maxCapacity + " ");
				}
			}
			gridString.append("\n");
			// gridString.setLength(0);
		}
		return gridString.toString();
	}

	public int getCountFile() { return countFile; }
	public String getFilepath() { return filepath; }

	@Override
	public String toString() {
		StringBuilder gridString = new StringBuilder();
		for (int i = 0; i < gridX; i++) {
			for (int j = 0; j < gridY; j++) {
				gridString.append(String.format("%5d", points[i][j]));
			}
			gridString.append("\n");
		}

		gridString.append("\n\n" + getMaxCapacity());
		return gridString.toString();
	}

}
