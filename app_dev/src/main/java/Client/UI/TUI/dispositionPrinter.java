package Client.UI.TUI;
import model.cards.PlayableCards.PlayableCard;
import model.placementArea.Coordinates;
import org.fusesource.jansi.AnsiConsole;


import java.util.HashMap;
import java.util.Map;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;


public class dispositionPrinter {

    private int[][] dispositionMap;
    private Coordinates mapCenter;

    public void mapDisposition(HashMap<Coordinates, PlayableCard> disposition){
        int xMax = 0;
        int yMax = 0;
        for (Map.Entry<Coordinates, PlayableCard> entry : disposition.entrySet()) {
            if (Math.abs(entry.getKey().getX()) >= xMax) {
                xMax = Math.abs(entry.getKey().getX());
            }
            if (Math.abs(entry.getKey().getY()) >= yMax) {
                yMax = Math.abs(entry.getKey().getY());
            }

        }
        int xDim = 2*xMax +1;
        int yDim = 2*yMax +1;
        mapCenter = new Coordinates(xMax, yMax);
        dispositionMap = new int[xMax][yMax];

        ///popoliamo la matrice

        for(Coordinates c : disposition.keySet()){
            dispositionMap[convertX(c.getX())][convertY(c.getY())] = disposition.get(c).getId();
        }

        for (int i = 0; i < dispositionMap.length; i++) {
            // Iterate over each column in the current row
            for (int j = 0; j < dispositionMap[i].length; j++) {
                // Print the current element followed by a space
                System.out.print(dispositionMap[i][j] + " ");
            }
            // Move to the next line after printing all elements of the current row
            System.out.println();
        }

    }

    private int convertX(int x){
        return mapCenter.getX() + x;
    }
    private int convertY(int y){
        return mapCenter.getY() + y;
    }
}
