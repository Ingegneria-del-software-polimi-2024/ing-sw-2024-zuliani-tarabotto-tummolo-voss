package Client.UI.GUI;

import Client.UI.GUI.EventListeners.BoardStarterClickedListener;
import model.cards.PlayableCards.PlayableCard;
import model.placementArea.Coordinates;

import javax.swing.*;
import java.awt.*;

/**
 * The type Placement area.
 */
public class PlacementArea extends JPanel {

    private final int cardLength;
    private final float heightLengthRatio =   (float) 2 /3;
    private final int cardHeight;
    private GUI gui;
    private int xCenter;//the central x coordinate of the panel
    private int yCenter;//the central y coordinate of the panel
    private int xOverlap;
    private int yOverlap;
    private final int borderWidth = 4;
    private boolean displayAvailable = false;
    private boolean drawSelectionRectangle = false;
    /**
     * holds the Coordinate of the rectangle that must be highlighted in orange on the board
     */
    private Coordinates selectionRectangleCoordinates;
    private BoardStarterClickedListener starterClickedListener;


    /**
     * JPanel used to display the player's board, each card is custom drawn using the paintComponent method.
     *
     * @param gui        the gui
     * @param cardLength the card length
     */
    public PlacementArea(GUI gui, int cardLength){
        this.cardLength = cardLength;
        this.cardHeight = (int)(cardLength * heightLengthRatio);
        xOverlap = (int) (0.22 * cardLength);
        yOverlap = (int) (0.41 * cardHeight);
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

        //draws all the cards that the player has placed
        for(Coordinates coord : gui.getView().getDispositions().get(gui.getCurrentDisposition()).keySet()){
            PlayableCard c = gui.getView().getDispositions().get(gui.getCurrentDisposition()).get(coord);
            int xCardCenter = xCenter + coord.getX()*(cardLength - xOverlap);
            int yCardCenter = yCenter - coord.getY()*(cardHeight - yOverlap);
            if(c.getFaceSide()) g2d.drawImage(gui.getFronts().get(c.getId()), xCardCenter - cardLength/2, yCardCenter - cardHeight/2 , cardLength, cardHeight, this);
            else g2d.drawImage(gui.getBacks().get(c.getId()), xCardCenter - cardLength/2, yCardCenter - cardHeight/2 , cardLength, cardHeight, this);

        }

        //when a player has to choose in which position he wants to place the next card, grey rectangles are drawn
        //on the board in the corresponding available places
        if(displayAvailable && gui.getCurrentDisposition().equals(gui.getView().getPlayerId()) ){
            for(Coordinates coord : gui.getView().getAvailablePlaces()){
                int xCardCenter = xCenter + coord.getX()*(cardLength - xOverlap);
                int yCardCenter = yCenter - coord.getY()*(cardHeight - yOverlap);
                g2d.setColor(Color.lightGray);
                g2d.setStroke(new BasicStroke(borderWidth));
                g2d.drawRoundRect(xCardCenter - cardLength/2, yCardCenter - cardHeight/2 , cardLength, cardHeight, 13, 13);
            }
        }

        //after the player selects the starter card from his hand, an orange rectangle is drawn on the board in the central position
        if(gui.starterSelected && gui.getCurrentDisposition().equals(gui.getView().getPlayerId())){
            g2d.setColor(Color.ORANGE);
            g2d.setStroke(new BasicStroke(borderWidth));
            g2d.drawRoundRect(xCenter - cardLength/2, yCenter - cardHeight/2 , cardLength, cardHeight, 13, 13);
        }

        //when the mouse enters one of the gray rectangles representing the available places, the rectangle is redrawn in orange
        if(drawSelectionRectangle && gui.getCurrentDisposition().equals(gui.getView().getPlayerId())){
            int xCardCenter = xCenter + selectionRectangleCoordinates.getX()*(cardLength - xOverlap);
            int yCardCenter = yCenter - selectionRectangleCoordinates.getY()*(cardHeight - yOverlap);
            g2d.setColor(Color.ORANGE);
            g2d.setStroke(new BasicStroke(borderWidth));
            g2d.drawRoundRect(xCardCenter - cardLength/2, yCardCenter - cardHeight/2 , cardLength, cardHeight, 13, 13);
        }

   }

    /**
     * Get x center int.
     *
     * @return the int
     */
    public int getXCenter(){return xCenter;}

    /**
     * Get y center int.
     *
     * @return the int
     */
    public int getYCenter(){return yCenter;}

    /**
     * Get card length int.
     *
     * @return the int
     */
    public int getCardLength(){return cardLength;}

    /**
     * Get card height int.
     *
     * @return the int
     */
    public int getCardHeight(){return cardHeight;}

    /**
     * Get x overlap int.
     *
     * @return the int
     */
    public int getXOverlap(){return xOverlap;}

    /**
     * Get y overlap int.
     *
     * @return the int
     */
    public int getYOverlap(){return yOverlap;}


    /**
     * when a player has to place a card, displayAvailble is set to true
     */
    public void setDisplayAvailable(){displayAvailable = !displayAvailable;}


    /**
     * when a player has to select a position to place the card, the rectangle at coordinates c is drawn in orange,
     * the boolean drawSelectionRectangle must be set to true
     *
     * @param c the c
     */
    public void drawSelectionRectangle(Coordinates c){
        selectionRectangleCoordinates = c;
        drawSelectionRectangle = true;
    }

    /**
     * Set draw selection rectangle.
     *
     * @param b the b
     */
    public void setDrawSelectionRectangle(boolean b){
        drawSelectionRectangle = b;
    }

    /**
     * Get selection rectangle coordinates coordinates.
     *
     * @return the coordinates
     */
    public Coordinates getSelectionRectangleCoordinates(){return  selectionRectangleCoordinates;}


    /**
     * enables the BoardStarterClickedListener
     */
    public void enableBoardStarterListener(){
        this.addMouseListener(starterClickedListener);
    }

    /**
     * disables the BoardStarterClickedListener
     */
    public void disableBoardStarterListener(){
        this.removeMouseListener(starterClickedListener);
    }

}
