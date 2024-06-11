package Client.UI.GUI;

import model.cards.PlayableCards.PlayableCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StarterPanel extends JPanel{

    private JFrame parentFrame;
    private GUI gui;

    public StarterPanel(JFrame parentFrame, GUI gui) {
        this.parentFrame = parentFrame;
        this.gui = gui;


        // Create a panel to hold the components
        setLayout(new FlowLayout());
        setBackground(Color.CYAN);


        PlayableCard starterCard = gui.getView().getStarterCard();
        CardLabel front = new CardLabel();
        CardLabel back = new CardLabel();
        starterCard.setFaceSide(true);
        front.updateCard(starterCard, gui.getFronts().get(starterCard.getId()), gui.getBacks().get(starterCard.getId()));
        starterCard.setFaceSide(false);
        back.updateCard(starterCard, gui.getFronts().get(starterCard.getId()), gui.getBacks().get(starterCard.getId()));


        front.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               front.setBackground(Color.BLUE);

           }
        });

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                front.setBackground(Color.BLUE);
            }
        });

        add(front);
        add(back);

 }

}
