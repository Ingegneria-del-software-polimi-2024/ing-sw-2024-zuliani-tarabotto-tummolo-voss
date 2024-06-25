package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelTranslator;
import SharedWebInterfaces.SharedInterfaces.Traslator;

/**
 * The type Choose secrete obj message.
 */
public class ChooseSecreteObjMessage implements MessageFromViewToModelController {

    private String chosenObjective;
    private String playerID;

    /**
     * Instantiates a new Choose secrete obj message.
     *
     * @param chosenObjective the chosen objective
     * @param playerID        the player id
     */
    public ChooseSecreteObjMessage(String chosenObjective, String playerID) {
        this.chosenObjective = chosenObjective;
        this.playerID = playerID;
    }

    @Override
    public void execute(ModelTranslator controller) {
            controller.chooseSecretObjective(chosenObjective, playerID);
    }

    //ignore this
    @Override
    public void execute(Traslator controller) {

    }

}
