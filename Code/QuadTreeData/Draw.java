/** Created: Wed 18 Jun 2014 10:07 AM
 * Modified: Wed 18 Jun 2014 10:19 AM
 */
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Draw extends JPanel {

	private static final long serialVersionUID = 1L;
	private QuadTree quadtree;

	public static void DrawQuadTree(QuadTree quadtree) {
		this.quadtree = quadtree;
		JFrame f = new JFrame();
		f.setSize(400, 425);
		f.add(new Draw());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public void paint(Graphics g) {
		g.drawRect (0, 0, 400, 400);
		g.drawRect (0, 0, 200, 200);
		g.drawRect (200, 0, 200, 200);
		g.drawRect (0, 200, 200, 200);
		g.drawRect (200, 200, 200, 200);

		recurseTree(quadtree, g);
	}

	private void recurseTree(QuadTree q, Graphics g) {
		if (q.isLeaf()) {
			g.drawRect(q.getMinX(), q.getMinY(), q.getMaxX(), q.getMaxY());
		}
	}
}
