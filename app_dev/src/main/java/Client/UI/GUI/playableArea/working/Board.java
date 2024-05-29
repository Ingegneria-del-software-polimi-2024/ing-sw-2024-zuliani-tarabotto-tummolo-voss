package Client.UI.GUI.playableArea.working;



import java.awt.*;

public class Board {
    final int MAX_COL = 50;
    final int MAX_ROW = 50;
    public static final int SQUARE_SIZE = 25;
    public static final int HALF_SQUARE_SIZE = SQUARE_SIZE / 2;

    public void draw(Graphics g2) {
        int c = 0;
        for(int row = 0; row < MAX_ROW; row++){


            for(int col = 0; col < MAX_COL; col++){

                if(c==0){
                    g2.setColor(new Color(245, 245, 245));
                    c=1;
                }

                else {
                    g2.setColor(new Color(240, 240,240));
                    c = 0;
                }
                g2.fillRect(col*SQUARE_SIZE, row*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }

            if(c==0){
                c=1;
            }
            else{
                c = 0;
            }
        }
    }
}
