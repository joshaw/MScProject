package simplegrid;

import simplegrid.*;
import utils.Coordinate;

public class SimpleGridTest {

	public static void main(String[] args) {

		SimpleGrid main;
		if (args.length == 4) {
			double xdim = Double.parseDouble(args[0]);
			double ydim = Double.parseDouble(args[1]);
			int density = Integer.parseInt(args[2]);
			main = new SimpleGrid(xdim, ydim, density, args[3]);
		} else if (args.length == 3) {
			int density = Integer.parseInt(args[0]);
			double scale = Double.parseDouble(args[1]);
			main = new SimpleGrid(41000.0, 41000.0, density, args[2]);
		} else if (args.length == 1) {
			main = new SimpleGrid(41000.0, 41000.0, 200, args[0]);
		} else {
			main = new SimpleGrid(41000.0, 41000.0, 200, "../sampledata/palm_1.txt");
		}

		// System.out.println(s.toString());
		main.writeToFile("analysis.pnm");

	}
}
