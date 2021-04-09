package PresentationLayer.Options;

import BusinessLayer.Product;
import PresentationLayer.CommandLineInterface;

import java.util.Scanner;

/**
 * This class represents the main menu of the system.
 * The menu contains all the required options.
 * Products, categories, discounts and reports.
 * The instruction of this class can be found in the attached document.
 */

public class MainOptionsMenu extends OptionsMenu{
    public MainOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);

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
        options.put(5, new Option( "Quit",() -> {
            System.out.println("Exiting.");
            System.exit(0);
        }));
    }
}
