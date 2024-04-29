package MockModel;

import Server.ModelController;
import Server.ServerAPI_COME;
import Server.ServerAPI_GO;
import controller.Controller;
import model.GameState.GameState;

import java.util.ArrayList;

public class Room {

    int playersNumber;
    String roomName;
    ArrayList<String> players;

    //will need a function that adds ONE player when needed
    //every time I add a new player I should control if I've reached the number playersNumber if so I must create a new game
}
