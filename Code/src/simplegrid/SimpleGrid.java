// Created:  Tue 01 Jul 2014 03:39 PM
// Modified: Tue 01 Jul 2014 03:39 PM

package simplegrid;

import utils.Coordinate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class SimpleGrid {

	private double maxX;
	private double maxY;
	private int gridX;
	private int gridY;

	private int points[][];
	private int cellSize;
	private String filepath;
	private int threshold = 0;

	public SimpleGrid(double maxX, double maxY, int cellSize, String filepath) {
		this.maxX = maxX;
		this.maxY = maxY;
		this.cellSize = cellSize;
		this.filepath = filepath;

		this.gridX = (int) maxX / cellSize + 1;
		this.gridY = (int) maxY / cellSize + 1;

		this.points = new int[gridX][gridY];

		for (int i = 0; i < gridX; i++) {
			for (int j = 0; j < gridY; j++) {
				points[i][j] = 0;
			}
		}

		readDataFile();
	}

	public int addPoint(Coordinate c) {
		checkValid(c);
		int posX = (int) c.getX()/cellSize;
		int posY = (int) c.getY()/cellSize;

		// System.out.println(c);
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

	private int getMaxCapacity() {
		int maxCapacity = 0;
		for (int j = 0; j < gridY; j++) {
			for (int i = 0; i < gridX; i++) {

				if (points[i][j] > maxCapacity) {
					maxCapacity = points[i][j];
				}
			}
		}
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

				// while ((line = reader.readLine()) != null) {
				// 	String[] xyString = line.split("\\s");
				// 	double[] xyDouble = new double[2];

				// 	xyDouble[0] = Double.parseDouble(xyString[0]);
				// 	xyDouble[1] = Double.parseDouble(xyString[1]);
				// 	Coordinate c = new Coordinate(xyDouble[0], xyDouble[1]);
				// 	addPoint(c);
				// }
				reader.readLine();
				while ((line = reader.readLine()) != null) {

					String[] entries = line.split("\t");
					double[] xyDouble = new double[2];
					xyDouble[0] = Double.parseDouble(entries[3]);
					xyDouble[1] = Double.parseDouble(entries[4]);

					Coordinate c = new Coordinate(xyDouble[0], xyDouble[1]);
					addPoint(c);
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
		}
		return true;
	}

	public boolean writeToFile(String output) {
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(output), "utf-8"));

			String headerInfo = "P2\n" + gridX + " " + gridY + "\n" + getMaxCapacity() + "\n";
			writer.write(headerInfo);

			StringBuilder gridString = new StringBuilder();

			for (int j = 0; j < gridY; j++) {
				for (int i = 0; i < gridX; i++) {
					int pixelValue = (int) (points[i][j]);
					int above = 0;
					int right = 0;
					int below = 0;
					int left  = 0;
					try {
						above = (int) (points[i][j-1]);
						right = (int) (points[i+1][j]);
						below = (int) (points[i][j+1]);
						left  = (int) (points[i-1][j]);
					} catch(ArrayIndexOutOfBoundsException e){
					}

					if (pixelValue > threshold &&
							above > threshold/2 && right > threshold/2 &&
							below > threshold/2 && left > threshold/2) {
						gridString.append(pixelValue + " ");
					} else {
						gridString.append("0 ");
					}
				}
				gridString.append("\n");
				writer.write(gridString.toString());
				gridString.setLength(0);
			}
		} catch (IOException ex) {
			// report
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
