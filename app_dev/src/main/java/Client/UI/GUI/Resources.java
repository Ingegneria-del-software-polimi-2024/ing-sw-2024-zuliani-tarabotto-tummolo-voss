package Client.UI.GUI;

public enum Resources {
    mushrooms("/Images/fungi.png"),
    paper("/Images/manuscript.png"),
    vegetals("/Images/plant.png"),
    ink("/Images/inkwell.png"),
    animals("/Images/animal.png"),
    feather("/Images/feather.png"),
    insects("/Images/insect.png");


    private final String img;
    Resources(String img){
        this.img = img;
    }

    public String getImg(){
        return img;
    }
}
