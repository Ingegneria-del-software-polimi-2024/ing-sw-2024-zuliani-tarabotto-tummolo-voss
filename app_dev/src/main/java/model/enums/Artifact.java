package model.enums;

import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * An enumeration representing different artifacts that can be present on the corners of the cards.
 */
public enum Artifact {

    /**
     * Feather artifact represented by symbol 'Q'.
     */
    feather(Ansi.ansi().fg(221).a("Q").reset().toString(),
            new String[]{
                    "\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557",
                    "\u2551             \u2551",
                    "\u2551  2          \u2551",
                    "\u2551       " + Ansi.ansi().fg(221).a("Q Q").reset() + "   \u2551",
                    "\u2551             \u2551",
                    "\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D"
            }),

    /**
     * Paper artifact represented by symbol 'M'.
     */
    paper(Ansi.ansi().fg(221).a("M").reset().toString(),
            new String[]{
                    "\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557",
                    "\u2551             \u2551",
                    "\u2551  2          \u2551",
                    "\u2551       " + Ansi.ansi().fg(221).a("M M").reset() + "   \u2551",
                    "\u2551             \u2551",
                    "\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D"
            }),

    /**
     * Ink artifact represented by symbol 'I'.
     */
    ink(Ansi.ansi().fg(221).a("I").reset().toString(),
            new String[]{
                    "\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557",
                    "\u2551             \u2551",
                    "\u2551  2          \u2551",
                    "\u2551       " + Ansi.ansi().fg(221).a("I I").reset() + "   \u2551",
                    "\u2551             \u2551",
                    "\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D"
            });

    /**
     * Symbolic representation of the artifact.
     */
    private final String stringValue;

    /**
     * Objective representation of the artifact.
     */
    private final String[] objective;
    /**
     * Constructor for Artifact enumeration.
     *
     * @param symbol    Symbolic representation of the artifact.
     * @param objective Objective representation of the artifact.
     */
    Artifact(String symbol, String[] objective) {
        this.stringValue = symbol;
        this.objective = objective;
    }

    /**
     * Gets the symbolic representation of the artifact.
     *
     * @return Symbolic representation of the artifact.
     */
    public String getStringValue() {
        return stringValue;
    }

    /**
     * Gets the objective representation of the artifact for a specific row.
     *
     * @param row Row index of the objective representation.
     * @return Objective representation of the artifact for the specified row.
     */
    public String getObjective(int row) {
        return objective[row];
    }

    /**
     * Gets the tris objective representation of the artifact for a specific row.
     *
     * @param row Row index of the tris objective representation.
     * @return Tris objective representation of the artifact for the specified row.
     */
    public static String getTrisObjective(int row) {
        return trisObjective[row];
    }

    // Static field representing the tris objective
    private static String[] trisObjective = new String[]{
            "\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557",
            "\u2551             \u2551",
            "\u2551  3          \u2551",
            "\u2551      " + Ansi.ansi().fg(221).a("I M Q").reset() + "  \u2551",
            "\u2551             \u2551",
            "\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D"
    };
}
