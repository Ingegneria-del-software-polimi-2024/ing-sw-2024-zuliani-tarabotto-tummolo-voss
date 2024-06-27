package SharedWebInterfaces.Messages.MessagesFromServer.Errors;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

/**
 * The type Return to start message.
 * This message is self sent from the client to the client when the server disconnects/falls.
 * It is used to return the client to the start screen (the choosing of a connection).
 * This is done to prevent the client from being stuck in a game that has been disconnected from the server.
 * Using this message will erase the viewModel data.
 */
public class ReturnToStartMessage implements MessageFromServer {
    @Override
    public void execute(ViewAPI_Interface view) {
        view.returnToStart();
    }
}
