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
