package Client.UI.GUI.EventListeners;

import Client.UI.GUI.CardLabel;
import Client.UI.GUI.GUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlaceCardListener extends MouseAdapter {
    //TODO

    private GUI gui;

    public PlaceCardListener(GUI gui){
        this.gui = gui;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        gui.getView().playStarterCard();
        System.out.println("helo");
    }
}
