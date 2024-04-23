package Server;

import SharedWebInterfaces.Messages.GeneralMessage;
import SharedWebInterfaces.ServerInterface;
import SharedWebInterfaces.ToDoList.MessageQueue;

import java.util.HashMap;

public class ServerAPI_COME {

    private MessageQueue toDoQueue;
    private HashMap<String, ServerInterface> players;
    //private Controller controller;

    /**
     * class constructor
     */
    public ServerAPI_COME() {
        toDoQueue = new MessageQueue();
        players = new HashMap<String, ServerInterface>();
    }

    //client called


     public void enqueueMessage(GeneralMessage message){
        toDoQueue.enqueueMessage(message);
     }
    
    public void addNewPlayer(){}

    //controller called
    public void performNextMethod(){}




}
