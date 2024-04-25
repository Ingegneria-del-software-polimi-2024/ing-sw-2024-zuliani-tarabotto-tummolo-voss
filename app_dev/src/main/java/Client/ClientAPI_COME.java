package Client;

<<<<<<< Updated upstream
import SharedWebInterfaces.Messages.ClientViewInterface;
import SharedWebInterfaces.Messages.MessageFromServer;
import SharedWebInterfaces.Messages.MessageQueue;

public class ClientAPI_COME {
    private MessageQueue toDoQueue;
    private ClientViewInterface view;
=======
import Client.View.ViewAPI;

import java.util.List;

public class ClientAPI_COME {
    private List<Runnable> actionList;
    private ViewAPI view;
>>>>>>> Stashed changes

    public void notifyChanges(MessageFromServer message){toDoQueue.enqueueMessage(message);}

    public void performChanges(){toDoQueue.executeNextMessage(view);};
}
