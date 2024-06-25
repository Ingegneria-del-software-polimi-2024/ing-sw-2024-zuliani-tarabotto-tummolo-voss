package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelTranslator;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.Traslator;

/**
 * The type Ready to play message.
 */
public class ReadyToPlayMessage implements  MessageFromClient {


    @Override
    public void execute(ModelTranslator controller) {
        controller.setPlayerReady();
    }


    @Override
    public void execute(Traslator controller) {
        System.out.println("sbagliato");
    }
}
