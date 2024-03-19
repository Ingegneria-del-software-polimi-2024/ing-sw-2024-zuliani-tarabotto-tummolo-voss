package main.java.model.deckFactory;
import main.java.model.enums.DeckType;
import java.text.MessageFormat;

public abstract class DeckGenerator {

    //decides which generateDeckBasedOnType() method to call based on the deckType parameter
    public static Deck generate (DeckType deckType) throws ClassNotFoundException {
        switch (deckType) {
            case STARTER -> {
                return new StarterCardsDeckGenerator().generateDeckBasedOnType();
            }
            case RESOURCE -> {
                return new ResourceCardsDeckGenerator().generateDeckBasedOnType();
            }
            case GOLD -> {
                return new GoldCardsDeckGenerator().generateDeckBasedOnType();
            }
            default -> {
                throw new ClassNotFoundException(MessageFormat.format(
                        "{0} is not an existing deck type.", deckType
                ));
            }
        }
    }


}

