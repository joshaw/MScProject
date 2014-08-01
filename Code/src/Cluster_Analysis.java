/** Created: Wed 02 Jul 2014 9:55 AM
 * Modified: Thu 31 Jul 2014 03:55 PM
 * @author Josh Wainwright
 * filename: Cluster_Analysis.java
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import ij.plugin.frame.*;
import ij.*;
import ij.process.*;
import ij.io.*;
import ij.gui.*;

import java.util.Timer;
import java.util.TimerTask;
import java.util.HashMap;

import clusterstructure.quadtree.*;
import clusterstructure.quadtree.DrawQuadTreeMapIJ;
import clusterstructure.quadtree.propagation.QuadTreePropagate;
import clusterstructure.quadtree.QuadTreeMap;
import clusterstructure.ClusterStructure;
import clusterstructure.simplegrid.*;
import utils.columnchooser.ColumnChooserGUI2;
import utils.FileHandler;
import utils.FileDescriptor;
import utils.Coordinate;

public class Cluster_Analysis extends PlugInFrame {

	private static final long serialVersionUID = 1L;

	private String     filepath;
	private double     maxXval;
	private double     maxYval;
	private int        densityVal;
	private int        depthVal;
	private double     scaleVal;
	private int        colX;
	private int        colY;
	private String     separator;
	private Coordinate maxCoord;

	private ClusterStructure dataStructure;

	private static Frame instance;
	private Panel panel;
	private Panel subpanel1;
	private Panel subpanel2;
	private Label statusMessage;
	private Label fileStatus;

	private TextField maxX;
	private TextField maxY;
	private TextField density;
	private TextField scale;
	private TextField depth;
	private Checkbox  linesBool;
	private Checkbox  pointsBool;
	private Checkbox  coloursBool;
	private Button    autoButton;

	public Cluster_Analysis() {
		super("Cluster Analysis");
		if (instance != null) {
			instance.toFront();
			return;
		}
		instance = this;
		addKeyListener(IJ.getInstance());

		setLayout(new BorderLayout());
		subpanel1 = new Panel();
		subpanel1.setLayout(new GridLayout(3, 1, 3, 3));

		Panel textpanel = new Panel();
		textpanel.setLayout(new GridLayout(5,2));
		textpanel.setComponentOrientation(
				ComponentOrientation.LEFT_TO_RIGHT);

		Label maxXLab = new Label("Max X");
		maxX = new TextField("", 10);
		maxX.addKeyListener(new NumberKeyListener());
		textpanel.add(maxXLab);
		textpanel.add(maxX);

		Label maxYLab = new Label("Max Y");
		maxY = new TextField("", 10);
		maxY.addKeyListener(new NumberKeyListener());
		textpanel.add(maxYLab);
		textpanel.add(maxY);

		Label densityLab = new Label("Density");
		density = new TextField("20", 10);
		density.addKeyListener(new NumberKeyListener());
		textpanel.add(densityLab);
		textpanel.add(density);

		Label scaleLab = new Label("Scale");
		scale = new TextField("0.03", 10);
		scale.addKeyListener(new NumberKeyListener());
		textpanel.add(scaleLab);
		textpanel.add(scale);

		Label depthLab = new Label("Depth Range");
		depth = new TextField("4", 10);
		depth.addKeyListener(new NumberKeyListener());
		textpanel.add(depthLab);
		textpanel.add(depth);

		linesBool = new Checkbox("Lines", true);
		subpanel1.add(linesBool);
		pointsBool = new Checkbox("Points", true);
		subpanel1.add(pointsBool);
		coloursBool = new Checkbox("Colourize", true);
		subpanel1.add(coloursBool);

		subpanel2 = new Panel();
		subpanel2.setLayout(new GridLayout(4, 1, 3, 3));

		autoButton = new Button("Auto");
		autoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				autoButtonActionPerformed();
			}
		});
		subpanel2.add(autoButton);
		autoButton.setVisible(false);

		Button dataFileButton = new Button("Data file");
		dataFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dataFileButtonActionPerformed(evt);
			}
		});
		subpanel2.add(dataFileButton);

		Button quadtreeButton = new Button("QuadTree");
		quadtreeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				drawStructureActionPerformed(evt);
			}
		});
		subpanel2.add(quadtreeButton);

		Button gridButton = new Button("Grid");
		gridButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				drawStructureActionPerformed(evt);
			}
		});
		subpanel2.add(gridButton);

		panel = new Panel();
		panel.setLayout(new FlowLayout());
		panel.add(textpanel);
		panel.add(subpanel1);
		panel.add(subpanel2);

		this.add(panel, BorderLayout.CENTER);
		statusMessage = new Label("");
		this.add(statusMessage, BorderLayout.NORTH);
		fileStatus = new Label("No data file chosen.");
		this.add(fileStatus, BorderLayout.SOUTH);

		pack();
		GUI.center(this);
		setVisible(true);
	}

	private void dataFileButtonActionPerformed(ActionEvent evt) {
		OpenDialog od    = new OpenDialog("Open data file ...");

		if (od.getPath() != null) {
			String file      = od .getFileName();
			String directory = od.getDirectory();
			filepath  = directory + file;
			ColumnChooserGUI2 cc = new ColumnChooserGUI2(filepath);

			if (cc.getSuccess()) {
				colX = cc.getXCol();
				System.out.println("ColX: " + colX);
				colY = cc.getYCol();
				System.out.println("ColY: " + colY);
				separator = cc.getSeparator();

				/* This is here to allow the "auto" button to be able to
				 * get the max and min values. */
				maxCoord = FileHandler.getMaxCoord( filepath,
						new FileDescriptor(colX, colY, separator));
				autoButton.setVisible(true);

				int numPoints = FileHandler.getNumberOfLines(filepath);
				fileStatus.setText("File: "+file+",  Points: "+numPoints);

				autoButtonActionPerformed();
			}
		}
	}

	private void autoButtonActionPerformed() {
		if (filepath != null) {
			maxXval = maxCoord.getX();
			maxYval = maxCoord.getY();
			scaleVal = ((int) (700/Math.max(maxXval, maxYval) * 1000) / 1)
				/ 1000.0;

			maxX.setText(maxXval + "");
			maxY.setText(maxYval + "");
			// scale.setText(scaleVal + "");
			// depth.setText(scaleVal + "");
		} else {
			changeStatus("Please select a data file first.");
		}
	}

	private void drawStructureActionPerformed(ActionEvent evt) {
		String label = evt.getActionCommand();

		String maxXstring    = removeSpaces(maxX.getText());
		String maxYstring    = removeSpaces(maxY.getText());
		String densityString = removeSpaces(density.getText());
		String scaleString   = removeSpaces(scale.getText());
		String depthString   = removeSpaces(depth.getText());

		if (filepath != null) {

			if (!maxXstring.equals("")    &&
				!maxYstring.equals("")    &&
				!densityString.equals("") &&
				!scaleString.equals("")   &&
				!depthString.equals("")
			   ) {

				maxXval    = Double.parseDouble(maxXstring);
				maxYval    = Double.parseDouble(maxYstring);
				densityVal = Integer.parseInt  (densityString);
				scaleVal   = Double.parseDouble(scaleString);
				depthVal   = Integer.parseInt  (depthString);

				if (label.equals("QuadTree")) {

long start = System.currentTimeMillis();

					dataStructure = new QuadTree(maxXval, maxYval,
							densityVal, filepath, colX, colY, separator);

					QuadTree qt = (QuadTree)dataStructure;

					// qt.draw(linesBool.getState(), pointsBool.getState(),
					// 	 	scaleVal);

					// QuadTreeMap hm = qt.toQuadTreeMap();
					qt.addQuadTreeMap();

					QuadTreePropagate qtp = new QuadTreePropagate(qt, depthVal);

					// DrawQuadTreeMap d = new DrawQuadTreeMap(hm, scaleVal);
					// d.draw(linesBool.getState(), pointsBool.getState());

					DrawQuadTreeMapIJ dij = new DrawQuadTreeMapIJ(filepath, qt, maxXval, maxYval, scaleVal);
					dij.draw(linesBool.getState(), pointsBool.getState());
					dij.draw(linesBool.getState(), pointsBool.getState(), coloursBool.getState());

System.out.println("Time: " + (System.currentTimeMillis()-start));

				} else if (label.equals("Grid")) {
					dataStructure = new SimpleGrid(maxXval, maxYval,
							densityVal, filepath, colX, colY, separator);

					SimpleGrid sg = (SimpleGrid)dataStructure;
					sg.draw();
				}

			} else {
				changeStatus("Please enter value for required parameter.");
			}
		} else {
			changeStatus("Please select a data file first.");
		}

	}

	/** ------------------------------------------------------------
	 * KEY LISTENER
	 * ----------------------------------------------------------- */
	class NumberKeyListener implements KeyListener{
		public void keyTyped(KeyEvent e) { }

		public void keyPressed(KeyEvent e) {
			char k = e.getKeyChar();
			int c = e.getKeyCode();
			if (Character.isDigit(k)            ||
					c == KeyEvent.VK_SHIFT      ||
					c == KeyEvent.VK_BACK_SPACE ||
					c == KeyEvent.VK_ENTER      ||
					c == KeyEvent.VK_SPACE      ||
					c == KeyEvent.VK_DELETE     ||
					c == KeyEvent.VK_CONTROL    ||
					c == KeyEvent.VK_ALT        ||
					c == KeyEvent.VK_ALT_GRAPH  ||
					c == KeyEvent.VK_CAPS_LOCK  ||
					c == KeyEvent.VK_HOME       ||
					c == KeyEvent.VK_END        ||
					c == KeyEvent.VK_META) {
				statusMessage.setText("");
			} else {
				changeStatus(k + ": Please enter a number.");
			}
		}

		public void keyReleased(KeyEvent e) { }
	}
	// ------------------------------------------------------------

	private String removeSpaces(String string) {
		return string.replaceAll("\\s","");
	}

	private Timer timer;
	private void changeStatus(String message) {
		statusMessage.setText(message);
		timer = new Timer();
		timer.schedule(new StatusTask(), 3 * 1000);
	}

	/** ------------------------------------------------------------
	 * TIMER TASK
	 * ----------------------------------------------------------- */
	class StatusTask extends TimerTask {
		public void run() {
			statusMessage.setText("");
			timer.cancel(); //Not necessary because we call System.exit
		}
	}
	// ------------------------------------------------------------

}
