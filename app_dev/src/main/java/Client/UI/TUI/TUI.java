package Client.UI.TUI;

import Chat.ChatHistory;
import Chat.MessagesFromClient.ChatMessage;
import Client.UI.TUI.Commands.*;
import Client.UI.UI;
import Client.View.ViewAPI;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Artifact;
import model.enums.Element;
import model.placementArea.Coordinates;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.fusesource.jansi.Ansi.ansi;


public class TUI implements UI {

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
    boolean enterPressed = false;
    private String nickname;
    private final Object lockForControllingCommands;
    private boolean uiAlreadyStarted = false;
    private boolean runningThread;
    private boolean inWaitingRoom = false;
    private String game;

    public TUI(ViewAPI view){

        this.view = view;
        cb = new CardBuilder();
        dispositionPrinter = new DispositionPrinter(cb);
        loginPrinter = new LoginPrinter();
        handPrinter = new HandPrinter();
        objectivesPrinter = new ObjectivesPrinter();
        drawCardPrinter = new DrawCardPrinter(cb);
        lock = new Object();
        lockForControllingCommands = new Object();

        this.commandMap = new HashMap<>();
        commandMap.put("--help", new HelpCommand());
        commandMap.put("--disp", new DispositionCommand(view));
        commandMap.put("--quit", new EndGameCommand(view, this));
        //commandMap.put("--chat", new ChatCommand(view, this)); //TODO decomment for chat

        sc = new Scanner(System.in);
    }
///////////////////////////////////////<Lobby>//////////////////////////////////////////////////////////////////////////
    public void firstWelcome(){
        loginPrinter.print();
        System.out.println("~> Welcome to Codex Naturalis, follow the instructions to join a game.\n   Once in the game write --help for a guide, --quit to exit");
    }
    public void chooseConnection(){
        System.out.print(ansi().fg(color).a("~> Insert the techonolgy of connection (RMI/Socket): \n").reset());
        String connectionType;
        do{
            connectionType = sc.nextLine();
            if(!(connectionType.equalsIgnoreCase("RMI") || connectionType.equalsIgnoreCase("SOCKET")))
                System.out.print(ansi().fg(color).a("~> Insert a correct value \"RMI\" or \"Socket\":\n").reset());
        }while (!(connectionType.equalsIgnoreCase("RMI") || connectionType.equalsIgnoreCase("SOCKET")));

        String host;
        System.out.print(ansi().fg(color).a("~> Insert the host ip address: \n").reset());
        do{
            host = sc.nextLine();
            if (!validIP(host))
                System.out.print(ansi().fg(color).a("~> Insert a valid ip address (xxx.xxx.xxx.xxx): \n").reset());
        }while(!validIP(host));

//        int port;
//        System.out.print(ansi().fg(color).a("~> Insert the host port: \n").reset());
//        do{
//            port = Integer.parseInt(sc.nextLine());
//            if (!validPort(port))
//                System.out.print(ansi().fg(color).a("~> Insert a valid port: \n").reset());
//        }while(!validPort(port));

//        int localPort;
//        System.out.print(ansi().fg(color).a("~> Insert the local port: \n").reset());
//        do{
//            localPort = Integer.parseInt(sc.nextLine());
//            if (!validPort(localPort))
//                System.out.print(ansi().fg(color).a("~> Insert a valid port: \n").reset());
//        }while(!validPort(localPort));

        try {
            view.startConnection(connectionType, host);
        } catch (StartConnectionFailedException e) {
            System.out.print(ansi().fg(color).a("~> An error during the connection occurred\n   Check your internet connection and retry\n").reset());
            chooseConnection();
        }
    }

    private boolean validIP(String ip){
        if(ip.toLowerCase().equals("localhost"))
            return true;
        String desiredFormat = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        Pattern pattern = Pattern.compile(desiredFormat);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    private boolean validPort(int port){
        return(1024<=port && port <=49151);
    }

    public void askNickname(){
        System.out.print(ansi().fg(color).a("~> Insert your nickname: \n").reset());
        nickname = sc.nextLine();
        view.setPlayerID(nickname);

    }
    public void nickNameAlreadyInUse(){
        System.out.print(ansi().fg(color).a("~> This nickname is already in use or it is empty, please change nickname\n").reset());
        askNickname();
    }

    @Override
    public void cantPlaceACard(PlayableCard card, Coordinates coord) {
        System.out.print(ansi().fg(color).a("~> Can't place the card in the position ("+coord.getX()+";"+coord.getY()+")"+"\n   Choose a different card\n").reset());
        displayPlacingCard(false);
    }

    @Override
    public void cantDrawCard(int source) {
        System.out.print(ansi().fg(color).a("~> Can't draw the card from the selected source\n").reset());
        displayCardDrawing(false);
    }

    @Override
    public void cantCreateRoom() {
        System.out.print(ansi().fg(color).a("~> Can't create the room, control the number of players and the name of the room\n").reset());
        System.out.println("   Players must be between 2 and 4 and the room name must not be empty nor it must be already existing, try typing a different room name\n");
        view.displayAvailableGames();
    }

    @Override
    public void cantJoinRoom() {
        System.out.print(ansi().fg(color).a("~> Can't join the room, too many players are present\n").reset());
        view.displayAvailableGames();
    }

    public void displayAvailableGames(ArrayList<String> listOfGames){

        if(listOfGames != null && !listOfGames.isEmpty()){
            System.out.print(ansi().fg(color).a("~> Available games:\n").reset());
            for(String g : listOfGames)
                System.out.print(ansi().fg(color).a("   "+g+"\n").reset());
            System.out.print(ansi().fg(color).a("~> Insert the name of the game you want to join or a new name if you want to create it, write -r to refresh the available games: \n").reset());
        }else
            System.out.print(ansi().fg(color).a("~> There are no available games, please create a new game by typing its name or write --r to refresh the available games: \n").reset());
        //sc.reset();
        game = sc.nextLine();
        if(game.equals("--r")) {
            view.requestAvailableGames();
        }else{
            int nPlayers = 0;
            if(listOfGames == null || !listOfGames.contains(game)) {
                String players;
                boolean flag = false;
                System.out.print(ansi().fg(color).a("~> You want to create a new game!\n~> Insert the number of players (insert --back to go back): ").reset());
                do {
                    players = sc.nextLine();
                    if(players.equals("--back")){
                        view.requestAvailableGames();
                        return;
                    }
                    try {
                        nPlayers = Math.abs(Integer.parseInt(players));
                        flag = false;
                    } catch (Exception e) {
                        flag = true;
                    }
                    if(flag||nPlayers<2 || nPlayers>4){
                        System.out.print(ansi().fg(color).a("~> The game must contain at least 2 players and maximum 4 players\n~> Insert the number of players: ").reset());
                        flag = true;
                    }
                } while (flag);
            }
            view.joinGame(game, nPlayers);
        }

    }
    public void joinedGame(String id){
        inWaitingRoom = true;
        view.startUI();

        rePrint = () -> {
            clear();
            loginPrinter.print();
            System.out.print(ansi().fg(color).a("~> Correctly joined the game "+id+"\n   waiting for other players...\n").reset());
        };
        rePrint.run();

    }

    public void returnToLobby(){
        view.stopUI(); //moved here from line 219
        displayReturnToLobby();
        clear();
        view.welcome();
        view.requestAvailableGames();
    }

    public void returnToChooseGame(){
        inputPresent = false;
        view.stopUI();
        clear();
        loginPrinter.print();
    }


    public void displayReturnToLobby(){
        String in = null;
        sc = new Scanner(System.in);
        do {
            if(input == null){
                clear();
                System.out.print(ansi().fg(color).a("~> Press enter to return to the lobby\n").reset());
                in = sc.nextLine();
            } else{
                synchronized (lock){
                    in = parseString();
                }

            }
        }while(in == null);
        inputPresent = false;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void displayInitialization() {
        inWaitingRoom = false;
        clear();
        loginPrinter.print();

       /* if(!uiAlreadyStarted){
            view.startUI();
            uiAlreadyStarted = true;
        }*/

        //view.startUI();

        enterPressed = false;
        // Continuously check if Enter is pressed until it's pressed
        while (!enterPressed) {
            System.out.print(ansi().fg(color).a("~> Press Enter to continue...\n").reset());

            synchronized (lock) {
                while (!inputPresent) {
                    try {
                       lock.wait();
                    } catch (InterruptedException e) {
                        //TODO ERROR management
                        System.out.println("Interrupted");
                        return;
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
            System.out.print(ansi().fg(color).a("~> This is your StarterCard, choose a face side: (front/back)\n").reset());
        };
        rePrint.run();


        //we read from input the line and parse it as boolean
        synchronized (lock){
            Boolean faceSide = parseBoolean();

            if(faceSide == null)
                return;

            view.getStarterCard().setFaceSide(faceSide);
            view.playStarterCard();
            inputPresent = false;
            lock.notifyAll();
        }
        printStarterCard();
    }

    public void printStarterCard(){
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
            System.out.print(ansi().fg(color).a("~> these are the two commonObjectives:\n").reset());
            objectivesPrinter.printCommonObjectives(view.getCommonObjectives().get(0),view.getCommonObjectives().get(1));

            System.out.print(ansi().fg(color).a("~> choose your secretObjective: (1/2)\n").reset());
            //we use the same function also to print the two objectives the player has to choose from
            objectivesPrinter.printCommonObjectives(view.getChooseSecretObjectives().get(0), view.getChooseSecretObjectives().get(1));
        };
        rePrint.run();

        synchronized (lock){
            Integer secretObj = parseInt();
            if(secretObj  == null)
                return;
            while(!secretObj.equals(1)  && !secretObj.equals(2)){
                System.out.println("~> Please insert 1/2\n");
                secretObj = parseInt();
                if(secretObj == null) {
                    lock.notifyAll();
                    return;
                }
            }
            view.setSecretObjective(view.getChooseSecretObjectives().get(secretObj - 1));
            inputPresent = false;
            lock.notifyAll();
        }
        printSecretObjective();
    }

    public void printSecretObjective(){
        rePrint = () -> {
            clear();
            System.out.print(ansi().fg(color).a("~> These are your objectives\n").reset());
            objectivesPrinter.printObjectivesBoard(view.getCommonObjectives().get(0), view.getCommonObjectives().get(1), view.getSecretObjective());
        };
        rePrint.run();
    }

    public void displayPlacingCard(){
        displayPlacingCard(true);
    }
    private void displayPlacingCard(boolean clear) {
        Integer index;
        Integer x = 0;
        Integer y = 0;
        Boolean faceSide;
        //if it's my turn then i need to play a card, else i just wait
        if(view.getMyTurn()) {

            // we choose which card we want to place
            rePrint = () -> {
                if(clear)
                    clear();
                dispositionPrinter.print(view.getDisposition(), view.getAvailablePlaces());
                ultimatePrint(view);
                System.out.print(ansi().fg(color).a("~> Choose a card to place from your hand (1/2/3): \n").reset());
            };
            rePrint.run();

            synchronized (lock){
                index = parseInt();
                if(index == null) {
                    lock.notifyAll();
                    return;
                }

                while(index != 1 && index != 2 && index != 3 ){
                    System.out.println("~> Please insert 1/2/3");
                    index = parseInt();
                    if(index == null){
                        lock.notifyAll();
                        return;
                    }
                }
                inputPresent = false;
                lock.notifyAll();
            }

            //we select the face for the card
            rePrint = () -> {
                clear();
                dispositionPrinter.print(view.getDisposition(), view.getAvailablePlaces());
                ultimatePrint(view);
                System.out.print(ansi().fg(color).a("~> Select a face side for the card: (front/back)\n").reset());
            };
            rePrint.run();

            synchronized (lock){
                faceSide = parseBoolean();
                if(faceSide == null){
                    lock.notifyAll();
                }
                if(faceSide && !view.getCanBePlaced()[index - 1]){
                    System.out.println("~> Sorry, card " + index + "can't be placed face up due to its placement constraint");
                    faceSide = false;
                }
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
                    if(x == null){
                        lock.notifyAll();
                        return;
                    }
                    inputPresent = false;
                    lock.notifyAll();
                }
                synchronized (lock){
                    y = parseInt();
                    if(y == null){
                        lock.notifyAll();
                        return;
                    }
                    //view.playCard(c, x, y);
                    if(!view.checkAvailable(x, y)) System.out.println("These coordinates are not available, please choose some valid ones");
                    else view.playCard(view.getHand().get(index - 1), faceSide, x, y);
                    //System.out.println(view.getHand().get(index - 1).getFaceSide());
                    inputPresent = false;
                    lock.notifyAll();
                }
            }

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

    public void displayCardDrawing(){
        displayCardDrawing(true);
    }
    private void displayCardDrawing(boolean clear) {

        if(view.getMyTurn()){

            rePrint= () -> {
                if(clear)
                    clear();
                drawCardPrinter.print(view.getGoldDeck().get(0), view.getResourceDeck().get(0), view.getOpenGold(), view.getOpenResource());
                System.out.print(ansi().fg(color).a("~> Draw a card: (1/2/3/4/5/6)\n").reset());
            };
            rePrint.run();


            synchronized (lock){
                Integer cardSource = parseInt();
                if(cardSource == null){
                    lock.notifyAll();
                    return;
                }
                while(!view.checkCanDrawFrom(cardSource)){
                    System.out.println("~> This card source is not available, choose a valid one\n");
                    cardSource = parseInt();
                    if(cardSource == null){
                        lock.notifyAll();
                        return;
                    }
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

        System.out.println(ansi().fg(226).a(
                "████████╗██╗  ██╗███████╗    ███████╗███╗   ██╗██████╗\n" +
                "╚══██╔══╝██║  ██║██╔════╝    ██╔════╝████╗  ██║██╔══██╗\n" +
                "   ██║   ███████║█████╗      █████╗  ██╔██╗ ██║██║  ██║\n" +
                "   ██║   ██╔══██║██╔══╝      ██╔══╝  ██║╚██╗██║██║  ██║\n" +
                "   ██║   ██║  ██║███████╗    ███████╗██║ ╚████║██████╔╝\n" +
                "   ╚═╝   ╚═╝  ╚═╝╚══════╝    ╚══════╝╚═╝  ╚═══╝╚═════╝\n"  +
                "                                                      ").reset());

        List<String> winners = view.getWinners();
        if(winners.size() == 1)
            System.out.print(ansi().fg(226).a("~> " + winners.get(0).toUpperCase() + " is the winner !!!\n").reset());
        else {
            System.out.print(ansi().fg(226).a("~> The winners are:\n").reset());
            for (String win : view.getWinners()) {
                System.out.print(ansi().fg(226).a("                   " + win.toUpperCase() + " !!!\n").reset());
            }
        }
        for (Map.Entry<String, Integer> stringIntegerEntry : entryList) {
            System.out.println("-> " + stringIntegerEntry.getKey() + ": " + stringIntegerEntry.getValue() + " points");
        }
        System.out.print(ansi().fg(color).a("~> Press enter to return to the lobby\n").reset());
    }

    private List<String> getInfoField(){
        List<String> infoField = new ArrayList<>();

        infoField.add("\u2554\u2550\u2550\u2550"+ ansi().fg(color).bold().a(" RESOURCES ").reset() + "\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557");
        infoField.add("\u2551                                                                   \u2551");
        infoField.add("\u2551   " + ansi().fg(color).a("POINTS:         ").reset() + buildStat(view.getPoints().get(view.getPlayerId())) + "                                              \u2551");
        infoField.add("\u2551   " + ansi().fg(color).a("ELEMENTS:").reset() + ansi().a("       " + Element.mushrooms.getStringValue() + "->" +buildStat(view.getAvailableElements(nickname).get(Element.mushrooms))).reset()  + ansi().a("       " + Element.animals.getStringValue() + "->" + buildStat( view.getAvailableElements(nickname).get(Element.animals))).reset() + ansi().a("       " + Element.vegetals.getStringValue() + "->" + buildStat(view.getAvailableElements(nickname).get(Element.vegetals))).reset() + ansi().a("       " + Element.insects.getStringValue() + "->" + buildStat(view.getAvailableElements(nickname).get(Element.insects))).reset() + "       \u2551");
        infoField.add("\u2551   " + ansi().fg(color).a("ARTIFACTS:").reset() + ansi().a("      " + Artifact.feather.getStringValue() + "->" + buildStat(view.getAvailableArtifacts(nickname).get(Artifact.feather))).reset() + ansi().a("       " + Artifact.paper.getStringValue() + "->" + buildStat(view.getAvailableArtifacts(nickname).get(Artifact.paper))).reset() + ansi().a("       " + Artifact.ink.getStringValue() + "->" + buildStat(view.getAvailableArtifacts(nickname).get(Artifact.ink))).reset() + "                   \u2551");
        infoField.add("\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D");

        return infoField;
    }


    @Override
    public void updateResourcesInUI() {

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
        runningThread = true;
        while (runningThread) {
            synchronized (lock) {
                while (inputPresent) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println("The interruptedException was caught");
                        return;
                    }
                }

                if(Thread.currentThread().isInterrupted()){
                    //thread safety
                    return;
                }

                //System.out.println("command thread");
                try{
                    input = sc.nextLine();
                }catch (Throwable e){
                    e.printStackTrace();
                }

                if (input.equals("--quit")) {
                    runningThread = false;
                    commandMap.get(input).execute();
                    input = null;
                    inputPresent = true;
                    lock.notifyAll();
                }else if(input.startsWith("--")){
                    clear();
                    Command c = commandMap.get(input);
                    if(c != null) {
                        c.execute();
                        //System.out.println("command");
                        System.out.println("type q to go back to the game");
                        while (!sc.nextLine().equals("q")) {
                            System.out.println("type q to go back to the game");
                        }
                        rePrint.run();
                    }
                } else {
                    inputPresent = true;
                    lock.notifyAll();
                }
            }
        }
    }



    private Integer parseInt(){
        int value = 0;
        boolean validInput = false;


        while(!validInput){

            while (!inputPresent) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.out.println("the InterruptedException was caught");
                    return null;
                }
            }

            try{
                if(input == null) {
                    return null;
                }
                value = Integer.parseInt(input);
                validInput = true;
            } catch(NumberFormatException e){
                System.out.println("Input type mismatch, please insert an integer\n");
            }
            inputPresent = false;
            lock.notifyAll();
        }
        return value;
    }

    private Boolean parseBoolean(){
        boolean value = false;
        boolean validInput = false;

        while(!validInput){
            while (!inputPresent) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.out.println("the InterruptedException was caught");
                    return null;
                }
            }

            if(input == null){
                return null;
            }

            if(input.equals("back")) {
                value = false;
                validInput = true;
            }else if(input.equals("front")) {
                value = true;
                validInput = true;
            }else{
                System.out.println("Input type mismatch, please insert (front/back)\n");
            }
            inputPresent = false;
            lock.notifyAll();
        }
        return value;

    }

    private String parseString(){
        boolean validInput = false;

        String value = null;

        while(!validInput){

            while (!inputPresent) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.out.println("the InterruptedException was caught");
                    return null;
                }
            }

            try{
                if(input == null) {
                    return null;
                }
                value = input;
                validInput = true;
            } catch(NumberFormatException e){
                System.out.println("Input type mismatch, please insert an integer\n");
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
        for (int i = 0; i < 200; i++) {
            System.out.println();
        }
        // Reset the cursor to the top-left corner again
        System.out.print(ansi().cursor(0, 0));
        System.out.flush();
    }


    public void displayReconnection(){
        clear();
        System.out.print(ansi().fg(color).a("~> "+view.getPlayerId()+", you have successfully reconnected to the game").reset());
    }

    public void setInWaitingRoom(Boolean b){
        inWaitingRoom = b;
    }

    public boolean getInWaitingRoom(){
        return inWaitingRoom;
    }

    public String getGame(){
        return  game;
    }

    ///////////////////////////////////////////chat/////////////////////////////////////////////////////////////////////
    @Override
    public void displayNewTextMessage(ChatMessage message) {
        //TODO implement
    }


    public void printChat(){
        //TODO implement
    }
}
