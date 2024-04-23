package SharedWebInterfaces.ToDoList;

import SharedWebInterfaces.Messages.ControllerInterface;
import SharedWebInterfaces.Messages.GeneralMessage;

import java.util.Queue;

public class MessageQueue {
    private Queue<GeneralMessage> MessageQueue;

    public void enqueueMessage(GeneralMessage msg){MessageQueue.add(msg);}
    public GeneralMessage getNextMessage(){return MessageQueue.poll();}
    public void executeNextMessage(ControllerInterface controller){MessageQueue.poll().execute(controller);}
}
