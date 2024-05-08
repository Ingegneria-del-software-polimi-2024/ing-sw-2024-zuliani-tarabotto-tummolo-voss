package model.enums;

import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * the elements that can be present on the corners of the cards
 */
public enum Element {
    mushrooms(ansi().fg(196).a("F").reset().toString(), 196, new String[]{"\u2554\u2550\u2550", "\u2560\u2550 "}),
    vegetals(ansi().fg(46).a("P").reset().toString(), 46, new String[]{"\u2554\u2550\u2557", "\u2560\u2550\u255D"}),
    animals(ansi().fg(45).a("A").reset().toString(), 45, new String[]{"\u2554\u2550\u2557", "\u2560\u2550\u2563"}),
    insects(ansi().fg(129).a("I").reset().toString(), 129, new String[]{" \u2566 ", " \u2569 "});



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
    Element(String symbol, int color, String[] backFaceSymbol) {
        this.stringValue = symbol;
        this.color = color;
        this.backFaceSymbol = backFaceSymbol;
    }

    public String getStringValue(){return stringValue;}
    public int getColor(){return color;}

    public String getBackFaceSymbol(int row){
        return ansi().fg(color).a(backFaceSymbol[row]).reset().toString();
    }


}