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
	private static Frame instance;

	private QuadTree main = null;

	public Cluster_Analysis() {
		super("Cluster Analysis");
		if (instance != null) {
			instance.toFront();
			return;
		}
		instance = this;
		addKeyListener(IJ.getInstance());

		setLayout(new FlowLayout());
		panel = new Panel();
		panel.setLayout(new GridLayout(4, 4, 5, 5));

		addButton("Draw");
		addButton("Choose file");

		Label fileStatus = new Label("File:");
		add(fileStatus);
		add(panel);

		pack();
		GUI.center(this);
		setVisible(true);
	}

	void addButton(String label) {
		Button b = new Button(label);
		b.addActionListener(this);
		b.addKeyListener(IJ.getInstance());
		panel.add(b);
	}

	public void actionPerformed(ActionEvent e) {
		String label = e.getActionCommand();

		if (label.equals("Choose file")) {
			OpenDialog od = new OpenDialog("Open image ...");
			String fileName = od.getDirectory() + od.getFileName();

			main = new QuadTree(41000.0, 41000.0, 20, 0.012,
					fileName, true, true);

		} else if (label.equals("Draw")) {
			if (main == null) {
				IJ.showMessage("Please select a file.");
			} else {
				main.draw();
			}
		}
	}

}
