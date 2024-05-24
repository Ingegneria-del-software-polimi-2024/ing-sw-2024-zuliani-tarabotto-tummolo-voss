package Server;

import Server.Web.Lobby.Lobby;

public class RunServer {
    public static void main(String[] args) {
        try{
            Lobby lobby = new Lobby(1234, 1237);
            lobby.start();
        }catch (Exception e){
            System.out.println("An error occurred");
            System.out.println(e.getClass());
            System.out.println(e.getCause());
            throw new RuntimeException(e);
        }
    }
}
