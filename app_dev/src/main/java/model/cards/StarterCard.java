package model.cards;
import java.util.List;
import model.enums.Element;

public class StarterCard {
    private List <Element> blockedElements;
    private Corner[] backFaceCorners;

    public void setBlockedElements(List<Element> blockedElements) {
        this.blockedElements = blockedElements;
    }

    public void setBackFaceCorners(Corner[] backFaceCorners) {
        this.backFaceCorners = backFaceCorners;


    }
}
