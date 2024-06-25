package controller;

import Server.Web.Game.ServerMessageQueue;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;

import java.util.ArrayList;

/**
 * The type Lobby controller.
 */
public class LobbyController {
    /**
     * The Incoming messages.
     */
    ServerMessageQueue incomingMessages;
    /**
     * The Connected players.
     */
    ArrayList<ClientHandlerInterface> connectedPlayers;

    /**
     * Instantiates a new Lobby controller.
     */
    public LobbyController(){
        incomingMessages = new ServerMessageQueue();
        connectedPlayers = new ArrayList<ClientHandlerInterface>();
    }

    /**
     * Start listening.
     *
     * @param port the port
     */
    public void startListening(int port){
        //Thread t = new Thread(()->new SOCKET_ClientHandler().listenForNewConnections(port));
        //t.start();
        //RMI_ClientHandler RMIhandler = new RMI_ClientHandler(port,)
    }
}
