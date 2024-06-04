package org.example;

import org.piece.*;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.util.ArrayList;

public class OutsidePanel extends JPanel {
    public static final int WIDTH = 200;
    public static final int HEIGHT = 800;

    ArrayList<Piece> pieces = new ArrayList<>();

    public OutsidePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.gray);

        // Set up drop target for this panel
        setDropTarget(new DropTarget(this, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    dtde.acceptDrop(DnDConstants.ACTION_MOVE);
                    Transferable transferable = dtde.getTransferable();
                    Piece piece = (Piece) transferable.getTransferData(PieceTransferable.pieceFlavor);
                    piece.x = dtde.getLocation().x - Board.HALF_SQUARE_SIZE;
                    piece.y = dtde.getLocation().y - Board.HALF_SQUARE_SIZE;
                    pieces.add(piece);
                    repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        for (Piece p : pieces) {
            p.draw(g2);
        }
    }
}
