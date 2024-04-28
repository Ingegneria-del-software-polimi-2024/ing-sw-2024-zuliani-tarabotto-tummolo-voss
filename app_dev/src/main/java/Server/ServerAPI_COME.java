package Server;

import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.ClientHandlerInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerAPI_COME {

    private ServerMessageQueue toDoQueue;
    private HashMap<String, ClientHandlerInterface> players;
    private ModelController controller;

    /**
     * class constructor
     */
    public ServerAPI_COME() {
        toDoQueue = new ServerMessageQueue();
        players = new HashMap<String, ClientHandlerInterface>();
        //TODO how to initialize the controller?
    }

    //client called

    /**
     * enqueues the incoming message in the toDoQueue
     * @param message is the message coming from the client
     */
     public void sendToServer(MessageFromClient message){
        toDoQueue.enqueueMessage(message);
     }
    
    public void addNewPlayer(String nickName, ClientHandlerInterface handler){
         if(players.get(nickName) != null)
             throw new RuntimeException();
         players.put(nickName, handler);
    }

    //controller called
    public void performNextMethod(){}

    public void loop(){
        MessageFromClient message;
         while (true) {
             message = toDoQueue.getNextMessage();
             //if you find a message in the waiting list
             if (message != null)
                 if(controller.checkMessage(message))
                     //if that message has sense execute it
                     message.execute(controller);
                 else
                     //if that message doesn't have sense
                     toDoQueue.enqueueMessage(message);
                    //TODO it would be interesting if we implemented a kind of "waiting time varible" that increases until a value when the message is destroyed

         }
    }


}
