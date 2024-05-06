package controller;

import Server.Web.Game.ServerMessageQueue;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;

import java.util.ArrayList;

public class LobbyController {
    ServerMessageQueue incomingMessages;
    ArrayList<ClientHandlerInterface> connectedPlayers;

    public LobbyController(){
        incomingMessages = new ServerMessageQueue();
        connectedPlayers = new ArrayList<ClientHandlerInterface>();
    }
    public void startListening(int port){
        //Thread t = new Thread(()->new SOCKET_ClientHandler().listenForNewConnections(port));
        //t.start();
        //RMI_ClientHandler RMIhandler = new RMI_ClientHandler(port,)
    }
}
