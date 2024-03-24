package model.cards.PlayableCards;
import com.fasterxml.jackson.annotation.JsonProperty;
import model.enums.Artifact;
import model.enums.Element;


public class Corner {
    private int id;
    private Element element;
    private Artifact artifact;
    @JsonProperty("isAvailable")
    private boolean isAvailable;

    public int getId() {
        return id;
    }


    public Element getElement() {
        return element;
    }


    public Artifact getArtifact() {
        return artifact;
    }

    public boolean isAvailable() { //da eliminare rimpiazzato con is availible
        return isAvailable;
    }


    public boolean isEmpty() {
            return isAvailable;

    }

    //constructor that creates a void corner
    public Corner(){
        element = null;
        artifact = null;
        isAvailable = true;
    }

    public Corner(Element el){
        artifact = null;
        element = el;
        isAvailable = true;
    }
}