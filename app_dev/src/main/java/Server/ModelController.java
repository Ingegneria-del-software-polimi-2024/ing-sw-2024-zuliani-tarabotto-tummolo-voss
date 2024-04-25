package Server;
import SharedWebInterfaces.Messages.ServerControllerInterface;
import model.GameState.GameState;
import model.GameState.TurnState;
import model.player.Player;

import java.util.ArrayList;

public class ModelController implements ServerControllerInterface {
    private GameState gameState;
    private ArrayList<String> playersNicknames;
    private String gameId;
    private int cont = 0;


    public ModelController(ArrayList<String> playersNicknames, String gameId){
        this.playersNicknames = playersNicknames;
        this.gameId = gameId;
    }

    @Override
    public void initializeGameState(){
        gameState.setTurnState(TurnState.GAME_INITIALIZATION);
        gameState = new GameState(playersNicknames, gameId);
        gameState.setTurnState(TurnState.STARTER_CARD_SELECTION);
    }

    @Override
    public void playStarterCard(boolean face, String player){
        gameState.setStartingCardFace(face, player);
        gameState.playStarterCard(player);
        //IF THIS FUNCTION WAS CALLED A NUMBER OF TIMES EQUALS TO THE NUMBER OF PLAYERS THEN THE STATE OF THE GAME IS CHANGED
        if(cont == playersNicknames.size() - 1){
            gameState.setTurnState(TurnState.OBJECTIVE_SELECTION);
        }
        cont++;
    }

    @Override
    public void playCard(int id, int x, int y) {

    }

    @Override
    public void drawCard() {

    }


}
