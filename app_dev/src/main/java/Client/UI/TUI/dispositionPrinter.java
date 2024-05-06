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
    private int[][] mat;
    private Coordinates mapCenter;
    final int CARDHEIGHT = 6;
    final int CARDLENGTH = 10;
    private CardBuilder cb = new CardBuilder();
    private String[] outputDisposition;
    private HashMap<Coordinates, PlayableCard> disposition;
    int xMax=0;
    int yMax=0;
    int xDim = 0;
    int yDim = 0;


    public dispositionPrinter(HashMap<Coordinates, PlayableCard> disposition){
        this.disposition = disposition;
    }
    //main function
    public void print(){
        mapDisposition();
        //firstLineBuilder();
        generalLineBuilder();

    }

    public void addAvailablePlaces(){

    }

    //here we build the auxiliary matrix that will be used to print the disposition
    public void mapDisposition(){

        //CERCHIAMO LA MASSIMA X E Y
        for (Map.Entry<Coordinates, PlayableCard> entry : disposition.entrySet()) {
            if (Math.abs(entry.getKey().getX()) >= xMax) {
                xMax = Math.abs(entry.getKey().getX());
            }
            if (Math.abs(entry.getKey().getY()) >= yMax) {
                yMax = Math.abs(entry.getKey().getY());
            }

        }

        //DEFINIAMO IL CENTRO DELLA MAPPA, NEL CENTRO SARA' MESSA LA STARTERCARD
        xDim = 2*xMax +1;
        yDim = 2*yMax +1;
        mapCenter = new Coordinates(xMax, yMax);
        dispositionMap = new int[yDim][xDim];
        mat = new int[yDim + 1][xDim];
        //riempiamo mat di zeri
        for (int i = 0; i < yDim + 1; i++) {
            for (int j = 0; j < xDim; j++) {
                mat[i][j] = 0;
            }
        }

        //POPOLIAMO LA MAPPA FACENDO UNA CONVERSIONE DELLE COORDINATE DAL SISTEMA DI DISPOSITION A QUELLO DI DISPOSITIONMAP
        for(Coordinates c : disposition.keySet()){
            dispositionMap[convertY(c.getY())][convertX(c.getX())] = disposition.get(c).getId();

            //popoliamo mat
            for(int i = 0; i < dispositionMap.length; i++){
                for(int j = 0; j < dispositionMap[0].length; j++){
                    if(dispositionMap[i][j] > 0){
                        mat[i][j] = dispositionMap[i][j];
                        mat[i + 1][j] = dispositionMap[i][j];
                    }
                }
            }
            //// aggiungiamo il segnalino -1
            for(int i = 0; i < dispositionMap.length - 1; i++){
                for(int j = 0; j < dispositionMap[0].length; j++){
                    if(dispositionMap[i + 1][j] > 0){
                        dispositionMap[i][j] = -1;
                    }
                }
            }

        }

        // stampiamo mat
        for (int i = 0; i < yDim +1; i++) {
            for (int j = 0; j < xDim; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }

        //aggiungiamo il segnalino -2
        int x;
        int y;
        for(int i = 0; i < dispositionMap.length; i++){
            for(int j = 0; j < dispositionMap[0].length; j++){
                if(dispositionMap[i][j] == 0){
                    x = inverseConvertX(j);
                    y = inverseConvertY(i);
                    if( (x*x + y*y)%2 == 0){
                        dispositionMap[i][j] = -2;
                    }
                }
            }
        }


        //FUNZIONE CHE STAMPA LA MATRICE
        for (int i = 0; i < dispositionMap.length; i++) {
            // Iterate over each column in the current row
            for (int j = 0; j < dispositionMap[i].length; j++) {
                // Print the current element followed by a space
                if(dispositionMap[i][j] >= 10 ){
                    System.out.print(dispositionMap[i][j] + " ");
                }else{System.out.print(dispositionMap[i][j] + "  ");}
            }
            // Move to the next line after printing all elements of the current row
            System.out.println();
        }

    }



    private void generalLineBuilder(){
        int j = 0;

        String line = new String();

        while(j < mat.length - 1){
            //itero tre volte su ogni riga
            for(int row = 0; row < 3; row++) {
                //colonna piu a sinisra
                if (mat[j][0] > 0 && mat[j + 1][0] == mat[j][0]) {
                    line = cb.buildLine(row, getCardFromDisposition(0, j));
                }
                else if (mat[j][0] > 0 && mat[j + 1][0] != mat[j][0]) {
                    line = cb.buildLine(row + 3, getCardFromDisposition(0, j - 1));
                }
                else if (mat[j][0] == 0) {
                    if(mat[j][1] == 0) line = "               ";//15
                    else if (mat[j][1] > 0) line = "            ";//12
                }

                //colonne centrali
                for(int col = 1; col < mat[0].length -1; col++){
                    if( (mat[j][col] > 0) && (mat[j + 1][col] == mat[j][col])) {
                        line += cb.buildLine(row, getCardFromDisposition(col, j));
                    }
                    else if (mat[j][col] > 0 && mat[j + 1][col] != mat[j][col]) {
                        line += cb.buildLine(row + 3, getCardFromDisposition(col, j - 1));
                    }
                    else if(mat[j][col] == 0){
                        if(mat[j][col - 1] == 0 && mat[j][col + 1] == 0) line += "            ";//12
                        else if(mat[j][col - 1] > 0 && mat[j][col + 1] > 0) line += "         ";//9
                        else if(mat[j][col - 1] == 0 && mat[j][col + 1] > 0) line += "         ";//9
                        else if(mat[j][col - 1] > 0 && mat[j][col + 1] == 0) line += "            ";//12
                        else if(mat[j][col - 1] == 0) line += "            ";//12
                        else if( mat[j][col + 1] == 0) line += "         ";//9
                    }
                }

                //colonna piu a destra
                if (mat[j][mat[0].length - 1] > 0 && mat[j + 1][mat[0].length - 1] == mat[j][mat[0].length - 1]) {
                    line += cb.buildLine(row, getCardFromDisposition(mat[0].length - 1, j));
                }
                else if (mat[j][mat[0].length-1] > 0 && mat[j + 1][mat[0].length-1] != mat[j][mat[0].length -1]) {
                    line += cb.buildLine(row + 3, getCardFromDisposition(mat[0].length-1, j - 1));
                }
                else if (mat[j][mat[0].length-1] == 0) {
                    if(mat[j][mat[0].length-2] == 0) line += "               ";//15
                    else if (mat[j][mat[0].length-2] > 0) line += "            ";//12
                }

                System.out.println(line);
            }
            line ="";
            j++;
        }

        //ULTIMA STRISCIA
        //itero tre volte su ogni riga
        for(int row = 0; row < 3; row++) {
            //colonna piu a sinisra
            if (mat[j][0] > 0) {
                line = cb.buildLine(row + 3, getCardFromDisposition(0, j - 1));
            }
            else if (mat[j][0] == 0) {
                if(mat[j][1] == 0) line = "               ";//15
                else if (mat[j][1] > 0) line = "            ";//12
            }

            //colonne centrali
            for(int col = 1; col < mat[0].length -1; col++){
                if( mat[j][col] > 0) {
                    line += cb.buildLine(row + 3, getCardFromDisposition(col, j - 1));
                }

                else if(mat[j][col] == 0){
                    if(mat[j][col - 1] == 0 && mat[j][col + 1] == 0) line += "            ";//12
                    else if(mat[j][col - 1] > 0 && mat[j][col + 1] > 0) line += "         ";//9
                    else if(mat[j][col - 1] == 0 && mat[j][col + 1] > 0) line += "         ";//9
                    else if(mat[j][col - 1] > 0 && mat[j][col + 1] == 0) line += "            ";//12
                    else if(mat[j][col - 1] == 0) line += "            ";//12
                    else if( mat[j][col + 1] == 0) line += "         ";//9
                }
            }

            //colonna piu a sinisra
            if (mat[j][mat[0].length - 1] > 0 && mat[j + 1][mat[0].length - 1] == mat[j][mat[0].length - 1]) {
                line += cb.buildLine(row + 3, getCardFromDisposition(mat[0].length - 1, j - 1));
            }

            else if (mat[j][mat[0].length-1] == 0) {
                if(mat[j][mat[0].length-2] == 0) line += "               ";//15
                else if (mat[j][mat[0].length-2] > 0) line += "            ";//12
            }

            System.out.println(line);
        }

    }



    //FROM THE COORDINATES OF HashMap<Coordinates, PlayableCard> to the coordinates of int[][] dispositionMap
    private int convertX(int xCoord){
        return mapCenter.getX() + xCoord ;//xDisp
    }
    private int convertY(int yCoord){
        return mapCenter.getY() - yCoord ;//yDisp
    }

    //FROM THE COORDINATES OF int[][] dispositionMap to the coordinates of HashMap<Coordinates, PlayableCard>
    private int inverseConvertX(int xDisp){
        return xDisp - mapCenter.getX(); //xCoord
    }
    private int inverseConvertY(int yDisp){
        return mapCenter.getY() - yDisp; //yCoord
    }

    private PlayableCard getCardFromDisposition(int x, int y) {
        Coordinates c = new Coordinates(inverseConvertX(x), inverseConvertY(y));
        for (Coordinates coord : disposition.keySet()) {
            if (coord.equals(c)) {
                return disposition.get(coord);
            }
        }
        return null;
    }
}
