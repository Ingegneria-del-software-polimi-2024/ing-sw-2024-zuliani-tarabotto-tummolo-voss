package SharedWebInterfaces.Messages.MessagesToLobby;


import MockModel.Lobby;

import java.io.IOException;

public interface MessageToLobby {
    public void execute(Lobby lobby) throws IOException;
}
