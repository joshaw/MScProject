/**
 * Created:  Tue 12 Aug 2014 09:35 AM
 * Author:   Josh Wainwright
 * Filename: ConcaveHull.java
 */
package hull;

import utils.Coordinate;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.*;

public class ConcaveHull extends JPanel {

	private static final long serialVersionUID = 1L;
	private Path2D poly;

	public ConcaveHull() {

	}

	public ArrayList<Coordinate> concaveHull(Set<Coordinate> dataset,
			int k) {

		System.out.println("ConcaveHull with " + dataset.size() + " points.");
		ArrayList<Coordinate> hull = new ArrayList<Coordinate>();

		int kk = Math.max(k, 3);

		if (dataset.size() < 3) {
			// throw new IllegalArgumentException
			// 	("A minimum of 3 dissimilar points is required.");
			return null;
		}

		// For a 3 points dataset, the polygon is the dataset itself.
		if (dataset.size() == 3) {
			return new ArrayList<Coordinate>(dataset);
		}

		kk = Math.min(kk, dataset.size()-1);

		Coordinate firstPoint = findMinYPoint(dataset);
		hull.add(firstPoint);
		Coordinate currentPoint = firstPoint;
		dataset.remove(firstPoint);

		double previousAngle = 0;
		int step = 1;

		while ( (!currentPoint.equals(firstPoint)) || (step == 1)
				&& (dataset.size() > 0) ) {

			if (step == 4) {
				dataset.add(firstPoint);
			}

			// Find the nearest neighbours
			Set<Coordinate> kNearestPoints =
				nearestPoints(dataset, currentPoint, kk);

			List<Coordinate> cPoints = sortByAngle(
					kNearestPoints, currentPoint, previousAngle);

			boolean its = true;
			int i = 0;

			while (its && i < cPoints.size()) {
				i++;
				int lastPoint;
				if (cPoints.get(i-1).equals(firstPoint)) {
					lastPoint = 1;
				} else {
					lastPoint = 0;
				}

				int j = 1;
				its = false;

				while (!its && j < hull.size()-lastPoint) {
					its = intersectsQ(
							hull.get(step-1), cPoints.get(i-1),
							hull.get(step-1-j), hull.get(step-j)
							);
					j++;
				}
			}

			//  since all candidates intersect at least one edge, try
			//  again with a higher number of neighbours.
			if (its) {
				return concaveHull(dataset, kk+1);
			}

			currentPoint = cPoints.get(i);
			hull.add(currentPoint);
			previousAngle = angle(hull.get(step), hull.get(step-1));
			dataset.remove(currentPoint);
			step++;

		}

		for (Coordinate c : dataset) {
			boolean allInside = pointInPolygonQ(c, hull);
			if (!allInside) {
				return concaveHull(dataset, kk+1);
			}
		}

		draw();
		return hull;
	}

	private Coordinate findMinYPoint(Set<Coordinate> dataset) {
		System.out.println("findMinYPoint");
		Coordinate minY = new Coordinate(0,0);
		for (Coordinate c : dataset) {
			if (c.getY() < minY.getY()) {
				minY = c;
			}
		}

		return minY;
	}

	private Set<Coordinate> nearestPoints(Set<Coordinate> dataset,
			Coordinate p, int kk) {

		System.out.println("nearestPoints");
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
					System.out.println("MinPoint: " + c);
				}
			}
			boolean added = nearest.add(minPoint);
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

	private boolean intersectsQ(Coordinate seg1a, Coordinate seg1b,
			Coordinate seg2a, Coordinate seg2b) {

		System.out.println("intersectsQ");
		Line2D line1 = new Line2D.Double(seg1a.getX(), seg1a.getY(),
		                                 seg1b.getX(), seg1b.getY());
		Line2D line2 = new Line2D.Double(seg2a.getX(), seg2a.getY(),
		                                 seg2b.getX(), seg2b.getY());
		return line2.intersectsLine(line1);
	}

	private double angle(Coordinate c1, Coordinate c2) {

		System.out.println("angle");
		double deltaX = c1.getX()-c2.getX();
		double deltaY = c1.getY()-c2.getY();

		// Use atan(x,y) rather than atan(y,x) to get right turn instead of
		// left turn.
		return Math.atan2(deltaX, deltaY);
	}

	private boolean pointInPolygonQ(Coordinate c,
			List<Coordinate> hull) {

		//http://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
		// boolean in = false;
		// int nvert = hull.size();

		// int i = 0;
		// int j = nvert - 1;
		// while (i < nvert) {

		// 	if ( ((verty.get(i) > texty) != (verty.get(j) > testy)) &&
		// 		( testx < (vertx.get(j) - vertx.git(i)) *
		// 		  (testy - verty.get(i) /
		// 		   (verty.get(j) - verty.get(i)) + vertx.get(i) ) ) ) {

		// 		in = !in;
		// 	}

		// 	j = i++;
		// }

		// return in;

		poly = new Path2D.Double();
		for (Coordinate p : hull) {
			poly.lineTo(p.getX(), p.getY());
		}
		poly.closePath();

		return poly.contains(c.getX(), c.getY());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g.create();
		g2d.draw(poly);
		g2d.dispose();
	}

	public void draw() {
		System.out.println("Message");
		JFrame frame = new JFrame("Hull");
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

}
