// Created:  Tue 08 Jul 2014 12:41 PM
// Modified: Tue 08 Jul 2014 12:43 PM

package utils;

import utils.Coordinate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler {

	public static Coordinate getMaxCoord(String filepath, int colX, int colY, String separator) {
		Coordinate maxCoord = new Coordinate(0,0);
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
		}
		return maxCoord;
	}

	public static int getNumberOfLines(String filepath) {
		int lines = 0;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filepath));
			while (reader.readLine() != null) lines++;
			reader.close();

		} catch(Exception e){
			e.printStackTrace();
		}
		return lines;
	}

}
