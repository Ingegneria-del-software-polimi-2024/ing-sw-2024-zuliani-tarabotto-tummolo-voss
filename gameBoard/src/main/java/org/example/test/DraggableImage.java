package org.example.test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class DraggableImage {

    private BufferedImage image;
    private int x, y;

    public DraggableImage(String imagePath) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        x = 0;
        y = 0;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getImageBounds() {
        if (image == null) {
            return new Rectangle(0, 0, 0, 0);
        }
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }
}

