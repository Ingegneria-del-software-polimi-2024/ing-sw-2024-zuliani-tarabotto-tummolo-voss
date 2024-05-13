package Client.UI.TUI;

import Client.UI.UI;
import Client.View.ViewAPI;

import javax.swing.text.View;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class TUI implements UI {

    private ViewAPI view;
    private DispositionPrinter dispositionPrinter;
    private LoginPrinter loginPrinter;
    private Scanner sc = new Scanner(System.in);
    private HandPrinter handPrinter;
    private ObjectivesPrinter objectivesPrinter;
    private final int color = 221;

    public TUI(ViewAPI view) {
        this.view = view;
        dispositionPrinter = new DispositionPrinter();
        loginPrinter = new LoginPrinter();
        handPrinter = new HandPrinter();
        objectivesPrinter = new ObjectivesPrinter();
    }


    @Override
    public void displayInitialization() {
        loginPrinter.print();
        boolean enterPressed = false;

        // Continuously check if Enter is pressed until it's pressed
        while (!enterPressed) {
            System.out.print(ansi().fg(color).a("~> Press Enter to continue...").reset());

            // Wait for the player to press Enter
            String input = sc.nextLine();

            // Check if the input is empty, meaning Enter was pressed
            if (input.isEmpty()) {
                enterPressed = true;
                //System.out.println("Enter key was pressed.");
                view.readyToPlay();
            } else {
                System.out.print(ansi().fg(color).a(" ~>Enter key was not pressed.").reset());
            }
        }
        System.out.print(ansi().fg(color).a("~> Waiting for other players to be ready").reset());

    }

    @Override
    public void displayStarterCardSelection() {
        System.out.print(ansi().fg(color).a("~> This is your StarterCard, choose a face side:").reset());
        handPrinter.printStarterCard(view.getStarterCard());
        boolean faceSide = sc.nextBoolean();
        view.getStarterCard().setFaceSide(faceSide);
        view.playStarterCard();
    }

    @Override
    public void displayObjectiveSelection() {
        System.out.print(ansi().fg(color).a("~> This is your hand:").reset());
        handPrinter.print(view.getHand());
        System.out.print(ansi().fg(color).a("~> this are the two commonObjectives").reset());
        objectivesPrinter.printCommonObjectives(view.getCommonObjectives().get(0),view.getCommonObjectives().get(1));
        System.out.print(ansi().fg(color).a("~> choose your secretObjective(0/1)").reset());
        objectivesPrinter.printCommonObjectives(view.getChooseSecretObjectives().get(0), view.getChooseSecretObjectives().get(1));
        view.setSecretObjective(view.getChooseSecretObjectives().get(sc.nextInt()));//TODO : correggere sto schifo

    }

    @Override
    public void displayPlacingCard() {
        if(view.getMyTurn()){
            dispositionPrinter.print(view.getDisposition(), view.getAvailablePlaces());
        }else{
            dispositionPrinter.print(view.getDisposition());
        }
    }

    @Override
    public void displayCardDrawing() {

    }

    @Override
    public void displayCalculateObjectives() {

    }

}
