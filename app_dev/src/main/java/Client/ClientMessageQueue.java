package Client;

import SharedWebInterfaces.Messages.GeneralAPI_Interface;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.ViewAPI_Interface;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientMessageQueue {
    private ConcurrentLinkedQueue<MessageFromServer> messageQueue;

    public void enqueueMessage(MessageFromServer msg){messageQueue.add(msg);}
    public MessageFromServer getNextMessage(){return messageQueue.poll();}
    public void executeNextMessage(ViewAPI_Interface api){
        Objects.requireNonNull(messageQueue.poll(), "list is empty").execute(api);}

    public ClientMessageQueue() {messageQueue = new ConcurrentLinkedQueue<MessageFromServer>();}
}
