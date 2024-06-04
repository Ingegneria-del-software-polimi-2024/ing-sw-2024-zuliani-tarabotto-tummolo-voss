package Client.UI.GUI.EventListeners;

import Client.UI.GUI.CardLabel;
import Client.UI.GUI.GUI;
import model.cards.PlayableCards.PlayableCard;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FlipCardListener extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
        CardLabel label = (CardLabel) e.getSource();
        label.flipCard();
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
