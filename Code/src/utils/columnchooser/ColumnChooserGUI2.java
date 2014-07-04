package utils.columnchooser;

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
import javax.swing.table.DefaultTableModel;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ColumnChooserGUI2 extends JPanel {

	private static final long serialVersionUID = 1L;

	private final int rows = 10;
	private String filepath;

	// Variables declaration - do not modify
	private javax.swing.ButtonGroup buttonGroup2;
	private javax.swing.JLabel xColLab;
	private javax.swing.JLabel yColLab;
	private javax.swing.JTable jTable1;
	private javax.swing.JTextField otherSepField;
	private javax.swing.JTextField xColField;
	private javax.swing.JTextField yColField;
	private javax.swing.JButton okButton;
	private javax.swing.JPanel optionsPanel;
	private javax.swing.JRadioButton otherSepCheck;
	private javax.swing.JButton readFileButton;
	private javax.swing.JScrollPane scrollPanel;
	private javax.swing.JRadioButton spaceSepCheck;
	private javax.swing.JRadioButton tabSepCheck;
	private javax.swing.JTabbedPane tabbedPane;

	private String[][] entries;
	private String[] columnNames;
	private String[] columnNumbers;
	private String separator = "\t";
	private int xCol;
	private int yCol;
	// End of variables declaration

	public ColumnChooserGUI2(String filepath) {
		this.filepath = filepath;

		readFile();
		initComponents();
		JFrame frame2 = new JFrame("Choose Data ...");
		this.setOpaque(true);
		frame2.setContentPane(this);

		System.out.println("GUI 2");
		frame2.setVisible(true);
		frame2.setSize(800, 250);
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
							System.out.println("File is not formatted correctly");
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

    private void initComponents() {

        buttonGroup2 = new javax.swing.ButtonGroup();
        scrollPanel = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        optionsPanel = new javax.swing.JPanel();
        tabSepCheck = new javax.swing.JRadioButton("Tabs");
        spaceSepCheck = new javax.swing.JRadioButton("Spaces");
        otherSepCheck = new javax.swing.JRadioButton("Other");
        otherSepField = new javax.swing.JTextField();
        readFileButton = new javax.swing.JButton("Read File");
        okButton = new javax.swing.JButton("OK");
        xColField = new javax.swing.JTextField();
        xColLab = new javax.swing.JLabel("X Column");
        yColLab = new javax.swing.JLabel("Y Column");
        yColField = new javax.swing.JTextField();

        scrollPanel.setMaximumSize(new java.awt.Dimension(32767, 31767));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(entries, columnNumbers));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        scrollPanel.setViewportView(jTable1);

        buttonGroup2.add(tabSepCheck);
        tabSepCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabSepCheckActionPerformed(evt);
            }
        });

        buttonGroup2.add(spaceSepCheck);
        spaceSepCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spaceSepCheckActionPerformed(evt);
            }
        });

        buttonGroup2.add(otherSepCheck);
        otherSepCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherSepCheckActionPerformed(evt);
            }
        });

        otherSepField.setColumns(6);

        readFileButton.setText("Read file");
        readFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                readFileButtonActionPerformed(evt);
            }
        });

        xColField.setColumns(6);
        xColField.setToolTipText("Column to use for the x coordinate.");
        xColField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xColFieldActionPerformed(evt);
            }
        });

        yColField.setColumns(6);
        yColField.setToolTipText("Column to use for the y coordinate.");
        yColField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yColFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout optionsPanelLayout = new javax.swing.GroupLayout(optionsPanel);
        optionsPanel.setLayout(optionsPanelLayout);
        optionsPanelLayout.setHorizontalGroup(
            optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(optionsPanelLayout.createSequentialGroup()
                        .addComponent(readFileButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(okButton)
                        .addContainerGap())
                    .addGroup(optionsPanelLayout.createSequentialGroup()
                        .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(optionsPanelLayout.createSequentialGroup()
                                .addComponent(otherSepCheck)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(otherSepField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tabSepCheck)
                            .addComponent(spaceSepCheck))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(optionsPanelLayout.createSequentialGroup()
                                .addComponent(yColLab)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(yColField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12))
                            .addGroup(optionsPanelLayout.createSequentialGroup()
                                .addComponent(xColLab)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(xColField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
        );
        optionsPanelLayout.setVerticalGroup(
            optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tabSepCheck)
                    .addComponent(xColLab)
                    .addComponent(xColField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spaceSepCheck)
                    .addComponent(yColLab)
                    .addComponent(yColField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(otherSepCheck)
                    .addComponent(otherSepField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(readFileButton)
                    .addComponent(okButton))
                .addGap(114, 114, 114))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(optionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(optionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>

    private void tabSepCheckActionPerformed(java.awt.event.ActionEvent evt) {
    	separator = "\t";
    }

    private void spaceSepCheckActionPerformed(java.awt.event.ActionEvent evt) {
    	separator = " ";
    }

    private void otherSepCheckActionPerformed(java.awt.event.ActionEvent evt) {
        separator = otherSepField.getText();
    }

    private void xColFieldActionPerformed(java.awt.event.ActionEvent evt) {
        xCol = Integer.parseInt(xColField.getText());
    }

    private void yColFieldActionPerformed(java.awt.event.ActionEvent evt) {
        yCol = Integer.parseInt(yColField.getText());
    }

    private void readFileButtonActionPerformed(java.awt.event.ActionEvent evt) {
		System.out.println("Re-reading file");
		readFile();
		repaint();
    }

    public String getSeparator() { return separator; }
    public int getXCol() { return xCol; }
    public int getYCol() { return yCol; }

}
