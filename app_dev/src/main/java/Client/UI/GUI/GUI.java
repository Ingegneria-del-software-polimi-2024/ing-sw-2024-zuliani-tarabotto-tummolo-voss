package Client.UI.GUI;

import Client.UI.GUI.ConnectionChoicePage;
import Client.UI.UI;
import Client.View.ViewAPI;
import model.cards.PlayableCards.PlayableCard;
import model.placementArea.Coordinates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class GUI implements UI {

    private ViewAPI view;

    public GUI(ViewAPI view){

        this.view = view;

    }

    ///////////////////////////////////////<Lobby>//////////////////////////////////////////////////////////////////////////
    @Override
    public void displayInitialization() {

    }

    @Override
    public void displayStarterCardSelection() {

    }

    @Override
    public void displayObjectiveSelection() {

    }

    @Override
    public void displayPlacingCard() {

    }

    @Override
    public void displayCardDrawing() {

    }

    @Override
    public void displayEndGame() {

    }

    @Override
    public void printDisposition(HashMap<Coordinates, PlayableCard> disposition) {

    }

    @Override
    public void displayCalculateObjectives() {

    }

    @Override
    public void chooseConnection(){



        view.startConnection(connectionType, host, port, localPort);
    }

    private boolean validIP(String ip) {
        if (ip == null || ip.isEmpty()) {
            return false;
        }
        String[] parts = ip.split("\\.");
        if (parts.length != 4) {
            return false;
        }
        for (String part : parts) {
            try {
                int value = Integer.parseInt(part);
                if (value < 0 || value > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    private boolean validPort(int port){
        return(1024<=port && port <=49151);
    }


    @Override
    public void askNickname() {

    }

    @Override
    public void displayAvailableGames(ArrayList<String> listOfGames) {

    }

    @Override
    public void joinedGame(String gameID) {

    }

    @Override
    public void run() {

    }
}
