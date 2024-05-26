package Client.UI.TUI.Commands;

import Client.View.ViewAPI;
import Server.ModelController;

public class EndGameCommand implements Command{

    private final String name = "--end";
    private ViewAPI view;

    public EndGameCommand(ViewAPI view){
        this.view = view;
    }
    @Override
    public void execute() {
        view.endGame();
    }

    @Override
    public String getName() {
        return name;
    }
}
