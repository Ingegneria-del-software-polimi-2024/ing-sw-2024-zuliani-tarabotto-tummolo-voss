package Server.Web.Lobby;

import Server.Web.Game.SOCKET_ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;

public class FirstSocketManager implements Runnable {
    private Lobby lobby;
    private static FirstSocketManager instance;
    private int port;
    private FirstSocketManager(Lobby l, int port) {
        this.lobby = l;
        this.port = port;
    }

    public static FirstSocketManager getInstance(Lobby l, int port){
        if(instance == null) {
            instance = new FirstSocketManager(l, port);
        }
        return instance;
    }

    public void run() {
        //for debug purpose only
        System.out.println("Started listening for connections");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);

            System.out.println("server started..");
            while(true){
                Socket clientSocket = serverSocket.accept();
                SOCKET_ClientHandler csh = new SOCKET_ClientHandler(clientSocket, lobby);
                Thread t = new Thread(csh);
                t.start();
            }
        } catch (IOException e) {
            //TODO what should I do here?
        }
    }
}
