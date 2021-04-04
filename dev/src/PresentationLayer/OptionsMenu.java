package PresentationLayer;


import java.util.Map;
import java.util.TreeMap;

public abstract class OptionsMenu {
    protected TreeMap<Integer,Option> options;

    public void displayOptions(){
        for (Map.Entry<Integer, Option> entry : options.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue().getDescription());
        }
    }

    public void chooseOption(int choice){
        // TODO check choice exists
        options.get(choice).run();
    }
}
