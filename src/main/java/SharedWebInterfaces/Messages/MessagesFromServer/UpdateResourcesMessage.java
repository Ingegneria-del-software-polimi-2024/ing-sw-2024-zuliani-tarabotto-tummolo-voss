package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.enums.Artifact;
import model.enums.Element;

import java.util.HashMap;

/**
 * The type Update resources message.
 * This message is used to notify the clients of the new resources of a player.
 * It is used after a player has placed a card from the hand.
 */
public class UpdateResourcesMessage implements MessageFromServer {
    /**
     * The Available elements of the player.
     */
    private HashMap<Element, Integer> availableElements;
    /**
     * The Available artifacts of the player.
     */
    private HashMap<Artifact, Integer> availableArtifacts;
    /**
     * The player of which the resources change.
     */
    private String player;

    /**
     * Instantiates a new Update resources message.
     *
     * @param player             the player
     * @param availableElements  the available elements
     * @param availableArtifacts the available artifacts
     */
    public UpdateResourcesMessage(String player, HashMap<Element, Integer> availableElements, HashMap<Artifact, Integer> availableArtifacts){
        this.availableElements = availableElements;
        this.availableArtifacts = availableArtifacts;
        this.player = player;
    }
    @Override
    public void execute(ViewAPI_Interface view) {
        view.updateElements(player, availableElements);
        view.updateArtifacts(player, availableArtifacts);
        view.updateResourcesInUI();
    }
}
