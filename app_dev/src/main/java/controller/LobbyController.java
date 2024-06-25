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
}

