package SharedWebInterfaces.Messages.MessagesFromClient;

import Server.ModelController;
import SharedWebInterfaces.Messages.Message;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;

import java.io.Serializable;

/**
 * The interface Message from client.
 */
public interface MessageFromClient extends Message, Serializable {
    /**
     * Execute.
     *
     * @param controller the controller
     */
    void execute(ControllerInterface controller);

    /**
     * Execute.
     *
     * @param controller the controller
     */
    void execute(ModelController controller);
    //TODO sistemare questa cosa
}
