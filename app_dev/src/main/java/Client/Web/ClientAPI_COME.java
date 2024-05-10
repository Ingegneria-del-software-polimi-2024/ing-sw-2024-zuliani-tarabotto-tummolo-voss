package Client.Web;


import Client.View.ViewAPI;
import SharedWebInterfaces.Messages.MessagesFromServer.InterruptConnectionMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;

public class ClientAPI_COME implements Runnable{
    private ClientMessageQueue toDoQueue;
    private ViewAPI view;

    public ClientAPI_COME(ViewAPI view){
        this.view = new ViewAPI();
        toDoQueue = new ClientMessageQueue();
    }

    /**
     * receives and enqueues the message specified from the Server
     * @param message the message from the server
     */
    public void notifyChanges(MessageFromServer message){toDoQueue.enqueueMessage(message);}

    /**
     * executes the first action in the queue
     */
    public void performChanges(){toDoQueue.executeNextMessage(view);};

    @Override
    public void run() {
        MessageFromServer message;
        do{
            message = toDoQueue.getNextMessage();
            if(message!=null && !(message instanceof InterruptConnectionMessage))
                //view.controlMessage(message);//TODO implement the control
                //IMPORTANT: I may choose to execute the InterruptConnectionMessage in order to end everything
                message.execute(view);
        }while(!(message instanceof InterruptConnectionMessage));
    }
    public void enqueue(MessageFromServer msg){
        toDoQueue.enqueueMessage(msg);
    }
}
