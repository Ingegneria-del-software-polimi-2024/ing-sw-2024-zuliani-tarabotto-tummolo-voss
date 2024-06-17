package Client.UI.TUI.Commands;

import Client.UI.TUI.TUI;
import Client.View.ViewAPI;

public class ChatCommand implements Command{
    private final String name = "--chat";
    private ViewAPI view;
    private TUI tui;

    public ChatCommand(ViewAPI view, TUI tui) {
        this.view = view;
        this.tui = tui;
    }

    @Override
    public void execute() {

    }

    @Override
    public String getName() {
        return name;
    }
}
