package Client;


import Client.View.ViewAPI;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;

public class ClientAPI_COME {
    private ClientMessageQueue toDoQueue;
    private ViewAPI view;


    public void notifyChanges(MessageFromServer message){toDoQueue.enqueueMessage(message);}

    public void performChanges(){toDoQueue.executeNextMessage(view);};
}
