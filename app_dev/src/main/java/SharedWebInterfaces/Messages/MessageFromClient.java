package SharedWebInterfaces.Messages;

public interface MessageFromClient extends GeneralMessage{
    public void execute(ServerControllerInterface controller);
}
