/** Created: Wed 18 Jun 2014 10:07 AM
 * Modified: Thu 19 Jun 2014 06:03 PM
 */
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;

public class Draw extends JPanel {

	private static final long serialVersionUID = 1L;
	private QuadTree quadtree;
	private int scaleFactor;

	/** Create a new object which does the drawing.
	 */
	public Draw(QuadTree quadtree) {
		this.quadtree = quadtree;
		this.scaleFactor = quadtree.getScaleFactor();
	}

	/** Create the window to hold the quadtree graphics.
	 *
	 * Implicitly calls the paint method.
	 */
	public void DrawQuadTree() {
		JFrame f = new JFrame();
		int width = (int) (quadtree.getScaleFactor()*quadtree.getMaxX()+25);
		int height = (int) (quadtree.getScaleFactor()*quadtree.getMaxY()+45);
		f.setSize(width, height);
		f.add(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	/** Draw the graphics of the quadtree.
	 *
	 * Called when updating the screen.
	 */
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		recurseTree(quadtree, g2);
	}

	/** Recursivly go through the quadtree and draw sqare on screen based on
	 * the minimum and maximum limits of the node are.
	 */
	private boolean recurseTree(QuadTree q, Graphics2D g) {
		double ox = q.getMinX();
		double oX = q.getMaxX();
		double oy = q.getMinY();
		double oY = q.getMaxY();
		double x = scaleFactor*q.getMinX();
		double X = scaleFactor*q.getMaxX();
		double y = scaleFactor*q.getMinY();
		double Y = scaleFactor*q.getMaxY();

		g.draw(new Rectangle2D.Double(10+x, 10+y, X-x, Y-y));
		// g.drawString("("+ox+","+oy+")", 10+x, 10+y);
		// g.drawString("("+oX+","+oy+")", 10+X, 10+y);
		// g.drawString("("+ox+","+oY+")", 10+x, 10+Y);
		// g.drawString("("+oX+","+oY+")", 10+X, 10+Y);
		if (q.isLeaf()) {
			for(Coordinate c: q.getPoints()) {
				g.draw(new Ellipse2D.Double(
							scaleFactor*c.getX()+10,
							scaleFactor*c.getY()+10,
							4, 4));
			}
			return true;
		}
		return recurseTree(q.getTL(), g) &&
			recurseTree(q.getTR(), g) &&
			recurseTree(q.getBL(), g) &&
			recurseTree(q.getBR(), g);
	}
}