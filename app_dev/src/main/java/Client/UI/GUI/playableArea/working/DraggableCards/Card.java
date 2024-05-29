package Client.UI.GUI.playableArea.working.DraggableCards;



import Client.UI.GUI.playableArea.working.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Card {
    public BufferedImage image;
    public int x,y;
    public int col, row, preCol, preRow;



    public Card(int col, int row){

        this.col = col;
        this.row = row;



        x = getX(col);
        y = getY(row);
    }


    public BufferedImage getImage(String imagePath) {

        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public int getX (int col){
    return col * Board.SQUARE_SIZE;
    }
    public int getY (int row){
        return row * Board.SQUARE_SIZE;
    }

    public int getCol(int x){
        return (x+ Board.HALF_SQUARE_SIZE)/ Board.SQUARE_SIZE;

    }

    public int getRow(int y ){
        return(y+Board.HALF_SQUARE_SIZE)/Board.SQUARE_SIZE;
    }

    public void updatePosition(){
        x = getX(col);
        y = getY(row);
        preCol = getCol(x);
        preRow = getRow(y);
    }
    public Rectangle getImageBounds() {
        if (image == null) {
            // Handle the case where the image is not loaded
            return new Rectangle(0, 0, 0, 0);
        }
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }

    public void draw(Graphics2D g2){
        g2.drawImage(image, x, y, Board.SQUARE_SIZE*5, Board.SQUARE_SIZE*3, null);
    }

}
