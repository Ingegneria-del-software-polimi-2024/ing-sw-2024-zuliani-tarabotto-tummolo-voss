package Client.UI.TUI.Commands;

import Client.View.ViewAPI;

public class EndGameCommand implements Command{

    private final String name = "--quit";
    private ViewAPI view;

    public EndGameCommand(ViewAPI view){
        this.view = view;
    }
    @Override
    public void execute() {
        view.quitGame();
    }

    @Override
    public String getName() {
        return name;
    }
}
