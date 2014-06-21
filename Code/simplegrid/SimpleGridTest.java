package simplegrid;

import simplegrid.*;
import utils.Coordinate;

public class SimpleGridTest {

	public static void main(String[] args) {

		SimpleGrid s = new SimpleGrid(41000, 41000, 10, "sampledata/palm_1.txt");

		// System.out.println(s.toString());
		s.writeToFile("analysis.pnm");

	}
}
