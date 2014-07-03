// Created:  Thu 03 Jul 2014 12:21 PM
// Modified: Thu 03 Jul 2014 05:09 PM

package utils.columnchooser;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ColumnChooserGUI extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	String[] columnNames;
	String[][] fileHead;
	boolean[] colStates;

	public static void showChooser(String[] columnNames, String[][] fileHead) {
		new ColumnChooserGUI(columnNames, fileHead);
	}

	public ColumnChooserGUI(String[] columnNames, String[][] fileHead) {
		super();
		super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.columnNames = columnNames;
		this.fileHead = fileHead;
		colStates = new boolean[columnNames.length];
		for (int i = 0; i < columnNames.length; i++) {
			colStates[i] = false;
		}

		JPanel checkboxPanel = new JPanel();
		checkboxPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
		for (int i = 0; i < columnNames.length; i++) {
			JPanel cbpanel = new JPanel();
			JCheckBox cb = new JCheckBox(i+"");
			cb.addActionListener(this);
			cbpanel.add(cb);
			checkboxPanel.add(cbpanel);
		}

		final JTable table = new JTable(fileHead, columnNames);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JTableHeader header = table.getTableHeader();

		for (int i = 0; i < columnNames.length; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(10);
		}

		JPanel tableBox = new JPanel();
		tableBox.setLayout(new BoxLayout(tableBox, BoxLayout.Y_AXIS));
		tableBox.add(checkboxPanel);
		tableBox.add(header);
		tableBox.add(table);

		JScrollPane scrollPane = new JScrollPane(tableBox,
				JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(scrollPane);

		Button okButton = new Button("OK");
		okButton.addActionListener(this);
		this.add(okButton);

		JFrame frame = new JFrame("Choose columns ...");
		this.setOpaque(true);
		frame.setContentPane(this);

		frame.setVisible(true);
		frame.setSize(800, 250);
		frame.setResizable(false);
	}

	public void actionPerformed(ActionEvent e) {

		Object obj = e.getSource();

		if (obj instanceof JCheckBox) {
			JCheckBox cb = (JCheckBox) obj;
			int colNum = Integer.parseInt(cb.getText());
			colStates[colNum] = cb.isSelected();

		} else if (obj instanceof Button) {
			System.out.println("Message");
			int trues = 0;
			for (int i = 0; i < colStates.length; i++) {
				if (colStates[i] && trues == 0) {
					trues = 10*(i+1);
				} else if (colStates[i]) {
					trues += i+1;
					break;
				}
			}
			System.out.println(trues +"");
		}
	}
}
