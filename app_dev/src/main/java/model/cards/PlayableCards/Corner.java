package model.cards.PlayableCards;
import com.fasterxml.jackson.annotation.JsonProperty;
import model.enums.Artifact;
import model.enums.Element;

import java.io.Serializable;

/**
 * Represents a corner in a playable card, which can contain an element or an artifact.
 * It also has an availability status which indicates id the corner is free or not.
 */
public class Corner implements Serializable {
    /**
     * The ID of the corner.
     */
    private int id;

    /**
     * The element contained in the corner.
     */
    private Element element;

    /**
     * The artifact contained in the corner.
     */
    private Artifact artifact;

    /**
     * Indicates whether the corner is available.
     */
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
     *
     * @param el Element
     */
    public Corner(Element el){
        artifact = null;
        element = el;
        isAvailable = true;
    }

    /**
     * returns true if the Corner does not contain neither an Element nor an Artifact
     *
     * @return Boolean boolean
     */
    public boolean isEmpty() {
        return element == null && artifact == null;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
///////////// GETTER METHODS /////////////////////////
    public int getId() { return id; }

    /**
     * Sets id.
     *
     * @param index the index
     */
    public void setId(int index) {this.id = index;}

    /**
     * Gets element.
     *
     * @return the element
     */
    public Element getElement() { return element; }

    /**
     * Gets artifact.
     *
     * @return the artifact
     */
    public Artifact getArtifact() { return artifact; }

    /**
     * Get is available boolean.
     *
     * @return the boolean
     */
    public boolean getIsAvailable(){
        return isAvailable;
    }

    /**
     * Sets is available.
     */
    public void setIsAvailable() {
        this.isAvailable = false;
    }
}