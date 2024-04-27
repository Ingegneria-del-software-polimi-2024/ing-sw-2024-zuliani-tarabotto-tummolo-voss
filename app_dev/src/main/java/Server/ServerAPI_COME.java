package Server;

import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.ClientHandlerInterface;

import java.util.HashMap;

public class ServerAPI_COME {

    private ServerMessageQueue toDoQueue;
    private HashMap<String, ClientHandlerInterface> players;
    //private Controller controller;

    /**
     * class constructor
     */
    public ServerAPI_COME() {
        toDoQueue = new ServerMessageQueue();
        players = new HashMap<String, ClientHandlerInterface>();
    }

    //client called

    /**
     * enqueues the incoming message in the toDoQueue
     * @param message is the message coming from the client
     */
     public void sendToServer(MessageFromClient message){
        toDoQueue.enqueueMessage(message);
     }
    
    public void addNewPlayer(){}

    //controller called
    public void performNextMethod(){}




}
