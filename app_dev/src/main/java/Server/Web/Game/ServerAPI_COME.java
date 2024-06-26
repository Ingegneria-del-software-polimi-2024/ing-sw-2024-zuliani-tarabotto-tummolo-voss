package Server.Web.Game;

import Chat.MessagesFromClient.ChatMessage;
import Server.ModelTranslator;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;

import java.util.HashMap;

/**
 * The type Server api come, an interface to handle the reception of messages from the clients.
 *
 */
public class ServerAPI_COME {
    /**
     * The queue containing the executable messages received from the clients
     */
    private ServerMessageQueue toDoQueue;
    /**
     * The map containing the players and their handlers
     */
    private HashMap<String, ClientHandlerInterface> players;
    /**
     * The controller interface
     */
    private ModelTranslator translator;
    //client called

    /**
     * Enqueues the incoming message in the toDoQueue
     *
     * @param message is the message coming from the client
     */
    public void sendToServer(MessageFromClient message){
         System.out.println("–––––––––––––––––––––––––––––");
         System.out.println("ARRIVED MESSAGE: "+message.getClass());
         toDoQueue.enqueueMessage(message);
     }

    /**
     * Add new player.
     *
     * @param nickName the nickname
     * @param handler  the handler
     */
    public void addNewPlayer(String nickName, ClientHandlerInterface handler){
         if(players.get(nickName) != null)
             throw new RuntimeException();
         players.put(nickName, handler);
    }

    /**
     * Performs next method.
     */
//controller called
    public void performNextMethod(){}

    /**
     * Loop that dequeues messages from the queue of incoming messages
     */
    public void loop(){
        MessageFromClient message;
         while (true) {
             message = toDoQueue.getNextMessage();
             //if you find a message in the waiting list
             if (message != null) {
                 if (translator.checkMessage(message) || message instanceof ChatMessage)
                     message.execute(translator);
             }
         }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Class constructor
     *
     * @param translator the controller
     */
    public ServerAPI_COME(ModelTranslator translator) {
        toDoQueue = new ServerMessageQueue();
        players = new HashMap<String, ClientHandlerInterface>();
        this.translator = translator;
    }


}
