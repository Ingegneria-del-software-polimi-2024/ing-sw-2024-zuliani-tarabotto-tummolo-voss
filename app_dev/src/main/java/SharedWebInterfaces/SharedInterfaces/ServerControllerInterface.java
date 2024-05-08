package SharedWebInterfaces.SharedInterfaces;

public interface ServerControllerInterface extends GeneralAPI_Interface, ControllerInterface {
    public void playCard(int id, int x, int y, Boolean faceSide);


    public void initializeGameState();


    public void playStarterCard(boolean face, String player);

    public void chooseSecretObjective(String cardId, String player);

    public void drawCard(int cardSource);

}
