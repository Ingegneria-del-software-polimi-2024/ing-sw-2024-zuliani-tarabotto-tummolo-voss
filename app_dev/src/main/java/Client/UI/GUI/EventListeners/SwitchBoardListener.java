package Client.UI.GUI.EventListeners;

import Client.UI.GUI.CardLabel;
import Client.UI.GUI.GUI;
import Client.UI.GUI.PlayerBanner.PlayerPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SwitchBoardListener extends MouseAdapter {

    private GUI gui;

    /**
     * the listener is responsible for 
     * @param gui
     */
    public SwitchBoardListener(GUI gui){
        this.gui = gui;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        PlayerPanel playerPanel = (PlayerPanel) e.getSource();
        gui.setCurrentDisposition(playerPanel.getPlayer());
    }
}
