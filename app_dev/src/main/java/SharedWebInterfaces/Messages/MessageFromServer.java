package SharedWebInterfaces.Messages;

public interface MessageFromServer extends GeneralMessage{
    public void execute(ClientViewInterface view);
}
