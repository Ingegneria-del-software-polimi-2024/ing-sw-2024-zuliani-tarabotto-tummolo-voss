package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Artifact;
import model.enums.Element;
import model.placementArea.Coordinates;

import java.util.HashMap;
import java.util.List;

public class UpdateDispositionMessage implements MessageFromServer{
    private String player;
    private HashMap<Coordinates, PlayableCard> disposition;
    private int points;
    private HashMap<Artifact, Integer> availableArtifacts;
    private HashMap<Element, Integer> availableElements;
    private List<Coordinates> availablePlaces;

    public UpdateDispositionMessage(String player, HashMap<Coordinates, PlayableCard> disposition, List<Coordinates> availablePlaces,
                                    int points, HashMap<Artifact, Integer> availableArtifacts,
                                    HashMap<Element, Integer> availableElements) {
        this.player = player;
        this.disposition = disposition;
        this.points = points;
        this.availableArtifacts = availableArtifacts;
        this.availableElements = availableElements;
        this.availablePlaces = availablePlaces;
    }


    @Override
    public void execute(ViewAPI_Interface view) {
        view.setDisposition(player, disposition);
        view.setPoints(player, points);
        view.updateArtifacts(availableArtifacts);
        view.updateElements(availableElements);
        view.setAvailablePlaces(availablePlaces);
    }
}
