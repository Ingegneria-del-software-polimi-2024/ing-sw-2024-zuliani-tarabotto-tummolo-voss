package Client.UI.TUI;
import model.cards.PlayableCards.PlayableCard;
import model.placementArea.Coordinates;
import org.fusesource.jansi.AnsiConsole;


import java.util.HashMap;
import java.util.List;
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
    int matXCenter = 0;
    int matYCenter = 0;


    public dispositionPrinter(HashMap<Coordinates, PlayableCard> disposition){
        this.disposition = disposition;
    }


    //main function
    public void print(){
        //mapDisposition();
        //firstLineBuilder();
        generalLineBuilder();

    }

    public void addAvailablePlaces(List<Coordinates> availablePlaces){
        int i, j;
        int matXDim = dispositionMap[0].length + 1;
        int matYDim = dispositionMap.length + 1;
        matXCenter = (matXDim + 1)/2;
        matYCenter = matYDim/2 ;
        for(Coordinates c : availablePlaces){
            i = matYCenter - c.getY();
            j = matXCenter + c.getX();
            mat[i][j] = -1;
        }

        // stampiamo mat
        for (int m = 0; m < yDim + 3; m++) {
            for (int n = 0; n < xDim + 2; n++) {
                System.out.print(mat[m][n] + " ");
            }
            System.out.println();
        }
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
        //mat = new int[yDim + 1][xDim];
        mat = new int[yDim + 3][xDim + 2];
        //riempiamo mat di zeri
        for (int i = 0; i < yDim + 3; i++) {
            for (int j = 0; j < xDim + 2; j++) {
                mat[i][j] = 0;
            }
        }

        //POPOLIAMO LA MAPPA FACENDO UNA CONVERSIONE DELLE COORDINATE DAL SISTEMA DI DISPOSITION A QUELLO DI DISPOSITIONMAP
        for(Coordinates c : disposition.keySet()){
            dispositionMap[convertY(c.getY())][convertX(c.getX())] = disposition.get(c).getId();

            //popoliamo mat
            for(int i = 1; i < dispositionMap.length + 1; i++){
                for(int j = 1; j < dispositionMap[0].length + 1; j++){
                    if(dispositionMap[i - 1][j - 1] > 0){
                        mat[i][j] = dispositionMap[i - 1][j - 1];
                        mat[i + 1][j] = dispositionMap[i -1][j -1];
                    }
                }
            }

        }

        // stampiamo mat
        for (int i = 0; i < yDim + 3; i++) {
            for (int j = 0; j < xDim + 2; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }


    }



    private void generalLineBuilder(){
        int j = 0;
        String line;

        //parte superiore della cornice
        line = "\u2554";
        int matLength = (15*mat[0].length) - (3*(mat[0].length - 1));
        for(int i = 0; i < matLength; i++){
            line += "\u2550";
        }
        line += "\u2557";
        System.out.println(line);


        while(j < mat.length - 1){

            //itero tre volte su ogni riga
            for(int row = 0; row < 3; row++) {

                ////////////////////////////////// PRIMA COLONNA ////////////////////////////////////////////////
                //nella collonna più a sinistra ci possono essere solo 0 o -1
                //caso in cui ho uno 0
                if (mat[j][0] == 0) {
                    if(mat[j][1] <= 0) line = "\u2551               ";//15
                    else line ="\u2551            ";//12

                //caso in cui ho un -1
                } else if (mat[j][0] == -1) {

                    //quando row != 2 stampo solamente degli spazi
                    if( row != 2){
                        if(mat[j][1] == 0) line = "\u2551               ";//15
                        else if (mat[j][1] > 0) line ="\u2551            ";//12

                    } else if (row == 2) {

                        if(mat[j][1] == 0) line = "\u2551    " + buildCoordinates(0,j) + "    ";//15
                        else if (mat[j][1] > 0) line = "\u2551  " + buildCoordinates(0,j) + "   ";//12
                    }
                }
                ///////////////////////////////////////////////////////////////////////////////////////////


                ////////////////////////////////////// COLONNE CENTRALI /////////////////////////////////////////////////////////
                //COLONNE CENTRALI
                for(int col = 1; col < mat[0].length -1; col++){

                    if( (mat[j][col] > 0) && (mat[j + 1][col] == mat[j][col])) {
                        line += cb.buildLine(row, getCardFromDisposition(col - 1, j -1));
                    }
                    else if (mat[j][col] > 0 && mat[j + 1][col] != mat[j][col]) {
                        line += cb.buildLine(row + 3, getCardFromDisposition(col  - 1 , j - 2));
                    }
                    else if(mat[j][col] == 0){
                        if(mat[j][col - 1] <= 0 && mat[j][col + 1] <= 0) line += "            ";//12  OK
                        else if(mat[j][col - 1] > 0 && mat[j][col + 1] > 0) line += "         ";//9   OK
                        else if(mat[j][col - 1] <= 0 && mat[j][col + 1] > 0) line += "         ";//9  OK
                        else if(mat[j][col - 1] > 0 && mat[j][col + 1] <= 0) line += "            ";//12  OK
                    }
                    else if(mat[j][col] == -1){

                        if(row != 2){
                            //tutti i casi
                            if(mat[j][col - 1] == 0 && mat[j][col + 1] == 0){line += "            ";} //12 OK
                            else if(mat[j][col - 1] > 0 && mat[j][col + 1] > 0) {line += "         ";} //9 OK
                            else if(mat[j][col - 1] == 0 && mat[j][col + 1] > 0) {line += "         ";}//9 OK
                            else if(mat[j][col - 1] > 0 && mat[j][col + 1] == 0){line += "            ";} //12 OK

                        } else if (row == 2) {
                            //tutti i casi
                            if(mat[j][col - 1] == 0 && mat[j][col + 1] == 0){line += "  " + buildCoordinates(col,j) + "   ";} //12 OK
                            else if(mat[j][col - 1] > 0 && mat[j][col + 1] > 0) {line += " " + buildCoordinates(col,j) + " ";} //9 spazi con in mezzo le coordinate
                            else if(mat[j][col - 1] == 0 && mat[j][col + 1] > 0) {line += " " + buildCoordinates(col,j) + " ";}//9 spazi con in mezzo le coordinate
                            else if(mat[j][col - 1] > 0 && mat[j][col + 1] == 0){line += "  " + buildCoordinates(col,j) + "   ";} //12 spazi con in mezzo le coordinate
                        }
                    }
                }
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                ///////////////////////////////////////////////// ULTIMA COLONNA  ///////////////////////////////////////////////////////////////////////////////////////////
                //nella COLONNA PIU' A DESTRA CI POSSONO ESSERE SOLO 0 O -1

                if (mat[j][mat[0].length-1] == 0) {
                    line += "            \u2551";//12 OK
                }
                else if (mat[j][mat[0].length-1] == -1 ) {
                    if(row != 2){
                        line += "            \u2551";//12 OK
                    }else if (row == 2){
                        line += "  " + buildCoordinates(0,j) + "   \u2551";//12
                    }

                }
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                System.out.println(line);
            }
            line ="";
            j++;
        }

        //ULTIMA STRISCIA //////////////////////////////////////////////////////////////////////////////////////////////////////
        // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //itero tre volte su ogni riga
        for(int row = 0; row < 3; row++) {

            //colonna piu a sinisra -> essendo questa l'ultima striscia ci saranno sempre e solo zeri
            if (mat[j][0] == 0) {
                if(mat[j][1] == 0) line = "\u2551               ";//15
                else if (mat[j][1] > 0) line = "\u2551            ";//12
            }

            //colonne centrali
            for(int col = 1; col < mat[0].length -1; col++){
                if(mat[j][col] == 0){
                    if(mat[j][col - 1] == 0 && mat[j][col + 1] == 0) line += "            ";//12  OK
                    else if(mat[j][col - 1] > 0 && mat[j][col + 1] > 0) line += "         ";//9  OK
                    else if(mat[j][col - 1] == 0 && mat[j][col + 1] > 0) line += "         ";//9  OK
                    else if(mat[j][col - 1] > 0 && mat[j][col + 1] == 0) line += "            ";//12  OK
                }
            }

            //colonna di destra ( CONTROLLO INUTILE MA LO LASCIO PER CHIAREZZA )
            if (mat[j][mat[0].length-1] == 0) {
                line += "            \u2551";//12  OK
            }

            System.out.println(line);
        }

        //parte inferiore della cornice
        line = "\u255A";
        for(int i = 0; i < matLength; i++){
            line += "\u2550";
        }
        line += "\u255D";
        System.out.println(line);

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

    private int inverseConvertMatX(int x){
        return x - matXCenter;
    }
    private int inverseConvertMatY(int y){
        return matYCenter - y;
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

    private String buildCoordinates(int x, int y){
        int X = inverseConvertMatX(x);
        int Y = inverseConvertMatY(y);

        if( X>= 0 && Y >= 0) return new String(" (" + X + "," + Y + ") ");
        else if(X < 0 && Y < 0) return new String("(" + X + "," + Y + ")");
        else return new String(" (" + X + "," + Y + ")");
    }
}
