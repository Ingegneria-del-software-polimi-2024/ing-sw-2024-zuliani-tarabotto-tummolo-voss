package Client;

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



    }



    private void startHeartbeatThread() {
        Thread heartbeatThread = new Thread(() -> {
            while (true) {
                try {
                    view.HeartbeatToServer();
                    System.out.println("l");
                    Thread.sleep(10000); // Send heartbeat every 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });

        heartbeatThread.setDaemon(true);
        heartbeatThread.start();
    }

    @Override
    public void run() {
        view.welcome();
        view.chooseConnection();
        startHeartbeatThread();
    }
}
