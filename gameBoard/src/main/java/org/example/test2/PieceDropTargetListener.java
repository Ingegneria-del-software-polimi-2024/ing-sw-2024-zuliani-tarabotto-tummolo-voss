package org.example.test2;

import org.example.Board;
import org.example.test2.GamePanel;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;

public class PieceDropTargetListener extends DropTargetAdapter {
    private GamePanel gamePanel;

    public PieceDropTargetListener(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        try {
            Transferable transferable = dtde.getTransferable();
            if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                dtde.acceptDrop(dtde.getDropAction());
                String data = (String) transferable.getTransferData(DataFlavor.stringFlavor);

                // Assuming data contains piece information, parse it and add the piece to the GamePanel
                Piece piece = parsePiece(data, dtde.getLocation());
                if (piece != null) {
                    gamePanel.addPiece(piece);
                }

                dtde.dropComplete(true);
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        dtde.rejectDrop();
    }

    // Method to parse the piece data and return a new Piece object
    private Piece parsePiece(String data, Point dropLocation) {
        // Split the dropped data to extract necessary information
        String[] parts = data.split(";");

        if (parts.length != 3) {
            System.err.println("Invalid data format");
            return null;
        }

        try {
            int color = Integer.parseInt(parts[0]); // Color of the piece
            int col = dropLocation.x / Board.SQUARE_SIZE;   // Column of the dropped location
            int row = dropLocation.y / Board.SQUARE_SIZE;   // Row of the dropped location

            // Create a new Piece object with the extracted information
            return new Piece(color, col, row);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing piece data");
            e.printStackTrace();
            return null;
        }
    }
}
