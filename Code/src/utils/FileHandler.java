/** Created: Tue 08 Jul 2014 12:41 PM
/* Modified: Wed 30 Jul 2014 12:42 PM
 * @author Josh Wainwright
 * File name : FileHandler.java
 */
package utils;

import utils.Coordinate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler {

	/** Read the given file, with the given column numbers and find the largest
	 * coordinate value found in the file.
	 * @param filepath path to the file to be read
	 * @param colX column number of the x-coordinate in the file
	 * @param colY column number of the y-coordinate in the file
	 * @param separator character(s) that delimit columns (tab, comma, etc.)
	 * @return a new Coordinate object holding the largest x value and largest
	 * y value found in the file. This combination might not exist in the file.
	 */
	public static Coordinate getMaxCoord(String filepath, FileDescriptor fd) {

		int colX = fd.getColX();
		int colY = fd.getColY();
		String separator = fd.getSeparator();
		Coordinate maxCoord = new Coordinate(0,0);

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

				if (xyDouble[0] > maxCoord.getX()) {
					maxCoord.setX(xyDouble[0]);
				}
				if (xyDouble[1] > maxCoord.getY()) {
					maxCoord.setY(xyDouble[1]);
				}
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
		return maxCoord;
	}

	/** Get the number of lines in the given file.
	 * @param filepath file to count lines in
	 * @return number of lines in filepath
	 */
	public static int getNumberOfLines(String filepath) {
		int lines = 0;

		try {
			BufferedReader r = new BufferedReader(new FileReader(filepath));
			while (r.readLine() != null) lines++;
			r.close();

		} catch(Exception e){
			e.printStackTrace();
		}
		return lines;
	}

}
