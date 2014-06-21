package simplegrid;

import simplegrid.*;
import utils.Coordinate;

public class SimpleGridTest {

	public static void main(String[] args) {

		SimpleGrid s = new SimpleGrid(100, 100, 10, "sampledata/r_generated");

		System.out.println(s.toString());

	}
}
