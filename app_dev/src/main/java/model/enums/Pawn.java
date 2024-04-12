package model.enums;

/**
 * the pawn color belonging to a player
 */
public enum Pawn {
    red(),
    yellow(),
    green(),
    blue(),
    black();

    private boolean isAvailable = true;

    /**
     *
     * @return FALSE if the colour has already been selected by a player, else TRUE
     */
    public boolean getIsAvailable() {return isAvailable;}

    /**
     * sets isAvailable false
     */
    public void setIsAvailable() {this.isAvailable = false;}

    public static void printAvailablePawns() {
        for(Pawn pawn : Pawn.values() ){
            if(pawn.isAvailable) System.out.println(pawn.toString());
        }
    }
}
