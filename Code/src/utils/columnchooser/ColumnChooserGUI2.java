/** Created: Wed 4 Jul 2014 12:52 PM
 * @author Josh Wainwright
 * filename: ColumnChooserGUI2.java
 */

package utils.columnchooser;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JTabbedPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.table.DefaultTableModel;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/** Class to disply a dialog window to choose which columns from a file should
 * be used.
 * Majority of code generated with NetBeans.
 */
public class ColumnChooserGUI2 extends JPanel {

	private static final long serialVersionUID = 1L;

	private final int rows = 10;
	private String filepath;

	private JDialog frame2;
	private ButtonGroup buttonGroup2;
	private JLabel xColLab;
	private JLabel yColLab;
	private JTable jTable1;
	private JTextField otherSepField;
	private JTextField xColField;
	private JTextField yColField;
	private JButton okButton;
	private JButton cancelButton;
	private JPanel optionsPanel;
	private JRadioButton otherSepCheck;
	private JButton readFileButton;
	private JScrollPane scrollPanel;
	private JRadioButton spaceSepCheck;
	private JRadioButton tabSepCheck;
	private JTabbedPane tabbedPane;

	private String[][] entries;
	private String[] columnNames;
	private String[] columnNumbers;
	private String separator = "\t";
	private int xCol;
	private int yCol;
	private boolean success;
	// End of variables declaration

	public ColumnChooserGUI2(String filepath) {
		this.filepath = filepath;

		readFile();
		initComponents();
		frame2 = new JDialog();
		this.setOpaque(true);
		frame2.setContentPane(this);

		if (columnNames.length < 2) {
			okButton.setEnabled(false);
		}

		frame2.setModal(true);
		frame2.setPreferredSize(new Dimension(800, 410));
		frame2.pack();
		frame2.setVisible(true);
		// frame2.setResizable(false);
	}

	private void readFile() {

		if (!filepath.equals("")) {
			BufferedReader reader = null;

			try {
				reader = new BufferedReader(new FileReader(filepath));
				String line = null;

				line = reader.readLine();
				columnNames = line.split(separator);
				int numCols = columnNames.length;
				entries = new String[rows+1][numCols];

				for (int i = 0; i < columnNames.length; i++) {
					entries[0][i] = columnNames[i];
				}
				columnNumbers = new String[columnNames.length];
				for (int i = 0; i < columnNames.length; i++) {
					columnNumbers[i] = i + "";
				}

				String[] lineArray;
				int countFile = 1;
				while (countFile < rows && (line = reader.readLine()) != null) {

					lineArray = line.split(separator);
					for (int i = 0; i < numCols; i++) {
						try {
							entries[countFile][i] = lineArray[i];
						} catch(ArrayIndexOutOfBoundsException e){
							System.err.println("File is not formatted correctly");
							break;
						}
					}

					countFile++;
				}

			} catch (IOException e) {
				System.err.println("Error: Could not open file " + filepath);
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch(IOException e){
						e.printStackTrace();
					}
				}
			}

		}
	}

	private void tabSepCheckActionPerformed(ActionEvent evt) {
		separator = "\t";
	}

	private void spaceSepCheckActionPerformed(ActionEvent evt) {
		separator = " ";
	}

	private void readFileButtonActionPerformed(ActionEvent evt) {
		if (otherSepCheck.isSelected()) {
			separator = otherSepField.getText();
		}

		readFile();
		jTable1.setModel(new DefaultTableModel(entries, columnNumbers));

		if (columnNames.length >= 2) {
			okButton.setEnabled(true);

			if (columnNames.length == 2) {
				xColField.setText("0");
				yColField.setText("1");
			}
		}
	}

	private void okButtonActionPerformed(ActionEvent evt) {
		xCol = Integer.parseInt(xColField.getText());
		yCol = Integer.parseInt(yColField.getText());
		this.success = true;
		frame2.dispose();
	}

	private void cancelButtonActionPerformed(ActionEvent evt) {
		this.success = false;
		frame2.dispose();
	}

	public String getSeparator() { return separator; }
	public int getXCol() { return xCol; }
	public int getYCol() { return yCol; }
	public boolean getSuccess() { return success; }

	private void initComponents() {

		scrollPanel = new JScrollPane();
		jTable1 = new JTable();
		optionsPanel = new JPanel();
		buttonGroup2 = new ButtonGroup();
		tabSepCheck = new JRadioButton("Tabs");
		spaceSepCheck = new JRadioButton("Spaces");
		otherSepCheck = new JRadioButton("Other");
		otherSepField = new JTextField();
		readFileButton = new JButton("Read File");
		okButton = new JButton("OK");
		xColField = new JTextField();
		xColLab = new JLabel("X Column");
		yColLab = new JLabel("Y Column");
		yColField = new JTextField();
		cancelButton = new JButton("Cancel");

		scrollPanel.setMaximumSize(new java.awt.Dimension(32767, 31767));

		jTable1.setModel(new DefaultTableModel(entries, columnNumbers));
		jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jTable1.setColumnSelectionAllowed(true);
    	jTable1.setRowSelectionAllowed(false);
		scrollPanel.setViewportView(jTable1);

		buttonGroup2.add(tabSepCheck);
		tabSepCheck.setSelected(true);
		tabSepCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				tabSepCheckActionPerformed(evt);
			}
		});

		buttonGroup2.add(spaceSepCheck);
		spaceSepCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				spaceSepCheckActionPerformed(evt);
			}
		});

		buttonGroup2.add(otherSepCheck);

		otherSepField.setColumns(6);

		readFileButton.setText("Read file");
		readFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				readFileButtonActionPerformed(evt);
			}
		});

		okButton.setText("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				okButtonActionPerformed(evt);
			}
		});

		xColField.setColumns(6);
		xColField.setText("3");
		xColField.setToolTipText("Column to use for the x coordinate.");
		xColLab.setText("X Column");

		yColLab.setText("Y Column");
		yColField.setColumns(6);
		yColField.setText("4");
		yColField.setToolTipText("Column to use for the y coordinate.");

		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cancelButtonActionPerformed(evt);
			}
		});

		GroupLayout optionsPanelLayout = new GroupLayout(optionsPanel);
		optionsPanel.setLayout(optionsPanelLayout);
		optionsPanelLayout.setHorizontalGroup(
				optionsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(optionsPanelLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(optionsPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addGroup(optionsPanelLayout.createSequentialGroup()
							.addGroup(optionsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(optionsPanelLayout.createSequentialGroup()
									.addComponent(otherSepCheck)
									.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(otherSepField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(tabSepCheck)
								.addComponent(spaceSepCheck))
							.addGap(18, 18, 18)
							.addGroup(optionsPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addGroup(optionsPanelLayout.createSequentialGroup()
									.addComponent(yColLab)
									.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(yColField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(optionsPanelLayout.createSequentialGroup()
									.addComponent(xColLab)
									.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(xColField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGroup(optionsPanelLayout.createSequentialGroup()
							.addComponent(readFileButton)
							.addGap(142, 142, 142)
							.addComponent(cancelButton)))
							.addGap(18, 18, 18)
							.addComponent(okButton)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							);
		optionsPanelLayout.setVerticalGroup(
				optionsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(optionsPanelLayout.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(tabSepCheck)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(spaceSepCheck)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(optionsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(otherSepCheck)
						.addComponent(otherSepField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18, 18, 18)
					.addGroup(optionsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(readFileButton)
						.addComponent(okButton)
						.addComponent(cancelButton))
					.addGap(114, 114, 114))
				.addGroup(optionsPanelLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(optionsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(xColLab)
						.addComponent(xColField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(optionsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(yColLab)
						.addComponent(yColField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(scrollPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(optionsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addComponent(scrollPanel, GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(optionsPanel, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE))
				);
	}// </editor-fold>

}
