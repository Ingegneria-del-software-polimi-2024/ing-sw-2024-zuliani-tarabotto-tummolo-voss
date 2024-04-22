package SharedWebInterfaces;

import java.rmi.Remote;

public interface ClientInterface extends Remote {

    //private ClientAPI_COME api

    //COME
    public void enqueueMethod(/*parameters*/);

    public void addNewPlayer(/*parameters*/);

    //GO
    public void notifyChanges(/*parameters*/);
}
