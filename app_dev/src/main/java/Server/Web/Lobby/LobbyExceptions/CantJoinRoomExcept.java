package Server.Web.Lobby.LobbyExceptions;

/**
 * The type Cant join room except.
 */
public class CantJoinRoomExcept extends Exception {

    private boolean creating;

    /**
     * Instantiates a new Cant join room except.
     *
     * @param creating the creating
     */
    public CantJoinRoomExcept(boolean creating) {
        this.creating = creating;
    }

    /**
     * Is creating boolean.
     *
     * @return the boolean
     */
    public boolean isCreating(){
        return creating;
    }
}
