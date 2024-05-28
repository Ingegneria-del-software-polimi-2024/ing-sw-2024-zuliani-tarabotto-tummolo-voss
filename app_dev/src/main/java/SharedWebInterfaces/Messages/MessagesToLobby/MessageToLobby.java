package SharedWebInterfaces.Messages.MessagesToLobby;


import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.Messages.Message;

import java.io.IOException;
import java.io.Serializable;

public interface MessageToLobby extends Message, Serializable {
    public abstract void execute(Lobby lobby);
    public abstract String getSender();

}
