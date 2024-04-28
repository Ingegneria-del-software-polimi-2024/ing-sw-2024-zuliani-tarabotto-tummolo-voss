package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.ViewAPI_Interface;

public class ConfirmSecretObjectiveMessage implements MessageFromServer{
    private int secretObjective;
    private String player;


    public ConfirmSecretObjectiveMessage(int secretObjective, String player){
        this.secretObjective = secretObjective;
        this.player = player;
    }


    @Override
    public void execute(ViewAPI_Interface view) {
        view.setSecretObjective(secretObjective, player);
    }

}
