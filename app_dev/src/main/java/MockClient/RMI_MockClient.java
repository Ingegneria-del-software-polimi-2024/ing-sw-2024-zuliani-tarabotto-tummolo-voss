package MockClient;

import Client.View.ViewAPI;
import Client.Web.ClientAPI_COME;
import Client.Web.ClientAPI_GO;
import SharedWebInterfaces.Messages.MessagesFromLobby.ACK_NewConnection;
import SharedWebInterfaces.Messages.MessagesFromLobby.ACK_RoomChoice;
import SharedWebInterfaces.Messages.MessagesFromLobby.AvailableGames;
import SharedWebInterfaces.Messages.MessagesFromLobby.WelcomeMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.Messages.MessagesToLobby.JoinGameMessage;
import SharedWebInterfaces.Messages.MessagesToLobby.NewConnectionMessage;
import SharedWebInterfaces.Messages.MessagesToLobby.NewRMI_Connection;
import SharedWebInterfaces.Messages.MessagesToLobby.RequestAvailableGames;

import javax.swing.*;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RMI_MockClient implements Runnable{
    private ConcurrentLinkedQueue<MessageFromServer> todo;
    private RMI_MockHandler handler;
    private ViewAPI view;
    private ClientAPI_GO goAPI;
    private int port;

    private String userName;


    public void run(){//TODO no infinite loop
        Scanner scIn = new Scanner(System.in);
        while(true){
            MessageFromServer msg = todo.poll();
            if(msg instanceof WelcomeMessage){

                //System.out.println(msg.toString());
                //view.displayLogin();
                System.out.println("inserire username");
                userName = scIn.next();
                try {
                    handler.send(new NewConnectionMessage(userName));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                //we notify the view about the nickName of its player
                this.view.setPlayerId(userName);


//                try {
//                    handler.send(new NewConnectionMessage(userName, game, players));
//                } catch (RemoteException e) {
//                    throw new RuntimeException(e);
//                }
            } else if (msg instanceof ACK_NewConnection || msg instanceof AvailableGames) {
                if(msg instanceof AvailableGames)
                    System.out.println(msg);

                String game;

                System.out.println("Inserire il gioco in cui entrare o un nome non presente per crearne uno, inserire --r per refreshare: ");
                game = scIn.next();
                if(game.equals("--r"))
                    try {
                        handler.send(new RequestAvailableGames(userName));
                    }catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                else {
                    System.out.println("inserire il numero di giocatori");
                    int players = scIn.nextInt();

                    try {
                        handler.send(new JoinGameMessage(userName, game, players));
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if (msg instanceof ACK_RoomChoice) {
                System.out.println(((ACK_RoomChoice) msg).getUser()+" correctly joined the game "+
                        ((ACK_RoomChoice) msg).getGame());
            }
        }
    }
    public void startConnection() throws RemoteException {
        handler.sendToLobby(new NewRMI_Connection(handler));
    }

    public void enQmsg(MessageFromServer msg){
        todo.add(msg);
    }

    public RMI_MockClient(int port) throws AlreadyBoundException, RemoteException, NotBoundException {
        this.port = port;
        this.todo = new ConcurrentLinkedQueue<MessageFromServer>();
        this.view = new ViewAPI();
        //handler is created and a reference to ClientAPI_COME is passed to it
        this.handler = new RMI_MockHandler(this, new ClientAPI_COME(view), port);
        //ClientAPI_GO has a reference to the handler
        this.goAPI = new ClientAPI_GO(handler);
        //we now tell the view its ClientAPI_GO interface
        this.view.setClientAPIGo(this.goAPI);
    }
}
