package MockClient;

import Client.Web.ClientAPI_COME;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.Messages.MessagesFromLobby.WelcomeMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;
import SharedWebInterfaces.SharedInterfaces.RMI_ManagerInterface;
import SharedWebInterfaces.SharedInterfaces.ServerHandlerInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMI_MockHandler implements ServerHandlerInterface {
    private RMI_ManagerInterface server;
    private RMI_MockClient api;
    private ClientHandlerInterface serverGame;
    private  ClientAPI_COME client;
    private int port;

    @Override
    public void sendToServer(MessageFromClient message) throws RemoteException {
        serverGame.sendToServer(message);
    }

//    @Override
//    public void addNewPlayer() throws RemoteException {
//    }

    @Override
    public void notifyChanges(MessageFromServer message) throws RemoteException {
        client.notifyChanges(message);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void receiveFromLobby(MessageFromServer msg) {
        if(msg instanceof WelcomeMessage){
//            try {
//                System.out.println(((WelcomeMessage) msg).getRegistryName());
//                Registry registry1 = LocateRegistry.getRegistry("localHost", 1237);
//                serverGame = (ClientHandlerInterface) registry1.lookup(((WelcomeMessage) msg).getRegistryName());
//                System.out.println("server found");
//            }catch (RemoteException | NotBoundException e){
//                throw new RuntimeException();
//            }
            serverGame =((WelcomeMessage) msg).getServer();
        }
        api.enQmsg(msg);
    }

    public RMI_MockHandler(RMI_MockClient api, ClientAPI_COME client, int port) throws RemoteException, AlreadyBoundException, NotBoundException {
        this.api = api;
        this.client = client;
        this.port = port;
        Thread thread = new Thread(client);
        thread.start(); // This starts the thread
        //client.run();
        UnicastRemoteObject.exportObject(this, 0);
//        Registry registry = LocateRegistry.createRegistry(port);
//        registry.bind("ciaoBello", this);
        System.out.println("Client published");

        Registry registry1 = LocateRegistry.getRegistry("localHost", 1237);
        server = (RMI_ManagerInterface) registry1.lookup("Lobby");
        System.out.println("server found");
    }

    public void sendToLobby(MessageToLobby msg) throws RemoteException {
        server.deliverToLobby(msg);
    }
    public void send(MessageToLobby msg) throws RemoteException {
        serverGame.deliverToLobby(msg);
    }
}
