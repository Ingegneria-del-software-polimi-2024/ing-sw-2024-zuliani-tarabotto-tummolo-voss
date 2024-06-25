package Server.Web.Lobby;

import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The type Lobby message queue.
 */
public class LobbyMessageQueue {
    private ConcurrentLinkedQueue<MessageToLobby> messageQueue;

    /**
     * Enqueue message.
     *
     * @param msg the msg
     */
    public void enqueueMessage(MessageToLobby msg){messageQueue.add(msg);}

    /**
     * Get next message message to lobby.
     *
     * @return the message to lobby
     */
    public MessageToLobby getNextMessage(){return messageQueue.poll();}

    /**
     * Instantiates a new Lobby message queue.
     */
    public LobbyMessageQueue() {messageQueue = new ConcurrentLinkedQueue<MessageToLobby>();}
}
