package org.example.APP2;



import org.example.Board;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import static org.example.GamePanel2.WHITE;

public abstract class Piece implements Transferable {
    public static final DataFlavor pieceFlavor = new DataFlavor(Piece.class, "A Piece of the Board Game");

    protected int x, y, row, col, color;
    public static final int SIZE = 80; // Example size for the piece

    public Piece(int color, int col, int row) {
        this.color = color;
        this.col = col;
        this.row = row;
        updatePosition();
    }

    public void updatePosition() {
        this.x = col * Board.SQUARE_SIZE;
        this.y = row * Board.SQUARE_SIZE;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{pieceFlavor};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(pieceFlavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (!isDataFlavorSupported(flavor)) {
            throw new UnsupportedFlavorException(flavor);
        }
        return this;
    }

    public abstract void draw(Graphics2D g);
}

class Pawn extends Piece {
    public Pawn(int color, int col, int row) {
        super(color, col, row);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color == WHITE ? Color.WHITE : Color.BLACK);
        g.fillRect(x, y, SIZE, SIZE);
    }
}
