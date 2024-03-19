package main.java.model.cards;
import main.java.model.enums.Element;
import main.java.model.Points.Points;

import java.util.ArrayList;
import java.util.List;

public class ResourceCard extends PlayableCard{
    private Element element;
    private Points pointsPolicy;

    public ResourceCard (char id) {
        // json parsing
    }

    @Override
    public int countPoints () {
        return this.pointsPolicy.count();
    }

    @Override
    public List<Element> getLockedElement() {
        List<Element> list = new ArrayList<Element>();
        list.add(element);
        return list;
    }
}