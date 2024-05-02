package MockModel;

import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.Messages.MessagesFromLobby.ACK_RoomChoice;
import SharedWebInterfaces.Messages.MessagesFromLobby.MessageFromLobby;
import SharedWebInterfaces.Messages.MessagesFromLobby.WelcomeMessage;
import SharedWebInterfaces.Messages.MessagesToLobby.NewConnectionMessage;
import SharedWebInterfaces.Messages.MessagesToLobby.NewRMI_Connection;

import javax.sound.midi.Soundbank;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RMI_MockClient implements Runnable{
    private ConcurrentLinkedQueue<MessageFromLobby> todo;
    private RMI_MockHandler handler;

    public void run(){
        Scanner scIn = new Scanner(System.in);
        while(true){
            MessageFromLobby msg = todo.poll();
            if(msg instanceof WelcomeMessage){
                System.out.println(msg.toString());
                System.out.println("inserire username");
                String userName = scIn.next();
                System.out.println("inserire la partita in cui entrare");
                String game = scIn.next();
                System.out.println("inserire il numero di giocatori");
                int players = scIn.nextInt();
                try {
                    handler.send(new NewConnectionMessage(userName, game, players));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } else if (msg instanceof ACK_RoomChoice) {
                System.out.println(((ACK_RoomChoice) msg).getUser()+" correctly joined the game "+((ACK_RoomChoice) msg).getGame());
            }
        }
    }
    public void startConnection() throws RemoteException {
        handler.sendToLobby(new NewRMI_Connection("ciaoBello", "localHost", 2345));
    }

    public void enQmsg(MessageFromLobby msg){
        todo.add(msg);
    }

    public RMI_MockClient() throws AlreadyBoundException, RemoteException, NotBoundException {
        this.todo = new ConcurrentLinkedQueue<MessageFromLobby>();
        this.handler = new RMI_MockHandler(this);
    }
}
