package org.nachc.tools.polites.util.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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

	// ---
	// CREATE CHECK BOXES
	// ---
	
	// select
	private JCheckBox selectAllCheckbox;
	// reset
	private JCheckBox burnEverythingToTheGround = new JCheckBox("Burn Everything to the Ground");
	// create database objects
	private JCheckBox createDatabase = new JCheckBox("Create Database");
	private JCheckBox createDatabaseUsers = new JCheckBox("Create Database Users");
	private JCheckBox createTables = new JCheckBox("Create Tables");
	private JCheckBox createCDMSourceRecord = new JCheckBox("Create CDM Source Record");
	private JCheckBox createLocationAndProviderRecords =  new JCheckBox("Create Dummy Provider and Location Records");
	// terminology
	private JCheckBox truncateTerminology = new JCheckBox("Truncate Terminology");
	private JCheckBox loadTerminology = new JCheckBox("Load Terminology (From Athena Files)");
	private JCheckBox importTerminology = new JCheckBox("Import Terminology (From an Export Zip File)");
	private JCheckBox exportTerminology = new JCheckBox("Export Terminology");
	// sequences, indexes, and constraints
	private JCheckBox createSequencesForPrimaryKeys = new JCheckBox("Create Sequences for Primary Keys");
	private JCheckBox createIndexes = new JCheckBox("Create Indexes");
	private JCheckBox addConstraints = new JCheckBox("Add Constraints");
	private JCheckBox disableConstraints = new JCheckBox("Disable Constraints");
	private JCheckBox enableConstraints = new JCheckBox("Enable Constraints");
	// truncate, import, and export data tables
	private JCheckBox truncateDataTables = new JCheckBox("Truncate (Data Tables Only)");
	private JCheckBox importDataTables = new JCheckBox("Import (Data Tables Only)");
	private JCheckBox exportDataTables = new JCheckBox("Export (Data Tables Only)");
	// truncate, import, and export data tables
	private JCheckBox truncateAll = new JCheckBox("Truncate All CDM Tables");
	private JCheckBox importAll = new JCheckBox("Import All CDM Tables");
	private JCheckBox exportAll = new JCheckBox("Export All CDM Tables");
	// load synthea
	private JCheckBox loadSyntheaCsv = new JCheckBox("Load Synthea CSV files");
	private JCheckBox addWebApiRecords = new JCheckBox("Add WebAPI Records");
	private JCheckBox deleteWebApiRecords = new JCheckBox("Delete WebAPI Records");
	// run achilles
	private JCheckBox createAchillesDatabase = new JCheckBox("Create Achilles Database");
	private JCheckBox runAchilles = new JCheckBox("Run Achilles");
	// other controls
	private JComboBox<String> databaseType, cdmVersion;
	private JButton goButton;

	public PolitesGui() {

		super("Polites");
		setLayout(new BorderLayout());

		//
		// checkboxes
		//

		JPanel checkboxPanel = new JPanel();
		checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));

		// Special checkbox for Select/Deselect All
		selectAllCheckbox = new JCheckBox("Select/Deselect All");
		selectAllCheckbox.setForeground(Color.GRAY);
		selectAllCheckbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ---
				// ADD TO SELECT ALL
				// --
				boolean selected = selectAllCheckbox.isSelected();
				// reset
				burnEverythingToTheGround.setSelected(selected);
				// create database objects
				createDatabase.setSelected(selected);
				createDatabaseUsers.setSelected(selected);
				createTables.setSelected(selected);
				createCDMSourceRecord.setSelected(selected);
				// terminology
				loadTerminology.setSelected(selected);
				truncateTerminology.setSelected(selected);
				importTerminology.setSelected(selected);
				exportTerminology.setSelected(selected);
				// sequences, indexes, and constraints
				createSequencesForPrimaryKeys.setSelected(selected);
				createIndexes.setSelected(selected);
				addConstraints.setSelected(selected);
				disableConstraints.setSelected(selected);
				enableConstraints.setSelected(selected);
				// truncate, import and export data tables
				truncateDataTables.setSelected(selected);
				importDataTables.setSelected(selected);
				exportDataTables.setSelected(selected);
				// truncate, import and export all tables
				truncateAll.setSelected(selected);
				importAll.setSelected(selected);
				exportAll.setSelected(selected);
				// load synthea
				loadSyntheaCsv.setSelected(selected);
				// run achilles
				addWebApiRecords.setSelected(selected);
				deleteWebApiRecords.setSelected(selected);
				createAchillesDatabase.setSelected(selected);
				runAchilles.setSelected(selected);
			}
		});
		
		// ---
		// ADD TO GUI
		// ---
		
		// select
		GroupPanel selectGroup = new GroupPanel(checkboxPanel, "Select");
		selectGroup.add(selectAllCheckbox);
		// reset
		GroupPanel burnGroup = new GroupPanel(checkboxPanel, "Reset");
		burnGroup.add(burnEverythingToTheGround);
		// create database
		GroupPanel createGroup = new GroupPanel(checkboxPanel, "Create Database Objects");
		createGroup.add(createDatabase);
		createGroup.add(createDatabaseUsers);
		createGroup.add(createTables);
		createGroup.add(createCDMSourceRecord);
		// terminology
		GroupPanel terminology = new GroupPanel(checkboxPanel, "Terminology");
		terminology.add(truncateTerminology);
		terminology.add(loadTerminology);
		terminology.add(importTerminology);
		terminology.add(exportTerminology);
		// sequences, indexes, and constraints
		GroupPanel seqIndCon = new GroupPanel(checkboxPanel, "Sequences, Indexes, and Constraints");
		seqIndCon.add(createSequencesForPrimaryKeys);
		seqIndCon.add(createIndexes);
		seqIndCon.add(addConstraints);
		seqIndCon.add(disableConstraints);
		seqIndCon.add(enableConstraints);
		// truncate, import, export data tables (does not include terminology tables)
		GroupPanel impExpDataTables = new GroupPanel(checkboxPanel, "Truncate, Import, and Export Data Tables (Does Not Include Terminology Tables)");
		impExpDataTables.add(truncateDataTables);
		impExpDataTables.add(importDataTables);
		impExpDataTables.add(exportDataTables);
		// truncate, import, export data tables (includes terminology tables)
		GroupPanel impExpAllTables = new GroupPanel(checkboxPanel, "Truncate, Import, and Export All Tables (Includes Terminology Tables)");
		impExpAllTables.add(truncateAll);
		impExpAllTables.add(importAll);
		impExpAllTables.add(exportAll);
		// load synthea data
		GroupPanel loadSynthea = new GroupPanel(checkboxPanel, "Load Synthea CSV Files");
		loadSynthea.add(loadSyntheaCsv);
		// run achilles
		GroupPanel runAchillesGroup = new GroupPanel(checkboxPanel, "Run Achilles");
		runAchillesGroup.add(deleteWebApiRecords);
		runAchillesGroup.add(addWebApiRecords);
		runAchillesGroup.add(createAchillesDatabase);
		runAchillesGroup.add(runAchilles);

		JScrollPane scrollPane = new JScrollPane(checkboxPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);

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

		// ---
		// FINAL LAYOUT
		// ---

		add(scrollPane, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(550, 650);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	//
	// handler for go button
	//

	private void handleGoButton() {
		// ---
		// Build the confirmation message
		// ---
		StringBuilder confirmationMessage = new StringBuilder("You have selected:\n");
		// reset
		if (burnEverythingToTheGround.isSelected())
			confirmationMessage.append("- Burn Everything to the Ground\n");
		// create database objects
		if (createDatabase.isSelected())
			confirmationMessage.append("- Create Database\n");
		if (createDatabaseUsers.isSelected())
			confirmationMessage.append("- Create Database Users\n");
		if (createTables.isSelected())
			confirmationMessage.append("- Create Tables\n");
		if (createCDMSourceRecord.isSelected())
			confirmationMessage.append("- Create CDM Source Record\n");
		// terminology
		if (truncateTerminology.isSelected())
			confirmationMessage.append("- Truncate Terminology\n");
		if (importTerminology.isSelected())
			confirmationMessage.append("- Import Terminology\n");
		if (loadTerminology.isSelected())
			confirmationMessage.append("- Load Terminology\n");
		if (exportTerminology.isSelected())
			confirmationMessage.append("- ExportTerminology\n");
		// sequences, indexes, and constraints
		if (createSequencesForPrimaryKeys.isSelected())
			confirmationMessage.append("- Create Sequences for Primary Keys\n");
		if (createIndexes.isSelected())
			confirmationMessage.append("- Create Indexes\n");
		if (addConstraints.isSelected())
			confirmationMessage.append("- Add Constraints\n");
		if (disableConstraints.isSelected())
			confirmationMessage.append("- Disable Constraints\n");
		if (enableConstraints.isSelected())
			confirmationMessage.append("- Enable Constraints\n");
		// truncate, import, and export data tables
		if(truncateDataTables.isSelected())
			confirmationMessage.append("- Truncate Data Tables\n");
		if(importDataTables.isSelected())
			confirmationMessage.append("- Import Data Tables\n");
		if(exportDataTables.isSelected())
			confirmationMessage.append("- Export Data Tables\n");
		// truncate, import, and export all tables
		if(truncateAll.isSelected())
			confirmationMessage.append("- Truncate All Tables\n");
		if(importAll.isSelected())
			confirmationMessage.append("- Import All Tables\n");
		if(exportAll.isSelected())
			confirmationMessage.append("- Export All Tables\n");
		// load synthea
		if (loadSyntheaCsv.isSelected())
			confirmationMessage.append("- Load Synthea CSV\n");
		// run achilles
		if (addWebApiRecords.isSelected())
			confirmationMessage.append("- Add WebAPI Records\n");
		if (deleteWebApiRecords.isSelected())
			confirmationMessage.append("- Delete WebAPI Records\n");
		if (createAchillesDatabase.isSelected())
			confirmationMessage.append("- Create Achilles Database\n");
		if (runAchilles.isSelected())
			confirmationMessage.append("- Run Achilles\n");

		confirmationMessage.append("Database Type: ").append(databaseType.getSelectedItem()).append("\n");
		confirmationMessage.append("CDM Version: ").append(cdmVersion.getSelectedItem());

		int result = JOptionPane.showConfirmDialog(this, confirmationMessage.toString(), "Continue or Cancel", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			Timer timer = new Timer();
			timer.start();
			ArrayList<String> sel = new ArrayList<String>();
			log.info("User chose to continue with the following selections:");
			// reset
			if (burnEverythingToTheGround.isSelected()) {
				log.info("- Burn Everything to the Ground");
				sel.add("burnEverythingToTheGround");
			}
			// create database objects
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
			// terminology
			if (truncateTerminology.isSelected()) {
				log.info("- Truncate Terminology");
				sel.add("truncateTerminology");
			}
			if (loadTerminology.isSelected()) {
				log.info("- Load Terminology");
				sel.add("loadTerminology");
			}
			if (importTerminology.isSelected()) {
				log.info("- Import Terminology");
				sel.add("importTerminology");
			}
			if (exportTerminology.isSelected()) {
				log.info("- Export Terminology");
				sel.add("exportTerminology");
			}
			// sequences, indexes, and constraints
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
			// truncate, import, and export data tables
			if (truncateDataTables.isSelected()) {
				log.info("- Truncate Data Tables");
				sel.add("truncateDataTables");
			}		
			if (importDataTables.isSelected()) {
				log.info("- Import Data Tables");
				sel.add("importDataTables");
			}		
			if (exportDataTables.isSelected()) {
				log.info("- Export Data Tables");
				sel.add("exportDataTables");
			}		
			// truncate, import, and export all tables
			if (truncateAll.isSelected()) {
				log.info("- Truncate all Tables");
				sel.add("truncateAll");
			}		
			if (importAll.isSelected()) {
				log.info("- Import All Tables");
				sel.add("importAll");
			}		
			if (exportAll.isSelected()) {
				log.info("- Export All Tables");
				sel.add("exportAll");
			}		
			// load synthea
			if (loadSyntheaCsv.isSelected()) {
				log.info("- Load Synthea CSV");
				sel.add("loadSyntheaCsv");
			}
			// add webapi records
			if (addWebApiRecords.isSelected()) {
				log.info("- Add WebAPI Records");
				sel.add("addWebApiRecords");
			}
			// del webapi records
			if (deleteWebApiRecords.isSelected()) {
				log.info("- Delete WebAPI Records");
				sel.add("deleteWebApiRecords");
			}
			// run achilles
			if (createAchillesDatabase.isSelected()) {
				log.info("- Create Achilles Database");
				sel.add("createAchillesDatabase");
			}
			// run achilles
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

