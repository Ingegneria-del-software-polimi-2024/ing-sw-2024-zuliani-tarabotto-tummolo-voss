package Client.Web;


import Client.View.ViewAPI;
import SharedWebInterfaces.Messages.MessagesFromServer.Errors.ReturnToStartMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.InterruptConnectionMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;

/**
 * The type Client api come.
 */
public class ClientAPI_COME implements Runnable{
    private ClientMessageQueue toDoQueue;
    private ClientMessageQueue chatQueue;
    private ViewAPI view;

    /**
     * Instantiates a new Client api come.
     *
     * @param view the view
     */
    public ClientAPI_COME(ViewAPI view){
        this.view = view;
        toDoQueue = new ClientMessageQueue();
        chatQueue = new ClientMessageQueue();
    }

    /**
     * receives and enqueues the message specified from the Server
     *
     * @param message the message from the server
     */
    public void notifyChanges(MessageFromServer message){
        toDoQueue.enqueueMessage(message);
    }

    /**
     * executes the first action in the queue
     */
    public void performChanges(){toDoQueue.executeNextMessage(view);};

    /**
     * method that dequeues continuously messages from the queue
     */
    @Override
    public void run() {
        MessageFromServer message;
        do{
            message = toDoQueue.getNextMessage();
            if(message!=null && !(message instanceof InterruptConnectionMessage)) {
                //view.controlMessage(message);//TODO implement the control
                //IMPORTANT: I may choose to execute the InterruptConnectionMessage in order to end everything
                message.execute(view);
            }
        }while(!(message instanceof InterruptConnectionMessage) && !(message instanceof ReturnToStartMessage));
    }

    /**
     * enqueues a message in the list of actions
     *
     * @param msg the message
     */
    public void enqueue(MessageFromServer msg){
        toDoQueue.enqueueMessage(msg);
    }

    /**
     * a loop that dequeues and executes only chat messages
     */
    public void dequeueChatMessages(){
        while (true){
            MessageFromServer msg = chatQueue.getNextMessage();
            if(msg != null){
                msg.execute(view);
            }
        }
    }

    /**
     * enqueues a chat message in the dedicated list
     *
     * @param msg the chat message to be enqueued
     */
    public void enqueueChatMessage(MessageFromServer msg) {
        chatQueue.enqueueMessage(msg);
    }
}
