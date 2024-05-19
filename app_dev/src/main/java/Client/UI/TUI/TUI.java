package Client.UI.TUI;

import Client.UI.TUI.Commands.DispositionCommand;
import Client.UI.TUI.Commands.HelpCommand;
import Client.UI.UI;
import Client.UI.exceptions.InvalidInputException;
import Client.View.ViewAPI;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Artifact;
import model.enums.Element;

import java.util.*;

import static org.fusesource.jansi.Ansi.ansi;
import Client.UI.TUI.Commands.Command;
import model.placementArea.Coordinates;
import org.fusesource.jansi.Ansi;

public class TUI implements UI{

    private ViewAPI view;
    private DispositionPrinter dispositionPrinter;
    private LoginPrinter loginPrinter;
    private Scanner sc;
    private HandPrinter handPrinter;
    private ObjectivesPrinter objectivesPrinter;
    private DrawCardPrinter drawCardPrinter;
    private CardBuilder cb;
    private final int color = 226;
    private String input;
    private volatile boolean inputPresent = false;
    private final Object lock;
    private final HashMap<String, Command> commandMap;
    private Runnable rePrint;
    private PlayableCard c = null;
    boolean enterPressed = false;

    public TUI(ViewAPI view){

        this.view = view;
        cb = new CardBuilder();
        dispositionPrinter = new DispositionPrinter(cb);
        loginPrinter = new LoginPrinter();
        handPrinter = new HandPrinter();
        objectivesPrinter = new ObjectivesPrinter();
        drawCardPrinter = new DrawCardPrinter(cb);
        lock = new Object();

        this.commandMap = new HashMap<>();
        commandMap.put("--help", new HelpCommand());
        commandMap.put("--disp", new DispositionCommand(view));

    }


    @Override
    public void displayInitialization() {
        clear();
        loginPrinter.print();
        view.startUI();

        // Continuously check if Enter is pressed until it's pressed
        while (!enterPressed) {
            System.out.print(ansi().fg(color).a("~> Press Enter to continue...\n").reset());

            synchronized (lock) {
                while (!inputPresent) {
                    try {
                       lock.wait();
                    } catch (InterruptedException e) {
                    }
                }

                // Wait for the player to press Enter
                //String in = sc.nextLine();
                String in = input;
                // Check if the input is empty, meaning Enter was pressed
                if (in.isEmpty()) {
                    enterPressed = true;
                    //System.out.println("Enter key was pressed.");
                    view.readyToPlay();
                } else {
                    System.out.print(ansi().fg(color).a(" ~> Enter key was not pressed.\n").reset());
                }
                inputPresent = false;
                lock.notifyAll();
            }
        }
        rePrint = () -> {
            clear();
            loginPrinter.print();
            System.out.print(ansi().fg(color).a("~> Waiting for other players to be ready\n").reset());
        };
        rePrint.run();

    }

    @Override
    public void displayStarterCardSelection() {

        rePrint = () -> {
            clear();
            handPrinter.printStarterCard(view.getStarterCard());
            System.out.print(ansi().fg(color).a("~> This is your StarterCard, choose a face side: (true/false)\n").reset());
        };
        rePrint.run();


        //we read from input the line and parse it as boolean
        synchronized (lock){
            boolean faceSide = parseBoolean();
            view.getStarterCard().setFaceSide(faceSide);
            view.playStarterCard();
            inputPresent = false;
            lock.notifyAll();
        }
        rePrint = () -> {
            clear();
            System.out.print(ansi().fg(color).a("~> This is your StarterCard: \n").reset());
            handPrinter.printStarterCard(view.getStarterCard());
        };
        rePrint.run();
    }

    @Override
    public void displayObjectiveSelection() {

        rePrint = () -> {
            clear();
            System.out.print(ansi().fg(color).a("~> This is your hand:\n").reset());
            handPrinter.print(view.getHand());
            System.out.print(ansi().fg(color).a("~> this are the two commonObjectives:\n").reset());
            objectivesPrinter.printCommonObjectives(view.getCommonObjectives().get(0),view.getCommonObjectives().get(1));

            System.out.print(ansi().fg(color).a("~> choose your secretObjective: (0/1)\n").reset());
            //we use the same function also to print the two objectives the player has to choose from
            objectivesPrinter.printCommonObjectives(view.getChooseSecretObjectives().get(0), view.getChooseSecretObjectives().get(1));
        };
        rePrint.run();

        synchronized (lock){
            Integer secretObj = parseInt();
            while(!secretObj.equals(0)  && !secretObj.equals(1)){
                System.out.println("please insert 0/1");
                secretObj = parseInt();
            }
            view.setSecretObjective(view.getChooseSecretObjectives().get(secretObj));
            inputPresent = false;
            lock.notifyAll();
        }


        rePrint = () -> {
            clear();
            System.out.print(ansi().fg(color).a("~> These are your objectives\n").reset());
            objectivesPrinter.getObjField(view.getCommonObjectives().get(0), view.getCommonObjectives().get(1), view.getSecretObjective());
        };
        rePrint.run();


    }

    @Override
    public void displayPlacingCard() {
        int index;
        int x = 0;
        int y = 0;
        //if it's myturn then i need to play a card, else i just wait
        if(view.getMyTurn()) {

            // we choose which card we want to place
            rePrint = () -> {
                clear();
                dispositionPrinter.print(view.getDisposition(), view.getAvailablePlaces());
                ultimatePrint(view);
                System.out.print(ansi().fg(color).a("~> Choose a card to place from your hand (1/2/3): ").reset());
            };
            rePrint.run();

            synchronized (lock){
                index = parseInt();
                while(index != 1 && index != 2 && index != 3 ){
                    System.out.println("please insert 1/2/3");
                    index = parseInt();
                }
                c = view.getHand().get(index - 1);
                inputPresent = false;
                lock.notifyAll();
            }

            //we select the face for the card
            rePrint = () -> {
                clear();
                dispositionPrinter.print(view.getDisposition(), view.getAvailablePlaces());
                ultimatePrint(view);
                System.out.print(ansi().fg(color).a("~> Select a face side for the card: (true/false)\n").reset());
            };
            rePrint.run();

            synchronized (lock){
                boolean faceSide = parseBoolean();
                if(faceSide == true && !view.getCanBePlaced()[index - 1]){
                    System.out.println("Sorry, card " + index + "can't be placed face up due to its placement constraint");
                    faceSide = false;
                }
                c.setFaceSide(faceSide);
                inputPresent = false;
                lock.notifyAll();
            }


            // we choose the coordinates where we want to place the card
            rePrint = () -> {
                clear();
                dispositionPrinter.print(view.getDisposition(), view.getAvailablePlaces());
                ultimatePrint(view);
                System.out.print(ansi().fg(color).a("~> Choose one of the available coordinates to place the card: (x/y)\n").reset());
            };
            rePrint.run();


            while (!view.checkAvailable(x, y)){
                synchronized (lock) {
                    x = parseInt();
                    inputPresent = false;
                    lock.notifyAll();
                }
                synchronized (lock){
                    y = parseInt();
                    //view.playCard(c, x, y);
                    inputPresent = false;
                    lock.notifyAll();
                }
                if(!view.checkAvailable(x, y)) System.out.println("These coordinates are not available, please choose some valid ones");
            }
            view.playCard(c, x, y);

        }else{
            rePrint = () -> {
                clear();
                dispositionPrinter.print(view.getDisposition());
                ultimatePrint(view);
                System.out.print(ansi().fg(color).a("~> " + view.getTurnPlayer() + " is placing a card\n").reset());
            };
            rePrint.run();
        }

    }

    @Override
    public void displayCardDrawing() {

        if(view.getMyTurn()){

            rePrint= () -> {
                clear();
                drawCardPrinter.print(view.getGoldDeck().get(0), view.getResourceDeck().get(0), view.getOpenGold(), view.getOpenResource());
                System.out.print(ansi().fg(color).a("~> Draw a card: (1/2/3/4/5/6)\n").reset());

            };
            rePrint.run();


            synchronized (lock){
                int cardSource = parseInt();
                while(!view.checkCanDrawFrom(cardSource)){
                    System.out.println("This card source is not available, choose a valid one");
                    cardSource = parseInt();
                }
                view.drawCard(cardSource);
                inputPresent = false;
                lock.notifyAll();
            }
        }else{
            rePrint = () -> {
                clear();
                dispositionPrinter.print(view.getDisposition());
                ultimatePrint(view);
                System.out.print(ansi().fg(color).a("~> " + view.getTurnPlayer() + " is drawing a card\n").reset());

            };
        }

    }

    @Override
    public void displayEndGame() {
        clear();
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(view.getPoints().entrySet());
        // Step 2: Sort the list with a comparator that compares the values in descending order
        entryList.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        System.out.print(ansi().fg(221).a(entryList.get(0).getKey().toUpperCase() + "is the winner !!!\n"));

        for(int i = 0; i < entryList.size(); i ++){
            System.out.println("-> " + entryList.get(i).getKey() + ": " + entryList.get(i).getValue() + "points");
        }
    }



    private List<String> getInfoField(){
        List<String> infoField = new ArrayList<>();

        infoField.add("\u2554\u2550\u2550\u2550"+ ansi().fg(color).bold().a(" RESOURCES ").reset() + "\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557");
        infoField.add("\u2551                                                                   \u2551");
        infoField.add("\u2551   " + ansi().fg(color).a("POINTS:         ").reset() + buildStat(view.getPoints().get(view.getPlayerId())) + "                                              \u2551");
        infoField.add("\u2551   " + ansi().fg(color).a("ELEMENTS:").reset() + ansi().a("       " + Element.mushrooms.getStringValue() + "->" +buildStat(view.getAvailableElements().get(Element.mushrooms))).reset()  + ansi().a("       " + Element.animals.getStringValue() + "->" + buildStat( view.getAvailableElements().get(Element.animals))).reset() + ansi().a("       " + Element.vegetals.getStringValue() + "->" + buildStat(view.getAvailableElements().get(Element.vegetals))).reset() + ansi().a("       " + Element.insects.getStringValue() + "->" + buildStat(view.getAvailableElements().get(Element.insects))).reset() + "       \u2551");
        infoField.add("\u2551   " + ansi().fg(color).a("ARTIFACTS:").reset() + ansi().a("      " + Artifact.feather.getStringValue() + "->" + buildStat(view.getAvailableArtifacts().get(Artifact.feather))).reset() + ansi().a("       " + Artifact.paper.getStringValue() + "->" + buildStat(view.getAvailableArtifacts().get(Artifact.paper))).reset() + ansi().a("       " + Artifact.ink.getStringValue() + "->" + buildStat(view.getAvailableArtifacts().get(Artifact.ink))).reset() + "                   \u2551");
        infoField.add("\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D");

        return infoField;
    }

    private String buildStat(int stat){
        if(stat < 10) return stat + " ";
        else return String.valueOf(stat);
    }

    private void ultimatePrint(ViewAPI view){
        List<String> handField = handPrinter.getHandField(view.getHand());
        List<String> objFiel = objectivesPrinter.getObjField(view.getCommonObjectives().get(0), view.getCommonObjectives().get(1), view.getSecretObjective());
        List<String> infoField = getInfoField();
        for(int i = 0; i < 12; i++){
            System.out.println(handField.get(i) + objFiel.get(i));
        }
        for(int i = 12; i < 18 ; i++){
            System.out.println(handField.get(i) + infoField.get(i - 12));
        }
    }


    @Override
    public void run(){
        sc = new Scanner(System.in);

        while (true) {

            synchronized (lock) {
                while (inputPresent) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {}
                }

                System.out.println("command thread");
                input = sc.nextLine();

                if (input.startsWith("--") && !input.equals("")) {
                    clear();
                    commandMap.get(input).execute();
                    //System.out.println("command");
                    //System.out.println("type q to go back to the game");
                    while(!sc.nextLine().equals("q")){
                        System.out.println("type q to go back to the game");
                    }
                    rePrint.run();
                } else {
                    inputPresent = true;
                    lock.notifyAll();
                }
            }
        }
    }



    private int parseInt(){
        int value = 0;
        boolean validInput = false;


        while(!validInput){

            while (!inputPresent) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                }
            }

            try{
                value = Integer.parseInt(input);
                validInput = true;
            } catch(NumberFormatException e){
                System.out.println("input type mismatch, please insert an integer");
            }
            inputPresent = false;
            lock.notifyAll();
        }
        return value;
    }

    private boolean parseBoolean(){
        boolean value = false;
        boolean validInput = false;

        while(!validInput){
            while (!inputPresent) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                }
            }

            if(input.equals("false") || input.equals("true")){
                value = Boolean.parseBoolean(input);
                validInput = true;

            }else{
                System.out.println("input type mismatch, please insert a boolean(true/false)");
            }
            inputPresent = false;
            lock.notifyAll();
        }
        return value;

    }


    //we use this function to visualize the disposition of other players
    @Override
    public void printDisposition(HashMap<Coordinates, PlayableCard> disp){
        dispositionPrinter.print(disp);
    }



    public void clear(){
        System.out.print(ansi().eraseScreen().cursor(0, 0));

        // Print a large number of new lines to push previous content out of view
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
        // Reset the cursor to the top-left corner again
        System.out.print(ansi().cursor(0, 0));
        System.out.flush();
    }



}
