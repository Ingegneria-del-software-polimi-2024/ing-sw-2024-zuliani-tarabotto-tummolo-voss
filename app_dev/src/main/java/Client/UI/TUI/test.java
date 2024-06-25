package Client.UI.TUI;


import org.fusesource.jansi.Ansi;

import java.util.Scanner;

public class test {

    public static void main(String[] args) {
        //animal
        System.out.println("\u2554\u2550\u2557");
        System.out.println("\u2560\u2550\u2563");

        //fungi
        System.out.println("\u2554\u2550\u2550");
        System.out.println("\u2560\u2550");

        //plants
        System.out.println("\u2554\u2550\u2557");
        System.out.println("\u2560\u2550\u255D");

        //Insects
        System.out.println(" \u2566 ");
        System.out.println(" \u2569 ");

        //System.out.println();
        //System.out.print("\u001b[1A"); //move the cursor one line up
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();

        System.out.print(Ansi.ansi().eraseScreen().cursor(0, 0));

        // Print a large number of new lines to push previous content out of view
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }

        // Reset the cursor to the top-left corner again
        System.out.print(Ansi.ansi().cursor(0, 0));
        System.out.flush();

        //animal
        System.out.println("\u2554\u2550\u2557");
        System.out.println("\u2560\u2550\u2563");

        //fungi
        System.out.println("\u2554\u2550\u2550");
        System.out.println("\u2560\u2550");

        //plants
        System.out.println("\u2554\u2550\u2557");
        System.out.println("\u2560\u2550\u255D");

        //Insects
        System.out.println(" \u2566 ");
        System.out.println(" \u2569 ");
        //ystem.out.println(num);
    }
}
