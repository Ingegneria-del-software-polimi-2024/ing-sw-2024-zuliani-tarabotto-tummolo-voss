package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.ViewAPI_Interface;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Artifact;
import model.enums.Element;
import model.placementArea.Coordinates;

import java.util.HashMap;

public class UpdateDispositionMessage implements MessageFromServer{
    private String player;
    private HashMap<Coordinates, PlayableCard> disposition;
    private int points;
    private HashMap<Artifact, Integer> availableArtifacts;
    private HashMap<Element, Integer> availableElements;

    public UpdateDispositionMessage(String player, HashMap<Coordinates, PlayableCard> disposition,
                                    int points, HashMap<Artifact, Integer> availableArtifacts,
                                    HashMap<Element, Integer> availableElements) {
        this.player = player;
        this.disposition = disposition;
        this.points = points;
        this.availableArtifacts = availableArtifacts;
        this.availableElements = availableElements;
    }


    @Override
    public void execute(ViewAPI_Interface view) {
        view.setDisposition(disposition);
        view.setPoints(points);
        view.updateArtifacts(availableArtifacts);
        view.updateElements(availableElements);
    }
}
