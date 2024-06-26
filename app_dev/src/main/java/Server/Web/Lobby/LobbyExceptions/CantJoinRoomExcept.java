package Server.Web.Lobby.LobbyExceptions;

public class CantJoinRoomExcept extends Exception {

    private boolean creating;

    public CantJoinRoomExcept(boolean creating) {
        this.creating = creating;
    }
    public boolean isCreating(){
        return creating;
    }
}
