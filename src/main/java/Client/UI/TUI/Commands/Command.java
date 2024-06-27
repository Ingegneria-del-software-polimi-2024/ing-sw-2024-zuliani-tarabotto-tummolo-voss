package Client.UI.TUI.Commands;


/**
 * A class that allows to recognize text commands and act accordingly
 */
public interface Command {

    /**
     * method executed after the command is activated
     */
    void execute();

    /**
     * returns the command's name
     *
     * @return name
     */
    String getName();
}
