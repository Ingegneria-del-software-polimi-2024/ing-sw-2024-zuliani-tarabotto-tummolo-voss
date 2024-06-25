package Server.Web.Game;

import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.ServerControllerInterface;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The type Server message queue.
 * A queue for messages directed to the server
 */
public class ServerMessageQueue {
    /**
     * the message queue
     */
    private ConcurrentLinkedQueue<MessageFromClient> messageQueue;

    /**
     * Enqueues a new message
     *
     * @param msg the message to be enqueued
     */
    public void enqueueMessage(MessageFromClient msg){messageQueue.add(msg);}

    /**
     * Get next message from client.
     *
     * @return the first message in the queue
     */
    public MessageFromClient getNextMessage(){return messageQueue.poll();}

    /**
     * Executes the first message in the queue
     *
     * @param api the api to execute the message
     */
    public void executeNextMessage(ServerControllerInterface api){

        Objects.requireNonNull(messageQueue.poll(), "list is empty").execute(api);}

    /**
     * Class constructor
     */
    public ServerMessageQueue() {messageQueue = new ConcurrentLinkedQueue<MessageFromClient>();}
}
