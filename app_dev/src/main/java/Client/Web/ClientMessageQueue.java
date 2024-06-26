package Client.Web;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The type Client message queue.
 * A queue for messages directed to the client
 */
public class ClientMessageQueue {
    /**
     * The message queue.
     */
    private ConcurrentLinkedQueue<MessageFromServer> messageQueue;

    /**
     * Receives and enqueues a message incoming from the server (this is called by the server handler)
     *
     * @param msg the message to be enqueued
     */
    public void enqueueMessage(MessageFromServer msg){messageQueue.add(msg);}

    /**
     * Gets next message from server in the queue.
     *
     * @return the first message in the queue
     */
    public MessageFromServer getNextMessage(){return messageQueue.poll();}

    /**
     * Executes the first message in the queue
     *
     * @param api the view api
     */
    public void executeNextMessage(ViewAPI_Interface api){
        Objects.requireNonNull(messageQueue.poll(), "list is empty").execute(api);}

    /**
     * Class constructor
     */
    public ClientMessageQueue() {messageQueue = new ConcurrentLinkedQueue<MessageFromServer>();}
}
