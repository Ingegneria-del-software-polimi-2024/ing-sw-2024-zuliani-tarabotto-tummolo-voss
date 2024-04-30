package Server;

import model.lobby.Lobby_SEND;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FirstSocketManager implements Runnable {
    private Lobby_RECEIVE_CONTROLLER receive;
    private Lobby_SEND send;
    private static FirstSocketManager instance;
    public FirstSocketManager(Lobby_RECEIVE_CONTROLLER l, Lobby_SEND s) {
        receive = l;
        send = s;
    }//todo private WTFFF

    public static FirstSocketManager getInstance(Lobby_RECEIVE_CONTROLLER l, Lobby_SEND s){
        if(instance == null) {
            instance = new FirstSocketManager(l, s);
        }
        return instance;
    }

    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(2910);

            System.out.println("server started..");
            while(true){
                Socket clientSocket = serverSocket.accept();
                SOCKET_ClientHandler csh = new SOCKET_ClientHandler(clientSocket, receive);
                send.addConnection(csh);
                Thread t = new Thread(csh);
                t.start();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
