package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

/**
 * The type Pawn color message.
 */
public class PawnColorMessage implements MessageFromServer {

    private String player;
    private String pawnColor;

    /**
     * Instantiates a new Pawn color message.
     *
     * @param player    the player
     * @param pawnColor the pawn color
     */
    public PawnColorMessage(String player, String pawnColor){
        this.player = player;
        this.pawnColor = pawnColor;
    }
    @Override
    public void execute(ViewAPI_Interface view) {
        view.setPawnColor(player, pawnColor);
    }
}
