package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.ViewAPI_Interface;
import model.cards.ObjectiveCard;

public class SecretObjectivesMessage implements MessageFromServer{
    private ObjectiveCard obj1;
    private ObjectiveCard obj2;
    public SecretObjectivesMessage(ObjectiveCard obj1, ObjectiveCard obj2){
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    @Override
    public void execute(ViewAPI_Interface view) {
        view.chooseSecretObjective(obj1, obj2);
    }
}
