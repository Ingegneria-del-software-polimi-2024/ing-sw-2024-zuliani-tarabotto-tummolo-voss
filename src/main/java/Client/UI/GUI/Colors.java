package Client.UI.GUI;

/**
 * associates pawnColor to the player's image icon
 */
public enum Colors {
    /**
     * Red colors.
     */
    red("/Images/playerIcon/red.jpeg"),
    /**
     * Purple colors.
     */
    purple("/Images/playerIcon/purple.jpeg"),
    /**
     * Blue colors.
     */
    blue("/Images/playerIcon/blue.jpeg"),
    /**
     * Green colors.
     */
    green("/Images/playerIcon/green.jpeg");


    private final String icon;
    Colors(String s) {
        this.icon = s;
    }


    /**
     * Get icon string.
     *
     * @return the string
     */
    public String getIcon(){
        return icon;
    }
}
