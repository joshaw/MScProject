import java.awt.*;
import java.awt.event.*;
import java.io.*;
import ij.plugin.frame.*;
import ij.*;
import ij.process.*;
import ij.io.*;
import ij.gui.*;

import quadtree.data.*;

public class Cluster_Analysis extends PlugInFrame implements ActionListener {

	private Panel panel;
	private Panel subpanel1;
	private Panel subpanel2;
	private static Frame instance;
	private Label statusMessage;
	private Label fileStatus;

	private String fileName;
	private TextField maxX;
	private double maxXval;
	private TextField maxY;
	private double maxYval;
	private TextField density;
	private int densityVal;
	private Checkbox linesBool;
	private Checkbox pointsBool;

	private QuadTree main = null;

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
			textpanel.setLayout(new GridLayout(3,2));
			textpanel.setComponentOrientation(
					ComponentOrientation.LEFT_TO_RIGHT);

				Label maxXLab = new Label("Max X");
				maxX = new TextField("41000", 10);
				maxX.addKeyListener(new NumberKeyListener());
				textpanel.add(maxXLab);
				textpanel.add(maxX);

				Label maxYLab = new Label("Max Y");
				maxY = new TextField("41000", 10);
				maxY.addKeyListener(new NumberKeyListener());
				textpanel.add(maxYLab);
				textpanel.add(maxY);

				Label densityLab = new Label("Density");
				density = new TextField("20", 10);
				density.addKeyListener(new NumberKeyListener());
				textpanel.add(densityLab);
				textpanel.add(density);

			linesBool = new Checkbox("Lines", true);
			subpanel1.add(linesBool);
			pointsBool = new Checkbox("Points", true);
			subpanel1.add(pointsBool);

		subpanel2 = new Panel();
		subpanel2.setLayout(new GridLayout(2, 1, 3, 3));

		addButton("Data file", subpanel2);
		addButton("Draw", subpanel2);

		panel = new Panel();
		panel.setLayout(new FlowLayout());
		panel.add(textpanel);
		panel.add(subpanel1);
		panel.add(subpanel2);

		this.add(panel, BorderLayout.CENTER);
		statusMessage = new Label("Go");
		this.add(statusMessage, BorderLayout.NORTH);
		fileStatus = new Label("File:");
		this.add(fileStatus, BorderLayout.SOUTH);

		pack();
		GUI.center(this);
		setVisible(true);
	}

	void addButton(String label, Panel p) {
		Button b = new Button(label);
		b.addActionListener(this);
		b.addKeyListener(IJ.getInstance());
		p.add(b);
	}

	public void actionPerformed(ActionEvent e) {
		String label = e.getActionCommand();

		if (label.equals("Data file")) {
			OpenDialog od    = new OpenDialog("Open data file ...");
			String file      = od .getFileName();
			String directory = od.getDirectory();
			fileName  = directory + file;
			fileStatus.setText("File: " + file);

		} else if (label.equals("Draw")) {
			maxXval = Double.parseDouble(removeSpaces(maxX.getText()));
			maxYval = Double.parseDouble(removeSpaces(maxY.getText()));
			densityVal = Integer.parseInt(removeSpaces(density.getText()));
			main = new QuadTree(maxXval, maxYval, densityVal, fileName);
			main.draw(linesBool.getState(), pointsBool.getState(), 0.012);
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
			if (Character.isDigit(k) ||
					c == KeyEvent.VK_SHIFT ||
					c == KeyEvent.VK_BACK_SPACE ||
					c == KeyEvent.VK_ENTER ||
					c == KeyEvent.VK_SPACE
					) {
				statusMessage.setText("");
			} else {
				statusMessage.setText(k + " Please enter a number");
			}
		}

		public void keyReleased(KeyEvent e) { }
	}
	// ------------------------------------------------------------

	private String removeSpaces(String string) {
		return string.replaceAll("\\s","");
	}

}
