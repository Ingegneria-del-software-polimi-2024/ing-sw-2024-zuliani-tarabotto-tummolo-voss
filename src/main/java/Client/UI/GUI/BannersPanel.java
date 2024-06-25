package Client.UI.GUI;

import Client.UI.GUI.EventListeners.SwitchBoardListener;
import Client.UI.GUI.PlayerBanner.PlayerPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * panel with a boxLayout that contains the players' PlayerPanel
 */
public class BannersPanel extends JPanel {

    private GUI gui;
    private HashMap<String, PlayerPanel> banners;

    public BannersPanel(GUI gui){
        int screenWidth = gui.getScreenWidth();
        int screenHeight = gui.getScreenHeight();
        this.gui = gui;
        banners = new HashMap<>();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        SwitchBoardListener listener = new SwitchBoardListener(gui);


        String s = gui.getView().getPlayerId();
        PlayerPanel pl = new PlayerPanel( s, (int ) (screenWidth * 0.18), (int)(screenHeight * 0.13), gui);
        pl.setPreferredSize(new Dimension((int ) (screenWidth * 0.18), (int)(screenHeight * 0.13)));
        pl.setMaximumSize(new Dimension((int ) (screenWidth * 0.18), (int)(screenHeight * 0.13)));
        banners.put(s, pl);
        add(pl);
        add(Box.createVerticalStrut(15)); // 20 pixels of space
        pl.addMouseListener(listener);


        for(String p : gui.getView().getPlayers()){
            if(!p.equals(s)){
                PlayerPanel player = new PlayerPanel( p, (int ) (screenWidth * 0.18), (int)(screenHeight * 0.13), gui);
                player.setPreferredSize(new Dimension((int ) (screenWidth * 0.18), (int)(screenHeight * 0.13)));
                player.setMaximumSize(new Dimension((int ) (screenWidth * 0.18), (int)(screenHeight * 0.13)));
                banners.put(p, player);
                add(player);
                add(Box.createVerticalStrut(15)); // 20 pixels of space
                player.addMouseListener(listener);
            }

        }
    }

    /**
     * updates every player's points and resources on the PlayerPanel
     */
    public void updateBanners(){
        for(String p : banners.keySet()){
            banners.get(p).updateResources();
            banners.get(p).updatePoints();
        }
    }
}
