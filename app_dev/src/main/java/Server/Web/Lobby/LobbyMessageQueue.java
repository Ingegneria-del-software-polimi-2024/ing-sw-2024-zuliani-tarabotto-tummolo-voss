package Server.Web.Lobby;

import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The type Lobby message queue.
 * This class is used to store messages that are sent to the lobby.
 */
public class LobbyMessageQueue {
    private ConcurrentLinkedQueue<MessageToLobby> messageQueue;

    /**
     * Enqueue message.
     *
     * @param msg the message
     */
    public void enqueueMessage(MessageToLobby msg){messageQueue.add(msg);}

    /**
     * Get next message to lobby.
     *
     * @return the message to lobby
     */
    public MessageToLobby getNextMessage(){return messageQueue.poll();}

    /**
     * Instantiates a new Lobby message queue.
     */
    public LobbyMessageQueue() {messageQueue = new ConcurrentLinkedQueue<MessageToLobby>();}
}
