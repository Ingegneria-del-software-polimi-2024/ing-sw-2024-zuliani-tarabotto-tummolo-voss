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
    private Graphics2D g2d;
    private boolean choose = false;


    /**
     * JPanel containing three ObjCardLabel representing the two common objectives and the player's secret objective
     * @param gui
     * @param panelHeight
     */
    public ObjectivesPanel(GUI gui, int panelHeight){
        this.gui = gui;
        setBackground(new Color(50, 84, 70));

        int cardLength = (int)this.getPreferredSize().getWidth() / 4;
        this.panelHeight = panelHeight;
        common1 = new ObjCardLabel(cardLength);
        common2 = new ObjCardLabel(cardLength);
        secret1 = new ObjCardLabel(cardLength);
        secret2 = new ObjCardLabel(cardLength);
        this.add(common1);
        this.add(common2);

    }


    /**
     * adds the third objective label to the panel once the player has chosen his secret objective
     */
    public void addSecretObjectives(){
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

        this.setBorder(BorderFactory.createEmptyBorder((panelHeight - (int)subPanel.getPreferredSize().getHeight())/2, 0, 0, 0));
        this.add(subPanel);
    }


    /**
     * displays the two objectives the player has two choose from
     */
    public void chooseObjectives(){

        ObjectiveCard s1 = gui.getView().getChooseSecretObjectives().get(0);
        ObjectiveCard s2 = gui.getView().getChooseSecretObjectives().get(1);

        secret1.updateCard(s1, gui.getFronts().get(s1.getId()), gui.getBacks().get(s1.getId()));
        secret2.updateCard(s2, gui.getFronts().get(s2.getId()), gui.getBacks().get(s2.getId()));

        panel = this;
        choose = true;

        secret1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panel.remove(subPanel);
                panel.add(secret1);
                panel.setBorder(BorderFactory.createEmptyBorder((panelHeight - (int)common1.getPreferredSize().getHeight())/2, 0, 0, 0));
                panel.revalidate();
                gui.getView().setSecretObjective(secret1.getCard());
                choose = false;
                secret1.unHighLight();
                secret1.removeMouseListener(this);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ObjCardLabel label = (ObjCardLabel) e.getSource();
                label.highLight();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ObjCardLabel label = (ObjCardLabel) e.getSource();
                label.unHighLight();
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
                choose = false;
                secret2.unHighLight();
                secret2.removeMouseListener(this);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                ObjCardLabel label = (ObjCardLabel) e.getSource();
                label.highLight();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ObjCardLabel label = (ObjCardLabel) e.getSource();
                label.unHighLight();
            }
        });


    }

    public void printObjectives(){
        setBorder(BorderFactory.createEmptyBorder((panelHeight - (int)common1.getPreferredSize().getHeight())/2, 0, 0, 0));
        ObjectiveCard secret = gui.getView().getSecretObjective();
        secret1.updateCard(secret, gui.getFronts().get(secret.getId()), gui.getBacks().get(secret.getId()));
        this.add(secret1);
        updateObjectivesPanel();
    }

    public void updateObjectivesPanel(){
        ObjectiveCard c1 = gui.getView().getCommonObjectives().get(0);
        ObjectiveCard c2 = gui.getView().getCommonObjectives().get(1);

        common1.updateCard(c1, gui.getFronts().get(c1.getId()), gui.getBacks().get(c1.getId()));
        common2.updateCard(c2, gui.getFronts().get(c2.getId()), gui.getBacks().get(c2.getId()));
    }


    @Override
    public Dimension getPreferredSize(){
        return new Dimension((int)(gui.getScreenWidth() * 0.33), (int)(gui.getScreenHeight() * 0.25));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int borderWidth = 4;

        if(choose){
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(borderWidth));
            g2d.drawRect(subPanel.getX(), subPanel.getY(), subPanel.getWidth(), subPanel.getHeight());
        }

        g2d.setColor(new Color(171, 144, 76));
        g2d.setStroke(new BasicStroke(borderWidth));
        g2d.drawRect(borderWidth/2, borderWidth/2, getWidth() - borderWidth, getHeight() - borderWidth);

        int innerBorder = borderWidth/2;

        g2d.setStroke(new BasicStroke(innerBorder));
        g2d.drawRect((borderWidth*2), (borderWidth*2), getWidth() - 2*(borderWidth*2), getHeight() - 2*(borderWidth*2));

    }
}
