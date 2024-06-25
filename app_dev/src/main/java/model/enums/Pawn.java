package model.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * the pawn color belonging to a player
 */
public enum Pawn {
    /**
     * Red pawn.
     */
    red(),
    /**
     * Purple pawn.
     */
    purple(),
    /**
     * Green pawn.
     */
    green(),
    /**
     * Blue pawn.
     */
    blue();

    /**
     * The constant shuffledList.
     */
    private static List<Pawn> shuffledList = new ArrayList<>();

    /**
     * Get random pawns list.
     *
     * @return the list
     */
    public static List<Pawn> getRandomPawns(){
            shuffledList.clear();
            Collections.addAll(shuffledList, Pawn.values());
            Collections.shuffle(shuffledList);
            return shuffledList;
        }


        /////////////////// ONLY USED IN Controller and ControllerTest to directly ask the player which color he wants /////////////////////////////


    /**
     * True if the pawn is available, false otherwise.
     */
    private boolean isAvailable = true;


        //public boolean getIsAvailable() {return isAvailable;}


    /**
     * Sets isAvailable to false.
     */
    public void setFalseIsAvailable() {this.isAvailable = false;}

        //MAI USATO
//        public static void printAvailablePawns() {
//            for(Pawn pawn : Pawn.values() ){
//                if(pawn.isAvailable) System.out.println(pawn.toString());
//            }
//        }
}
