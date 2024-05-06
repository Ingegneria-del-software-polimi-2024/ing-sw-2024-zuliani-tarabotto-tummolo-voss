package MockClient;

import Server.Web.Lobby.Lobby;

import java.io.IOException;
import java.rmi.AlreadyBoundException;

class LobbyTest {
    public static void main(String[] args) throws IOException, AlreadyBoundException {
        Lobby lobby = new Lobby(1234);
        lobby.start();
    }

}