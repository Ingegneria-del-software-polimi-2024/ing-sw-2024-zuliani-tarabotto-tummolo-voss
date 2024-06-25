package Server.Web.Lobby;

import Server.Web.Game.SOCKET_ClientHandler;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * The type First socket manager.
 */
public class FirstSocketManager implements Runnable {
    private Lobby lobby;
    private static FirstSocketManager instance;
    private int port;
    /**
     * class constructor
     * @param lobby the lobby-controller
     * @param port the port of the server
     * @throws RemoteException when an error in setting of the connection occurs
     */
    private FirstSocketManager(Lobby lobby, int port) {
        this.lobby = lobby;
        this.port = port;
    }

    /**
     * the class is a singleton, returns the single instance of the class. See First_RMI_Manager constructor
     *
     * @param lobby the lobby
     * @param port  the port
     * @return the first socket manager
     */
    public static FirstSocketManager getInstance(Lobby lobby, int port){
        if(instance == null) {
            instance = new FirstSocketManager(lobby, port);
        }
        return instance;
    }
    /**
     * this method starts to listen for new connections, in case a new connection is found, a new handler for that
     * client is created
     */
    public void run() {

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
        } catch (IOException | RuntimeException e) {
            throw new RuntimeException();
        }
    }
}
