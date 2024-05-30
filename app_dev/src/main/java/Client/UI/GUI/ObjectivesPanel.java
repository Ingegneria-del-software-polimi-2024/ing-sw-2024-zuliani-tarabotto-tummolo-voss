package Client.UI.GUI;

import model.cards.ObjectiveCard;
import model.objective.Objective;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ObjectivesPanel extends JPanel {
    private GUI gui;
    private objCardLabel common1;
    private objCardLabel common2;
    private objCardLabel secret;

    public ObjectivesPanel(GUI gui){
        this.gui = gui;
        setBackground(new Color(50, 84, 70));

        //this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        common1 = new objCardLabel();
        common2 = new objCardLabel();
        secret = new objCardLabel();
        this.add(common1);
        this.add(common2);
    }

    public void updateObjectivesPanel(){
        ObjectiveCard c1 = gui.getView().getCommonObjectives().get(0);
        ObjectiveCard c2 = gui.getView().getCommonObjectives().get(1);

        common1.updateCard(c1, gui.getFronts().get(c1.getId()), gui.getBacks().get(c1.getId()));
        common2.updateCard(c2, gui.getFronts().get(c2.getId()), gui.getBacks().get(c2.getId()));
    }

    public void addSecretObj(){
        ObjectiveCard secretObj = gui.getView().getSecretObjective();
        secret.updateCard(secretObj, gui.getFronts().get(secretObj.getId()), gui.getFronts().get(secretObj.getId()));
    }
}
