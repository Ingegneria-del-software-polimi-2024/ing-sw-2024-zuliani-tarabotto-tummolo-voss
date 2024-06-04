package org.example.APP2;

import org.example.Board;
import org.example.GamePanel;

import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;

public class PieceDropTargetListener extends DropTargetAdapter {
    private final GamePanel gamePanel;

    public PieceDropTargetListener(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        try {
            dtde.acceptDrop(DnDConstants.ACTION_MOVE);
            Transferable transferable = dtde.getTransferable();
            Piece piece = (Piece) transferable.getTransferData(Piece.pieceFlavor);
            Point dropPoint = dtde.getLocation();
            piece.x = dropPoint.x - Board.HALF_SQUARE_SIZE;
            piece.y = dropPoint.y - Board.HALF_SQUARE_SIZE;
            piece.updatePosition();
            gamePanel.addPiece(piece);
            dtde.dropComplete(true);
            gamePanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
            dtde.dropComplete(false);
        }
    }
}
