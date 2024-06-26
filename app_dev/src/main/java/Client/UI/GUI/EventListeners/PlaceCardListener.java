package Client.UI.GUI.EventListeners;

import Client.UI.GUI.CardLabel;
import Client.UI.GUI.GUI;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The type Place card listener.
 */
public class PlaceCardListener extends MouseAdapter {

    private GUI gui;

    /**
     * during the card selection phase, the listener detects which one of the three cards from the hand the player wants to play
     *
     * @param gui the gui
     */
    public PlaceCardListener(GUI gui){
        this.gui = gui;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            CardLabel label = (CardLabel) e.getSource();
            gui.setSelectedCard(label.getCard());
        }
    }

}
