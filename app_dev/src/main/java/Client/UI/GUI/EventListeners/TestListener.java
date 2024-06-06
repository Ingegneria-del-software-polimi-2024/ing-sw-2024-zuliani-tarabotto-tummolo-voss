package Client.UI.GUI.EventListeners;

import Client.UI.GUI.GUI;
import Client.UI.GUI.PlacementArea;
import model.placementArea.Coordinates;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TestListener extends MouseAdapter {

    private GUI gui;
    private PlacementArea board;
    public TestListener(GUI gui, PlacementArea board){
        this.gui = gui;
        this.board = board;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println(x + "" + y);

        if(gui.cardSelected){

            for(Coordinates coord : gui.getView().getAvailablePlaces()){
                int xCardCenter = board.getXCenter() + coord.getX()*(board.getCardLength() - board.getXOverlap());
                int yCardCenter = board.getYCenter() - coord.getY()*(board.getCardHeight() - board.getYOverlap());

                if((xCardCenter - board.getCardLength()/2 <= x && xCardCenter + board.getCardLength()/2 >= x) && (yCardCenter - board.getCardHeight()/2 <= y && yCardCenter + board.getCardHeight()/2 >= y)){
                    board.drawSelectionRectangle(coord);
                }else{
                    board.setDrawSelectionRectangle(false);
                }

            }
        }

    }
}
