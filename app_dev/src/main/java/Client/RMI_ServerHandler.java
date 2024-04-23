package Client;

import SharedWebInterfaces.ClientInterface;
import SharedWebInterfaces.ServerInterface;

public class RMI_ServerHandler implements ClientInterface {

    private ClientAPI_COME api;
    private ServerInterface server;
    @Override
    public void enqueueMessage() {}

    @Override
    public void addNewPlayer() {}

    @Override
    public void notifyChanges() {}
}
