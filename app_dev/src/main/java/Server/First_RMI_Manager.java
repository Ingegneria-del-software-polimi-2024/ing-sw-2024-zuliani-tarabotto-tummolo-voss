package Server;

import MockModel.Lobby;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;
import SharedWebInterfaces.Messages.MessagesToLobby.NewConnectionMessage;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class First_RMI_Manager implements Remote {
    private Lobby lobby;
    private int serverPort;
    public First_RMI_Manager(Lobby lobby, int serverPort) throws RemoteException, AlreadyBoundException {
        this.lobby = lobby;
        this.serverPort = serverPort;
        UnicastRemoteObject.exportObject(this, 0);
        Registry registry = LocateRegistry.createRegistry(serverPort);
        registry.bind("Lobby", this);
    }

    public void newConnection(NewConnectionMessage msg){
        //1) il client si connette e cerca "Lobby" oppure "CodexNaturalis"
        //2) poi chiama server.newConnection(RMI_StartMessage msg)
        //   RMI_StartMessage ha al suo interno tutto il necessario per far partire
        //   la connessione RMI, il suo execute crea ed inserisce l'handler dala parte del client
        //   infine manda al client un messaggio con le coordinate rmi del nuovo handler
        //3) il client riceve queste coordinate, le valorizza e attende un welcome message
        //   (in alternativa potrebbe ricevere direttamente tutto NEL welcome message stesso)
        //   questo messaggio o il contenuto del welcome message viene prima parsato in ricezione e poi passa allo stadio
        //   seguente
        //4) A questo punto il client può continuare la comunicazione come da protocollo

    }

    public void deliverToLobby(MessageToLobby msg){
        lobby.enqueueMessage(msg);
    }


}
