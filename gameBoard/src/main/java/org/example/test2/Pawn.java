package org.example.test2;



import org.example.GamePanel;

public class Pawn extends Piece {
    public Pawn(int color, int col, int row) {
        super(color, col, row);

        // Load the image of the pawn based on its color
        if (color == GamePanel.WHITE) {
            image = getImage("/piece/w-pawn.png");
        } else {
            image = getImage("/piece/b-pawn.png");
        }
    }
}
