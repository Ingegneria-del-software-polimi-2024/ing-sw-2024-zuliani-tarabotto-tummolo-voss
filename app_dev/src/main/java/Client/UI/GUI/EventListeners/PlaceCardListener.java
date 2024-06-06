package Client.UI.GUI.EventListeners;

import Client.UI.GUI.CardLabel;
import Client.UI.GUI.GUI;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlaceCardListener extends MouseAdapter {
    //TODO

    private GUI gui;

    public PlaceCardListener(GUI gui){
        this.gui = gui;
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            CardLabel label = (CardLabel) e.getSource();
            gui.setSelectedCard(label.getCard());
            System.out.println("Right click detected at (" + e.getX() + ", " + e.getY() + ")");
        }
    }

}
