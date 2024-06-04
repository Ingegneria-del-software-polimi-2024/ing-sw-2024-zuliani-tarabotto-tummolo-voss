package org.example;

import org.piece.Piece;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;

public class PieceDropTargetListener extends DropTargetAdapter {
    private GamePanel gamePanel;

    public PieceDropTargetListener(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        try {
            dtde.acceptDrop(DnDConstants.ACTION_MOVE);
            Transferable transferable = dtde.getTransferable();
            Piece piece = (Piece) transferable.getTransferData(PieceTransferable.pieceFlavor);
            piece.x = dtde.getLocation().x - Board.HALF_SQUARE_SIZE;
            piece.y = dtde.getLocation().y - Board.HALF_SQUARE_SIZE;
            gamePanel.simPieces.add(piece);
            gamePanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
