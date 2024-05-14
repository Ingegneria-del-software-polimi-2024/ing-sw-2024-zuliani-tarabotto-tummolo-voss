package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Artifact;
import model.enums.Element;
import model.placementArea.Coordinates;

import java.util.HashMap;
import java.util.List;

//TODO must consider the possibility to notify also the other players of the change in the disposition
public class UpdateDispositionMessage implements MessageFromServer{
    private String player;
    private HashMap<Coordinates, PlayableCard> disposition;
    private int points;
    private List<Coordinates> availablePlaces;

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
