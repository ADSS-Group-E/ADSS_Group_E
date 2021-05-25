package PresentationLayer;

import java.util.Scanner;

/**
 * This class represents the main menu of the system.
 *
 * It lists the four main modules - products, categories, discounts and reports
 * and prompts the user to enter their selection accordingly.
 *
 * It then activates the next sub-menu of the system based on the user's selection.
 *  */

public class MainOptionsMenu extends OptionsMenu {
    public MainOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);

        // Prompt the user for their module selection
        Scanner in = new Scanner(System.in);
        options.put(1, new Option( "Inventory options",() -> {
            parentCLI.getMainInventoryOptionsMenu().enter();
        }));
        options.put(2, new Option( "Supplier options",() -> {
            parentCLI.getSupplierOptionsMenu().enter();
        }));
        options.put(3, new Option( "Quit",() -> {
            System.out.println("Exiting. Thank you for using our system.");
            System.exit(0);
        }));
    }
}
