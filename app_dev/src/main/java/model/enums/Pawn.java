package model.enums;

public enum Pawn {
    red(),
    yellow(),
    green(),
    blue(),
    black();

    private boolean isAvailable = true;
    public boolean getIsAvailable() {return isAvailable;}
    public void setIsAvailable() {this.isAvailable = false;}

    public static void printAvailablePawns() {
        for(Pawn pawn : Pawn.values() ){
            if(pawn.isAvailable) System.out.println(pawn.toString());
        }
    }
}
