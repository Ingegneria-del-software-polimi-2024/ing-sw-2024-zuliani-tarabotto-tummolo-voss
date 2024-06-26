package Client.UI.TUI.Commands;

/**
 * The type Exit app command.
 * Contains the command to exit the app
 */
public class ExitAppCommand implements Command{

    /**
     * The name of the command
     */
    private final String name = "--exit";

    @Override
    public void execute() {
        System.exit(0);
    }

    @Override
    public String getName() {
        return name;
    }
}
