package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

/**
 * The type Pawn color message.
 * This message is used to set the color of the pawn of a player.
 */
public class PawnColorMessage implements MessageFromServer {
    /**
     * The Player's name.
     */
    private String player;
    /**
     * The Pawn color.
     */
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
