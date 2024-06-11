package Client.UI.GUI;

public enum Resources {
    mushrooms("/Images/icons/fungi.png"),
    paper("/Images/icons/manuscript.png"),
    vegetals("/Images/icons/plant.png"),
    ink("/Images/icons/inkwell.png"),
    animals("/Images/icons/animal.png"),
    feather("/Images/icons/feather.png"),
    insects("/Images/icons/insect.png");


    private final String img;
    Resources(String img){
        this.img = img;
    }

    public String getImg(){
        return img;
    }
}
