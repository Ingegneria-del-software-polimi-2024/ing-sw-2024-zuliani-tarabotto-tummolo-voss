package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

public class SecretObjectivesMessage implements MessageFromServer{
    private int obj1;
    private int obj2;
    public SecretObjectivesMessage(int obj1, int obj2){
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    @Override
    public void execute(ViewAPI_Interface view) {
        view.chooseSecretObjective(obj1, obj2);
    }
}
