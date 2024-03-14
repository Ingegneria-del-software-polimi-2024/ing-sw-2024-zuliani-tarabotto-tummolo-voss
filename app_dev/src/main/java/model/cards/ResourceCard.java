package model.cards;
import model.Points.Points;
import model.enums.Element;

public class ResourceCard {
    private Element element;
    private Points pointsPolicy;

    public void setElement (Element element) {
        this.element = element;
    }

    public void setPoints (Points pointsPolicy) {
        this.pointsPolicy = pointsPolicy;
    }

    public int countPoints () {
        return this.pointsPolicy.count();
    }
}