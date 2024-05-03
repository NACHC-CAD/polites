package org.nachc.tools.politesforsqlserver.util.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JComboBox<String> databaseType, cdmVersion;
    private JButton goButton;

    public PolitesGui() {
        super("Polites");

        // Set up the JFrame with BorderLayout
        setLayout(new BorderLayout());

        // Initialize checkboxes
        burnEverythingToTheGround = new JCheckBox("Burn Everything to the Ground");
        createDatabase = new JCheckBox("Create Database");
        createDatabaseUsers = new JCheckBox("Create Database Users");
        createTables = new JCheckBox("Create Tables");
        createCDMSourceRecord = new JCheckBox("Create CDM Source Record");
        loadTerminology = new JCheckBox("Load Terminology");
        createSequencesForPrimaryKeys = new JCheckBox("Create Sequences for Primary Keys");
        createIndexes = new JCheckBox("Create Indexes");
        addConstraints = new JCheckBox("Add Constraints");

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

        JScrollPane scrollPane = new JScrollPane(checkboxPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Combo boxes and button panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        databaseType = new JComboBox<>(new String[]{"sqlServer", "databricks"});
        databaseType.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if ("sqlServer".equals(value)) {
                    setText("SQL Server");
                } else if ("databricks".equals(value)) {
                    setText("Databricks");
                }
                return this;
            }
        });
        cdmVersion = new JComboBox<>(new String[]{"cdm53"});
        cdmVersion.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText("CDM 5.3");
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

        add(scrollPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 325);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void handleGoButton() {
        // Build the confirmation message
        StringBuilder confirmationMessage = new StringBuilder("You have selected:\n");
        if (burnEverythingToTheGround.isSelected()) confirmationMessage.append("- Burn Everything to the Ground\n");
        if (createDatabase.isSelected()) confirmationMessage.append("- Create Database\n");
        if (createDatabaseUsers.isSelected()) confirmationMessage.append("- Create Database Users\n");
        if (createTables.isSelected()) confirmationMessage.append("- Create Tables\n");
        if (createCDMSourceRecord.isSelected()) confirmationMessage.append("- Create CDM Source Record\n");
        if (loadTerminology.isSelected()) confirmationMessage.append("- Load Terminology\n");
        if (createSequencesForPrimaryKeys.isSelected()) confirmationMessage.append("- Create Sequences for Primary Keys\n");
        if (createIndexes.isSelected()) confirmationMessage.append("- Create Indexes\n");
        if (addConstraints.isSelected()) confirmationMessage.append("- Add Constraints\n");

        confirmationMessage.append("Database Type: ").append(databaseType.getSelectedItem()).append("\n");
        confirmationMessage.append("CDM Version: ").append(cdmVersion.getSelectedItem());

        int result = JOptionPane.showConfirmDialog(this, confirmationMessage.toString(), "Continue or Cancel", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            System.out.println("User chose to continue with the following selections:");
            if (burnEverythingToTheGround.isSelected()) System.out.println("- Burn Everything to the Ground");
            if (createDatabase.isSelected()) System.out.println("- Create Database");
            if (createDatabaseUsers.isSelected()) System.out.println("- Create Database Users");
            if (createTables.isSelected()) System.out.println("- Create Tables");
            if (createCDMSourceRecord.isSelected()) System.out.println("- Create CDM Source Record");
            if (loadTerminology.isSelected()) System.out.println("- Load Terminology");
            if (createSequencesForPrimaryKeys.isSelected()) System.out.println("- Create Sequences for Primary Keys");
            if (createIndexes.isSelected()) System.out.println("- Create Indexes");
            if (addConstraints.isSelected()) System.out.println("- Add Constraints");
            System.out.println("Database type selected: " + databaseType.getSelectedItem());
            System.out.println("CDM version selected: " + cdmVersion.getSelectedItem());
        } else {
            System.out.println("User canceled the action.");
        }
    }

    public static void main(String[] args) {
        new PolitesGui();
    }
}
