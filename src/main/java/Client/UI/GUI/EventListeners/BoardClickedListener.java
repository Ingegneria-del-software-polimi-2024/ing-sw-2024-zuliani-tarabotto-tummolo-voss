package Client.UI.GUI.EventListeners;

import Client.UI.GUI.GUI;
import Client.UI.GUI.PlacementArea;
import model.placementArea.Coordinates;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The type Board clicked listener.
 */
public class BoardClickedListener extends MouseAdapter {

    private GUI gui;
    private PlacementArea board;

    /**
     * detects whether one of the available places
     * on the turnPlayer's board is clicked
     *
     * @param gui   the gui
     * @param board the board
     */
    public BoardClickedListener(GUI gui, PlacementArea board){
        this.gui = gui;
        this.board = board;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if(gui.getCurrentDisposition().equals(gui.getView().getPlayerId())){
            for(Coordinates coord : gui.getView().getAvailablePlaces()){
                int xCardCenter = board.getXCenter() + coord.getX()*(board.getCardLength() - board.getXOverlap());
                int yCardCenter = board.getYCenter() - coord.getY()*(board.getCardHeight() - board.getYOverlap());

                if((xCardCenter - board.getCardLength()/2 <= x && xCardCenter + board.getCardLength()/2 >= x) && (yCardCenter - board.getCardHeight()/2 <= y && yCardCenter + board.getCardHeight()/2 >= y) && gui.checkCardIsPlaceable()) {
                    gui.getView().playCard(gui.getPlayCard(), gui.getPlayCard().getFaceSide(), board.getSelectionRectangleCoordinates().getX(), board.getSelectionRectangleCoordinates().getY());
                    gui.setSelectedCard(null);
                    board.setDrawSelectionRectangle(false);
                    return;
                }
            }
        }

    }


}
