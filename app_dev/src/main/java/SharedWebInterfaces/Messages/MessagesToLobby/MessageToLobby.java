package SharedWebInterfaces.Messages.MessagesToLobby;


import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.Messages.Message;

import java.io.IOException;
import java.io.Serializable;

public interface MessageToLobby extends Serializable, Message{
    public void execute(Lobby lobby) throws IOException;
    public String getSender();
}
