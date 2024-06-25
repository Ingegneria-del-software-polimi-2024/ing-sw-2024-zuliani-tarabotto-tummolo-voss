package SharedWebInterfaces.Messages.MessagesFromServer.Errors;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

public class EmptyDeckMessage implements MessageFromServer {
    private int source;
    @Override
    public void execute(ViewAPI_Interface view) {
        view.cantDrawCard(source);
    }

    public EmptyDeckMessage(int source) {
        this.source = source;
    }
}
