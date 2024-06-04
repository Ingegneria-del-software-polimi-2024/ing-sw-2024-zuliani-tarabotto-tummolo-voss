package org.example;

import org.piece.Piece;

import java.awt.*;
import java.awt.dnd.*;

import java.awt.Cursor;
import java.awt.dnd.*;

import javax.swing.*;

public class PieceDragGestureListener implements DragGestureListener {
    @Override
    public void dragGestureRecognized(DragGestureEvent dge) {
        Cursor cursor = null;
        Component component = dge.getComponent();

        if (component instanceof JLabel) {
            JLabel pieceLabel = (JLabel) component;
            Piece piece = (Piece) pieceLabel.getClientProperty("piece");

            if (dge.getDragAction() == DnDConstants.ACTION_MOVE) {
                cursor = DragSource.DefaultMoveDrop;
            }

            dge.startDrag(cursor, new PieceTransferable(piece));
        }
    }
}
