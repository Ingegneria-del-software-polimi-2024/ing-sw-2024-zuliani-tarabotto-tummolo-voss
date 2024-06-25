package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelTranslator;
import SharedWebInterfaces.SharedInterfaces.Traslator;

/**
 * The type Select starter card message.
 */
public class SelectStarterCardMessage implements MessageFromViewToModelController {
    @Override
    public void execute(ModelTranslator controller) {
        //good method
        System.out.println("HI");
    }

    @Override
    public void execute(Traslator controller) {

    }
}
