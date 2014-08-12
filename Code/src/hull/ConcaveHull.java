/** Created:  Tue 12 Aug 2014 09:35 AM
 * author: Josh Wainwright
 * filename: ConcaveHull.java
 */
package hull;

import utils.Coordinate;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ConcaveHull {

	private Set<Coordinate> dataset;
	private ArrayList<Coordinate> hull = new ArrayList<Coordinate>();

	public ConcaveHull(Set<Coordinate> dataset) {
		this.dataset = dataset;
	}

	public ArrayList<Coordinate> ConcaveHull
		(Set<Coordinate> dataset, int k) {

		int kk = Math.max(k, 3);

		if (dataset.size() < 3) {
			throw new IllegalArgumentException
				("A minimum of 3 dissimilar points is required.");
		}

		// For a 3 points dataset, the polygon is the dataset itself.
		if (dataset.size() == 3) {
			return new ArrayList<Coordinate>(dataset);
		}

		kk = Math.min(kk, dataset.size()-1);

		Coordinate firstPoint = findMinYPoint();
		hull.add(firstPoint);
		Coordinate currentPoint = firstPoint;
		dataset.remove(firstPoint);

		double previousAngle = 0;
		int step = 2;

		while ( (!currentPoint.equals(firstPoint)) || (step == 2)
				&& (dataset.size() > 0) ) {

			if (step == 5) {
				dataset.add(firstPoint);
			}

			// Find the nearest neighbours
			Set<Coordinate> kNearestPoints = nearestPoints(currentPoint, kk);

			List<Coordinate> cPoints = sortByAngle(
					kNearestPoints, currentPoint, previousAngle);
		}

		return new ArrayList<Coordinate>();
	}

	private Coordinate findMinYPoint() {
		Coordinate minY = new Coordinate(0,0);
		for (Coordinate c : dataset) {
			if (c.getY() < minY.getY()) {
				minY = c;
			}
		}

		return minY;
	}

	private Set<Coordinate> nearestPoints(Coordinate p, int kk) {
		Set<Coordinate> tmpDataset = dataset;
		Set<Coordinate> nearest = new HashSet<Coordinate>();
		Coordinate minPoint = new Coordinate(0,0);

		for (int i = 0; i < kk; i++) {

			double minDistance = Double.MAX_VALUE;
			for (Coordinate c : tmpDataset) {
				double distance = Math.sqrt(
						Math.pow((p.getX()-c.getX()), 2) +
						Math.pow((p.getY()-c.getY()), 2)
						);
				if (distance < minDistance) {
					minDistance = distance;
					minPoint = c;
				}
			}
			nearest.add(minPoint);
			tmpDataset.remove(minPoint);
		}

		if (nearest.size() != kk) {
			System.out.println(
					"Nearest points function is wrong. We should be left with "
					+ kk + " points, but have " + nearest.size() + "."
					);
		}

		return nearest;
	}

	private List<Coordinate> sortByAngle(Set<Coordinate> nearest,
			Coordinate p, double prev) {

		Map<Double, Coordinate> mapping = new TreeMap<Double, Coordinate>();
		for (Coordinate c : nearest) {
			double deltaX = p.getX()-c.getX();
			double deltaY = p.getY()-c.getY();

			// Use atan(x,y) rather than atan(y,x) to get right turn instead of
			// left turn.
			double angle = prev + Math.atan2(deltaX, deltaY);
			mapping.put(angle, c);
		}

		return new ArrayList<Coordinate>(mapping.values());
	}

}
