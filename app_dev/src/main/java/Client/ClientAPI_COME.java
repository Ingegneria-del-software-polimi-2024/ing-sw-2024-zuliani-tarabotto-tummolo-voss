package Client;


import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.Messages.MessageQueue;

public class ClientAPI_COME {
    private MessageQueue toDoQueue;
    private ClientViewInterface view;


    public void notifyChanges(MessageFromServer message){toDoQueue.enqueueMessage(message);}

    public void performChanges(){toDoQueue.executeNextMessage(view);};
}
