package SharedWebInterfaces.Messages.MessagesFromLobby;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

public class ACK_NewConnection implements MessageFromServer {
    private String user;
    @Override
    public void execute(ViewAPI_Interface view) {
        view.ackNickName(user);
        view.displayAvailableGames();
    }

    public ACK_NewConnection(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }
}
