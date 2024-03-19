import model.GameState.GameState;
import model.placementArea.Coordinates;
import model.player.Player;

import java.io.*;

public class Main {
    public static void main(String[] args){
        GameState state = new GameState();
        //initialization of the gameState
        //...
        //...

        startGame(state);
    }

    private static void startGame(GameState state) throws IOException {
        int inputCommand, x, y;

        Player player = new Player();
        while (!state.isLastTurn()){
            player = state.getTurnPlayer();
            //reading for placing
            for(int i = 0; i <= 3; i++) {System.out.println(i+":" + player.getPlayingHand().get(i) + "\n");}
            inputCommand = System.in.read();
            if (inputCommand - 48 > 9 || inputCommand - 48 < 0) {return;}
            x =  System.in.read();
            y = System.in.read();
            //placement
            player.playCard(player.getPlayingHand().get(inputCommand-48), new Coordinates(x-48, y-48));
            //reading for drawing
            System.out.println("card placed\n");
            System.out.println("0: pick from GoldDeck\n1: pick from Res Deck\n2:pick open Gold\n3: pick open Res");
            //drawing
            inputCommand = System.in.read();
            inputCommand -= 48;
            switch(inputCommand) {
                case 0:
                    state.drawCard(player, state.getGoldDeck());
                case 1:
                    state.drawCard(player, state.getResourceDeck());
                case 2:
                    state.drawCard(player, state.getOpenGold(), System.in.read()-48);
                case 3:
                    state.drawCard(player, state.getOpenResources(), System.in.read()-48);
            }

            //verify if you've reached 20 points, if so declare last turn
            if(player.getPoints() >= 20) state.setLastTurnTrue();

            state.nextPlayer();
        }
        //last round following rules, to be impl.

    }
}
