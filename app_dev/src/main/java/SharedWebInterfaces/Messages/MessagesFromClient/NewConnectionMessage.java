package SharedWebInterfaces.Messages.MessagesFromClient;

import Server.Lobby_RECEIVE_CONTROLLER;
import SharedWebInterfaces.Messages.MessagesFromServer.ACK_RoomChoice;
import model.lobby.Lobby_SEND;

public class NewConnectionMessage {//todo implements MessageFromClient...

    private String username;
    private String RoomName;

    public void execute(Lobby_SEND lobby){
        lobby.sendMessageToUser(username, new ACK_RoomChoice(username));
    }
}
