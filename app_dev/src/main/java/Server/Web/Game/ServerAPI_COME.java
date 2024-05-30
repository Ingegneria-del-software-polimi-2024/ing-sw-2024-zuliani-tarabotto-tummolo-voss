package Server.Web.Game;

import Server.ModelController;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;

import java.util.HashMap;

public class ServerAPI_COME {

    private ServerMessageQueue toDoQueue;
    private HashMap<String, ClientHandlerInterface> players;
    private ModelController controller;
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

    /**
     * loops that dequeues messages from the queue of incoming messages
     */
    public void loop(){
        MessageFromClient message;
         while (true) {
             message = toDoQueue.getNextMessage();
             //if you find a message in the waiting list
             if (message != null) {
                 if(controller.checkMessage(message))
                    //if that message has sense execute it
                    message.execute(controller);
                 //else
                 //if that message doesn't have sense
                 //    toDoQueue.enqueueMessage(message);
                 //it would be interesting if we implemented a kind of "waiting time varible" that increases until a value when the message is destroyed
             }
         }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * class constructor
     * @param controller
     */
    //TODO complete description
    public ServerAPI_COME(ModelController controller) {
        toDoQueue = new ServerMessageQueue();
        players = new HashMap<String, ClientHandlerInterface>();
        this.controller = controller;
    }


}
