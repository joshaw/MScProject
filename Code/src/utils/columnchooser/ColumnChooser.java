// Created:  Thu 03 Jul 2014 12:01 PM
// Modified: Thu 03 Jul 2014 12:01 PM

package utils.columnchooser;

import utils.columnchooser.ColumnChooserGUI;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ColumnChooser {

	private final int rows = 10;
	private String filepath;

	public ColumnChooser(String filepath) {
		this.filepath = filepath;
		readFile();
	}

	private void readFile() {

		String[] columnNames;
		String[][] entries;
		if (!filepath.equals("")) {
			BufferedReader reader = null;

			try {
				reader = new BufferedReader(new FileReader(filepath));
				String line = null;

				line = reader.readLine();
				columnNames = line.split("\t");
				int numCols = columnNames.length;
				entries = new String[rows][numCols];

				String[] lineArray;
				int countFile = 0;
				while (countFile < rows && (line = reader.readLine()) != null) {

					lineArray = line.split("\t");
					for (int i = 0; i < numCols; i++) {
						entries[countFile][i] = lineArray[i];
					}

					countFile++;
				}

				ColumnChooserGUI.showChooser(columnNames, entries);

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

			// for (String[] lA : entries) {
			// 	for (String s : lA) {
			// 		System.out.print(s + "\t");
			// 	}
			// 	System.out.println("\n");
			// }

		}
	}
}