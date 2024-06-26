package SharedWebInterfaces.SharedInterfaces;

import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMI_ManagerInterface extends Remote {
//    public void newHandler(String clientRegistry, String clientHost, int clientPort, ArrayList<String> games) throws RemoteException;
    public void deliverToLobby(MessageToLobby msg) throws RemoteException;
    public void newHandler(ServerHandlerInterface clientRemote, ArrayList<String> games) throws RemoteException;
}
