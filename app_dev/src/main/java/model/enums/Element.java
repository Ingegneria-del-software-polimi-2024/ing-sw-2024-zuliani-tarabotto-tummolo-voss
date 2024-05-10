package model.enums;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * the elements that can be present on the corners of the cards
 */
public enum Element {
    mushrooms(ansi().fg(196).a("F").reset().toString(),
            196,
            new String[]{"\u2554\u2550\u2550", "\u2560\u2550 "},
            // diagonal objective
            new String[]{"\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557",
                    "\u2551             \u2551",
                    "\u2551  2       "+ ansi().fg(196).a("F").reset() +"  \u2551",
                    "\u2551        "+ ansi().fg(196).a("F").reset() + "    \u2551",
                    "\u2551      "+ ansi().fg(196).a("F").reset() +"      \u2551",
                    "\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D"
            },
            //lShapeObjective
            new String[]{"\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557",
                    "\u2551             \u2551",
                    "\u2551  3   " +ansi().fg(196).a("F").reset() +"      \u2551",
                    "\u2551      " +ansi().fg(196).a("F").reset() +"      \u2551",
                    "\u2551        "+ansi().fg(46).a("P").reset() +"    \u2551",
                    "\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D"
            },
            //elementObjective
            new String[]{"\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557",
                    "\u2551             \u2551",
                    "\u2551  2          \u2551",
                    "\u2551        "+ ansi().fg(196).a("F").reset()+"    \u2551",
                    "\u2551      "+ ansi().fg(196).a("F").reset()+"   "+ ansi().fg(196).a("F").reset()+"  \u2551",
                    "\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D"
            }),
    vegetals(ansi().fg(46).a("P").reset().toString(),
            46,
            new String[]{"\u2554\u2550\u2557", "\u2560\u2550\u255D"},
            new String[]{"\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557",
                    "\u2551             \u2551",
                    "\u2551  2   "+ansi().fg(46).a("P").reset() +"      \u2551",
                    "\u2551        "+ansi().fg(46).a("P").reset() +"    \u2551",
                    "\u2551          "+ansi().fg(46).a("P").reset() +"  \u2551",
                    "\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D"
            },
            new String[]{"\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557",
                    "\u2551             \u2551",
                    "\u2551  3       "+ansi().fg(46).a("P").reset() +"  \u2551",
                    "\u2551          "+ansi().fg(46).a("P").reset() +"  \u2551",
                    "\u2551        "+ansi().fg(129).a("I").reset() +"    \u2551",
                    "\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D"
            },
            new String[]{"\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557",
                    "\u2551             \u2551",
                    "\u2551  2          \u2551",
                    "\u2551        "+ ansi().fg(46).a("P").reset()+"    \u2551",
                    "\u2551      "+ ansi().fg(46).a("P").reset()+"   "+ ansi().fg(46).a("P").reset()+"  \u2551",
                    "\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D"
            }),
    animals(ansi().fg(45).a("A").reset().toString(),
            45,
            new String[]{"\u2554\u2550\u2557", "\u2560\u2550\u2563"},
            new String[]{"\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557",
                    "\u2551             \u2551",
                    "\u2551  2       "+ ansi().fg(45).a("A").reset() +"  \u2551",
                    "\u2551        "+ ansi().fg(45).a("A").reset() + "    \u2551",
                    "\u2551      "+ ansi().fg(45).a("A").reset() +"      \u2551",
                    "\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D"
            },
            new String[]{"\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557",
                    "\u2551             \u2551",
                    "\u2551  3       " +ansi().fg(196).a("F").reset() +"  \u2551",
                    "\u2551        "+ ansi().fg(45).a("A").reset()+"    \u2551",
                    "\u2551        "+ ansi().fg(45).a("A").reset()+"    \u2551",
                    "\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D"
            },
            new String[]{"\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557",
                    "\u2551             \u2551",
                    "\u2551  2          \u2551",
                    "\u2551        "+ ansi().fg(45).a("A").reset()+"    \u2551",
                    "\u2551      "+ ansi().fg(45).a("A").reset()+"   "+ ansi().fg(45).a("A").reset()+"  \u2551",
                    "\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D"
            }),
    insects(ansi().fg(129).a("I").reset().toString(),
            129,
            new String[]{" \u2566 ", " \u2569 "},
            new String[]{"\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557",
                    "\u2551             \u2551",
                    "\u2551  2   "+ansi().fg(129).a("I").reset() +"      \u2551",
                    "\u2551        "+ansi().fg(129).a("I").reset() +"    \u2551",
                    "\u2551          "+ansi().fg(129).a("I").reset() +"  \u2551",
                    "\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D"
            },
            new String[]{"\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557",
                    "\u2551             \u2551",
                    "\u2551  3     "+ ansi().fg(45).a("A").reset()+"    \u2551",
                    "\u2551          "+ansi().fg(129).a("I").reset() +"  \u2551",
                    "\u2551          "+ansi().fg(129).a("I").reset() +"  \u2551",
                    "\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D"
            },
            new String[]{"\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557",
                    "\u2551             \u2551",
                    "\u2551  2          \u2551",
                    "\u2551        "+ ansi().fg(129).a("I").reset()+"    \u2551",
                    "\u2551      "+ ansi().fg(129).a("I").reset()+"   "+ ansi().fg(129).a("I").reset()+"  \u2551",
                    "\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D"
            });



    public Element calculateComplementar() {
        switch (this){
            case animals:
                return mushrooms;
            case mushrooms:
                return vegetals;
            case insects:
                return animals;
            case vegetals:
                return insects;
        }
        return null;
    }

    private final String stringValue;
    private final int color;
    private final String[] backFaceSymbol;
    private final String[] diagonalObjective;
    private final String[] lShapeObjective;
    private final String[] elementObjective;
    Element(String symbol, int color, String[] backFaceSymbol, String[] diagonalObjective, String[] lShapeObjective, String[] elementObjective) {
        this.stringValue = symbol;
        this.color = color;
        this.backFaceSymbol = backFaceSymbol;
        this.diagonalObjective = diagonalObjective;
        this.lShapeObjective = lShapeObjective;
        this.elementObjective = elementObjective;
    }

    public String getStringValue(){return stringValue;}
    public int getColor(){return color;}

    public String getBackFaceSymbol(int row){
        return ansi().fg(color).a(backFaceSymbol[row]).reset().toString();
    }

    public String getDiagonalObjective(int row) { return diagonalObjective[row];}

    public String getLShapeObjective(int row) { return lShapeObjective[row];}
    public String getElementObjective(int row) { return elementObjective[row];}

}