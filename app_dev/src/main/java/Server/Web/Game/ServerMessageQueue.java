package Server.Web.Game;

import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.ServerControllerInterface;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerMessageQueue {
    private ConcurrentLinkedQueue<MessageFromClient> messageQueue;

    public void enqueueMessage(MessageFromClient msg){messageQueue.add(msg);}
    public MessageFromClient getNextMessage(){return messageQueue.poll();}
    public void executeNextMessage(ServerControllerInterface api){

        Objects.requireNonNull(messageQueue.poll(), "list is empty").execute(api);}

    public ServerMessageQueue() {messageQueue = new ConcurrentLinkedQueue<MessageFromClient>();}
}
