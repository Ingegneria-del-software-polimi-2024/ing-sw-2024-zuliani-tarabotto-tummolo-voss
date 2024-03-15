package main.model.cards;
import main.model.enums.Element;
import main.model.Points.Points;

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