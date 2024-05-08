package model.enums;

import org.fusesource.jansi.Ansi;

/**
 * the artifact that can be present on the corners of the cards
 */
public enum Artifact {
    feather(Ansi.ansi().fgYellow().a("Q").reset().toString()),//quill
    paper(Ansi.ansi().fgYellow().a("M").reset().toString()),//manuscript
    ink(Ansi.ansi().fgYellow().a("I").reset().toString())//inkwell
    ;

    private final String stringValue;
    Artifact(String symbol) {
        this.stringValue = symbol;
    }

    public String getStringValue(){return stringValue;}
}

