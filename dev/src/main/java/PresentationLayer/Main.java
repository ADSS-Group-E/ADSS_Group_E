package PresentationLayer;

import PresentationLayer.CommandLineInterface;
import PresentationLayer.Supplier.ServiceController;

import java.util.Scanner;

/**
 * This is the main function of the system.
 * When activated, it first prompts the user to load sample data, and  acts accordingly.
 * It then runs the program, invoking the main options menus.
 */
public class Main {
    public static void main(String[] args) {
        CommandLineInterface CLI = new CommandLineInterface();


        // Prompt the user to optionally load the sample data
        System.out.println("Welcome to our system.");
        System.out.println("Do you wish to load the sample data? (y/n) ");
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
