package Client.UI.GUI;

public enum Colors {
    RED("/Images/playerIcon/icon2.jpeg"),
    YELLOW("/Images/playerIcon/icon2.jpeg"),
    BLUE("/Images/playerIcon/icon2.jpeg"),
    GREEN("/Images/playerIcon/icon2.jpeg"),
    BLACK("/Images/playerIcon/icon2.jpeg");


    private final String icon;
    Colors(String s) {
        this.icon = s;
    }


    public String getIcon(){
        return icon;
    }
}
