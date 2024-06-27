package SharedWebInterfaces.Messages.MessagesFromClient;

import Server.ModelTranslator;
import SharedWebInterfaces.Messages.Message;
import SharedWebInterfaces.SharedInterfaces.Traslator;

import java.io.Serializable;

/**
 * The interface Message from client.
 * This interface is used to define the methods that a message from the client must have.
 */
public interface MessageFromClient extends Message, Serializable {
    /**
     * Executes the message using the controller.
     * In truth the Translator interface is used to communicate with the controller.
     *
     * @param controller the controller
     */
    void execute(Traslator controller);

    /**
     * Executes the message using the controller.
     * In truth the Translator is used to communicate with the controller.     *
     * @param controller the controller
     */
    void execute(ModelTranslator controller);
}
