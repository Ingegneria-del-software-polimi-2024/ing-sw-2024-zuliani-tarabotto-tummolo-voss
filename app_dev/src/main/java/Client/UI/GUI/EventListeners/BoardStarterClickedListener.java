package Client.UI.GUI.EventListeners;

import Client.UI.GUI.GUI;
import Client.UI.GUI.PlacementArea;
import model.placementArea.Coordinates;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardStarterClickedListener extends MouseAdapter {
    private GUI gui;
    private PlacementArea board;

    /**
     * during the starterCard placing phase the listener detects whether the central position of the board is clicked
     * @param gui
     * @param board
     */
    public BoardStarterClickedListener(GUI gui, PlacementArea board){
        this.gui = gui;
        this.board = board;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();


        int xRel = x - board.getXCenter();
        int yRel = board.getYCenter() - y;
        if(xRel / (board.getCardLength()/2) == 0 && yRel / (board.getCardHeight()/2) == 0){
            gui.getView().playStarterCard();
            gui.setStarterSelected();
            gui.updateHand();
            System.out.println("cliccata");
        }

    }

}
