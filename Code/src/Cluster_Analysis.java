/** Created: Wed 02 Jul 2014 9:55 AM
 * Modified: Thu 03 Jul 2014 11:55 AM
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

import quadtree.data.*;
import simplegrid.*;
import utils.columnchooser.ColumnChooserGUI2;
import utils.ClusterStructure;
import utils.FileHandler;
import utils.Coordinate;

public class Cluster_Analysis extends PlugInFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private String filepath;
	private double maxXval;
	private double maxYval;
	private int densityVal;
	private double scaleVal;
	private int colX;
	private int colY;
	private String separator;
	private Coordinate maxCoord;

	private ClusterStructure dataStructure;

	private Panel panel;
	private Panel subpanel1;
	private Panel subpanel2;
	private static Frame instance;
	private Label statusMessage;
	private Label fileStatus;

	private TextField maxX;
	private TextField maxY;
	private TextField density;
	private TextField scale;
	private Checkbox linesBool;
	private Checkbox pointsBool;
	private Button auto;

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
		subpanel1.setLayout(new GridLayout(2, 1, 3, 3));

		Panel textpanel = new Panel();
		textpanel.setLayout(new GridLayout(4,2));
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
		scale = new TextField("0.012", 10);
		scale.addKeyListener(new NumberKeyListener());
		textpanel.add(scaleLab);
		textpanel.add(scale);

		linesBool = new Checkbox("Lines", true);
		subpanel1.add(linesBool);
		pointsBool = new Checkbox("Points", true);
		subpanel1.add(pointsBool);

		subpanel2 = new Panel();
		subpanel2.setLayout(new GridLayout(4, 1, 3, 3));

		auto = new Button("Auto");
		auto.addActionListener(this);
		subpanel2.add(auto);
		auto.setVisible(false);

		addButton("Data file", subpanel2);
		addButton("QuadTree", subpanel2);
		addButton("Grid", subpanel2);

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

	private void addButton(String label, Panel p) {
		Button b = new Button(label);
		b.addActionListener(this);
		b.addKeyListener(IJ.getInstance());
		p.add(b);
	}

	public void actionPerformed(ActionEvent e) {
		String label = e.getActionCommand();

		if (label.equals("Data file")) {
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
					maxCoord = FileHandler.getMaxCoord(
							filepath, colX, colY, separator);
					auto.setVisible(true);

					int numPoints = FileHandler.getNumberOfLines(filepath);
					fileStatus.setText("File: "+file+",  Points: "+numPoints);
				}
			}

		} else if (label.equals("QuadTree") || label.equals("Grid")) {
			String maxXstring = removeSpaces(maxX.getText());
			String maxYstring = removeSpaces(maxY.getText());
			String densityString = removeSpaces(density.getText());
			String scaleString = removeSpaces(scale.getText());

			if (filepath != null) {

				if (!maxXstring.equals("") && !maxYstring.equals("")
						&& !densityString.equals("") && !scaleString.equals("")
				   ) {

					maxXval = Double.parseDouble(maxXstring);
					maxYval = Double.parseDouble(maxYstring);
					densityVal = Integer.parseInt(densityString);
					scaleVal = Double.parseDouble(scaleString);

					if (label.equals("QuadTree")) {
						dataStructure = new QuadTree(maxXval, maxYval,
								densityVal, filepath, colX, colY, separator);

						dataStructure = (QuadTree)dataStructure;
						System.out.println(dataStructure.getDepth());

						 dataStructure.draw(linesBool.getState(),
						 		 pointsBool.getState(), scaleVal);

					} else if (label.equals("Grid")) {
						dataStructure = new SimpleGrid(maxXval, maxYval,
								densityVal, filepath, colX, colY, separator);

						 dataStructure.draw(linesBool.getState(),
						 		 pointsBool.getState(), scaleVal);
					}

				} else {
					changeStatus("Please enter values for required parameters.");
				}
			} else {
				changeStatus("Please select a data file first.");
			}

		} else if (label.equals("Auto")) {
			if (filepath != null) {
				maxXval = maxCoord.getX();
				maxYval = maxCoord.getY();
				scaleVal = ((int) (700/Math.max(maxXval, maxYval) * 1000) / 1)
					/ 1000.0;

				maxX.setText(maxXval + "");
				maxY.setText(maxYval + "");
				scale.setText(scaleVal + "");
			} else {
				changeStatus("Please select a data file first.");
			}
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
