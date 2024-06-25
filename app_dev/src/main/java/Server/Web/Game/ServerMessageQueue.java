package Server.Web.Game;

import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.ServerControllerInterface;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The type Server message queue.
 */
public class ServerMessageQueue {
    private ConcurrentLinkedQueue<MessageFromClient> messageQueue;

    /**
     * enqueues a new message
     *
     * @param msg the message to be enqueued
     */
    public void enqueueMessage(MessageFromClient msg){messageQueue.add(msg);}

    /**
     * Get next message message from client.
     *
     * @return the first message in the queue
     */
    public MessageFromClient getNextMessage(){return messageQueue.poll();}

    /**
     * executes the first message in the queue
     *
     * @param api the api to execute the message
     */
    public void executeNextMessage(ServerControllerInterface api){

        Objects.requireNonNull(messageQueue.poll(), "list is empty").execute(api);}

    /**
     * class constructor
     */
    public ServerMessageQueue() {messageQueue = new ConcurrentLinkedQueue<MessageFromClient>();}
}
