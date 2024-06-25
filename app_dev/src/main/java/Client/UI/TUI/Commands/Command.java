package Client.UI.TUI.Commands;


/**
 * interface for each command implemented in the TUI
 */
public interface Command {

    /**
     * method executed after the command is activated
     */
    void execute();

    /**
     * returns the command's name
     * @return
     */
    String getName();
}
