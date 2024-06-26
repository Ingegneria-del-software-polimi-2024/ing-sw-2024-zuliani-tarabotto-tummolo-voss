package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.ObjectiveCard;

public class ConfirmSecretObjectiveMessage implements MessageFromServer{
    private ObjectiveCard secretObjective;


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
