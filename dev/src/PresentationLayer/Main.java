package PresentationLayer;

public class Main {
    public static void main(String[] args) {
        CommandLineInterface CLI = new CommandLineInterface();

        // TODO Ask if user wants to load sample data
        CLI.loadSampleData();

        CLI.run();
    }
}
