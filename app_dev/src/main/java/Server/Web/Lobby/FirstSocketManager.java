package Server.Web.Lobby;

import Server.Web.Game.SOCKET_ClientHandler;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * The type First socket manager.
 * This class is responsible for the first connection of the client to the server. It listens for new connections
 * and creates a new handler for each client that connects.
 */
public class FirstSocketManager implements Runnable {
    /**
     * The Lobby interface.
     */
    private Lobby lobby;
    /**
     * The instance of the class, it is a singleton
     */
    private static FirstSocketManager instance;
    /**
     * The port of the server
     */
    private int port;
    /**
     * Class constructor
     * @param lobby the lobby interface
     * @param port the port of the server
     * @throws RemoteException when an error in setting of the connection occurs
     */
    private FirstSocketManager(Lobby lobby, int port) {
        this.lobby = lobby;
        this.port = port;
    }

    /**
     * The class is a singleton, returns the single instance of the class. See First_RMI_Manager constructor
     *
     * @param lobby the lobby interface
     * @param port  the port of the server
     * @return the first socket manager
     */
    public static FirstSocketManager getInstance(Lobby lobby, int port){
        if(instance == null) {
            instance = new FirstSocketManager(lobby, port);
        }
        return instance;
    }
    /**
     * This method starts to listen for new connections, in case a new connection is found, a new handler for that
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
