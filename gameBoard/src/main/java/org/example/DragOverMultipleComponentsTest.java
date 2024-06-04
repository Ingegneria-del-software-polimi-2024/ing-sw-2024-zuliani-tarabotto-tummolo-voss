package org.example;

import org.example.ComponentDrag;

import javax.swing.*;
import java.awt.*;

public class DragOverMultipleComponentsTest {

    public DragOverMultipleComponentsTest() {
        createAndShowUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new org.example.DragOverMultipleComponentsTest();
            }
        });
    }

    private void createAndShowUI() {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JLabel draggableLabel = new JLabel("CARTA 1");
        JLabel draggableLabel2 = new JLabel("CARTA 2");

        JLabel labelPanel1 = new JLabel("JPANEL1");
        JPanel panel1 = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(300, 300);
            }
        };
        panel1.add(labelPanel1);
        panel1.setName("Droppable");

        JLabel labelPanel2 = new JLabel("JPANEL2");
        JPanel panel2 = new JPanel();
        panel2.add(labelPanel2);
        panel2.add(draggableLabel);
        panel2.add(draggableLabel2);
        panel2.setName("Droppable");

        JSeparator js = new JSeparator(JSeparator.VERTICAL);

        ComponentDrag cd = new ComponentDrag(frame);

        cd.registerComponent(draggableLabel);
        cd.registerComponent(draggableLabel2);

        frame.add(panel1, BorderLayout.WEST);
        frame.add(js);
        frame.add(panel2, BorderLayout.EAST);

        frame.pack();
        frame.setVisible(true);
    }
}
