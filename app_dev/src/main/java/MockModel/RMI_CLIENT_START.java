package MockModel;

import Server.RMI_ClientHandler;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMI_CLIENT_START {
    public static void main(String[] args) throws AlreadyBoundException, NotBoundException, RemoteException {
        RMI_MockClient api = new RMI_MockClient();
        Thread t = new Thread(api);
        t.start();
        api.startConnection();
    }
}
