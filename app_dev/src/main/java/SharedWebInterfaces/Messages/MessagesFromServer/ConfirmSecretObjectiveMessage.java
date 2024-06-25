package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.ObjectiveCard;

/**
 * The type Confirm secret objective message.
 */
public class ConfirmSecretObjectiveMessage implements MessageFromServer{
    private ObjectiveCard secretObjective;


    /**
     * Instantiates a new Confirm secret objective message.
     *
     * @param secretObjective the secret objective
     */
    public ConfirmSecretObjectiveMessage(ObjectiveCard secretObjective){
        this.secretObjective = secretObjective;

    }

    /**
     * The objective chosen is selected
     * @param view the view interface of the receiver
     */
    @Override
    public void execute(ViewAPI_Interface view) {
        view.confirmSecretObjective(secretObjective);
    }

}
