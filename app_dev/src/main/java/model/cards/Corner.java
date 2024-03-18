package main.java.model.cards;
import main.java.model.enums.*;


public class Corner {
    private Element element;
    private Artifact artifact;
    private boolean isAvailable;

    public Corner (Element element, Artifact artifact, boolean isAvailable) {
        this.element = element;
        this.artifact = artifact;
        this.isAvailable = isAvailable;
    }

    public boolean isEmpty() {
        if ( element == null && artifact == null) {
            return true;
        } else {return false;}
    }
    public Element getElement() {
        return element;
    }

    public Artifact getArtifact() {
        return artifact;
    }
 }
