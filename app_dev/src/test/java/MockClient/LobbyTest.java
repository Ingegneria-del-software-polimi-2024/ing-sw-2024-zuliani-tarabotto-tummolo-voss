package MockClient;

import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.WebExceptions.MsgNotDeliveredException;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.rmi.AlreadyBoundException;

class LobbyTest {
    @Test
    public void main() {
        Lobby lobby = new Lobby(1234, 1237);
        try {
            lobby.start();
        } catch (MsgNotDeliveredException e) {
            throw new RuntimeException("A message couldn't be delivered: "+e.getCause().getClass(), e);
        } catch (StartConnectionFailedException e) {
            throw new RuntimeException("Can't start an RMI connection", e);
        }
    }

}