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
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setOpaque(false);


        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(gui.getView().getPoints().entrySet());
        // Step 2: Sort the list with a comparator that compares the values in descending order
        entryList.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        List<String> winners = gui.getView().getWinners();


        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(218, 211, 168));;

        // Create and add components to the end-game panel
        JLabel winnerLabel = new JLabel();
        if(winners.size() == 1) {
            winnerLabel.setText("The winner is: ");
        }else{ winnerLabel.setText("The winners are: ");}
        winnerLabel.setForeground(Color.YELLOW);
        winnerLabel.setFont(new Font("Beaufort", Font.BOLD, 70));
        winnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        middlePanel.add(winnerLabel);
        //add(winnerLabel);

        ///////////////////////////////////////////////////////////////////////////////////
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
        //add(winnerNameLabel);
        middlePanel.add(winnerNameLabel);

        //////////////////////////////////////////////////////////////////////////////////
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
        //add(scoresLabel);
        middlePanel.add(scoresLabel);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton closeButton = new JButton("Close Game");
        closeButton.addActionListener(e -> System.exit(0));

        JButton lobbyButton = new JButton("Lobby");
        lobbyButton.addActionListener(e -> gui.quitGame());

        buttonPanel.add(lobbyButton);
        buttonPanel.add(closeButton);
        middlePanel.add(buttonPanel);


        add(middlePanel);



        if(winners.size() == 1)
            System.out.print(ansi().fg(226).a("~> " + winners.get(0).toUpperCase() + " is the winner !!!\n").reset());
        else {
            System.out.print(ansi().fg(226).a("~> The winners are:\n").reset());
            for (String win : winners) {
                System.out.print(ansi().fg(226).a("                   " + win.toUpperCase() + " !!!\n").reset());
            }
        }
        for (Map.Entry<String, Integer> stringIntegerEntry : entryList) {
            System.out.println("-> " + stringIntegerEntry.getKey() + ": " + stringIntegerEntry.getValue() + " points");
        }

    }
}
