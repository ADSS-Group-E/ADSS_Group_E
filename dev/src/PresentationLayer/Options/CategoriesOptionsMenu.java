package PresentationLayer.Options;

import PresentationLayer.CommandLineInterface;

public class CategoriesOptionsMenu extends OptionsMenu{
    public CategoriesOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);
        int i=1;
        options.put(i++, new Option( "Get category",() -> {

        }));

        options.put(i++, new Option( "List all categories",() -> {

        }));

        options.put(i++, new Option( "Add category",() -> {

        }));

        options.put(i++, new Option( "Remove category",() -> {

        }));

        options.put(i, new Option( "Back",() -> {
            System.out.println("Going back.");
            goBack=true;
        }));
    }
}
