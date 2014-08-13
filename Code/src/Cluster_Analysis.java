/** Created: Wed 02 Jul 2014 9:55 AM
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

	private final int DENSITY_INIT = 20;
	private final int DEPTH_INIT   = 3;
	private final int MAXD_INIT    = 10;
	private final int MINC_INIT    = 50;
	private final int SCALE_INIT   = 30;

	private String     filepath = "";
	private double     maxXval;
	private double     maxYval;
	private int        densityVal;
	private int        depthVal;
	private double     scaleVal;
	private int        colX = 3;
	private int        colY = 4;
	private String     separator = "\t";
	private Coordinate maxCoord;
	private String kernel = "1 1 1\n1 1 1\n1 1 1";
	private boolean kernelChanged = false;
	private boolean changed = false;
	private boolean drawQuadTreeImediately = false;
	private boolean drawGridTreeImediately = false;

	private ClusterStructure dataStructure;
	private DrawQuadTreeMapIJ dij = null;

	private static Frame instance;
	private Panel panel;
	private Panel subpanel1;
	private Panel subpanel2;
	private Label statusMessage;
	private Label fileStatus = new Label("");

	private Label     densityLab;
	private JSlider   densitySlider;
	private Label     scaleLab;
	private JSlider   scaleSlider;
	private Label     depthLab;
	private JSlider   depthSlider;
	private Label     minClusterLab;
	private JSlider   minClusterSlider;
	private Label     maxDepthLab;
	private JSlider   maxDepthSlider;
	private Checkbox  linesBool;
	private Checkbox  pointsBool;
	private Checkbox  coloursBool;
	private Button    defaultsButton;
	private Button    quadtreeButton;
	private Button    gridButton;

	public Cluster_Analysis() {
		super("Cluster Analysis");
		if (instance != null) {
			instance.toFront();
			return;
		}
		instance = this;

		handleOptions();

		setLayout(new BorderLayout());
		subpanel1 = new Panel();
		subpanel1.setLayout(new GridLayout(0, 1, 3, 3));

		Panel textpanel = new Panel();
		textpanel.setLayout(new GridLayout(0,1, 0, 0));
		textpanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		// Density Slider {{{
		densityLab = new Label("Density: "+DENSITY_INIT+"    ");
		densitySlider = new JSlider(JSlider.HORIZONTAL, 0, 100, DENSITY_INIT);
		densitySlider.setMajorTickSpacing(1);
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

		// }}}
		// Depth Slider {{{
		depthLab = new Label("Depth Range: "+DEPTH_INIT+"    ");
		depthSlider = new JSlider(JSlider.HORIZONTAL, 0, 25, DEPTH_INIT);
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

		// }}}
		// Max Depth Slider {{{
		maxDepthLab = new Label("Max Depth: "+MAXD_INIT+"    ");
		maxDepthSlider = new JSlider(JSlider.HORIZONTAL, 5, 25, MAXD_INIT);
		maxDepthSlider.setMajorTickSpacing(1);
		maxDepthSlider.setSnapToTicks(true);
		maxDepthSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				int val = ((JSlider)evt.getSource()).getValue();
				maxDepthLab.setText("Max Depth: " + val);
				changed = true;
			}
		});
		Panel maxDepthPanel = new Panel();
		maxDepthPanel.add(maxDepthLab);
		maxDepthPanel.add(maxDepthSlider);
		textpanel.add(maxDepthPanel);

		// }}}
		// Cluster Size Slider {{{
		minClusterLab = new Label("Cluster Size: "+(MINC_INIT/100000.0)+" ");
		minClusterSlider = new JSlider(JSlider.HORIZONTAL, 0, 200, MINC_INIT); //*10000
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

		// }}}
		// Scale Slider {{{
		scaleLab = new Label("Scale: "+(SCALE_INIT/1000.0)+" ");
		scaleSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, SCALE_INIT); //*1000
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

		// }}}
		linesBool = new Checkbox("Lines", true);
		subpanel1.add(linesBool);
		pointsBool = new Checkbox("Points", true);
		subpanel1.add(pointsBool);
		coloursBool = new Checkbox("Colourize", true);
		subpanel1.add(coloursBool);

		subpanel2 = new Panel();
		subpanel2.setLayout(new GridLayout(0, 1, 0, 0));

		// Data File Button {{{
		Button dataFileButton = new Button("Data file");
		dataFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dataFileButtonActionPerformed();
			}
		});
		subpanel2.add(dataFileButton);

		// }}}
		subpanel2.add(new Label(""));

		// Kernel Button {{{
		Button kernelButton = new Button("Kernel");
		kernelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				kernelButtonActionPerformed(evt);
			}
		});
		subpanel2.add(kernelButton);

		// }}}
		// Defaults Button {{{
		defaultsButton = new Button("Defaults");
		defaultsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				defaultsButtonActionPerformed();
			}
		});
		subpanel2.add(defaultsButton);

		// }}}
		subpanel2.add(new Label(""));

		// QuadTree Button {{{
		quadtreeButton = new Button("QuadTree");
		quadtreeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				drawStructureActionPerformed(evt);
			}
		});
		subpanel2.add(quadtreeButton);
		if (filepath.equals("")) {
			quadtreeButton.setVisible(false);
		}

		// }}}
		// Grid Button {{{
		gridButton = new Button("Grid");
		gridButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				drawStructureActionPerformed(evt);
			}
		});
		subpanel2.add(gridButton);
		if (filepath.equals("")) {
			gridButton.setVisible(false);
		}
		// }}}

		panel = new Panel();
		panel.setLayout(new FlowLayout());
		panel.add(textpanel);
		panel.add(subpanel1);
		panel.add(subpanel2);

		this.add(panel, BorderLayout.CENTER);
		statusMessage = new Label("");
		this.add(statusMessage, BorderLayout.NORTH);
		this.add(fileStatus, BorderLayout.SOUTH);

		if (drawQuadTreeImediately) {
			ActionEvent evt = new ActionEvent(new Object(), 100, "QuadTree");
			drawStructureActionPerformed(evt);
		}
		if (drawGridTreeImediately) {
			ActionEvent evt = new ActionEvent(new Object(), 100, "Grid");
			drawStructureActionPerformed(evt);
		}

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

	private void dataFileButtonActionPerformed() {
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

				performAutoActions(file);

				quadtreeButton.setVisible(true);
				gridButton.setVisible(true);
			}
		}
	}

	private void performAutoActions(String file) {
		/* This is here to allow the "auto" button to be able to
		 * get the max and min values. */
		maxCoord = FileHandler.getMaxCoord( filepath,
				new FileDescriptor(colX, colY, separator));

		int numPoints = FileHandler.getNumberOfLines(filepath);
		String filename = FileHandler.getFileName(file);
		fileStatus.setText("File: "+filename+",  Points: "+numPoints);

		autoButtonActionPerformed();
		changed = true;
	}

	private void defaultsButtonActionPerformed() {
		densitySlider.setValue(DENSITY_INIT);
		depthSlider.setValue(DEPTH_INIT);
		maxDepthSlider.setValue(MAXD_INIT);
		minClusterSlider.setValue(MINC_INIT);
		scaleSlider.setValue(SCALE_INIT);
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
		int maxDepthVal      = maxDepthSlider.getValue();

		if (filepath != null) {

			changed = changed | kernelChanged;

			if (label.equals("QuadTree")) {

				boolean lines = linesBool.getState();
				boolean points = pointsBool.getState();
				boolean colours = coloursBool.getState();

				System.out.println("#######################" +
						"\nChanged?   : " + changed +
						"\nDensity    : " + densityVal +
						"\nDepth Range: " + depthVal +
						"\nMax Depth  : " + maxDepthVal +
						"\nScale      : " + scaleVal +
						"\nCluser Size: " + minClusterVal +
						"\nLines      : " + lines +
						"\nPoints     : " + points +
						"\nColourize  : " + colours +
						"\nMax X/Max Y: " + maxCoord.getX() + "/" + maxCoord.getY() +
						"\n#######################");

				long start = System.currentTimeMillis();

				if (changed || dij == null) {
					System.out.println("Generating quadtree...");
					dataStructure = new QuadTree(maxXval, maxYval, densityVal,
							maxDepthVal, filepath, colX, colY, separator);

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

	/** Handle the optional parameters that can be passed to a plugin from
	 * ImageJ using the run("Command", "parameters"); macro command.
	 * Supports
	 *     filepath=/path/to/file  Data file to read from
	 *     sep=\t                  Separator to use when reading file
	 *     quadtree=1              Draw quadtree without interaction
	 *     grid=1                  Draw grid without interaction
	 */
	private void handleOptions() {
		String options = Macro.getOptions();
		String optionsOrig = options;
		options = options.toLowerCase();
		if (options != null) {
			if (options.indexOf("sep=") != -1) {
				int sepp = options.indexOf("sep=");
				int comma = options.indexOf(",", sepp);
				comma = (comma == -1)? Integer.MAX_VALUE: comma;
				int end = Math.min(comma , options.length()-1);
				String seps = options.substring(sepp, end);
				seps = seps.replaceAll("sep=", "");
				this.separator = seps;
			}
			if (options.indexOf("filepath=") != -1) {
				int fp = options.indexOf("filepath=");
				int comma = options.indexOf(",", fp);
				comma = (comma == -1)? Integer.MAX_VALUE: comma;
				int end = Math.min(comma , options.length()-1);
				String fps = optionsOrig.substring(fp, end);
				fps = fps.replaceAll("filepath=", "");
				if (!(new File(fps)).exists()) {
					throw new IllegalArgumentException("File, " + fps + " not found.");
				}
				this.filepath = fps;
				performAutoActions(filepath);
			}
			if (options.indexOf("quadtree=1") != -1) {
				drawQuadTreeImediately = true;
			}
			if (options.indexOf("grid=1") != -1) {
				drawGridTreeImediately = true;
			}
		}

	}

	/** ------------------------------------------------------------
	 * TIMER TASK
	 * ----------------------------------------------------------- */
	class StatusTask extends TimerTask {
		public void run() {
			statusMessage.setText("");
			timer.cancel();
		}
	}
	// ------------------------------------------------------------

}
