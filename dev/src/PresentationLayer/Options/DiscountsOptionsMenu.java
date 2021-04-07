package PresentationLayer.Options;

import PresentationLayer.CommandLineInterface;

public class DiscountsOptionsMenu extends OptionsMenu{

    public DiscountsOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);
        int i = 1;
        options.put(i++, new Option("Get discount", () -> {

        }));

        options.put(i++, new Option("List all discounts", () -> {

        }));

        options.put(i++, new Option("Add discount", () -> {

        }));

        options.put(i++, new Option("Remove discount", () -> {

        }));

        options.put(i, new Option( "Back",() -> System.out.println("Going back.")));
    }
}
