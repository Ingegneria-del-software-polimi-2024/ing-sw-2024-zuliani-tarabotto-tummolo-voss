package org.example;

import org.piece.*;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PiecesPanel extends JPanel {

    ArrayList<Piece> pieces;
    GamePanel gamePanel;

    public PiecesPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.pieces = gamePanel.pieces;
        setLayout(new GridLayout(8, 4));
        setPreferredSize(new Dimension(100, 600));
        setBackground(Color.gray);

        for (Piece piece : pieces) {
            PieceLabel pieceLabel = new PieceLabel(piece);
            add(pieceLabel);
        }
    }

    class PieceLabel extends JPanel {
        Piece piece;

        PieceLabel(Piece piece) {
            this.piece = piece;
            setPreferredSize(new Dimension(Board.SQUARE_SIZE, Board.SQUARE_SIZE));
            setTransferHandler(new TransferHandler("piece"));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    JComponent comp = (JComponent) e.getSource();
                    TransferHandler handler = comp.getTransferHandler();
                    handler.exportAsDrag(comp, e, TransferHandler.MOVE);
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            piece.draw(g2);
        }
    }
}
