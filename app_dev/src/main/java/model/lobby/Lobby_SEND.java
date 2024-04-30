package model.lobby;


import SharedWebInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;

import java.util.ArrayList;
import java.util.List;

public class Lobby_SEND {
    private List<ClientHandlerInterface> conns = new ArrayList<>(); // list of all the connected clients

    public void broadcast(MessageFromServer msg){
        for (ClientHandlerInterface conn: conns){
            if(!conn.getClientName().equals(msg.getUser())){
                conn.sendMessageToClient(msg);
            }

        }
    }
    public void broadcastToall(String msg){
        for (ClientHandlerInterface conn: conns){

                conn.sendMessageToClient(new Message(msg, (String) conn.getClientName()));


        }
    }


    public void removeConnection(ClientHandlerInterface csh) {
        conns.remove(csh);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Lobby_SEND(){
        //receives a RMI handler
    }
    public void sendMessageToUser(String username, MessageFromServer message) {
        for (ClientHandlerInterface handler : conns) {
            if (handler.getClientName().equals(username)) {
                handler.sendMessageToClient(message);
                break; // Only send to the target user
            }
        }
    }
    public void addConnection(ClientHandlerInterface csh){conns.add(csh);}

}
