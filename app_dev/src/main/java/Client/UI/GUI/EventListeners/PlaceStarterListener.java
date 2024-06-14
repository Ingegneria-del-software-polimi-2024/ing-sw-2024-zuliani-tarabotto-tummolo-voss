package Client.UI.GUI.EventListeners;

import Client.UI.GUI.CardLabel;
import Client.UI.GUI.GUI;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlaceStarterListener extends MouseAdapter {

    private GUI gui;

    /**
     * during the starterCard selection phase, the listener detects when the starterCard is selected
     * @param gui
     */
    public PlaceStarterListener(GUI gui){
        this.gui = gui;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            CardLabel label = (CardLabel) e.getSource();
            gui.setStarterSelected();
        }
    }
}
