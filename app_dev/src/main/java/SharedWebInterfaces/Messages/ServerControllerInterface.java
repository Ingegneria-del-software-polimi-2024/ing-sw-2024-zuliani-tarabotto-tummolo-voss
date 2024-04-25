package SharedWebInterfaces.Messages;

import java.util.ArrayList;

public interface ServerControllerInterface extends GeneralAPI_Interface {
    public void playCard(int id, int x, int y);

    public void drawCard();

    public void initializeGameState();

    public void playStarterCard(boolean face, String player);


}
