package org.example;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;

public class Test extends JPanel {

    public static void main(String... args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                Test test = new Test();
                GamePanel gp = new GamePanel();
                JFrame frame = new JFrame();
                JScrollPane scrollPane = new JScrollPane(gp);
                scrollPane.setPreferredSize(new Dimension(800, 600));

                JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
                verticalScrollBar.setUnitIncrement(5); // Adjust unit increment for smoother scrolling
                verticalScrollBar.setBlockIncrement(1); // Adjust block increment for smoother scrolling

                // Customize the horizontal scrollbar
                JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
                horizontalScrollBar.setUnitIncrement(5); // Adjust unit increment for smoother scrolling
                horizontalScrollBar.setBlockIncrement(1); // Adjust block increment for smoother scrolling

                frame.add(scrollPane);
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(3000, 3000);
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawLine(30, 30, 30, 3000);
        g2.drawLine(30, 400, 500, 3000);
    }
}