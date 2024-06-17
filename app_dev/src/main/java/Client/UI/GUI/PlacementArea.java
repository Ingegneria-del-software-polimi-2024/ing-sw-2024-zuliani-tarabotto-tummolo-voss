package Client.UI.GUI;

import Client.UI.GUI.EventListeners.BoardStarterClickedListener;
import model.cards.PlayableCards.PlayableCard;
import model.placementArea.Coordinates;

import javax.swing.*;
import java.awt.*;

public class PlacementArea extends JPanel {

    private final int cardLength = 100;
    private final float heightLengthRatio =   (float) 2 /3;
    private final int cardHeight = (int)(cardLength * heightLengthRatio);
    private GUI gui;
    private int xCenter;
    private int yCenter;
    private final int xOverlap = (int) (0.22*cardLength);
    private final int yOverlap = (int) (0.401 * cardHeight);
    private boolean displayAvailable = false;
    private final int borderWidth = 4;
    private boolean drawSelectionRectangle = false;
    private Coordinates selectionRectangleCoordinates;
    private BoardStarterClickedListener starterClickedListener;

    public PlacementArea(GUI gui){
        this.gui = gui;
        setBackground(new Color(50, 84, 70));
        setPreferredSize(new Dimension(2000, 2000));
        xCenter = (int)(this.getPreferredSize().getWidth() / 2);
        yCenter = (int)(this.getPreferredSize().getHeight()/2);
        starterClickedListener = new BoardStarterClickedListener(gui, this);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        //board.draw(g2d);

        for(Coordinates coord : gui.getView().getDispositions().get(gui.getCurrentDisposition()).keySet()){
            PlayableCard c = gui.getView().getDispositions().get(gui.getCurrentDisposition()).get(coord);
            int xCardCenter = xCenter + coord.getX()*(cardLength - xOverlap);
            int yCardCenter = yCenter - coord.getY()*(cardHeight - yOverlap);
            if(c.getFaceSide()) g2d.drawImage(gui.getFronts().get(c.getId()), xCardCenter - cardLength/2, yCardCenter - cardHeight/2 , cardLength, cardHeight, this);
            else g2d.drawImage(gui.getBacks().get(c.getId()), xCardCenter - cardLength/2, yCardCenter - cardHeight/2 , cardLength, cardHeight, this);

        }

        if(displayAvailable && gui.getCurrentDisposition().equals(gui.getView().getPlayerId()) ){
            for(Coordinates coord : gui.getView().getAvailablePlaces()){
                int xCardCenter = xCenter + coord.getX()*(cardLength - xOverlap);
                int yCardCenter = yCenter - coord.getY()*(cardHeight - yOverlap);
                g2d.setColor(Color.lightGray);
                g2d.setStroke(new BasicStroke(borderWidth));
                g2d.drawRoundRect(xCardCenter - cardLength/2, yCardCenter - cardHeight/2 , cardLength, cardHeight, 13, 13);
            }
        }

        if(gui.starterSelected && gui.getCurrentDisposition().equals(gui.getView().getPlayerId())){
            g2d.setColor(Color.ORANGE);
            g2d.setStroke(new BasicStroke(borderWidth));
            g2d.drawRoundRect(xCenter - cardLength/2, yCenter - cardHeight/2 , cardLength, cardHeight, 13, 13);
        }

        if(drawSelectionRectangle && gui.getCurrentDisposition().equals(gui.getView().getPlayerId())){
            int xCardCenter = xCenter + selectionRectangleCoordinates.getX()*(cardLength - xOverlap);
            int yCardCenter = yCenter - selectionRectangleCoordinates.getY()*(cardHeight - yOverlap);
            g2d.setColor(Color.ORANGE);
            g2d.setStroke(new BasicStroke(borderWidth));
            g2d.drawRoundRect(xCardCenter - cardLength/2, yCardCenter - cardHeight/2 , cardLength, cardHeight, 13, 13);
        }

   }

    public int getXCenter(){return xCenter;}
    public int getYCenter(){return yCenter;}

    public int getCardLength(){return cardLength;}
    public int getCardHeight(){return cardHeight;}

    public int getXOverlap(){return xOverlap;}
    public int getYOverlap(){return yOverlap;}

    public void setDisplayAvailable(){displayAvailable = !displayAvailable;}

    public void drawSelectionRectangle(Coordinates c){
        selectionRectangleCoordinates = c;
        drawSelectionRectangle = true;
    }
    public void setDrawSelectionRectangle(boolean b){
        drawSelectionRectangle = b;
    }
    public boolean getDrawSelectionRectangle(){
        return drawSelectionRectangle;
    }

    public Coordinates getSelectionRectangleCoordinates(){return  selectionRectangleCoordinates;}

    public void enableBoardStarterListener(){
        this.addMouseListener(starterClickedListener);
    }

    public void disableBoardStarterListener(){
        this.removeMouseListener(starterClickedListener);
    }

}
