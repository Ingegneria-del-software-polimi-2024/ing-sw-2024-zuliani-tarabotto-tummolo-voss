package Client;


import Client.View.ViewAPI;
import SharedWebInterfaces.Messages.MessagesFromServer.InterruptConnectionMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;

public class ClientAPI_COME {
    private ClientMessageQueue toDoQueue;
    private ViewAPI view;

    /**
     * receives and enqueues the message specified from the Server
     * @param message the message from the server
     */
    public void notifyChanges(MessageFromServer message){toDoQueue.enqueueMessage(message);}

    /**
     * executes the first action in the queue
     */
    public void performChanges(){toDoQueue.executeNextMessage(view);};

    public void loop(){
        MessageFromServer message;
        do{
            message = toDoQueue.getNextMessage();
            if(message!=null && !(message instanceof InterruptConnectionMessage))
                //view.controlMessage(message);//TODO implement the control
                //IMPORTANT: I may choose to execute the InterruptConnectionMessage in order to end everything
                message.execute(view);
        }while(!(message instanceof InterruptConnectionMessage));
    }
}
