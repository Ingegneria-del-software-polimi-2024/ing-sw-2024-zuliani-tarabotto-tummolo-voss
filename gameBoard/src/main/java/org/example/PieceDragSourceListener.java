package org.example;

import java.awt.dnd.*;

public class PieceDragSourceListener implements DragSourceListener {

    @Override
    public void dragEnter(DragSourceDragEvent dsde) {}

    @Override
    public void dragOver(DragSourceDragEvent dsde) {}

    @Override
    public void dropActionChanged(DragSourceDragEvent dsde) {}

    @Override
    public void dragExit(DragSourceEvent dse) {}

    @Override
    public void dragDropEnd(DragSourceDropEvent dsde) {
        if (dsde.getDropSuccess()) {
            System.out.println("Drop completed successfully");
        } else {
            System.out.println("Drop failed");
        }
    }
}
