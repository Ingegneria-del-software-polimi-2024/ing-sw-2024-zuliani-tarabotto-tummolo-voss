package Client.UI.TUI.Commands;

public class HelpCommand implements Command{

    private final String name = "--help";
    @Override
    public void execute() {
        System.out.println("Il giocatore sceglie una delle 3 carte dalla mano e la colloca nella propria area di gioco in base al disegno e nel rispetto delle seguenti 2 regole:\n" +
                "Regola di Piazzamento\n" +
                "La carta deve coprire almeno uno degli angoli visibili delle carte già presenti nell’area di gioco, ma senza coprirne più di uno di una stessa carta.");
    }

    @Override
    public String getName() {
        return name;
    }
}
