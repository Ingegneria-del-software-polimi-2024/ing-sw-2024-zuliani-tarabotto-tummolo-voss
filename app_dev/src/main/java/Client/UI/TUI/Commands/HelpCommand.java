package Client.UI.TUI.Commands;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * The type Help command.
 */
public class HelpCommand implements Command{

    private final String name = "--help";
    private final int color = 226;
    @Override
    public void execute() {
        System.out.print(ansi().fg(color).bold().a("COMMANDS: \n").reset());
        System.out.println("  --help    - Display this help message");
        System.out.println("  --quit    - Quit the game and go back to the lobby");
        System.out.println("  --disp    - Switch the displayed disposition the check another player's board");
        System.out.println("  --chat    - Open the chat");


        System.out.println("\n");
        System.out.print(ansi().fg(color).bold().a("GAME RULES: \n").reset());

        System.out.print(ansi().fg(color).a("~> Game overview: \n").reset());
        System.out.println(" Play and place your cards carefully to benefit from the resources\n" +
                " and the objects:\n" +
                " • Play cards to win immediate points\n" +
                " • Fulfill objectives to acquire points at the end of the game\n" +
                " The first player to reach 20 points triggers the end of the game.\n" +
                " Do not hesitate to cover and lose resources to develop your play area.\n ");

        System.out.print(ansi().fg(color).a("~> Playing the game: \n").reset());
        System.out.println(" A game consists of several rounds that players take one after\n" +
                " another in a clockwise direction. In turn, the player must perform\n" +
                " the following two actions in order:\n\n" +
                " 1_Play a card from their hand\n" +
                " 2_Draw a card\n");

        System.out.print(ansi().bold().a(" Placement rule: ").reset());
        System.out.println(" the card must cover one or several visible corners of cards already\n" +
                "                  present in their play area. It cannot cover more than one corner of\n" +
                "                  the same card. Only the card already present in the play area may\n" +
                "                  contain the necessary visible corners.\n" +
                "                  Important: if the player does not like the front of the card, they can\n" +
                "                  play it facedown.\n");

        System.out.print(ansi().bold().a(" Gold cards rule: ").reset());
        System.out.println("to place the Gold cards, the player must possess the indicated\n" +
                "                  resources visible in their play area. The ressources must be visible\n" +
                "                  before they place the card, but they may be covered afterwards.\n");

        System.out.print(ansi().bold().a(" Scoring points:  ").reset());
        System.out.println("If the card placed allows the player to score points, they must be\n" +
                "                  immediately added to the Score track.\n");


        System.out.print(ansi().fg(color).a("~> End of game: \n").reset());
        System.out.println(" The end of the game is triggered when a player reaches 20 points\n" +
                " (or more) or if the two decks are empty. Players finish the round\n" +
                " and then each have a last turn before the game ends.\n" +
                " Each player counts the points from the Objective cards (2 common objectives + the secret objective) and adds them to the points\n" +
                " already scored on the Score track.\n" +
                " The player with the most points wins the game. In case of a tie,\n" +
                " the player with the most Objective card points wins. If there is still\n" +
                " a tie, the players share the victory.");
    }



    @Override
    public String getName() {
        return name;
    }
}
