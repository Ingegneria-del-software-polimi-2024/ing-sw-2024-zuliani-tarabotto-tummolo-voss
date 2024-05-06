package MockClient;

import SharedWebInterfaces.Messages.MessagesFromLobby.WelcomeMessage;
import SharedWebInterfaces.Messages.MessagesToLobby.NewConnectionMessage;
import SharedWebInterfaces.Messages.MessagesFromLobby.ACK_RoomChoice;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class SOCKET_MockClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Scanner scIn = new Scanner(System.in);

        Socket server = new Socket("localHost", 1234);
        ObjectOutputStream out =  new ObjectOutputStream(server.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(server.getInputStream());

        WelcomeMessage welcome = (WelcomeMessage) in.readObject();
        ArrayList<String> games = welcome.getListOfGames();

        for (String g : games)
            System.out.println(g+"\n");

        System.out.println("Inserire il proprio username: ");
        String username = scIn.next();

        System.out.println("Inserire il gioco in cui entrare o un nome non presente per crearne uno: ");
        String gameName = scIn.next();
        System.out.println("Inserire il numero di giocatori: ");
        int n = scIn.nextInt();
        System.out.println("Creating object with: "+username+" "+gameName+" "+n);
        out.writeObject(new NewConnectionMessage(username, gameName, n));

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
