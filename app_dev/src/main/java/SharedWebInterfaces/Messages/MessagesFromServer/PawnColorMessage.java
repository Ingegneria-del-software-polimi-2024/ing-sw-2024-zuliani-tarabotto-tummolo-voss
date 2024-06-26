package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

public class PawnColorMessage implements MessageFromServer {

    private String player;
    private String pawnColor;
    public PawnColorMessage(String player, String pawnColor){
        this.player = player;
        this.pawnColor = pawnColor;
    }
    @Override
    public void execute(ViewAPI_Interface view) {
        view.setPawnColor(player, pawnColor);
    }
}
