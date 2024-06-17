package org.nachc.tools.polites.util.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.nachc.tools.polites.util.action.ExecutePolitesGoAction;
import org.yaorma.util.time.Timer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PolitesGui extends JFrame {

	private JCheckBox selectAllCheckbox;
	private JCheckBox burnEverythingToTheGround;
	private JCheckBox createDatabase;
	private JCheckBox createDatabaseUsers;
	private JCheckBox createTables;
	private JCheckBox createCDMSourceRecord;
	private JCheckBox loadTerminology;
	private JCheckBox createSequencesForPrimaryKeys;
	private JCheckBox createIndexes;
	private JCheckBox addConstraints;
	private JCheckBox disableConstraints;
	private JCheckBox enableConstraints;
	private JCheckBox loadSyntheaCsv;
	private JCheckBox runAchilles;
	private JComboBox<String> databaseType, cdmVersion;
	private JButton goButton;

	public PolitesGui() {

		super("Polites");
		setLayout(new BorderLayout());

		//
		// checkboxes
		//

		burnEverythingToTheGround = new JCheckBox("Burn Everything to the Ground");
		createDatabase = new JCheckBox("Create Database");
		createDatabaseUsers = new JCheckBox("Create Database Users");
		createTables = new JCheckBox("Create Tables");
		createCDMSourceRecord = new JCheckBox("Create CDM Source Record");
		loadTerminology = new JCheckBox("Load Terminology");
		createSequencesForPrimaryKeys = new JCheckBox("Create Sequences for Primary Keys");
		createIndexes = new JCheckBox("Create Indexes");
		addConstraints = new JCheckBox("Add Constraints");
		disableConstraints = new JCheckBox("Disable Constraints");
		enableConstraints = new JCheckBox("Enable Constraints");
		loadSyntheaCsv = new JCheckBox("Load Synthea CSV files");
		runAchilles = new JCheckBox("Run Achilles");

		JPanel checkboxPanel = new JPanel();
		checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));

		// Special checkbox for Select/Deselect All
		selectAllCheckbox = new JCheckBox("Select/Deselect All");
		selectAllCheckbox.setForeground(Color.GRAY);
		selectAllCheckbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean selected = selectAllCheckbox.isSelected();
				burnEverythingToTheGround.setSelected(selected);
				createDatabase.setSelected(selected);
				createDatabaseUsers.setSelected(selected);
				createTables.setSelected(selected);
				createCDMSourceRecord.setSelected(selected);
				loadTerminology.setSelected(selected);
				createSequencesForPrimaryKeys.setSelected(selected);
				createIndexes.setSelected(selected);
				addConstraints.setSelected(selected);
				disableConstraints.setSelected(selected);
				enableConstraints.setSelected(selected);
				loadSyntheaCsv.setSelected(selected);
				runAchilles.setSelected(selected);
			}
		});
		checkboxPanel.add(selectAllCheckbox);

		// Add individual checkboxes to the panel
		checkboxPanel.add(burnEverythingToTheGround);
		checkboxPanel.add(createDatabase);
		checkboxPanel.add(createDatabaseUsers);
		checkboxPanel.add(createTables);
		checkboxPanel.add(createCDMSourceRecord);
		checkboxPanel.add(loadTerminology);
		checkboxPanel.add(createSequencesForPrimaryKeys);
		checkboxPanel.add(createIndexes);
		checkboxPanel.add(addConstraints);
		checkboxPanel.add(disableConstraints);
		checkboxPanel.add(enableConstraints);
		checkboxPanel.add(loadSyntheaCsv);
		checkboxPanel.add(runAchilles);

		JScrollPane scrollPane = new JScrollPane(checkboxPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());

		//
		// combo boxes and button panel
		//

		JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		databaseType = new JComboBox<>(new String[] { "sqlServer" });
		databaseType.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if ("sqlServer".equals(value)) {
					setText("SQL Server");
				}
				return this;
			}
		});

		cdmVersion = new JComboBox<>(new String[] { "cdm54" });
		cdmVersion.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if ("cdm54".equals(value)) {
					setText("CDM 5.4");
				}
				return this;
			}
		});

		controlPanel.add(databaseType);
		controlPanel.add(cdmVersion);

		goButton = new JButton("Go");
		goButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleGoButton();
			}
		});
		controlPanel.add(goButton);

		//
		// final layout
		//

		add(scrollPane, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(650, 450);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	//
	// handler for go button
	//

	private void handleGoButton() {
		// Build the confirmation message
		StringBuilder confirmationMessage = new StringBuilder("You have selected:\n");
		if (burnEverythingToTheGround.isSelected())
			confirmationMessage.append("- Burn Everything to the Ground\n");
		if (createDatabase.isSelected())
			confirmationMessage.append("- Create Database\n");
		if (createDatabaseUsers.isSelected())
			confirmationMessage.append("- Create Database Users\n");
		if (createTables.isSelected())
			confirmationMessage.append("- Create Tables\n");
		if (createCDMSourceRecord.isSelected())
			confirmationMessage.append("- Create CDM Source Record\n");
		if (loadTerminology.isSelected())
			confirmationMessage.append("- Load Terminology\n");
		if (createSequencesForPrimaryKeys.isSelected())
			confirmationMessage.append("- Create Sequences for Primary Keys\n");
		if (createIndexes.isSelected())
			confirmationMessage.append("- Create Indexes\n");
		if (addConstraints.isSelected())
			confirmationMessage.append("- Add Constraints\n");
		if(disableConstraints.isSelected())
			confirmationMessage.append("- Disable Constraints\n");
		if(enableConstraints.isSelected())
			confirmationMessage.append("- Enable Constraints\n");
		if(loadSyntheaCsv.isSelected())
			confirmationMessage.append("- Load Synthea CSV\n");
		if(runAchilles.isSelected())
			confirmationMessage.append("- Run Achilles\n");

		confirmationMessage.append("Database Type: ").append(databaseType.getSelectedItem()).append("\n");
		confirmationMessage.append("CDM Version: ").append(cdmVersion.getSelectedItem());

		int result = JOptionPane.showConfirmDialog(this, confirmationMessage.toString(), "Continue or Cancel", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			Timer timer = new Timer();
			timer.start();
			ArrayList<String> sel = new ArrayList<String>();
			log.info("User chose to continue with the following selections:");
			if (burnEverythingToTheGround.isSelected()) {
				log.info("- Burn Everything to the Ground");
				sel.add("burnEverythingToTheGround");
			}
			if (createDatabase.isSelected()) {
				log.info("- Create Database");
				sel.add("createDatabase");
			}
			if (createDatabaseUsers.isSelected()) {
				log.info("- Create Database Users");
				sel.add("createDatabaseUsers");
			}
			if (createTables.isSelected()) {
				log.info("- Create Tables");
				sel.add("createTables");
			}
			if (createCDMSourceRecord.isSelected()) {
				log.info("- Create CDM Source Record");
				sel.add("createCDMSourceRecord");
			}
			if (loadTerminology.isSelected()) {
				log.info("- Load Terminology");
				sel.add("loadTerminology");
			}
			if (createSequencesForPrimaryKeys.isSelected()) {
				log.info("- Create Sequences for Primary Keys");
				sel.add("createSequencesForPrimaryKeys");
			}
			if (createIndexes.isSelected()) {
				log.info("- Create Indexes");
				sel.add("createIndexes");
			}
			if (addConstraints.isSelected()) {
				log.info("- Add Constraints");
				sel.add("addConstraints");
			}
			if (disableConstraints.isSelected()) {
				log.info("- Disable Constraints");
				sel.add("disableConstraints");
			}
			if (enableConstraints.isSelected()) {
				log.info("- Enable Constraints");
				sel.add("enableConstraints");
			}
			if (loadSyntheaCsv.isSelected()) {
				log.info("- Load Synthea CSV");
				sel.add("loadSyntheaCsv");
			}
			if (runAchilles.isSelected()) {
				log.info("- Run Achilles");
				sel.add("runAchilles");
			}
			log.info("Database type selected: " + databaseType.getSelectedItem());
			log.info("CDM version selected: " + cdmVersion.getSelectedItem());
			log.info("----------------------------------");
			log.info("CALLING ACTION...");
			ExecutePolitesGoAction.exec(sel, databaseType.getSelectedItem() + "", cdmVersion.getSelectedItem() + "");
			log.info("DONE WITH ACTION:");
			timer.stop();
			String msg = "";
			msg += "\n---------------";
			msg += "\nstart:   " + timer.getStartAsString();
			msg += "\nstop:    " + timer.getStopAsString();
			msg += "\nelapsed: " + timer.getElapsedString();
			msg += "\n---------------";
			log.info("\n" + msg + "\n");
			log.info("Done.");
		} else {
			log.info("User canceled the action.");
		}
	}

}
