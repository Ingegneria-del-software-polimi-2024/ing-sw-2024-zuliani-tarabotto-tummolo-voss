package main.java.model.cards;
import java.util.List;
import main.java.model.enums.Element;

public class StarterCard extends PlayableCard{
    private List <Element> lockedElements;
    private Corner[] backFaceCorners;

    public StarterCard (char id) {
        //json parsing
    }

    public Corner getBackFaceCorner(int index) {
        return backFaceCorners[index];
    }

    @Override
    public int countPoints() { return 0;}

    @Override
    public List<Element> getLockedElement() {
        return lockedElements;
    }
}
