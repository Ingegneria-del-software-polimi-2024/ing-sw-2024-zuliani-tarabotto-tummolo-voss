package Client;

import Client.UI.TUI.TUI;
import Client.UI.UI;
import Client.View.ViewAPI;
import Client.Web.ClientAPI_COME;
import Client.Web.ClientAPI_GO;
import Client.Web.RMI_ServerHandler;
import Client.Web.SOCKET_ServerHandler;
import SharedWebInterfaces.SharedInterfaces.ServerHandlerInterface;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;

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
        view.chooseConnection();
    }
}
