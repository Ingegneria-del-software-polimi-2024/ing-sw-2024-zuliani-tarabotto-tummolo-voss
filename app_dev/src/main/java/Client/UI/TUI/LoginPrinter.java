package Client.UI.TUI;


import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class LoginPrinter {
    public void print(){
        printTitle();
    }

    private static void printTitle(){
        System.out.println(ansi().fg(221).a(

        " ██████╗ ██████╗ ██████╗ ███████╗██╗  ██╗    ███╗   ██╗ █████╗ ████████╗██╗   ██╗██████╗  █████╗ ██╗     ██╗███████╗\n" +
                "██╔════╝██╔═══██╗██╔══██╗██╔════╝╚██╗██╔╝    ████╗  ██║██╔══██╗╚══██╔══╝██║   ██║██╔══██╗██╔══██╗██║     ██║██╔════╝\n" +
                "██║     ██║   ██║██║  ██║█████╗   ╚███╔╝     ██╔██╗ ██║███████║   ██║   ██║   ██║██████╔╝███████║██║     ██║███████╗\n" +
                "██║     ██║   ██║██║  ██║██╔══╝   ██╔██╗     ██║╚██╗██║██╔══██║   ██║   ██║   ██║██╔══██╗██╔══██║██║     ██║╚════██║\n" +
                "╚██████╗╚██████╔╝██████╔╝███████╗██╔╝ ██╗    ██║ ╚████║██║  ██║   ██║   ╚██████╔╝██║  ██║██║  ██║███████╗██║███████║\n" +
                " ╚═════╝ ╚═════╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝    ╚═╝  ╚═══╝╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝\n" +
                "                                                                                                                    ").reset());
    }



/*
        private static final String[] GAME_ROOMS = {"Room 1", "Room 2", "Room 3"};
        private static int selectedRoomIndex = 0;

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            printTitle();

            while (true) {
                // Print game rooms with selection
                for (int i = 0; i < GAME_ROOMS.length; i++) {
                    if (i == selectedRoomIndex) {
                        System.out.print("> ");
                    } else {
                        System.out.print("  ");
                    }
                    System.out.println(GAME_ROOMS[i]);
                }

                // Wait for user input
                System.out.print("Use arrow keys to navigate, press Enter to select: ");
                String input = scanner.nextLine();

                // Handle arrow key input
                if (input.equals("\u001B[A")) { // Up arrow key
                    selectedRoomIndex = Math.max(0, selectedRoomIndex - 1);
                } else if (input.equals("\u001B[B")) { // Down arrow key
                    selectedRoomIndex = Math.min(GAME_ROOMS.length - 1, selectedRoomIndex + 1);
                } else if (input.equals("\n")) { // Enter key
                    System.out.println("Selected room: " + GAME_ROOMS[selectedRoomIndex]);
                    break; // Exit loop after room selection
                }

                // Clear console
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }

            // Further actions after room selection
            // (e.g., join the selected room)
        }

*/
}
