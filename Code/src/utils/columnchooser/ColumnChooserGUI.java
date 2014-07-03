// Created:  Thu 03 Jul 2014 12:21 PM
// Modified: Thu 03 Jul 2014 12:21 PM

package utils.columnchooser;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ColumnChooserGUI extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	String[] columnNames;
	String[][] fileHead;

	public static void showChooser(String[] columnNames, String[][] fileHead) {
		new ColumnChooserGUI(columnNames, fileHead);
	}

	public ColumnChooserGUI(String[] columnNames, String[][] fileHead) {
		super(new GridLayout(1,0));

		this.columnNames = columnNames;
		this.fileHead = fileHead;

		final JTable table = new JTable(fileHead, columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);

		JFrame frame = new JFrame("SimpleTableDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SimpleTableDemo newContentPane = new SimpleTableDemo();
		newContentPane.setOpaque(true); //content panes must be opaque
		frame.setContentPane(newContentPane);

		frame.pack();
		frame.setVisible(true);
		// new JScrollPane(myTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		// 			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}

	public void actionPerformed(ActionEvent e) {
	}
}
