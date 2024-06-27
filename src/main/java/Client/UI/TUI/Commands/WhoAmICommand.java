package Client.UI.TUI.Commands;

import Client.UI.TUI.TUI;

/**
 * The type Who am I command.
 * Contains the command to get the name of the player
 */
public class WhoAmICommand implements Command{
    /**
     * The TUI
     */
    private TUI tui;

    /**
     * Instantiates a new Who am I command.
     *
     * @param tui the tui
     */
    public WhoAmICommand(TUI tui) {
        this.tui = tui;
    }
    /**
     * The name of the command
     */
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
