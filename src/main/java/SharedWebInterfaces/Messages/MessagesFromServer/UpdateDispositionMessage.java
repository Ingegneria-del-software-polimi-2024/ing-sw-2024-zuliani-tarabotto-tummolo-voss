package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Artifact;
import model.enums.Element;
import model.placementArea.Coordinates;

import java.util.HashMap;
import java.util.List;

/**
 * The type Update disposition message.
 * This message is used to notify the clients of the new disposition of a player.
 */
public class UpdateDispositionMessage implements MessageFromServer{
    /**
     * The player of which the disposition changes.
     */
    private String player;
    /**
     * The new disposition of the player.
     */
    private HashMap<Coordinates, PlayableCard> disposition;
    /**
     * The points of the player.
     */
    private int points;
    /**
     * The available places where a card can be placed.
     */
    private List<Coordinates> availablePlaces;

    /**
     * Instantiates a new Update disposition message.
     *
     * @param player          the player
     * @param disposition     the disposition
     * @param availablePlaces the available places
     * @param points          the points
     */
    public UpdateDispositionMessage(String player, HashMap<Coordinates, PlayableCard> disposition,
                                    List<Coordinates> availablePlaces,
                                    int points) {
        this.player = player;
        this.disposition = disposition;
        this.points = points;
        this.availablePlaces = availablePlaces;
    }


    /**
     * Sets a disposition on the client receiving the message
     * @param view the view interface of the receiver
     */
    @Override
    public void execute(ViewAPI_Interface view) {
        view.setDisposition(player, disposition);
        view.setPoints(player, points);
        view.setAvailablePlaces(availablePlaces);
    }
}
