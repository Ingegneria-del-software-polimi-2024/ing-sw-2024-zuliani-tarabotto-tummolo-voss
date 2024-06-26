package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelTranslator;
import SharedWebInterfaces.SharedInterfaces.Traslator;

/**
 * The type Play starter card message.
 * This message is sent from the view to the model controller to play the starter card.
 * It contains the face of the card and the player id because the card is univocally assigned to the player.
 */
public class PlayStarterCardMessage implements MessageFromViewToModelController {
    /**
     * The Starter card face.
     */
    private boolean starterCardFace;
    /**
     * The Player id.
     */
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
