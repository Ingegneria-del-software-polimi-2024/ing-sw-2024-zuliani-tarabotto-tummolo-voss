package view.labels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardLabel extends JLabel {
    private String id; // id of the card
    private String imagePath; // Path to the image representing the card
    private Color color; //solo per il testing
    private String text;

    public CardLabel(String id, String imagePath, Color color) {
        this.id = id;
        this.imagePath = imagePath;
        this.color = color;
        this.text = "CARD ID: " + id;
        setPreferredSize(new Dimension(250, 150));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Change text when clicked
                if (text.equals("CARD ID: " + id)) {
                    text = "clicked";
                    repaint();
                } else {
                    text = "CARD ID: " + id;
                    repaint();
                }
            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw rectangle
        int width = getWidth();
        int height = getHeight();
        g.setColor(this.color);
        g.fillRect(10, 10, (int)getPreferredSize().getWidth() - 20, (int)getPreferredSize().getHeight()- 20);
        // Draw text
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        FontMetrics metrics = g.getFontMetrics();
        int textWidth = metrics.stringWidth(String.valueOf(20));
        int textHeight = metrics.getHeight();
        int x = (width - textWidth) / 2;
        int y = (height - textHeight) / 2 + metrics.getAscent();
        g.drawString(this.text, x, y);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(250, 150); // Set preferred size
    }
}
