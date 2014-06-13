/**
 *
 * @author Josh Wainwright
 * UID       : 1079596
 * Worksheet : 5
 * Exercise  : 4
 * File name : QuadtreeTest.java
 * @version 2013-12-07
 */
import org.junit.Test;
import static org.junit.Assert.*;

public class QuadTreeTest {
	public static void main(String[] args) {

		// /* Create a QuadTree as described on the sheet. */
		QuadTree<Integer> qt0 = new QuadTree<Integer>(0);
		QuadTree<Integer> qt10 = new QuadTree<Integer>(10);
		QuadTree<Integer> qt20 = new QuadTree<Integer>(20);
		QuadTree<Integer> qt30 = new QuadTree<Integer>(30);
		QuadTree<Integer> qt40 = new QuadTree<Integer>(40);
		QuadTree<Integer> qt50 = new QuadTree<Integer>(50);
		QuadTree<Integer> qt60 = new QuadTree<Integer>(60);
		QuadTree<Integer> qt70 = new QuadTree<Integer>(70);
		QuadTree<Integer> qt80 = new QuadTree<Integer>(80);
		QuadTree<Integer> qt90 = new QuadTree<Integer>(90);
		QuadTree<Integer> qt100 = new QuadTree<Integer>(100);
		QuadTree<Integer> qt110 = new QuadTree<Integer>(110);
		QuadTree<Integer> qt120 = new QuadTree<Integer>(120);

		QuadTree<Integer> qtBig1 = new QuadTree<Integer>(qt110, qt100, qt120, qt90);
		QuadTree<Integer> qtBig2 = new QuadTree<Integer>(qt60, qtBig1, qt70, qt80);
		QuadTree<Integer> qtBig3 = new QuadTree<Integer>(qt50, qt40, qtBig2, qt30);

		QuadTree<Integer> qtBig = new QuadTree<Integer>(qt0, qtBig3, qt10, qt20);
		/* End Creation. */

		System.out.println(qtBig.toDot());

		/* Create a QuadTree as described on the sheet. */
		// QuadTree<Integer> qt1 = new QuadTree<Integer>(1);
		// QuadTree<Integer> qt2 = new QuadTree<Integer>(2);
		// QuadTree<Integer> qt3 = new QuadTree<Integer>(3);
		// QuadTree<Integer> qt4 = new QuadTree<Integer>(4);
		// QuadTree<Integer> qt5 = new QuadTree<Integer>(5);
		// QuadTree<Integer> qt6 = new QuadTree<Integer>(6);
		// QuadTree<Integer> qt7 = new QuadTree<Integer>(7);

		// QuadTree<Integer> qtBig = new QuadTree<Integer>(qt2, qt3, qt4, qt5);

		// QuadTree<Integer> qt = new QuadTree<Integer>(qt1, qtBig, qt6, qt7);
		// /* End Creation. */

		// System.out.println(qt.toDot());
	}
}
