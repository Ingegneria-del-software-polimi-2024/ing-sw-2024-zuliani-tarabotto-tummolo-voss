package Client.View;

public class CardApi {
    private String id;
    private boolean face;


    public CardApi(String id, boolean face){
        this.id = id;
        this.face = face;
    }

    public String getId() { return id; }
    public boolean getFace() { return face; }
}
