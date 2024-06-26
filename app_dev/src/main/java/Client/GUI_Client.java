package Client;

import Client.UI.GUI.GUI;
import Client.UI.TUI.TUI;
import Client.UI.UI;
import Client.View.ViewAPI;

/**
 * The type Gui client.
 * A runnable that instantiates and starts a new GUI client
 */
public class GUI_Client implements Runnable{
    /**
     * The View API.
     */
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
