package org.nachc.tools.polites.util.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class GroupPanel extends JPanel {

    public GroupPanel(JPanel parent, String title) {
        super(new FlowLayout(FlowLayout.LEFT)); 
        this.setBorder(BorderFactory.createTitledBorder(title));
        JPanel borderPanel = new JPanel(new BorderLayout());
        borderPanel.add(this, BorderLayout.NORTH); 
        parent.add(borderPanel, BorderLayout.CENTER);
    }

}
