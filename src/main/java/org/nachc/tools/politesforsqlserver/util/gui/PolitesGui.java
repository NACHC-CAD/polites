package org.nachc.tools.politesforsqlserver.util.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PolitesGui extends JFrame {
    private JCheckBox[] checkboxes;
    private JCheckBox selectAllCheckbox;
    private JComboBox<String> comboBox;
    private JButton goButton;

    public PolitesGui() {
        super("Checkbox Application");

        // Set up the JFrame with BorderLayout
        setLayout(new BorderLayout());

        // List of checkbox labels
        String[] labels = {
            "Burn Everything to the Ground",
            "Create Database",
            "Create Database Users",
            "Create Tables",
            "Create CDM Source Record",
            "Load Terminology",
            "Create Sequences for Primary Keys",
            "Create Indexes",
            "Add Constraints"
        };

        // Initialize the checkboxes
        checkboxes = new JCheckBox[labels.length];
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
        
        // Special checkbox for Select/Deselect All
        selectAllCheckbox = new JCheckBox("Select/Deselect All");
        selectAllCheckbox.setForeground(Color.GRAY);  // Set the text color to gray to highlight it is special
        selectAllCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean selected = selectAllCheckbox.isSelected();
                for (JCheckBox checkbox : checkboxes) {
                    checkbox.setSelected(selected);
                }
            }
        });
        checkboxPanel.add(selectAllCheckbox);  // Add this checkbox at the top

        for (int i = 0; i < labels.length; i++) {
            checkboxes[i] = new JCheckBox(labels[i]);
            checkboxes[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            checkboxPanel.add(checkboxes[i]);
        }

        JScrollPane scrollPane = new JScrollPane(checkboxPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Create the combo box and button panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        String[] items = {"Option 1", "Option 2", "Option 3"};
        comboBox = new JComboBox<>(items);
        controlPanel.add(comboBox);

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
        setSize(650, 325);  // Set the initial window size to 650 pixels wide by 325 pixels high
        setLocationRelativeTo(null);  // Center the frame on the screen
        setVisible(true);
    }

    private void handleGoButton() {
        StringBuilder selectedOptions = new StringBuilder("<html>Selected Options:<br>");
        ArrayList<String> selectedLabels = new ArrayList<>();
        boolean anySelected = false;

        for (JCheckBox checkbox : checkboxes) {
            if (checkbox.isSelected()) {
                selectedOptions.append(checkbox.getText()).append("<br>");
                selectedLabels.add(checkbox.getText());
                anySelected = true;
            }
        }

        // Get the selected item from the picklist
        String selectedPicklistItem = (String) comboBox.getSelectedItem();
        selectedOptions.append("Picklist selection: ").append(selectedPicklistItem).append("<br></html>");

        if (anySelected || selectedPicklistItem != null) {
            int response = JOptionPane.showConfirmDialog(
                this,
                selectedOptions.toString(),
                "Confirm Selection",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (response == JOptionPane.OK_OPTION) {
                System.out.println("User confirmed choices:");
                for (String label : selectedLabels) {
                    System.out.println(label);
                }
                System.out.println("Picklist selection: " + selectedPicklistItem);
            } else {
                System.out.println("User canceled operation.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No options selected.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new PolitesGui();
    }
}
