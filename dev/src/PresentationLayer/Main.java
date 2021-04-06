package PresentationLayer;

public class Main {
    public static void main(String[] args) {
        CommandLineInterface CLI = new CommandLineInterface();
        CLI.loadSampleData();
        CLI.run();
    }
}
