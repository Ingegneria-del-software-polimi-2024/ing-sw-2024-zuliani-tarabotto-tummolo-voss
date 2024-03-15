package main.java.model.cards;
import main.java.model.enums.Element;
import main.java.model.Points.Points;

public class ResourceCard {
    private Element element;
    private Points pointsPolicy;

    public ResourceCard (char id) {
        // json parsing
    }

    public int countPoints () {
        return this.pointsPolicy.count();
    }
}