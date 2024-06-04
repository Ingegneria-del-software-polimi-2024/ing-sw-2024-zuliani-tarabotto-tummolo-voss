package org.example;

import org.piece.Piece;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class AvailablePiecesPanel extends JPanel implements MouseListener {

    private ArrayList<Piece> availablePieces;
    private Piece activePiece;

    public AvailablePiecesPanel(ArrayList<Piece> pieces) {
        this.availablePieces = pieces;
        addMouseListener(this);
    }



    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        for (Piece piece : availablePieces) {
            if (piece.getImageBounds().contains(x, y)) {
                activePiece = piece;
                TransferHandler handler = getTransferHandler();
                if (handler != null) {
                    handler.exportAsDrag(this, e, TransferHandler.COPY);
                }
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        activePiece = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Not used in this case
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Not used in this case
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
}

