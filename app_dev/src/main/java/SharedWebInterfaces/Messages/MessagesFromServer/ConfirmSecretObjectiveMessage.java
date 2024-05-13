package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.ObjectiveCard;

public class ConfirmSecretObjectiveMessage implements MessageFromServer{
    private ObjectiveCard secretObjective;


    public ConfirmSecretObjectiveMessage(ObjectiveCard secretObjective){
        this.secretObjective = secretObjective;

    }

    @Override
    public void execute(ViewAPI_Interface view) {
        view.confirmSecretObjective(secretObjective);
    }

}
