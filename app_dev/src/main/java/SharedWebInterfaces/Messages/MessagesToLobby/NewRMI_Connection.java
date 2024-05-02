package SharedWebInterfaces.Messages.MessagesToLobby;


import MockModel.Lobby;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

public class NewRMI_Connection implements MessageToLobby {

    private String registryName;
    private String host;
    private int port; //is this actually necessary

    @Override
    public void execute(Lobby lobby) throws IOException{
        try {
            lobby.newRMI_Connection(registryName, host, port);
        } catch (AlreadyBoundException | NotBoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getSender() {
        return registryName;
    }

    public NewRMI_Connection(String registry, String host, int port){
        registryName = registry;
        this.host = host;
        this.port = port;
    }


}
