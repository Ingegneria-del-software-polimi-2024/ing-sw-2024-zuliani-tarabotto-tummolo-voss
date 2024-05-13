package MockClient;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class prova {
    public static void main(String[] args) throws AlreadyBoundException, NotBoundException, RemoteException {
        RMI_MockClient api1 = new RMI_MockClient(2345);
        Thread t1 = new Thread(api1);
        t1.start();
        api1.startConnection();

    }
}
