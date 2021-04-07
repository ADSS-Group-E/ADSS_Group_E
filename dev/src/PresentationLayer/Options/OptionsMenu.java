package PresentationLayer.Options;


import PresentationLayer.CommandLineInterface;

import java.util.Map;
import java.util.TreeMap;

public abstract class OptionsMenu {
    protected TreeMap<Integer,Option> options;
    protected CommandLineInterface parentCLI;

    public OptionsMenu(CommandLineInterface parentCLI) {

        options = new TreeMap<Integer,Option>();
        this.parentCLI = parentCLI;
    }

    /*
    * Lists options in sorted order.
     */
    public void displayOptions(){
        System.out.println("\nPlease choose an option:");
        for (Map.Entry<Integer, Option> entry : options.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue().getDescription());
        }
    }

    public void chooseOption(int choice){
        // TODO check choice exists
        options.get(choice).run();
    }
}