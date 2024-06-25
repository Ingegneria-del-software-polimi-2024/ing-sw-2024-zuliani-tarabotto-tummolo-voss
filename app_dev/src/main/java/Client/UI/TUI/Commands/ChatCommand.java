package Client.UI.TUI.Commands;

import Client.UI.TUI.TUI;
import Client.View.ViewAPI;

/**
 * The type Chat command.
 * Contains the command to open the chat
 */
public class ChatCommand implements Command{
    /**
     * The name of the command
     */
    private final String name = "--chat";
    /**
     * The view API
     */
    private ViewAPI view;
    /**
     * The TUI
     */
    private TUI tui;

    /**
     * Instantiates a new Chat command.
     *
     * @param view the view
     * @param tui  the tui
     */
    public ChatCommand(ViewAPI view, TUI tui) {
        this.view = view;
        this.tui = tui;
    }

    @Override
    public void execute() {
        if(!view.isGameStarted()){
            System.out.println("Can't print the chat before the game starts");
            return;
        }

        tui.printChat();
    }

    @Override
    public String getName() {
        return name;
    }
}
