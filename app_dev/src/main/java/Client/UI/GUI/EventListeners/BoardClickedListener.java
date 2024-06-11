package Client.UI.GUI.EventListeners;

import Client.UI.GUI.GUI;
import Client.UI.GUI.PlacementArea;
import model.placementArea.Coordinates;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardClickedListener extends MouseAdapter {

    private GUI gui;
    private PlacementArea board;
    public BoardClickedListener(GUI gui, PlacementArea board){
        this.gui = gui;
        this.board = board;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if(gui.starterSelected){
            int xRel = x - board.getXCenter();
            int yRel = board.getYCenter() - y;
            if(xRel / (board.getCardLength()/2) == 0 && yRel / (board.getCardHeight()/2) == 0){
                gui.getView().playStarterCard();
                gui.setStarterSelected();
                gui.updateHand();
                System.out.println("cliccata");
            }
        }else{

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



}
