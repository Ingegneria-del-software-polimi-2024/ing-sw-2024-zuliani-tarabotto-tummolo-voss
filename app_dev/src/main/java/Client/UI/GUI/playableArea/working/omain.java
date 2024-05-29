package Client.UI.GUI.playableArea.working;


import javax.swing.*;
import java.awt.*;

public class omain {
    public static void main(String[] args) {
        JFrame window = new JFrame("Board");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main container panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create the three sub-panels
        JPanel topPanel = new JPanel();
        JPanel middlePanel = new JPanel(new BorderLayout()); // Middle panel uses BorderLayout to fit JScrollPane
        JPanel bottomPanel = new JPanel();
        JPanel rightPanel = new JPanel(); // Right panel for vertical section

        // Set preferred size for the top, bottom, and right panels if needed
        topPanel.setPreferredSize(new Dimension(800, 100));
        bottomPanel.setPreferredSize(new Dimension(800, 100));
        rightPanel.setPreferredSize(new Dimension(100, 600));

        // Create the game panel
        GamePanel gp = new GamePanel();
        gp.setLayout(new BoxLayout(gp, BoxLayout.Y_AXIS));

        // Create the scroll pane and add the game panel to it
        JScrollPane scroll = new JScrollPane(gp);
        scroll.setPreferredSize(new Dimension(800, 400));
        JScrollBar verticalScrollBar = scroll.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(5); // Adjust unit increment for smoother scrolling
        verticalScrollBar.setBlockIncrement(1); // Adjust block increment for smoother scrolling

        // Customize the horizontal scrollbar
        JScrollBar horizontalScrollBar = scroll.getHorizontalScrollBar();
        horizontalScrollBar.setUnitIncrement(5); // Adjust unit increment for smoother scrolling
        horizontalScrollBar.setBlockIncrement(1); // Adjust block increment for smoother scrolling

        int min = 0;
        int max = 100;
        verticalScrollBar.setMinimum(min);
        verticalScrollBar.setMaximum(max);
        horizontalScrollBar.setMinimum(min);
        horizontalScrollBar.setMaximum(max);

        // Calculate the center value
        int centerValue = (max - min) / 2;

        // Set the scroll bar value to the center value
        verticalScrollBar.setValue(centerValue);
        horizontalScrollBar.setValue(centerValue);

        // Add the scroll pane to the middle panel
        middlePanel.add(scroll, BorderLayout.CENTER);

        // Add the four sub-panels to the main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(rightPanel, BorderLayout.EAST);


        // Add the main panel to the window
        window.add(mainPanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gp.launchGame();
    }
}

