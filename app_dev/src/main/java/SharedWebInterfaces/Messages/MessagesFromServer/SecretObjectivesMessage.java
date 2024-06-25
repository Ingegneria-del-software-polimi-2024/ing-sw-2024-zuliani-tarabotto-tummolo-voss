package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.ObjectiveCard;

/**
 * The type Secret objectives message.
 */
public class SecretObjectivesMessage implements MessageFromServer{
    private ObjectiveCard obj1;
    private ObjectiveCard obj2;

    /**
     * Instantiates a new Secret objectives message.
     *
     * @param obj1 the obj 1
     * @param obj2 the obj 2
     */
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
