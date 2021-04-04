package PresentationLayer;

import java.util.TreeMap;

public class MainOptionsMenu extends OptionsMenu{
    public MainOptionsMenu() {
        options = new TreeMap<Integer,Option>();
        options.put(1, new Option(1, "Test one",() -> System.out.println("Test one success!")));
    }
}
