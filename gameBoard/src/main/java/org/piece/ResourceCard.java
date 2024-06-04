package org.piece;


import org.example.GamePanel;

public class ResourceCard extends Piece{
    public ResourceCard(int color, int col, int row){
        super(color, col, row);


        if(color == GamePanel.WHITE)
        {
            image = getImage("/piece/w-pawn");

        }else{
            image = getImage("/piece/b-pawn");
        }
    }
}

