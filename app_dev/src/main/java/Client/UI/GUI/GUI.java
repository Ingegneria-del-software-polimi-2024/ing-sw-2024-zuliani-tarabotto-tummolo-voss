package Client.UI.GUI;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;



public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("CODEX NATURALIS");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int width = (int)screenSize.getWidth();
            int height = (int)screenSize.getHeight();


            frame.setSize(width, height);
            frame.setLayout(new FlowLayout(FlowLayout.LEADING));

            System.out.println((width + height));

            ArrayList<String> paths= new ArrayList<>();
            int[] numbers = {1,2,3,4,5,6,7};
            paths.add("/Images/f.png");
            paths.add("/Images/animal.png");
            paths.add("/Images/plant.png");
            paths.add("/Images/insect.png");
            paths.add("/Images/feather.png");
            paths.add("/Images/inkwell.png");
            paths.add("/Images/manuscript.png");

            ImageIcon playerIcon = new ImageIcon("/Images/playerIcon/icon2.jpeg");


            int[] nums = {1,2,3,4,5,6,7};
            PlayerPanel player = new PlayerPanel(paths, numbers, (int ) (width * 0.18), (int)(height * 0.13));
            player.setPreferredSize(new Dimension((int ) (width * 0.18), (int)(height * 0.13)));
            frame.add(player);
            //frame.add(new test("/Images/playerIcon/icon2.jpeg", paths, nums));

            //PlayerBanner player1 = new PlayerBanner(width, height);
            //player.setBackground(new Color(14, 32, 24));
            //frame.add(player1);
            frame.setVisible(true);
        });


    }

}
