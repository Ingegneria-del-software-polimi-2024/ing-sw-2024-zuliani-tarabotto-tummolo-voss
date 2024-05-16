package Client;

import Client.View.ViewAPI;
import Client.Web.ClientAPI_COME;
import Client.Web.ClientAPI_GO;
import Client.Web.RMI_ServerHandler;
import Client.Web.SOCKET_ServerHandler;
import SharedWebInterfaces.SharedInterfaces.ServerHandlerInterface;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;

import java.util.Scanner;

public class RunTUI_Client {
    public static void main(String[] args) {
        Scanner scin = new Scanner(System.in);
        String in;
        if(args.length == 0){
            System.out.println("Insert the techonolgy of connection (RMI/Socket): ");
            do{
                in = scin.next();
                if(!(in.equalsIgnoreCase("RMI") || in.equalsIgnoreCase("SOCKET")))
                    System.out.println("Insert a correct value \"RMI\" or \"Socket\"");

            }while (!(in.equalsIgnoreCase("RMI") || in.equalsIgnoreCase("SOCKET")));
        }else
            in = args[0];

        String host;
        System.out.println("Insert the host ip address:");
        do{
            host = scin.next();
            if (!validIP(host))
                System.out.println("Insert a valid ip address (xxx.xxx.xxx.xxx): ");
        }while(!validIP(host));

        int port;
        System.out.println("Insert the host port:");
        do{
            port = Integer.parseInt(scin.next());
            if (!validPort(port))
                System.out.println("Insert a valid port: ");
        }while(!validPort(port));

        ViewAPI viewAPI = new ViewAPI();
        ClientAPI_COME clientAPICome = new ClientAPI_COME(viewAPI);

        try {
            ServerHandlerInterface serverHandler;
            if (in.equalsIgnoreCase("RMI")) {
                serverHandler = new RMI_ServerHandler(host, port, clientAPICome);
            } else {
                serverHandler = new SOCKET_ServerHandler(host, port, clientAPICome);
            }
            ClientAPI_GO clientAPIGo = new ClientAPI_GO(serverHandler);
            viewAPI.setClientAPIGo(clientAPIGo);
        }catch (StartConnectionFailedException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            throw new RuntimeException("Couldn't instaurate the connection due to a net error");
        }

        Thread readMessagesLoop = new Thread(clientAPICome);
        readMessagesLoop.start();

    }

    private static boolean validIP(String ip){
        //control if the ip is valid
        return true;
    }
    private static boolean validPort(int port){
        //control the port
        return true;
    }
}
