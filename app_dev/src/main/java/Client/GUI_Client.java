package Client;

import Client.UI.GUI.GUI;
import Client.UI.TUI.TUI;
import Client.UI.UI;
import Client.View.ViewAPI;

/**
 * The type Gui client.
 */
public class GUI_Client implements Runnable{

    private ViewAPI view;

    /**
     * Instantiates a new Gui client.
     */
    public GUI_Client() {
        this.view = new ViewAPI();
        UI ui = new GUI(view);
        view.setUI(ui);
        Thread t = new Thread(ui);
        t.start();
    }

    @Override
    public void run() {
        view.chooseConnection();
    }
}
