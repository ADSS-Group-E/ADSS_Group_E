package PresentationLayer.Options;

import BusinessLayer.Product;
import PresentationLayer.CommandLineInterface;

import java.util.Scanner;

public class MainOptionsMenu extends OptionsMenu{
    public MainOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);

        options.put(1, new Option( "Test one",() -> System.out.println("Test one success!")));
        options.put(2, new Option( "Products",() -> {
            Scanner in = new Scanner(System.in);
            ProductsOptionsMenu pMenu = parentCLI.getProductsOptionsMenu();
            pMenu.displayOptions();
            int choice = in.nextInt();
            pMenu.chooseOption(choice);
        }));
        options.put(3, new Option( "Quit",() -> {
            System.out.println("Exiting.");
            System.exit(0);
        }));
    }
}
