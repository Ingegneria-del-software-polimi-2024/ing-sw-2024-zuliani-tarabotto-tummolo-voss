package Client.UI.TUI;
import model.cards.PlayableCards.PlayableCard;
import model.placementArea.Coordinates;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fusesource.jansi.Ansi.*;


/**
 * Class responsible of printing the player's disposition on the console.
 * The class uses two auxiliary matrices to determine the printing order of the cards.
 */
public class DispositionPrinter {

    /**
     * the matrix containing an exact corresponding representation of the player's disposition
     */
    private int[][] dispositionMap;
    /**
     * matrix based on the dispositionMap matrix, this is the matrix used by the DispositionPrinter to actually iterate over the player's disposition
     */
    private int[][] mat;

    private Coordinates mapCenter;
    private CardBuilder cb;
    /**
     * contains the player's disposition
     */
    private HashMap<Coordinates, PlayableCard> disposition;
    /**
     * The X max.
     */
    int xMax=0;
    /**
     * The Y max.
     */
    int yMax=0;
    /**
     * The X dim.
     */
    int xDim = 0;
    /**
     * The Y dim.
     */
    int yDim = 0;
    /**
     * The Mat x center.
     */
    int matXCenter = 0;
    /**
     * The Mat y center.
     */
    int matYCenter = 0;
    /**
     * The Min col.
     */
    int minCol = 0;
    /**
     * The Max col.
     */
    int maxCol = 0;

    /**
     * CardBuilder passed as a parameter
     *
     * @param cb CardBuilder used by the class to build the cards
     */
    public DispositionPrinter(CardBuilder cb) {
        this.cb = cb;
    }


    /**
     * prints the player's disposition without the indication of availablePlaces
     *
     * @param disposition HashMap containing the coordinates of each card on the player's board and their PlayableCard references
     */
    public void print(HashMap<Coordinates, PlayableCard> disposition){
        this.disposition = disposition;
        mapDisposition();
        generalLineBuilder();

    }

    /**
     * prints the player's disposition with the coordinates of the availablePlaces
     *
     * @param disposition     HashMap containing the coordinates of each card on the player's board and their PlayableCard references
     * @param availablePlaces a List of Coordinates containing all the coordinates where a player can place his next card
     */
    public void print(HashMap<Coordinates, PlayableCard> disposition, List<Coordinates> availablePlaces){
        this.disposition = disposition;
        mapDisposition();
        addAvailablePlaces(availablePlaces);
        generalLineBuilder();

    }

    /**
     * adds to the auxiliary matrix the symbols that signal the presence of an available place
     *
     * @param availablePlaces a List of Coordinates containing all the coordinates where a player can place his next card
     */
    public void addAvailablePlaces(List<Coordinates> availablePlaces){
        int i, j;
        int matXDim = dispositionMap[0].length + 1;
        int matYDim = dispositionMap.length + 1;
        matXCenter = (matXDim + 1)/2;
        matYCenter = matYDim/2 ;
        // "-1" is put in the coordinates where we have an available place
        for(Coordinates c : availablePlaces){
            i = matYCenter - c.getY();
            j = matXCenter + c.getX();
            mat[i][j] = -1;
        }

    }


    /**
     * the method fills both the "dispositionMap" and the "mat" matrices
     */
    public void mapDisposition(){

        //we find the maximum x and y
        for (Map.Entry<Coordinates, PlayableCard> entry : disposition.entrySet()) {
            if (Math.abs(entry.getKey().getX()) >= xMax) {
                xMax = Math.abs(entry.getKey().getX());
            }
            if (Math.abs(entry.getKey().getY()) >= yMax) {
                yMax = Math.abs(entry.getKey().getY());
            }

        }

        //we find the center of the matrix which will correspond to the position of the StarterCard
        xDim = 2*xMax +1;
        yDim = 2*yMax +1;
        mapCenter = new Coordinates(xMax, yMax);
        dispositionMap = new int[yDim][xDim];
        mat = new int[yDim + 3][xDim + 2];
        //we fill mat with zeros
        for (int i = 0; i < yDim + 3; i++) {
            for (int j = 0; j < xDim + 2; j++) {
                mat[i][j] = 0;
            }
        }


        minCol = mat[0].length - 1;
        maxCol = 0;
        //we fill the dispositionMap converting the disposition's coordinates system to the one of the dispositionMap
        for(Coordinates c : disposition.keySet()){
            dispositionMap[convertY(c.getY())][convertX(c.getX())] = disposition.get(c).getId();
        }


        //we fill the mat matrix
        for(int i = 1; i < dispositionMap.length + 1; i++){
            for(int j = 1; j < dispositionMap[0].length + 1; j++){
                if(dispositionMap[i - 1][j - 1] > 0){
                    mat[i][j] = dispositionMap[i - 1][j - 1];
                    mat[i + 1][j] = dispositionMap[i -1][j -1];
                    if(j < minCol) minCol = j;
                    if(j > maxCol) maxCol = j;
                }
            }
        }
        minCol -- ;
        maxCol ++;
    }



    private void generalLineBuilder(){
        int j = 0;
        String line;

        //top part of the frame
        line = "\u2554\u2550\u2550\u2550" + ansi().fg(226).a(" DISPOSITION ").reset();
        int numCol = maxCol - minCol;
        int matLength = (15*(numCol + 1)) - (3*(numCol));
        int matLength1 = matLength - 16;
        for(int i = 0; i < matLength1; i++){
            line += "\u2550";
        }
        line += "\u2557";
        System.out.println(line);


        while(j < mat.length - 1){


            for(int row = 0; row < 3; row++) {

                ////////////////////////////////// FIRST COLUMN ////////////////////////////////////////////////
                //the first column will contain only 0 or -1
                //case in which I find a 0
                if (mat[j][minCol] == 0) {
                    if(mat[j][minCol + 1] <= 0) line = "\u2551               ";//15
                    else line ="\u2551            ";//12

                //case in which I find -1
                } else if (mat[j][minCol] == -1) {


                    if( row != 2){
                        if(mat[j][minCol + 1] == 0) line = "\u2551               ";//15
                        else if (mat[j][minCol + 1] > 0) line ="\u2551            ";//12

                    } else if (row == 2) {

                        if(mat[j][minCol + 1] == 0) line = "\u2551    " + buildCoordinates(minCol,j) + "    ";//15
                        else if (mat[j][minCol + 1] > 0) line = "\u2551  " + buildCoordinates(minCol,j) + "   ";//12
                    }
                }
                ///////////////////////////////////////////////////////////////////////////////////////////


                ////////////////////////////////////// CENTRAL COLUMNS /////////////////////////////////////////////////////////
                for(int col = minCol + 1; col < maxCol; col++){

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


                ///////////////////////////////////////////////// LAST COLUMN  ///////////////////////////////////////////////////////////////////////////////////////////
                //the last column will contain only 0 or -1
                if (mat[j][maxCol] == 0) {
                    line += "            \u2551";//12 OK
                }
                else if (mat[j][maxCol] == -1 ) {
                    if(row != 2){
                        line += "            \u2551";//12 OK
                    }else if (row == 2){
                        line += "  " + buildCoordinates(maxCol,j) + "   \u2551";//12
                    }

                }
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                System.out.println(line);
            }
            line ="";
            j++;
        }

        /////////////////////////////////////////// LAST ROW //////////////////////////////////////////////////////////////////////////////////////////////////////
        //we iterate three times on the last row
        for(int row = 0; row < 3; row++) {

            //left column
            if (mat[j][minCol] == 0) {
                if(mat[j][minCol + 1] == 0) line = "\u2551               ";//15
                else if (mat[j][minCol + 1] > 0) line = "\u2551            ";//12
            }

            //central columns
            for(int col = minCol + 1; col < maxCol; col++){
                if(mat[j][col] == 0){
                    if(mat[j][col - 1] == 0 && mat[j][col + 1] == 0) line += "            ";//12
                    else if(mat[j][col - 1] > 0 && mat[j][col + 1] > 0) line += "         ";//9
                    else if(mat[j][col - 1] == 0 && mat[j][col + 1] > 0) line += "         ";//9
                    else if(mat[j][col - 1] > 0 && mat[j][col + 1] == 0) line += "            ";//12
                }
            }

            //right column
            if (mat[j][maxCol] == 0) {
                line += "            \u2551";//12  OK
            }

            System.out.println(line);
        }

        //bottom part of the frame
        line = "\u255A";
        for(int i = 0; i < matLength; i++){
            line += "\u2550";
        }
        line += "\u255D";
        System.out.println(line);

    }


    /**
     * converts the x coordinate of the HashMap<Coordinates, PlayableCard> to the x coordinate of dispositionMap coordinates system
     * @param xCoord
     * @return
     */
    private int convertX(int xCoord){
        return mapCenter.getX() + xCoord ;//xDisp
    }

    /**
     * converts the y coordinate of the HashMap<Coordinates, PlayableCard> to the y coordinate of dispositionMap coordinates system
     * @param yCoord
     * @return
     */
    private int convertY(int yCoord){
        return mapCenter.getY() - yCoord ;//yDisp
    }

    /**
     * converts the x coordinate of the dispositionMap to the x coordinate of HashMap<Coordinates, PlayableCard> coordinates system
     * @param xDisp
     * @return
     */
    private int inverseConvertX(int xDisp){
        return xDisp - mapCenter.getX(); //xCoord
    }

    /**
     * converts the y coordinate of the dispositionMap to the y coordinate of HashMap<Coordinates, PlayableCard> coordinates system
     * @param yDisp
     * @return
     */
    private int inverseConvertY(int yDisp){
        return mapCenter.getY() - yDisp; //yCoord
    }


    /**
     * converts the x coordinate of mat to the x coordinate of HashMap<Coordinates, PlayableCard> coordinates system
     * @param x
     * @return
     */
    private int inverseConvertMatX(int x){
        return x - matXCenter;
    }

    /**
     * converts the y coordinate of mat to the y coordinate of HashMap<Coordinates, PlayableCard> coordinates system
     * @param y
     * @return
     */
    private int inverseConvertMatY(int y){
        return matYCenter - y;
    }


    /**
     * given the x and y coordinates of the dispositionMap matrix, the method checks if there is an entry in the HashMap<Coordinates, PlayableCard>
     * with equal coordinates, in case the entry exists it is then returned
     * @param x
     * @param y
     * @return
     */
    private PlayableCard getCardFromDisposition(int x, int y) {
        Coordinates c = new Coordinates(inverseConvertX(x), inverseConvertY(y));
        for (Coordinates coord : disposition.keySet()) {
            if (coord.equals(c)) {
                return disposition.get(coord);
            }
        }
        return null;
    }

    /**
     * returns a line containing the representation for the corresponding available place
     * @param x
     * @param y
     * @return
     */
    private String buildCoordinates(int x, int y){
        int X = inverseConvertMatX(x);
        int Y = inverseConvertMatY(y);

        if( X>= 0 && Y >= 0) return new String(" (" + X + "," + Y + ") ");
        else if(X < 0 && Y < 0) return new String("(" + X + "," + Y + ")");
        else return new String(" (" + X + "," + Y + ")");
    }
}
