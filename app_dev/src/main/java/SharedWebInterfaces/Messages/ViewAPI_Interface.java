package SharedWebInterfaces.Messages;

public interface ViewAPI_Interface extends GeneralAPI_Interface{

    void setState(String state);

    void setGoldDeck(int[] deck);
    void setResourceDeck(int[] deck);
    void setStarterDeck(int[] deck);
    void setObjectiveDeck(int[] deck);
    void setPlayers(String[] players);
    void setGameId(String gameId);
    void setStarterCard(int starterCard);
    void chooseSecretObjective(int obj1, int obj2);
    // se player == client allora il secret objective del client è settato, sennò viene aggiornato quello del client corrispondente a player
    void setSecretObjective(int secretObjective, String player);
}
