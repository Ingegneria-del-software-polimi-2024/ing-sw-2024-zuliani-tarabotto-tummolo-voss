package SharedWebInterfaces.Messages.MessagesFromLobby;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

public class AlreadyExistingNameMessage implements MessageFromServer {
    private String name;
    public AlreadyExistingNameMessage(String name) {
        this.name = name;
    }

    @Override
    public void execute(ViewAPI_Interface view) {
        view.nickNameAlreadyInUse();
    }

    public String getUnavailableName(){
        return name;
    }
}
