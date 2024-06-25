package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelController;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;

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
    public void execute(ControllerInterface controller) {
    }

    @Override
    public void execute(ModelController controller) {
        controller.quitGame(playerID);
    }

}
