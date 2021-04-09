package PresentationLayer;

import java.util.Scanner;

/**
 * This is the main function.
 * To run the program, you need to run this class.
 */
public class Main {
    public static void main(String[] args) {
        CommandLineInterface CLI = new CommandLineInterface();

        // Option to load sample data
        System.out.println("Load sample data? (y/n) ");
        Scanner in = new Scanner(System.in);
        String verify = in.next().trim();
        if (verify.equals("y")) {
            CLI.loadSampleData();
            System.out.println("Sample data loaded.");
        }
        else {
            System.out.println("Sample data not loaded.");
        }

        CLI.run();
    }
}
