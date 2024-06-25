package Client.UI.TUI.Commands;

import Client.UI.TUI.TUI;

public class WhoAmICommand implements Command{
    private TUI tui;

    public WhoAmICommand(TUI tui) {
        this.tui = tui;
    }

    private final String name = "--whoami";

    @Override
    public void execute() {
        tui.printName();
    }

    @Override
    public String getName() {
        return name;
    }
}
