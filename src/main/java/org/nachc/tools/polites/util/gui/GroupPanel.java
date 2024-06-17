package org.nachc.tools.polites.util.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class GroupPanel extends JPanel {

    public GroupPanel(JPanel parent, String title) {
    	// set the layout of this panel
        super();
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        // add the title border
        Border titledBorder = BorderFactory.createTitledBorder(title);
        this.setBorder(titledBorder);
        // add the jpanel
        JPanel borderPanel = new JPanel(new BorderLayout());
        borderPanel.add(this, BorderLayout.NORTH); 
        // set the offsets of the borders
        Border padding = BorderFactory.createEmptyBorder(0, 10, 0, 10);
        parent.setBorder(padding);
        // add the whole thing to the parent
        parent.add(borderPanel, BorderLayout.CENTER);
    }

}
