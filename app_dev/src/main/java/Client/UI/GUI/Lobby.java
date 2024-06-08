/*
 * Created by JFormDesigner on Tue May 28 11:56:52 CEST 2024
 */

package Client.UI.GUI;

import java.awt.*;
import javax.swing.*;

/**
 * @author nicol
 */
public class Lobby extends JFrame {
    public Lobby() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Nicola Tummolo
        panel1 = new JPanel();
        scrollPane1 = new JScrollPane();
        list1 = new JList();
        button1 = new JButton();
        button2 = new JButton();
        label1 = new JLabel();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== panel1 ========
        {
            panel1.setBackground(new Color(0xd2cd89));
            panel1.setLayout(null);

            //======== scrollPane1 ========
            {

                //---- list1 ----
                list1.setBackground(new Color(0xada038));
                scrollPane1.setViewportView(list1);
            }
            panel1.add(scrollPane1);
            scrollPane1.setBounds(0, 85, 785, 265);

            //---- button1 ----
            button1.setText("Refresh the Page");
            button1.setBackground(new Color(0xada038));
            panel1.add(button1);
            button1.setBounds(new Rectangle(new Point(665, 55), button1.getPreferredSize()));

            //---- button2 ----
            button2.setText("CREATE NEW GAME");
            button2.setBackground(new Color(0xada038));
            panel1.add(button2);
            button2.setBounds(330, 385, 165, button2.getPreferredSize().height);

            //---- label1 ----
            label1.setText("LOBBY");
            label1.setFont(new Font("Ravie", Font.PLAIN, 18));
            panel1.add(label1);
            label1.setBounds(350, 40, 120, label1.getPreferredSize().height);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel1.getComponentCount(); i++) {
                    Rectangle bounds = panel1.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel1.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel1.setMinimumSize(preferredSize);
                panel1.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(panel1);
        panel1.setBounds(0, 0, 790, 445);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Nicola Tummolo
    private JPanel panel1;
    private JScrollPane scrollPane1;
    private JList list1;
    private JButton button1;
    private JButton button2;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
