package org.example.APP2;

import java.awt.dnd.DragSourceAdapter;
import java.awt.dnd.DragSourceDropEvent;

public class PieceDragSourceListener extends DragSourceAdapter {
    @Override
    public void dragDropEnd(DragSourceDropEvent dsde) {
        if (dsde.getDropSuccess()) {
            // Handle the end of a successful drag operation
        }
    }
}
