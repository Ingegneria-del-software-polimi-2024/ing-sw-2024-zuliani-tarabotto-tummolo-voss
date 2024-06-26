package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelTranslator;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.Traslator;

/**
 * The type Ready to play message.
 * This message is sent by the client when the player is ready to play.
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
