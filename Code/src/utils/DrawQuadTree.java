/** Created: Wed 18 Jun 2014 10:07 AM
 * Modified: Wed 02 Jul 2014 04:55 PM
 */
package utils;

import utils.Coordinate;
import quadtree.data.*;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class DrawQuadTree extends JPanel {

	private static final long serialVersionUID = 1L;
	private QuadTree quadtree;
	private double scaleFactor;
	private int addedCount = 0;
	private boolean incPoints;
	private boolean incLines;

	/** Create a new object which does the drawing.
	 */
	public DrawQuadTree(QuadTree quadtree, double scaleFactor) {
		this.quadtree = quadtree;
		this.scaleFactor = scaleFactor;
	}

	/** Create the window to hold the quadtree graphics.
	 * Implicitly calls the paint method.
	 */
	public void draw(boolean incLines, boolean incPoints) {
		this.incLines = incLines;
		this.incPoints = incPoints;
		JFrame f = new JFrame();

		int width = (int) (scaleFactor*quadtree.getMaxX()+25);
		int height = (int) (scaleFactor*quadtree.getMaxY()+45);

		f.setSize(width, height);
		f.add(this);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setVisible(true);
	}

	public void draw() {
		draw(true, true);
	}

	/** Draw the graphics of the quadtree.
	 * Called when updating the screen.
	 */
	public void paintComponent(Graphics g) {
		addedCount = 0;
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		Graphics2D g2 = (Graphics2D) g;
		recurseTree(quadtree, g2);
		System.out.println("Total added to tree: " + addedCount);
	}

	/** Recursivly go through the quadtree and draw sqare on screen based on
	 * the minimum and maximum limits of the node are.
	 */
	private boolean recurseTree(QuadTree q, Graphics2D g) {
		String code = q.getCode();
		double x = scaleFactor*q.getMinX();
		double X = scaleFactor*q.getMaxX();
		double y = scaleFactor*q.getMinY();
		double Y = scaleFactor*q.getMaxY();

		if (incLines) {
			g.draw(new Rectangle2D.Double(10+x, 10+y, X-x, Y-y));
		}
		if (q.isLeaf()) {
			if (incPoints) {
				for(Coordinate c: q.getPoints()) {
					// g.drawString(code, (float)(13+x), (float)(22+y));
					g.draw(new Line2D.Double(
								scaleFactor*c.getX()+10,
								scaleFactor*c.getY()+10,
								scaleFactor*c.getX()+10,
								scaleFactor*c.getY()+10));
					addedCount++;
				}
			}
			return true;
		}
		return recurseTree(q.getTL(), g) &&
			recurseTree(q.getTR(), g) &&
			recurseTree(q.getBL(), g) &&
			recurseTree(q.getBR(), g);
	}
}
