package PresentationLayer.Inventory.Options;

import PresentationLayer.CommandLineInterface;
import PresentationLayer.Option;
import PresentationLayer.OptionsMenu;

import java.util.Scanner;

/**
 * This class represents the main menu of the system.
 *
 * It lists the four main modules - products, categories, discounts and reports
 * and prompts the user to enter their selection accordingly.
 *
 * It then activates the next sub-menu of the system based on the user's selection.
 *  */

public class MainInventoryOptionsMenu extends OptionsMenu {
    public MainInventoryOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);

        // Prompt the user for their module selection
        Scanner in = new Scanner(System.in);
        options.put(1, new Option( "Products",() -> {
            parentCLI.getProductsOptionsMenu().enter();
        }));
        options.put(2, new Option( "Categories",() -> {
            parentCLI.getCategoriesOptionsMenu().enter();
        }));
        options.put(3, new Option( "Discounts",() -> {
            parentCLI.getDiscountsOptionsMenu().enter();
        }));
        options.put(4, new Option( "Reports",() -> {
            parentCLI.getReportsOptionsMenu().enter();
        }));
        options.put(5, new Option( "Back", this::back));
    }
}
