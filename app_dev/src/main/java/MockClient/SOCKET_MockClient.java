package MockClient;

import SharedWebInterfaces.Messages.Message;
import SharedWebInterfaces.Messages.MessagesFromLobby.ACK_NewConnection;
import SharedWebInterfaces.Messages.MessagesFromLobby.AvailableGames;
import SharedWebInterfaces.Messages.MessagesFromLobby.WelcomeMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.Messages.MessagesToLobby.JoinGameMessage;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;
import SharedWebInterfaces.Messages.MessagesToLobby.NewConnectionMessage;
import SharedWebInterfaces.Messages.MessagesFromLobby.ACK_RoomChoice;
import SharedWebInterfaces.Messages.MessagesToLobby.RequestAvailableGames;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class SOCKET_MockClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Scanner scIn = new Scanner(System.in);

        Socket server = new Socket("172.20.10.10", 1235);
        ObjectOutputStream out =  new ObjectOutputStream(server.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(server.getInputStream());

        WelcomeMessage welcome = (WelcomeMessage) in.readObject();
        ArrayList<String> games = welcome.getListOfGames();

        for (String g : games)
            System.out.println(g+"\n");

        System.out.println("Inserire il proprio username: ");
        String username = scIn.next();

        out.writeObject(new NewConnectionMessage(username));
        out.flush();
        out.reset();

        MessageFromServer incoming = (MessageFromServer) in.readObject();
        if(incoming instanceof ACK_NewConnection){
            System.out.println(((ACK_NewConnection) incoming).getUser()+" Correctly connected");
        }

        System.out.println("Inserire il gioco in cui entrare o un nome non presente per crearne uno, inserire r per refreshare: ");
        String gameName;
        do{
             gameName = scIn.next();
             if(gameName.equals("r")){
                 out.writeObject(new RequestAvailableGames(username));
                 out.flush();
                 out.reset();
                 MessageFromServer availableGamesMsg = (MessageFromServer) in.readObject();
                 if(availableGamesMsg instanceof AvailableGames){
                     System.out.println(availableGamesMsg);
                 }else
                     throw new RuntimeException();
                 System.out.println("Inserire il gioco in cui entrare o un nome non presente per crearne uno, inserire r per refreshare: ");
             }
        }while(gameName.equals("r"));

        System.out.println("Inserire il numero di giocatori: ");
        int n = scIn.nextInt();
        System.out.println("Creating object with: "+username+" "+gameName+" "+n);

        out.writeObject(new JoinGameMessage(username, gameName, n));

        out.flush();
        out.reset();

        System.out.println("object sent");

        ACK_RoomChoice ack = (ACK_RoomChoice) in.readObject();
        System.out.println(ack.getUser()+" correttamente inserito nella stanza: "+ack.getGame());

        out.close();
        in.close();
        server.close();
        System.out.println("chiuso tutto, ciao");
    }
}
