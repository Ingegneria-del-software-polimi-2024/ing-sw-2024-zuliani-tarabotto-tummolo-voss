package Client.Web;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The type Client message queue.
 */
public class ClientMessageQueue {
    private ConcurrentLinkedQueue<MessageFromServer> messageQueue;

    /**
     * receives and enqueues a message incoming from the server (this is called by the server handler)
     *
     * @param msg the message to be enqueued
     */
    public void enqueueMessage(MessageFromServer msg){messageQueue.add(msg);}

    /**
     * Get next message message from server.
     *
     * @return the first message in the queue
     */
    public MessageFromServer getNextMessage(){return messageQueue.poll();}

    /**
     * executes the first message in the queue
     *
     * @param api the view api
     */
    public void executeNextMessage(ViewAPI_Interface api){
        Objects.requireNonNull(messageQueue.poll(), "list is empty").execute(api);}

    /**
     * class constructor
     */
    public ClientMessageQueue() {messageQueue = new ConcurrentLinkedQueue<MessageFromServer>();}
}
