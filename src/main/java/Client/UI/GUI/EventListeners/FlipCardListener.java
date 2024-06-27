package Client.UI.GUI.EventListeners;

import Client.UI.GUI.CardLabel;
import Client.UI.GUI.GUI;
import model.cards.PlayableCards.PlayableCard;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * the listener is responsible for flipping the cards in the player's hand
 */
public class FlipCardListener extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            CardLabel label = (CardLabel) e.getSource();
            label.flipCard();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        CardLabel label = (CardLabel) e.getSource();
        label.highLight();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        CardLabel label = (CardLabel) e.getSource();
        label.unHighLight();
    }


}
