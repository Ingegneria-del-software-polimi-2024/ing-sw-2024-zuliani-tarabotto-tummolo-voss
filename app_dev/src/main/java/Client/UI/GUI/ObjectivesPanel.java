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

    public ObjectivesPanel(GUI gui){
        this.gui = gui;
        setBackground(new Color(50, 84, 70));

        //this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        common1 = new ObjCardLabel();
        common2 = new ObjCardLabel();
        secret1 = new ObjCardLabel();
        secret2 = new ObjCardLabel();
        this.add(common1);
        this.add(common2);
        this.add(secret1);
        this.add(secret2);
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
                panel.remove(secret2);
                panel.revalidate();
                panel.repaint();
            }
        });

        secret2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panel.remove(secret1);
                panel.revalidate();
                panel.repaint();
            }
        });
    }

    public void updateObjectivesPanel(){
        ObjectiveCard c1 = gui.getView().getCommonObjectives().get(0);
        ObjectiveCard c2 = gui.getView().getCommonObjectives().get(1);

        common1.updateCard(c1, gui.getFronts().get(c1.getId()), gui.getBacks().get(c1.getId()));
        common2.updateCard(c2, gui.getFronts().get(c2.getId()), gui.getBacks().get(c2.getId()));
    }

    public void addSecretObj(){
        ObjectiveCard secretObj = gui.getView().getSecretObjective();
        secret1.updateCard(secretObj, gui.getFronts().get(secretObj.getId()), gui.getFronts().get(secretObj.getId()));
    }
}
