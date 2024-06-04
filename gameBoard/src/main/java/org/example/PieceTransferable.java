package org.example;

import org.piece.*;

import java.awt.datatransfer.*;
import java.io.IOException;



import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class PieceTransferable implements Transferable {
    public static final DataFlavor pieceFlavor = new DataFlavor(Piece.class, "Piece");
    private Piece piece;

    public PieceTransferable(Piece piece) {
        this.piece = piece;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{pieceFlavor};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return pieceFlavor.equals(flavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (!isDataFlavorSupported(flavor)) {
            throw new UnsupportedFlavorException(flavor);
        }
        return piece;
    }
}
