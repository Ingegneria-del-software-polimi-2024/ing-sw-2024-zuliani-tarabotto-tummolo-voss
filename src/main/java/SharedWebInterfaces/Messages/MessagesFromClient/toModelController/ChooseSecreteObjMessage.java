package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelTranslator;
import SharedWebInterfaces.SharedInterfaces.Traslator;

/**
 * The type Choose secrete obj message.
 * This message is used to communicate the choosing of the secret objective of a player
 */
public class ChooseSecreteObjMessage implements MessageFromViewToModelController {

    /**
     * The Chosen objective's id.
     */
    private String chosenObjective;
    /**
     * The player's id.
     */
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
