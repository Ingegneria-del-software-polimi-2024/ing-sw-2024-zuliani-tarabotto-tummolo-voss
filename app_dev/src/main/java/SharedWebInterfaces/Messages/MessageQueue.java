package SharedWebInterfaces.Messages;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageQueue {
    private ConcurrentLinkedQueue<GeneralMessage> messageQueue;

    public void enqueueMessage(GeneralMessage msg){messageQueue.add(msg);}
    public GeneralMessage getNextMessage(){return messageQueue.poll();}
    public void executeNextMessage(GeneralAPI_Interface api){
        Objects.requireNonNull(messageQueue.poll(), "list is empty").execute(api);}

    public MessageQueue() {messageQueue = new ConcurrentLinkedQueue<GeneralMessage>();}
}
