package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelTranslator;
import SharedWebInterfaces.SharedInterfaces.Traslator;

/**
 * The type Quit game message.
 * This message is sent from the view to the model controller to quit the game in every moment after the game starts.
 */
public class QuitGameMessage implements MessageFromViewToModelController{
    /**
     * The Player id.
     */
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
