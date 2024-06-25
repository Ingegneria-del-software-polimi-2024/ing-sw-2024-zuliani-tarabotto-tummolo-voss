package Client.UI.TUI.Commands;

import Client.UI.TUI.TUI;

/**
 * The type Who am i command.
 */
public class WhoAmICommand implements Command{
    private TUI tui;

    /**
     * Instantiates a new Who am i command.
     *
     * @param tui the tui
     */
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
