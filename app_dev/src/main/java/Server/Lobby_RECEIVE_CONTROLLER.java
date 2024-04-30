package Server;

import SharedWebInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.Messages.MessagesFromClient.NewConnectionMessage;
import model.lobby.Lobby_SEND;

import java.util.HashMap;

public class Lobby_RECEIVE_CONTROLLER {

    private ServerMessageQueue toDoQueue;

    private Lobby_SEND lobby;
    //client called
    /**
     * enqueues the incoming message in the toDoQueue
     * @param message is the message coming from the client
     */
     public void sendToServer(MessageFromClient message){
        toDoQueue.enqueueMessage(message);
     }

    public void addNewPlayer(String nickName, ClientHandlerInterface handler){
         if(players.get(nickName) != null)
             throw new RuntimeException();
         players.put(nickName, handler);
    }

    //controller called
    public void performNextMethod(){}

    public void loop(){
        MessageFromClient message;
         while (true) {
             message = toDoQueue.getNextMessage();
             //if you find a message in the waiting list
             if (message != null)
                 if(controller.checkMessage(message))
                     //if that message has sense execute it
                     message.execute(controller);
                 else
                     //if that message doesn't have sense
                     toDoQueue.enqueueMessage(message);
                    //TODO it would be interesting if we implemented a kind of "waiting time varible" that increases until a value when the message is destroyed

         }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Lobby_RECEIVE_CONTROLLER() {

        lobby = new Lobby_SEND();

        FirstSocketManager socketManager = FirstSocketManager.getInstance(this, lobby);
        Thread NewSocketConnections = new Thread(socketManager);
        NewSocketConnections.start();

        //TODO rmi connection


    }

    public void handleRoomCreationMessage(NewConnectionMessage msg){
         //handles the messages, parses it and...
        msg.execute(lobby);
    }


}
