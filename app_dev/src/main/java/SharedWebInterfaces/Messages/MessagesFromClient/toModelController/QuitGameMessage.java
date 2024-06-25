package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelTranslator;
import SharedWebInterfaces.SharedInterfaces.Traslator;

/**
 * The type Quit game message.
 */
public class QuitGameMessage implements MessageFromViewToModelController{
    private final String playerID;

    /**
     * Instantiates a new Quit game message.
     *
     * @param playerID the player id
     */
    public QuitGameMessage(String playerID) {
        this.playerID = playerID;
    }

    @Override
    public void execute(Traslator controller) {
    }

    @Override
    public void execute(ModelTranslator controller) {
        controller.quitGame(playerID);
    }

}
