package SharedWebInterfaces.Messages.MessagesFromLobby;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

/**
 * The type Already existing name message.
 * This message is sent from the server to the client when the client tries to select a nickname that is already in use.
 */
public class AlreadyExistingNameMessage implements MessageFromServer {
    /**
     * The non-valid Name.
     */
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
     * Get the unavailable name.
     *
     * @return the string
     */
    public String getUnavailableName(){
        return name;
    }
}
