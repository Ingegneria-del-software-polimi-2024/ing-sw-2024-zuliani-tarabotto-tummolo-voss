package Client.UI.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.fusesource.jansi.Ansi.ansi;

public class EndGamePanel extends JPanel {

    private GUI gui;

    public EndGamePanel(GUI gui){
        this.gui = gui;

        //we set the layout for the frame
        setLayout(new BorderLayout());
        setBackground(new Color(218, 211, 168));;

        //we create the main panel with  a box layout
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setOpaque(false);
        middlePanel.setOpaque(false);




        // we create a wrapper panel with gridbaglayout to center the main panel in the middle of the screen
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.add(middlePanel);
        add(wrapperPanel, BorderLayout.CENTER);
        wrapperPanel.setOpaque(false);


        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(gui.getView().getPoints().entrySet());
        entryList.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        List<String> winners = gui.getView().getWinners();



        // Create and add components to the end-game panel
        /////////////////////////////// winner string ////////////////////////////////////////////////
        JLabel winnerLabel = new JLabel();
        if(winners.size() == 1) {
            winnerLabel.setText("The winner is: ");
        }else{ winnerLabel.setText("The winners are: ");}
        winnerLabel.setForeground(Color.YELLOW);
        winnerLabel.setFont(new Font("Beaufort", Font.BOLD, 70));
        winnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        middlePanel.add(winnerLabel);


        //////////////////////////////////// winner name ///////////////////////////////////////////////
        String names = "";
        for(int i = 0; i < winners.size() - 1; i++){
            names += winners.get(i) + ", ";
        }

        names += winners.get(winners.size() - 1);
        JLabel winnerNameLabel = new JLabel();
        winnerNameLabel.setText(names);
        winnerNameLabel.setForeground(Color.YELLOW);
        winnerNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        winnerNameLabel.setFont(new Font("Beaufort", Font.BOLD, 50));
        middlePanel.add(winnerNameLabel);

        ///////////////////////////////////// player's scores   /////////////////////////////////////////////
        String scores = "Scores -> ";
        for(int i = 0; i < entryList.size() - 1; i++){
            scores += entryList.get(i).getKey() + ": " + entryList.get(i).getValue() + ", ";
        }

        scores += entryList.get(entryList.size() - 1).getKey() + ": " + entryList.get(entryList.size() - 1).getValue() + ", ";
        JLabel scoresLabel = new JLabel();
        scoresLabel.setForeground(Color.BLACK);
        scoresLabel.setText(scores);
        scoresLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoresLabel.setFont(new Font("Beaufort", Font.BOLD, 30));
        middlePanel.add(scoresLabel);


        ///////////////////////////////////// buttons /////////////////////////////////////////
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton closeButton = new JButton("Close Game");
        closeButton.addActionListener(e -> System.exit(0));

        JButton lobbyButton = new JButton("Lobby");
        lobbyButton.addActionListener(e -> gui.goBackToLobby());

        buttonPanel.add(lobbyButton);
        buttonPanel.add(closeButton);
        middlePanel.add(buttonPanel);

        add(wrapperPanel);

    }
}
