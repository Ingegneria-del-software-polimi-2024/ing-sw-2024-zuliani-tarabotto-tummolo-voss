package model.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * the pawn color belonging to a player
 */

    public enum Pawn {
        red(),
        purple(),
        green(),
        blue();

        private static List<Pawn> shuffledList = new ArrayList<>();
        private static int currentIndex = 0;

        public static void reset() {
            shuffledList.clear();
            Collections.addAll(shuffledList, Pawn.values());
            Collections.shuffle(shuffledList);
            currentIndex = 0;
        }

        public static Pawn randomPick() {
            if (shuffledList.isEmpty() || currentIndex >= shuffledList.size()) {
                reset();
            }
            return shuffledList.get(currentIndex++);
        }


        /////////////////// ONLY USED IN Controller and ControllerTest to directly ask the player which color he wants /////////////////////////////

        private boolean isAvailable = true;


        public boolean getIsAvailable() {return isAvailable;}


        public void setIsAvailable() {this.isAvailable = false;}

        public static void printAvailablePawns() {
            for(Pawn pawn : Pawn.values() ){
                if(pawn.isAvailable) System.out.println(pawn.toString());
            }
        }
}
