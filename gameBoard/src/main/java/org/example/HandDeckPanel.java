package org.example;

import org.piece.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;

import static org.example.GamePanel.WHITE;

public class HandDeckPanel extends JPanel {
    public HandDeckPanel() {
        setLayout(new FlowLayout());
        Piece piece = new Piece(WHITE, 0, 0); // Example piece

        JLabel pieceLabel = new JLabel(new ImageIcon(piece.getImage("pawn"))); // Assuming the image is "pawn.png"
        pieceLabel.setTransferHandler(new ValueExportTransferHandler(piece));
        add(pieceLabel);

        DragSource dragSource = new DragSource();
        dragSource.createDefaultDragGestureRecognizer(pieceLabel, DnDConstants.ACTION_MOVE, new PieceDragGestureListener());
    }
}

