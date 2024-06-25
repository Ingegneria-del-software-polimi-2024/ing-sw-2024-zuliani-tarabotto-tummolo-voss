package Server;

import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.WebExceptions.MsgNotDeliveredException;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;

/**
 * The type Run server.
 */
public class RunServer {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        try{
            Lobby lobby = new Lobby(1234, 1237);
            lobby.start();
        }catch (MsgNotDeliveredException | StartConnectionFailedException e){
            System.out.println("An error occurred");
            System.out.println(e.getClass());
            System.out.println(e.getCause());
            throw new RuntimeException(e);
        }
    }
}
