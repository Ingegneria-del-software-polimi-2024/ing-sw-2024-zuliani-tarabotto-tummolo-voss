package Server.Web.Lobby;

import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LobbyMessageQueue {
    private ConcurrentLinkedQueue<MessageToLobby> messageQueue;

    public void enqueueMessage(MessageToLobby msg){messageQueue.add(msg);}
    public MessageToLobby getNextMessage(){return messageQueue.poll();}
    public void executeNextMessage(Lobby lobby) throws IOException {

        Objects.requireNonNull(messageQueue.poll(), "list is empty").execute(lobby);}

    public LobbyMessageQueue() {messageQueue = new ConcurrentLinkedQueue<MessageToLobby>();}
}
