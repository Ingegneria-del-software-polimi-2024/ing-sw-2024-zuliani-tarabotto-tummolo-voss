package model.enums;

import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * the artifact that can be present on the corners of the cards
 */
public enum Artifact {
    feather(Ansi.ansi().fg(221).a("Q").reset().toString(),
            new String[]{"\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557",
                    "\u2551             \u2551",
                    "\u2551  2          \u2551",
                    "\u2551       "+ ansi().fg(221).a("Q Q").reset() + "   \u2551",
                    "\u2551             \u2551",
                    "\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D"
            }),//quill
    paper(Ansi.ansi().fg(221).a("M").reset().toString(),
            new String[]{"\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557",
                    "\u2551             \u2551",
                    "\u2551  2          \u2551",
                    "\u2551       "+ ansi().fg(221).a("M M").reset() + "   \u2551",
                    "\u2551             \u2551",
                    "\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D"
            }),//manuscript
    ink(Ansi.ansi().fg(221).a("I").reset().toString(),
            new String[]{"\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557",
                    "\u2551             \u2551",
                    "\u2551  2          \u2551",
                    "\u2551       "+ ansi().fg(221).a("I I").reset() + "   \u2551",
                    "\u2551             \u2551",
                    "\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D"
            })//inkwell
    ;

    private final String stringValue;
    private final String[] objective;
    private static String[] trisObjective = new String[]{"\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557",
            "\u2551             \u2551",
            "\u2551  3          \u2551",
            "\u2551      "+ ansi().fg(221).a("I M Q").reset() + "  \u2551",
            "\u2551             \u2551",
            "\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D"
    };
    Artifact(String symbol, String[] objective) {
        this.stringValue = symbol;
        this.objective = objective;
    }

    public String getStringValue(){return stringValue;}
    public String getObjective(int row){return objective[row];}
    public static String getTrisObjective(int row){return trisObjective[row];}
}

