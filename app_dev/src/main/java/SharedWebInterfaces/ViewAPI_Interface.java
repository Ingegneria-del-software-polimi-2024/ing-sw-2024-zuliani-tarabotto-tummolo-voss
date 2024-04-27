package SharedWebInterfaces;

import SharedWebInterfaces.Messages.GeneralAPI_Interface;

public interface ViewAPI_Interface extends GeneralAPI_Interface {

    public void setState(String state);

    public void setGoldDeck(int[] deck);
    public void setResourceDeck(int[] deck);
    public void setStarterDeck(int[] deck);
    public void setObjectiveDeck(int[] deck);
    public void setPlayers(String[] players);
    public void setGameId(String gameId);
    public void setStarterCard(int starterCard);
    public void chooseSecretObjective(int obj1, int obj2);
    // se player == client allora il secret objective del client è settato, sennò viene aggiornato quello del client corrispondente a player
    public void setSecretObjective(int secretObjective, String player);
}
