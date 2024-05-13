package SharedWebInterfaces.Messages.MessagesToLobby;

import Server.Web.Lobby.Lobby;

public class RequestAvailableGames implements MessageToLobby{

    @Override
    public void execute(Lobby lobby) {}

    @Override
    public String getSender() {return null;}
}
