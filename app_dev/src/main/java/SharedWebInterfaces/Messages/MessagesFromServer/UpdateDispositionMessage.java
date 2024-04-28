package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.Messages.GeneralAPI_Interface;
import SharedWebInterfaces.Messages.ViewAPI_Interface;
import model.enums.Artifact;
import model.enums.Element;
import model.placementArea.Coordinates;

import java.util.HashMap;

public class UpdateDispositionMessage implements MessageFromServer{
    private String player;
    private int lastPlacedCard;
    private Coordinates coordinates;
    private boolean faceSide;
    private int points;
    private HashMap<Artifact, Integer> availableArtifacts;
    private HashMap<Element, Integer> availableElements;

    public UpdateDispositionMessage(String player, int lastPlacedCard, Coordinates coordinates,
                                    boolean faceSide, int points, HashMap<Artifact, Integer> availableArtifacts,
                                    HashMap<Element, Integer> availableElements) {
        this.player = player;
        this.lastPlacedCard = lastPlacedCard;
        this.coordinates = coordinates;
        this.faceSide = faceSide;
        this.points = points;
        this.availableArtifacts = availableArtifacts;
        this.availableElements = availableElements;
    }

    @Override
    public void execute(GeneralAPI_Interface api) {

    }

    @Override
    public void execute(ViewAPI_Interface view) {
        view.addCardToDisposition(lastPlacedCard, coordinates, faceSide);
        view.setPoints(points);
        view.updateArtifacts(availableArtifacts);
        view.updateElements(availableElements);
    }
}
