package PresentationLayer;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CommandLineInterface CLI = new CommandLineInterface();

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
