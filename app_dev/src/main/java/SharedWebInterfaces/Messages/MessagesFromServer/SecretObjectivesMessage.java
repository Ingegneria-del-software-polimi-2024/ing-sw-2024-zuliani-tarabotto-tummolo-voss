package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.ObjectiveCard;

/**
 * The type Secret objectives message.
 * This message is used to notify the client of the secret objectives from which the player can choose.
 */
public class SecretObjectivesMessage implements MessageFromServer{
    /**
     * The Objective card number 1.
     */
    private ObjectiveCard obj1;
    /**
     * The Objective card number 2.
     */
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
