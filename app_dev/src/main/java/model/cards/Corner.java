package model.cards;
import model.enums.*;


public class Corner {
    private Element elemenent;
    private Artifact artifact;
    private boolean isAvailable;

    public Corner (Element element, Artifact artifact, boolean available) {
        this.elemenent = element;
        this.artifact = artifact;
        this.isAvailable = available;
    }
}
