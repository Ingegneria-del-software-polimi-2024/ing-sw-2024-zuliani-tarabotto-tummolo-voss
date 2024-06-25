package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelTranslator;
import SharedWebInterfaces.SharedInterfaces.Traslator;

/**
 * The type Play starter card message.
 */
public class PlayStarterCardMessage implements MessageFromViewToModelController {
    private boolean starterCardFace;
    private String playerId;

    /**
     * Instantiates a new Play starter card message.
     *
     * @param starterCardFace the starter card face
     * @param playerId        the player id
     */
    public PlayStarterCardMessage(boolean starterCardFace, String playerId){
        this.starterCardFace = starterCardFace;
        this.playerId = playerId;
    }

    @Override
    public void execute(ModelTranslator controller) {
        controller.playStarterCard(starterCardFace, playerId);
    }

    //ignore this
    @Override
    public void execute(Traslator controller) {

    }
}
