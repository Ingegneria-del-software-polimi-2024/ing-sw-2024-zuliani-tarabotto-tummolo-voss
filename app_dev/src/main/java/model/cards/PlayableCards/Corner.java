package model.cards.PlayableCards;
import com.fasterxml.jackson.annotation.JsonProperty;
import model.enums.Artifact;
import model.enums.Element;

import java.io.Serializable;


public class Corner implements Serializable {
    private int id;
    private Element element;
    private Artifact artifact;
    @JsonProperty("isAvailable")
    private boolean isAvailable;

    /**
     * class constructor that creates a void Corner
     */
    public Corner(){
        element = null;
        artifact = null;
        isAvailable = true;
    }

    /**
     * class constructor that creates a Corner containing an Element el
     * @param el Element
     */
    public Corner(Element el){
        artifact = null;
        element = el;
        isAvailable = true;
    }

    /**
     * returns true if the Corner does not contain neither an Element nor an Artifact
     * @return Boolean
     */
    public boolean isEmpty() {
        return element == null && artifact == null;
    }

    ///////////// GETTER METHODS /////////////////////////
    public int getId() { return id; }
    public void setId(int index) {this.id = index;}
    public Element getElement() { return element; }
    public Artifact getArtifact() { return artifact; }
    public boolean getIsAvailable(){
        return isAvailable;
    }
    public void setIsAvailable() {
        this.isAvailable = false;
    }
}