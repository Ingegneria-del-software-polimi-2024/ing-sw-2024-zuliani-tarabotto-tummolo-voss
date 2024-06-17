package Client;

import Client.UI.TUI.TUI;
import Client.UI.UI;
import Client.View.ViewAPI;


import java.util.Scanner;

public class TUI_Client implements Runnable{
    private ViewAPI view;

    public TUI_Client() {
        this.view = new ViewAPI();
        UI ui = new TUI(view);
        view.setUI(ui);
    }





    @Override
    public void run() {
        view.welcome();
        view.chooseConnection();
    }
}
