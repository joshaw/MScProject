/** Created: Wed 02 Jul 2014 9:55 AM
 * Modified: Wed 06 Aug 2014 09:53 am
 * @author Josh Wainwright
 * filename: Cluster_Analysis.java
 */
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

import java.awt.*;
import java.awt.event.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.JSlider;
import java.io.*;
import ij.plugin.frame.*;
import ij.*;
import ij.process.*;
import ij.io.*;
import ij.gui.*;

import java.util.Timer;
import java.util.TimerTask;
import java.util.HashMap;

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
	private String kernel = "1 1 1\n1 1 1\n1 1 1";
	private boolean kernelChanged = false;
	private boolean changed = false;

	private ClusterStructure dataStructure;
	private DrawQuadTreeMapIJ dij = null;

	private static Frame instance;
	private Panel panel;
	private Panel subpanel1;
	private Panel subpanel2;
	private Label statusMessage;
	private Label fileStatus;

	private Label     densityLab;
	private JSlider   densitySlider;
	private Label     scaleLab;
	private JSlider   scaleSlider;
	private Label     depthLab;
	private JSlider   depthSlider;
	private Label     minClusterLab;
	private JSlider   minClusterSlider;
	private Checkbox  linesBool;
	private Checkbox  pointsBool;
	private Checkbox  coloursBool;
	private Button    autoButton;
	private Button    quadtreeButton;
	private Button    gridButton;

	public Cluster_Analysis() {
		super("Cluster Analysis");
		if (instance != null) {
			instance.toFront();
			return;
		}
		instance = this;

		setLayout(new BorderLayout());
		subpanel1 = new Panel();
		subpanel1.setLayout(new GridLayout(3, 1, 3, 3));

		Panel textpanel = new Panel();
		textpanel.setLayout(new GridLayout(4,1));
		textpanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		densityLab = new Label("Density: 20    ");
		densitySlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 20);
		densitySlider.setMajorTickSpacing(10);
		densitySlider.setSnapToTicks(true);
		densitySlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				int val = ((JSlider)evt.getSource()).getValue();
				val = (val == 0) ? 1: val;
				densityLab.setText("Density: " + val);
				changed = true;
			}
		});
		Panel densityPanel = new Panel();
		densityPanel.add(densityLab);
		densityPanel.add(densitySlider);
		textpanel.add(densityPanel);

		scaleLab = new Label("Scale: 0.03");
		scaleSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 30); //*1000
		scaleSlider.setMajorTickSpacing(10);
		scaleSlider.setSnapToTicks(true);
		scaleSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				int val = ((JSlider)evt.getSource()).getValue();
				scaleLab.setText("Scale: " + (val/1000.0));
				changed = true;
			}
		});
		Panel scalePanel = new Panel();
		scalePanel.add(scaleLab);
		scalePanel.add(scaleSlider);
		textpanel.add(scalePanel);

		depthLab = new Label("Depth Range: 2    ");
		depthSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 2);
		depthSlider.setMajorTickSpacing(1);
		depthSlider.setSnapToTicks(true);
		depthSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				int val = ((JSlider)evt.getSource()).getValue();
				depthLab.setText("Depth Range: " + val);
				changed = true;
			}
		});
		Panel depthPanel = new Panel();
		depthPanel.add(depthLab);
		depthPanel.add(depthSlider);
		textpanel.add(depthPanel);

		minClusterLab = new Label("Cluster Size: 5.0E-4");
		minClusterSlider = new JSlider(JSlider.HORIZONTAL, 0, 200, 50); //*10000
		minClusterSlider.setMajorTickSpacing(10);
		minClusterSlider.setSnapToTicks(true);
		minClusterSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				int val = ((JSlider)evt.getSource()).getValue();
				minClusterLab.setText("Cluster Size: " + (val/100000.0));
				changed = true;
			}
		});
		Panel minClusterPanel = new Panel();
		minClusterPanel.add(minClusterLab);
		minClusterPanel.add(minClusterSlider);
		textpanel.add(minClusterPanel);

		linesBool = new Checkbox("Lines", true);
		subpanel1.add(linesBool);
		pointsBool = new Checkbox("Points", true);
		subpanel1.add(pointsBool);
		coloursBool = new Checkbox("Colourize", true);
		subpanel1.add(coloursBool);

		subpanel2 = new Panel();
		subpanel2.setLayout(new GridLayout(5, 1, 3, 3));

		Button kernelButton = new Button("Kernel");
		kernelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				kernelButtonActionPerformed(evt);
			}
		});
		subpanel2.add(kernelButton);

		Button dataFileButton = new Button("Data file");
		dataFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dataFileButtonActionPerformed(evt);
			}
		});
		subpanel2.add(dataFileButton);

		quadtreeButton = new Button("QuadTree");
		quadtreeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				drawStructureActionPerformed(evt);
			}
		});
		subpanel2.add(quadtreeButton);
		quadtreeButton.setVisible(false);

		gridButton = new Button("Grid");
		gridButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				drawStructureActionPerformed(evt);
			}
		});
		subpanel2.add(gridButton);
		gridButton.setVisible(false);

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
		setResizable (false);
		setVisible(true);
	}

	private void kernelButtonActionPerformed(ActionEvent evt) {
		GenericDialog gd = new GenericDialog("Neighbours Kernel", IJ.getInstance());
		gd.addTextAreas(kernel, null, 10, 30);
		gd.showDialog();
		String kernelText = gd.getNextText();
		if (!kernelText.equals("") && !kernelText.equals(kernel)) {
			kernel = kernelText;
			kernelChanged = true;
		}
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
				colY = cc.getYCol();
				separator = cc.getSeparator();

				/* This is here to allow the "auto" button to be able to
				 * get the max and min values. */
				maxCoord = FileHandler.getMaxCoord( filepath,
						new FileDescriptor(colX, colY, separator));
				quadtreeButton.setVisible(true);
				gridButton.setVisible(true);

				int numPoints = FileHandler.getNumberOfLines(filepath);
				fileStatus.setText("File: "+file+",  Points: "+numPoints);

				autoButtonActionPerformed();
				changed = true;
			}
		}
	}

	private void autoButtonActionPerformed() {
		if (filepath != null) {
			maxXval = maxCoord.getX();
			maxYval = maxCoord.getY();

			// maxX.setText(maxXval + "");
			// maxY.setText(maxYval + "");
		} else {
			changeStatus("Please select a data file first.");
		}
	}

	private void drawStructureActionPerformed(ActionEvent evt) {
		String label = evt.getActionCommand();

		int densityVal       = densitySlider.getValue();
		densityVal = (densityVal == 0) ? 1 : densityVal;
		double scaleVal      = scaleSlider.getValue()/1000.0;
		int depthVal         = depthSlider.getValue()*2;
		double minClusterVal = minClusterSlider.getValue()/100000.0;

		if (filepath != null) {

			changed = changed | kernelChanged;

			if (label.equals("QuadTree")) {

				boolean lines = linesBool.getState();
				boolean points = pointsBool.getState();
				boolean colours = coloursBool.getState();

				System.out.println("#######################" +
						"\nChanged?   : " + changed +
						"\nDensity    : " + densityVal +
						"\nScale      : " + scaleVal +
						"\nDepth Range: " + depthVal +
						"\nCluser Size: " + minClusterVal +
						"\nLines      : " + lines +
						"\nPoints     : " + points +
						"\nColourize  : " + colours +
						"\n#######################");

				long start = System.currentTimeMillis();

				if (changed || dij == null) {
					System.out.println("Generating quadtree...");
					dataStructure = new QuadTree(maxXval, maxYval,
							densityVal, filepath, colX, colY, separator);

					QuadTree qt = (QuadTree)dataStructure;

					System.out.println("Converting to hashmap...");
					qt.addQuadTreeMap();
					System.out.println("Nodes: " + qt.getQuadTreeMap().size());

					System.out.println("Propagating...");
					QuadTreePropagate qtp =
						new QuadTreePropagate(qt, depthVal, kernel);

					System.out.println("Calculating pixels...");
					dij = new DrawQuadTreeMapIJ(
							filepath, qt, maxXval, maxYval, scaleVal);
				}

				System.out.println("Drawing...");
				dij.draw(lines, points, colours, minClusterVal);


				System.out.println("Time: " + (System.currentTimeMillis()-start));
				changed = false;

			} else if (label.equals("Grid")) {
				dataStructure = new SimpleGrid(maxXval, maxYval,
						densityVal, filepath, colX, colY, separator);

				SimpleGrid sg = (SimpleGrid)dataStructure;
				sg.draw();
			}

		} else {
			changeStatus("Please select a data file first.");
		}

	}

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

// count = 0;
// count1 = 0;
// pixel = getPixel(150,50);
// for (i = 0; i < getWidth(); i++) {
// 	width = getWidth() - i;
// 	for (j = 0; j < getHeight(); j++) {
// 		height = getHeight() - j;
// 		pixel = getPixel(i,j);
// 		if (pixel > 0) {
// 			count++;
// 		} else {
// 			count1++;
// 		}
// 	}
// }
// message = toString(count) + "\n" + toString(count1);
// showMessage(message)
