package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelController;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;

public class DisconnectionMessage implements MessageFromViewToModelController{


    public DisconnectionMessage(){
    }

    @Override
    public void execute(ModelController controller) {
        controller.HandleDisconnection();
    }

    //ignore this





    @Override
    public void execute(ControllerInterface controller)  {}
}