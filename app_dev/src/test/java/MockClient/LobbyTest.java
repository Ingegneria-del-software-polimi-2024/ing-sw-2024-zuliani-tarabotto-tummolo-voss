package MockClient;

import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.WebExceptions.MsgNotDeliveredException;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;

import java.io.IOException;
import java.rmi.AlreadyBoundException;

class LobbyTest {
    public static void main(String[] args) {
        Lobby lobby = new Lobby(1234);
        try {
            lobby.start();
        } catch (MsgNotDeliveredException e) {
            throw new RuntimeException("A message couldn't be delivered", e);
        } catch (StartConnectionFailedException e) {
            throw new RuntimeException("Can't start an RMI connection", e);
        }
    }

}