package Client.View;

public class CardApi {
    private int id;
    private boolean face;
    private boolean placeable;



    public CardApi(int id, boolean face, boolean placeable){
        this.id = id;
        this.face = face;
        this.placeable = placeable;
    }

    public int getId() { return id; }
    public boolean getFace() { return face; }
    public void setId(int id) { this.id = id;}
    public void setFace(boolean faceSide) {this.face = faceSide;}

    public boolean getPlaceable() { return placeable; }
    public void setPlaceable(boolean placeable) {this.placeable = placeable;}

}
