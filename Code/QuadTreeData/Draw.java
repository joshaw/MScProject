/** Created: Wed 18 Jun 2014 10:07 AM
 * Modified: Wed 18 Jun 2014 11:47 AM
 */
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Draw extends JPanel {

	private static final long serialVersionUID = 1L;
	private QuadTree quadtree;

	public Draw(QuadTree quadtree) {
		this.quadtree = quadtree;
	}

	public void DrawQuadTree() {
		JFrame f = new JFrame();
		f.setSize(800, 800);
		f.add(new Draw(quadtree));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public void paint(Graphics g) {
		recurseTree(quadtree, g);
	}

	private boolean recurseTree(QuadTree q, Graphics g) {
		System.out.println("minX: " + q.getMinX() + ", minY: " + q.getMinY()
				+ ", maxX: " + q.getMaxX() + ", maxY: " + q.getMaxY());
		int ox = q.getMinX();
		int oX = q.getMaxX();
		int oy = q.getMinY();
		int oY = q.getMaxY();
		int x = 5*q.getMinX();
		int X = 5*q.getMaxX();
		int y = 5*q.getMinY();
		int Y = 5*q.getMaxY();
		g.drawRect(100+x, 100+y, X-x, Y-y);
		// g.drawString("("+ox+","+oy+")", 100+x, 100+y);
		// g.drawString("("+oX+","+oy+")", 100+X, 100+y);
		// g.drawString("("+ox+","+oY+")", 100+x, 100+Y);
		// g.drawString("("+oX+","+oY+")", 100+X, 100+Y);
		if (q.isLeaf()) {
			return true;
		}
		return recurseTree(q.getTL(), g) &&
			recurseTree(q.getTR(), g) &&
			recurseTree(q.getBL(), g) &&
			recurseTree(q.getBR(), g);
	}
}
