package org.example;

import org.piece.Piece;

import javax.swing.*;
import java.awt.datatransfer.*;

public class ValueExportTransferHandler extends TransferHandler {
    private Piece piece;

    public ValueExportTransferHandler(Piece piece) {
        this.piece = piece;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        return new PieceTransferable(piece);
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }
}
