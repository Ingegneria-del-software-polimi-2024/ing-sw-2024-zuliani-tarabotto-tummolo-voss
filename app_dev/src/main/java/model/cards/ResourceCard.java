package model.cards;
import model.Points.Points;
import model.enums.Element;

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