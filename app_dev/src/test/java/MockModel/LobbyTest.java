package MockModel;

import java.io.IOException;
import java.rmi.AlreadyBoundException;

import static org.junit.jupiter.api.Assertions.*;

class LobbyTest {
    public static void main(String[] args) throws IOException, AlreadyBoundException {
        Lobby lobby = new Lobby(1234);
        lobby.start();
    }

}