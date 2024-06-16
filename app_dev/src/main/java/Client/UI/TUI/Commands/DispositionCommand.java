package Client.UI.TUI.Commands;

import Client.View.ViewAPI;

import javax.swing.text.View;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class DispositionCommand implements Command{
    private final String name = "--disp";
    private ViewAPI view;
    private Scanner s = new Scanner(System.in);

    public DispositionCommand(ViewAPI view) {
        this.view = view;
    }


    @Override
    public void execute() {
        System.out.println("Enter the name of the player you want to check: ");
        String line = "";
        for(String p : view.getPlayers()){
            if(!p.equals(view.getPlayerId())){
                line += p;
                line += "\n";
            }
        }
        System.out.println(line);
        String player = s.nextLine();

        view.getUi().printDisposition( view.getDispositions().get(player));
        System.out.print(ansi().fg(226).a("POINTS: "). reset() + String.valueOf(view.getPoints().get(player)) + "\n");
    }

    @Override
    public String getName() {
        return name;
    }
}
