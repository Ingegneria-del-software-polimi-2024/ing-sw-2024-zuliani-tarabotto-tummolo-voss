package Client.UI.GUI.EventListeners;

import Client.UI.GUI.GUI;
import Client.UI.GUI.PlacementArea;
import model.placementArea.Coordinates;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The type Board motion listener.
 */
public class BoardMotionListener extends MouseAdapter {

    private GUI gui;
    private PlacementArea board;

    /**
     * the listener is responsible for monitoring mouse position and highlighting the board in case the position
     * coincides with one of the availablePlaces (only during player's turn)
     *
     * @param gui   the gui
     * @param board the board
     */
    public BoardMotionListener(GUI gui, PlacementArea board){
        this.gui = gui;
        this.board = board;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        for(Coordinates coord : gui.getView().getAvailablePlaces()){
            int xCardCenter = board.getXCenter() + coord.getX()*(board.getCardLength() - board.getXOverlap());
            int yCardCenter = board.getYCenter() - coord.getY()*(board.getCardHeight() - board.getYOverlap());

            if(((xCardCenter - board.getCardLength()/2 <= x && xCardCenter + board.getCardLength()/2 >= x) && (yCardCenter - board.getCardHeight()/2 <= y && yCardCenter + board.getCardHeight()/2 >= y) && gui.checkCardIsPlaceable())){
                board.drawSelectionRectangle(coord);
                return;
            }else{
                board.setDrawSelectionRectangle(false);
            }

        }
    }
}
