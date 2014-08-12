/** Created: Tue 12 Aug 2014 11:46 AM
 * @author Josh Wainwright
 * filename : DrawQuadTreeMap.java
 */
package clusterstructure.quadtree;

import utils.Coordinate;
import utils.PropogationDatum;
import clusterstructure.quadtree.*;
import clusterstructure.quadtree.QuadTreeMap;

import java.util.HashMap;
import java.util.Map.Entry;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class DrawQuadTreeMap extends JPanel {

	private static final long serialVersionUID = 1L;
	private QuadTreeMap qtm;
	private double scaleFactor;
	private double globalMaxX;
	private double globalMaxY;
	private int addedCount = 0;
	private boolean incPoints;
	private boolean incLines;
	int boxCount = 0;

	/** Create a new object which does the drawing.
	 */
	public DrawQuadTreeMap(QuadTreeMap qtm, double scaleFactor) {
		this.qtm = qtm;
		this.scaleFactor = scaleFactor;
		this.globalMaxX = qtm.getMaxX();
		this.globalMaxY = qtm.getMaxY();
	}

	/** Create the window to hold the quadtree graphics.
	 * Implicitly calls the paint method.
	 */
	public void draw(boolean incLines, boolean incPoints) {
		this.incLines = incLines;
		this.incPoints = incPoints;
		// JFrame f = new JFrame(quadtree.getFilepath());
		JFrame f = new JFrame();

		int width = (int) (scaleFactor*globalMaxX+25);
		int height = (int) (scaleFactor*globalMaxY+45);

		f.setSize(width, height);
		f.add(this);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setVisible(true);
	}

	/** Draw the graphics of the quadtree.
	 * Called when updating the screen.
	 */
	public void paintComponent(Graphics g) {
		addedCount = 0;
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		Graphics2D g2 = (Graphics2D) g;

		traverseMap(g2);
	}

	/** Recursivly go through the quadtree and draw sqare on screen based on
	 * the minimum and maximum limits of the node are.
	 */
	private void traverseMap(Graphics2D g2) {

		for (Entry<String, PropogationDatum> e : qtm.entrySet()) {

			String code = e.getKey();
			double minX = 0.0;
			double maxX = globalMaxX;
			double minY = 0.0;
			double maxY = globalMaxY;
			String nibble;

			while (code.length() > 0) {
				nibble = code.substring(0, 2);
				if (nibble.equals("00")) {
					minX = minX;
					minY = minY;
					maxX = maxX/2+minX/2;
					maxY = maxY/2+minY/2;

				} else if (nibble.equals("01")) {
					minX = maxX/2+minX/2;
					minY = minY;
					maxX = maxX;
					maxY = maxY/2+minY/2;

				} else if (nibble.equals("10")) {
					minX = minX;
					minY = maxY/2+minY/2;
					maxX = maxX/2+minX/2;
					maxY = maxY;

				} else if (nibble.equals("11")) {
					minX = maxX/2+minX/2;
					minY = maxY/2+minY/2;
					maxX = maxX;
					maxY = maxY;
				}

				code = code.substring(2);
			}

			double x = scaleFactor*minX;
			double X = scaleFactor*maxX;
			double y = scaleFactor*minY;
			double Y = scaleFactor*maxY;

			if (e.getValue().status() == 1) {
				g2.setPaint(Color.RED);
				g2.fill(new Rectangle2D.Double(10+x, 10+y, X-x, Y-y));
			}
			g2.setPaint(Color.BLACK);

			if (incLines) {
				g2.draw(new Rectangle2D.Double(10+x, 10+y, X-x, Y-y));
				boxCount++;
			}

			if (incPoints) {
				for(Coordinate c: e.getValue().points()) {
					// g.drawString(code, (float)(13+x), (float)(22+y));
					g2.draw(new Line2D.Double(
								scaleFactor*c.getX()+10,
								scaleFactor*c.getY()+10,
								scaleFactor*c.getX()+10,
								scaleFactor*c.getY()+10));
					addedCount++;
				}
			}
		}
	}
}
