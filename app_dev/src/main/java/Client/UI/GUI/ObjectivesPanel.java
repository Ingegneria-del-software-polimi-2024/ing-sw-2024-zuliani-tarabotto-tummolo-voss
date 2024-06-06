package Client.UI.GUI;

import model.cards.ObjectiveCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ObjectivesPanel extends JPanel {
    private GUI gui;
    private ObjCardLabel common1;
    private ObjCardLabel common2;
    private ObjCardLabel secret1;
    private ObjCardLabel secret2;
    private ObjectivesPanel panel;
    private JPanel subPanel;
    private int panelHeight;

    public ObjectivesPanel(GUI gui, int panelHeight){
        this.gui = gui;
        setBackground(new Color(50, 84, 70));

        //this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.panelHeight = panelHeight;
        common1 = new ObjCardLabel();
        common2 = new ObjCardLabel();
        secret1 = new ObjCardLabel();
        secret2 = new ObjCardLabel();
        this.add(common1);
        this.add(common2);
       // this.add(secret1);
       // this.add(secret2);

        JPanel secretPanel1 = new JPanel();
        secretPanel1.setOpaque(false);
        secretPanel1.add(secret1);
        JPanel secretPanel2 = new JPanel();
        secretPanel2.setOpaque(false);
        secretPanel2.add(secret2);
        subPanel = new JPanel();
        subPanel.setOpaque(false);
        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
        subPanel.add(secretPanel1, 0 );
        subPanel.add(secretPanel2, 1);

        System.out.println(subPanel.getHeight());
        System.out.println(subPanel.getPreferredSize().getHeight());
        this.setBorder(BorderFactory.createEmptyBorder((panelHeight - (int)subPanel.getPreferredSize().getHeight())/2, 0, 0, 0));
        this.add(subPanel);
    }

    public void chooseObjectives(){
        ObjectiveCard s1 = gui.getView().getChooseSecretObjectives().get(0);
        ObjectiveCard s2 = gui.getView().getChooseSecretObjectives().get(1);

        secret1.updateCard(s1, gui.getFronts().get(s1.getId()), gui.getBacks().get(s1.getId()));
        secret2.updateCard(s2, gui.getFronts().get(s2.getId()), gui.getBacks().get(s2.getId()));

        panel = this;
        secret1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panel.remove(subPanel);
                panel.add(secret1);
                panel.setBorder(BorderFactory.createEmptyBorder((panelHeight - (int)common1.getPreferredSize().getHeight())/2, 0, 0, 0));
                panel.revalidate();
                gui.getView().setSecretObjective(secret1.getCard());
                //panel.repaint();
            }
        });

        secret2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panel.remove(subPanel);
                panel.add(secret2);
                panel.setBorder(BorderFactory.createEmptyBorder((panelHeight - (int)common1.getPreferredSize().getHeight())/2, 0, 0, 0));
                panel.revalidate();
                gui.getView().setSecretObjective(secret2.getCard());
                //panel.repaint();
            }
        });
    }

    public void updateObjectivesPanel(){
        ObjectiveCard c1 = gui.getView().getCommonObjectives().get(0);
        ObjectiveCard c2 = gui.getView().getCommonObjectives().get(1);

        common1.updateCard(c1, gui.getFronts().get(c1.getId()), gui.getBacks().get(c1.getId()));
        common2.updateCard(c2, gui.getFronts().get(c2.getId()), gui.getBacks().get(c2.getId()));
    }




    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        int borderWidth = 4;
        g2d.setColor(new Color(171, 144, 76));
        g2d.setStroke(new BasicStroke(borderWidth));
        g2d.drawRect(borderWidth/2, borderWidth/2, getWidth() - borderWidth, getHeight() - borderWidth);

        int innerBorder = borderWidth/2;

        g2d.setStroke(new BasicStroke(innerBorder));
        g2d.drawRect((borderWidth*2), (borderWidth*2), getWidth() - 2*(borderWidth*2), getHeight() - 2*(borderWidth*2));

    }
}
