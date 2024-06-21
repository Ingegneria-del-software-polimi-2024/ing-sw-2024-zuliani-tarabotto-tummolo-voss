package Client.UI.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.fusesource.jansi.Ansi.ansi;

public class EndGamePanel extends JPanel {

    private GUI gui;

    /**
     * creates a new panel that displays the winners and the final points.
     * Two buttons let the player choose whether he wants to go back to the lobby or close the game.
     * @param gui
     */
    public EndGamePanel(GUI gui){
        this.gui = gui;

        //we set the layout for the frame
        setLayout(new BorderLayout());
        setBackground(new Color(50, 84, 70));

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
        winnerLabel.setForeground(new Color(200, 170, 110));
        winnerLabel.setFont(new Font("Serif", Font.BOLD, 100));
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
        winnerNameLabel.setForeground(new Color(200, 170, 110));
        winnerNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        winnerNameLabel.setFont(new Font("Serif", Font.BOLD, 80));
        middlePanel.add(winnerNameLabel);

        ///////////////////////////////////// player's scores   /////////////////////////////////////////////
        StringBuilder scores = new StringBuilder("Scores: ");
        for(int i = 0; i < entryList.size() -1; i++){
            scores.append(entryList.get(i).getKey()).append("(").append(entryList.get(i).getValue()).append("), ");
        }

        scores.append(entryList.get(entryList.size() - 1).getKey()).append("(").append(entryList.get(entryList.size() - 1).getValue()).append(")");
        JLabel scoresLabel = new JLabel();
        scoresLabel.setForeground(Color.BLACK);
        scoresLabel.setText(scores.toString());
        scoresLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoresLabel.setFont(new Font("Serif", Font.BOLD, 20));
        middlePanel.add(scoresLabel);


        ///////////////////////////////////// buttons /////////////////////////////////////////
        //Jpanel for holding the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        //close game button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> System.exit(0));

        //go back to lobby button
        JButton lobbyButton = new JButton("Lobby");
        lobbyButton.addActionListener(e -> gui.goBackToLobby());

        buttonPanel.add(lobbyButton);
        buttonPanel.add(closeButton);
        middlePanel.add(buttonPanel);

        add(wrapperPanel);

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int borderWidth = 6;
        g2d.setColor(new Color(171, 144, 76));
        g2d.setStroke(new BasicStroke(borderWidth));
        g2d.drawRect(borderWidth/2, borderWidth/2, getWidth() - borderWidth, getHeight() - borderWidth);

        int innerBorder = borderWidth/2;

        g2d.setColor(new Color(171, 144, 76));
        g2d.setStroke(new BasicStroke(innerBorder));
        g2d.drawRect((borderWidth*2), (borderWidth*2), getWidth() - 2*(borderWidth*2), getHeight() - 2*(borderWidth*2));
    }
}
