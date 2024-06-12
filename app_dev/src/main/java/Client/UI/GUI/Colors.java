package Client.UI.GUI;

public enum Colors {
    red("/Images/playerIcon/red.jpeg"),
    purple("/Images/playerIcon/purple.jpeg"),
    blue("/Images/playerIcon/blue.jpeg"),
    green("/Images/playerIcon/green.jpeg");


    private final String icon;
    Colors(String s) {
        this.icon = s;
    }


    public String getIcon(){
        return icon;
    }
}
