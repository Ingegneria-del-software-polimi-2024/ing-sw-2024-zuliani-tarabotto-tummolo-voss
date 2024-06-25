package SharedWebInterfaces.Messages.MessagesFromLobby;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

/**
 * The type Already existing name message.
 */
public class AlreadyExistingNameMessage implements MessageFromServer {
    private String name;

    /**
     * Instantiates a new Already existing name message.
     *
     * @param name the name
     */
    public AlreadyExistingNameMessage(String name) {
        this.name = name;
    }

    @Override
    public void execute(ViewAPI_Interface view) {
        view.nickNameAlreadyInUse();
    }

    /**
     * Get unavailable name string.
     *
     * @return the string
     */
    public String getUnavailableName(){
        return name;
    }
}
