package Client.UI.GUI.EventListeners;

import Client.UI.GUI.CardLabel;
import Client.UI.GUI.GUI;
import Client.UI.GUI.PlayerBanner.PlayerPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The type Switch board listener.
 */
public class SwitchBoardListener extends MouseAdapter {

    private GUI gui;

    /**
     * when a PlayerPanel is clicked, the listener changes the disposition that must be displayed on screen
     *
     * @param gui the gui
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
