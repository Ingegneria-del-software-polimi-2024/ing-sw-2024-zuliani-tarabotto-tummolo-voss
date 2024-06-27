package Client.UI.GUI;

/**
 * The enum Resources.
 */
public enum Resources {
    /**
     * associates to each resource a png
     */
    mushrooms("/Images/icons/fungi.png"),
    /**
     * Paper resources.
     */
    paper("/Images/icons/manuscript.png"),
    /**
     * Vegetals resources.
     */
    vegetals("/Images/icons/plant.png"),
    /**
     * Ink resources.
     */
    ink("/Images/icons/inkwell.png"),
    /**
     * Animals resources.
     */
    animals("/Images/icons/animal.png"),
    /**
     * Feather resources.
     */
    feather("/Images/icons/feather.png"),
    /**
     * Insects resources.
     */
    insects("/Images/icons/insect.png");


    private final String img;
    Resources(String img){
        this.img = img;
    }

    /**
     * Get img string.
     *
     * @return the string
     */
    public String getImg(){
        return img;
    }
}
