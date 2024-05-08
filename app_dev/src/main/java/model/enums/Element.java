package model.enums;

import org.fusesource.jansi.Ansi;

/**
 * the elements that can be present on the corners of the cards
 */
public enum Element {
    mushrooms(Ansi.ansi().fgRed().a("F").reset().toString(), 196),
    vegetals(Ansi.ansi().fgGreen().a("P").reset().toString(), 46),
    animals(Ansi.ansi().fgCyan().a("A").reset().toString(), 45),
    insects(Ansi.ansi().fgMagenta().a("I").reset().toString(), 129);



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
    Element(String symbol, int color) {
        this.stringValue = symbol;
        this.color = color;
    }

    public String getStringValue(){return stringValue;}
    public int getColor(){return color;}
}