package Client.UI.TUI;

import Client.UI.UI;
import Client.View.ViewAPI;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Artifact;
import model.enums.Element;

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
    private DrawCardPrinter drawCardPrinter;
    private CardBuilder cb;
    private final int color = 226;

    public TUI(ViewAPI view) {

        this.view = view;
        cb = new CardBuilder();
        dispositionPrinter = new DispositionPrinter(cb);
        loginPrinter = new LoginPrinter();
        handPrinter = new HandPrinter();
        objectivesPrinter = new ObjectivesPrinter();
        drawCardPrinter = new DrawCardPrinter(cb);

    }


    @Override
    public void displayInitialization() {
        //TODO: ERASE SCREEN
        loginPrinter.print();
        boolean enterPressed = false;

        // Continuously check if Enter is pressed until it's pressed
        while (!enterPressed) {
            System.out.print(ansi().fg(color).a("~> Press Enter to continue...\n").reset());

            // Wait for the player to press Enter
            String input = sc.nextLine();

            // Check if the input is empty, meaning Enter was pressed
            if (input.isEmpty()) {
                enterPressed = true;
                //System.out.println("Enter key was pressed.");
                view.readyToPlay();
            } else {
                System.out.print(ansi().fg(color).a(" ~>Enter key was not pressed.\n").reset());
            }
        }
        System.out.print(ansi().fg(color).a("~> Waiting for other players to be ready\n").reset());

    }

    @Override
    public void displayStarterCardSelection() {
        //TODO: ERASE SCREEN
        System.out.print(ansi().fg(color).a("~> This is your StarterCard, choose a face side: (true/false)\n").reset());
        handPrinter.printStarterCard(view.getStarterCard());
        boolean faceSide = sc.nextBoolean();
        view.getStarterCard().setFaceSide(faceSide);
        view.playStarterCard();
    }

    @Override
    public void displayObjectiveSelection() {
        //TODO: ERASE SCREEN
        System.out.print(ansi().fg(color).a("~> This is your hand:\n").reset());
        handPrinter.print(view.getHand());

        System.out.print(ansi().fg(color).a("~> this are the two commonObjectives:\n").reset());
        objectivesPrinter.printCommonObjectives(view.getCommonObjectives().get(0),view.getCommonObjectives().get(1));

        System.out.print(ansi().fg(color).a("~> choose your secretObjective: (0/1)\n").reset());
        //we use the same function also to print the two objectives the player has to choose from
        objectivesPrinter.printCommonObjectives(view.getChooseSecretObjectives().get(0), view.getChooseSecretObjectives().get(1));

        view.setSecretObjective(view.getChooseSecretObjectives().get(sc.nextInt()));//TODO : correggere sto schifo

    }

    @Override
    public void displayPlacingCard() {
        //if it's myturn then i need to play a card, else i just wait
        //TODO: ERASE SCREEN
        if(view.getMyTurn()){
            dispositionPrinter.print(view.getDisposition(), view.getAvailablePlaces());
            handPrinter.print(view.getHand());
            printPlayerInformation();
            System.out.print(ansi().fg(color).a("~> Choose a card to place from your hand. You can place card: ").reset());
            String line = new String();
            if(view.getCanBePlaced()[0]) line = "1, ";
            if(view.getCanBePlaced()[1]) line += "2, ";
            if(view.getCanBePlaced()[2]) line += "3";
            System.out.print(ansi().fg(color).a("(" + line + ")\n").reset());
            PlayableCard c = view.getHand().get(sc.nextInt() - 1);

            System.out.print(ansi().fg(color).a("~> Select a face side for the card: (true/false)\n").reset());
            boolean faceSide = sc.nextBoolean();
            c.setFaceSide(faceSide);

            System.out.print(ansi().fg(color).a("~> Choose one of the available coordinates to place the card: (x/y)\n").reset());

            int x = sc.nextInt();
            int y = sc .nextInt();

            view.playCard(c, x, y);
            printPlayerInformation();
        }else{
            dispositionPrinter.print(view.getDisposition());
            handPrinter.print(view.getHand());
            printPlayerInformation();
        }
    }

    @Override
    public void displayCardDrawing() {
        //TODO: ERASE SCREEN

        if(view.getMyTurn()){
            dispositionPrinter.print(view.getDisposition(), view.getAvailablePlaces());
            handPrinter.print(view.getHand());
            printPlayerInformation();
            drawCardPrinter.print(view.getGoldDeck().get(0), view.getResourceDeck().get(0), view.getOpenGold(), view.getOpenResource());
            System.out.print(ansi().fg(color).a("~> Draw a card: (1/2/3/4/5/6)\n").reset());
            view.drawCard(sc.nextInt());
        }else{
            dispositionPrinter.print(view.getDisposition());
            handPrinter.print(view.getHand());
            printPlayerInformation();
        }

    }

    @Override
    public void displayCalculateObjectives() {
        //TODO: ERASE SCREEN

    }



    private void printPlayerInformation(){
        System.out.print(ansi().fg(color).a("POINTS: ").reset());
        System.out.print(ansi().a(view.getPoints().get(view.getPlayerId())+ "\n"));

        System.out.print(ansi().fg(color).a("ELEMENTS: \n").reset());
        for(Element e : view.getAvailableElements().keySet()){
            System.out.print(ansi().a("       " + e.getStringValue() + "->" + view.getAvailableElements().get(e)).reset());
        }

        System.out.println("\n");
        System.out.print(ansi().fg(color).a("ARTIFACTS: \n").reset());
        for(Artifact a : view.getAvailableArtifacts().keySet()){
            System.out.print(ansi().a("       " + a.getStringValue() + "->" + view.getAvailableArtifacts().get(a)).reset());
        }
        System.out.println("\n");
    }

}
