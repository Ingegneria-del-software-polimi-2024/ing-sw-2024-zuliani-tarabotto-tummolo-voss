package Client.UI.TUI;

import model.cards.ObjectiveCard;
import model.enums.Artifact;
import model.objective.*;
import java.util.ArrayList;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;


/**
 * Class with methods for printing on console the Objectives panel
 */
public class ObjectivesPrinter {
    private final int color = 226;
    /**
     * List of Strings representing the Objectives panel
     */
    private List<String> objField;


    /**
     * depending on the ObjectiveCard c, the method retrieves the corresponding objective line from the Element or Artifact Enumeration
     * @param c the reference to the ObjectiveCard
     * @param i the row of the ObjectiveCard to be printed
     * @return a String containing the i line of the ObjectiveCard c
     */
    public String printRow(ObjectiveCard c, int i){
        String line;
        if(c.getObjective() instanceof ElementObjective) line = c.getObjective().getElement().getElementObjective(i);
        else if(c.getObjective() instanceof DiagonalShapeObjective) line = c.getObjective().getElement().getDiagonalObjective(i);
        else if (c.getObjective() instanceof LShapeObjective) line = c.getObjective().getElement().getLShapeObjective(i);
        else if (c.getObjective().getArtifact() != null ) line = c.getObjective().getArtifact().getObjective(i);
        else line = Artifact.getTrisObjective(i);

        return line;
    }

    /**
     * directly prints on the console the Objectives panel.
     * @param c1 the first common objective
     * @param c2 the second common objective
     * @param s the player's secret objective
     */
    public void printObjectivesBoard(ObjectiveCard c1, ObjectiveCard c2, ObjectiveCard s){

        System.out.println("\u2554\u2550\u2550\u2550"+ ansi().fg(color).bold().a(" OBJECTIVES ").reset() + "\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557");
        System.out.println("\u2551                                                                   \u2551");

        System.out.println("\u2551          OBJ:1                OBJ:2          Secret Objective     \u2551");

        for(int i = 0; i < 6; i++){
            System.out.println("\u2551     " + printRow(c1, i) + "      " + printRow(c2, i)  + "      " + printRow(s, i)  + "     \u2551");
        }


        System.out.println("\u2551                                                                   \u2551");
        System.out.println("\u2551                                                                   \u2551");
        System.out.println("\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D");
    }

    /**
     * method that can be used for printing both the commonObjectives and the two objectives the player has to choose from
     * @param c1 first ObjectiveCard
     * @param c2 second ObjectiveCard
     */
    public void printCommonObjectives(ObjectiveCard c1, ObjectiveCard c2){
        System.out.println("\u2554\u2550\u2550\u2550"+ ansi().fg(color).bold().a(" OBJECTIVES ").reset() + "\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557");
        System.out.println("\u2551                                              \u2551");

        System.out.println("\u2551          OBJ:1                OBJ:2          \u2551");

        for(int i = 0; i < 6; i++){
            System.out.println("\u2551     " + printRow(c1, i) + "      " + printRow(c2, i)  + "     \u2551");
        }


        System.out.println("\u2551                                              \u2551");
        System.out.println("\u2551                                              \u2551");
        System.out.println("\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D");
    }


    /**
     * returns a List of Strings representing the Objectives panel
     * @param c1 first common objective
     * @param c2 second common objective
     * @param s secret objective
     * @return List of Strings "objField"
     */
    public List<String> getObjField(ObjectiveCard c1, ObjectiveCard c2, ObjectiveCard s){
        this.objField = new ArrayList<>();


        objField.add("\u2554\u2550\u2550\u2550"+ ansi().fg(color).bold().a(" OBJECTIVES ").reset() + "\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557");
        objField.add("\u2551                                                                   \u2551");

        objField.add("\u2551          OBJ:1                OBJ:2          Secret Objective     \u2551");

        for(int i = 0; i < 6; i++){
            objField.add("\u2551     " + printRow(c1, i) + "      " + printRow(c2, i)  + "      " + printRow(s, i)  + "     \u2551");
        }


        objField.add("\u2551                                                                   \u2551");
        objField.add("\u2551                                                                   \u2551");
        objField.add("\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D");

        return objField;
    }

}
