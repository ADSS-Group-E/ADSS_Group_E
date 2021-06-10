package PresentationLayer;


import PresentationLayer.CommandLineInterface;
import PresentationLayer.Option;
import PresentationLayer.Workers_Transport.QualificationsDTO;
import PresentationLayer.Workers_Transport.WorkerDTO;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public abstract class OptionsMenu {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    protected TreeMap<Integer, Option> options;
    protected TreeMap<Integer, Option> authorizedOptions;

    protected CommandLineInterface parentCLI;
    protected boolean goBack;
    protected Scanner in;
    protected String prompt = "\nPlease choose an option:";

    public OptionsMenu(CommandLineInterface parentCLI) {

        options = new TreeMap<>();
        this.parentCLI = parentCLI;
        goBack=false;
        in = new Scanner(System.in);
        setAuthorizedOptions(parentCLI.getLoggedInWorker());
    }

    private void setAuthorizedOptions(WorkerDTO loggedInWorker){
        int i = 1;
        for (Map.Entry<Integer, Option> entry : options.entrySet()) {
            Option option = entry.getValue();
            if (option.checkQualified(loggedInWorker))
                authorizedOptions.put(i, entry.getValue());
        }
    }

    /*
    * Lists options in sorted order.
     */
    public void displayOptions(){

        System.out.println(prompt);
        for (Map.Entry<Integer, Option> entry : options.entrySet()) {
            System.out.println(ANSI_GREEN + entry.getKey()+ " => "  + entry.getValue().getDescription() +  ANSI_RESET);
        }
    }

    public void chooseOption(int choice){
        if(options.containsKey(choice)){
            options.get(choice).run();
        }
        else{
            System.out.println("Invalid choice.");
        }
    }

    public void enter(){
        Scanner in = new Scanner(System.in);
        while (!goBack) {
            this.displayOptions();
            int choice = in.nextInt();
            this.chooseOption(choice);
        }
        goBack = false;
    }

    protected void back(){
        System.out.println("Going back.");
        goBack=true;
    }
}
