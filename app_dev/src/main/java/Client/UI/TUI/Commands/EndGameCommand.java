package Client.UI.TUI.Commands;

import Client.UI.TUI.TUI;
import Client.View.ViewAPI;

/**
 * The type End game command.
 */
public class EndGameCommand implements Command{

    private final String name = "--quit";
    private ViewAPI view;
    private TUI tui;

    /**
     * Instantiates a new End game command.
     *
     * @param view the view
     * @param tui  the tui
     */
    public EndGameCommand(ViewAPI view, TUI tui){
        this.view = view;
        this.tui = tui;
    }
    @Override
    public void execute() {
        if(tui.getInWaitingRoom()){
            tui.setInWaitingRoom(false);
            view.quitGame(tui.getGame());
        } else{ view.quitGame();}
    }

    @Override
    public String getName() {
        return name;
    }
}
