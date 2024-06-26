package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.ObjectiveCard;

public class SecretObjectivesMessage implements MessageFromServer{
    private ObjectiveCard obj1;
    private ObjectiveCard obj2;
    public SecretObjectivesMessage(ObjectiveCard obj1, ObjectiveCard obj2){
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    /**
     * Sets the secret objectives of the receiver
     * @param view the view interface of the receiver
     */
    @Override
    public void execute(ViewAPI_Interface view) {
        view.setSecretObjectives(obj1, obj2);
    }
}
