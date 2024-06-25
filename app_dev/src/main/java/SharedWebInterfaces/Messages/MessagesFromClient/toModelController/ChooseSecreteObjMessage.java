package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelController;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;
import SharedWebInterfaces.SharedInterfaces.ServerControllerInterface;

public class ChooseSecreteObjMessage implements MessageFromViewToModelController {

    private String chosenObjective;
    private String playerID;

    public ChooseSecreteObjMessage(String chosenObjective, String playerID) {
        this.chosenObjective = chosenObjective;
        this.playerID = playerID;
    }

    @Override
    public void execute(ModelController controller) {
            controller.chooseSecretObjective(chosenObjective, playerID);
    }

    //ignore this
    @Override
    public void execute(ControllerInterface controller) {

    }

}
