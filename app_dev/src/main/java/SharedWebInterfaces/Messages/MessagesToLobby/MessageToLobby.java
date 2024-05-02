package SharedWebInterfaces.Messages.MessagesToLobby;


import MockModel.Lobby;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

public interface MessageToLobby extends Serializable {
    public void execute(Lobby lobby) throws IOException;
    public String getSender();
}
