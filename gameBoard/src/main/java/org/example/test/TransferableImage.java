package org.example.test;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TransferableImage implements Transferable {

    public static final DataFlavor imageFlavor = new DataFlavor(BufferedImage.class, "Image");
    private final DraggableImage image;

    public TransferableImage(DraggableImage image) {
        this.image = image;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{imageFlavor};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(imageFlavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (isDataFlavorSupported(flavor)) {
            return image.getImage();
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }
}
