package SharedWebInterfaces.Messages.MessagesToLobby;


import Client.Web.RMI_ServerHandler;
import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.SharedInterfaces.ServerHandlerInterface;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

public class NewRMI_Connection implements MessageToLobby {
    private ServerHandlerInterface handler;
//    private String registryName;
//    private String host;
//    private int port; //is this actually necessary

    @Override
    public void execute(Lobby lobby){
        lobby.newRMI_Connection(handler);
    }

    @Override
    public String getSender() {
        //TO BE IMPLEMENTED
        return null;
    }

//    @Override
//    public String getSender() {
//        return registryName;
//    }

//    public NewRMI_Connection(String registry, String host, int port){
//        registryName = registry;
//        this.host = host;
//        this.port = port;
//    }


    public NewRMI_Connection(ServerHandlerInterface handler) {
        this.handler = handler;
    }
}
