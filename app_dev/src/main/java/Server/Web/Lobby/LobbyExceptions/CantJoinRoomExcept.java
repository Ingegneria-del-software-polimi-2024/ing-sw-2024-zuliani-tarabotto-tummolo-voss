package Server.Web.Lobby.LobbyExceptions;

/**
 * The type Cant join room except.
 * This exception is thrown when a user tries to join a room that is full or when the user tries to create a room that is already existing.
 * The exception is thrown with a boolean value that indicates if the room is being created or not.
 */
public class CantJoinRoomExcept extends Exception {

    /**
     * The boolean value that indicates if the room is being created or not.
     */
    private boolean creating;

    /**
     * Instantiates a new Cant join room except.
     *
     * @param creating true if the room is being created, false otherwise
     */
    public CantJoinRoomExcept(boolean creating) {
        this.creating = creating;
    }

    /**
     * The boolean value that indicates if the room is being created or not.
     * @return true if the room is being created, false otherwise
     */
    public boolean isCreating(){
        return creating;
    }
}
