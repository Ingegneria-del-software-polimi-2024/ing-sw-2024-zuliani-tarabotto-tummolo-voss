package SharedWebInterfaces.Messages.MessagesFromClient;

import Server.ModelTranslator;
import SharedWebInterfaces.Messages.Message;
import SharedWebInterfaces.SharedInterfaces.Traslator;

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
    void execute(Traslator controller);

    /**
     * Execute.
     *
     * @param controller the controller
     */
    void execute(ModelTranslator controller);
    //TODO sistemare questa cosa
}
