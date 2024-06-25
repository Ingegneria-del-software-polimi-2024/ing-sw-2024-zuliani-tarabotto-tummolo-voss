package Client.UI.TUI.Commands;

import Client.View.ViewAPI;

import javax.swing.text.View;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * The type Disposition command.
 */
public class DispositionCommand implements Command{
    private final String name = "--disp";
    private ViewAPI view;
    private Scanner s = new Scanner(System.in);
    private final int color = 226;

    /**
     * Instantiates a new Disposition command.
     *
     * @param view the view
     */
    public DispositionCommand(ViewAPI view) {
        this.view = view;
    }


    @Override
    public void execute() {
        if(!view.isGameStarted()){
            System.out.println("~> Can't print the disposition before the game starts");
            return;
        }

        System.out.print(ansi().fg(color).a("~> Enter the name of the player you want to check: (or press enter)\n").reset());
        String line = "";
        for(String p : view.getPlayers()){
            if(!p.equals(view.getPlayerId())){
                line += "  - " + p;
                line += "\n";
            }
        }
        System.out.println(line);
        String player = s.nextLine();

        while(!checkValidName(player)) {
            System.out.print(ansi().fg(color).a("~> The inserted nickname is not valid, please try a new one: (or press enter)\n").reset());
            System.out.println(line);
            player = s.nextLine();
        }

        if(player.isEmpty()) return;

        if(view.getDispositions().get(player) == null)
            return;
        view.getUi().printDisposition( view.getDispositions().get(player));
        System.out.print(ansi().fg(226).a("POINTS: "). reset() + String.valueOf(view.getPoints().get(player)) + "\n");
    }

    @Override
    public String getName() {
        return name;
    }

    private boolean checkValidName(String name){
        if(view.getPlayers().contains(name) || name.isEmpty()) return true;
        return false;
    }
}
