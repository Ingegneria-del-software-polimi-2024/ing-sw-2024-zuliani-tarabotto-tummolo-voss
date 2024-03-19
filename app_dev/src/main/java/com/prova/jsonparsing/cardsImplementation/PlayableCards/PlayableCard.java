package com.prova.jsonparsing.cardsImplementation.PlayableCards;

import com.prova.jsonparsing.cardsImplementation.Card;

import java.util.Collections;
import java.util.List;

public abstract class PlayableCard implements Card {
    private String type;//utile per parsing
    protected int id;
    protected boolean faceSide;
    protected List<Corner> corners;

    public List<Corner> getCorners() {
        return Collections.unmodifiableList(corners);
    }
    public boolean isFaceSide() {
        return faceSide;
    }

    public String getType() {
        return type;
    }
    public int getId() {
        return id;
    }


}
