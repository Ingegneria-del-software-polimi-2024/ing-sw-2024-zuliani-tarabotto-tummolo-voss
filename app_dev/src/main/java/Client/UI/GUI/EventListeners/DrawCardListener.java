package Client.UI.GUI.EventListeners;

import Client.UI.GUI.CardLabel;
import Client.UI.GUI.GUI;
import model.cards.Card;
import model.cards.ObjectiveCard;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawCardListener extends MouseAdapter {
    private GUI gui;

    /**
     * the listener detects which card the player wants to draw
     * @param gui
     */
    public DrawCardListener(GUI gui){
        this.gui = gui;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        CardLabel label = (CardLabel) e.getSource();
        gui.getView().drawCard(label.getCardSource());
    }
}
