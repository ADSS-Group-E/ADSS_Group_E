package PresentationLayer.Options;

import BusinessLayer.Product;
import PresentationLayer.CommandLineInterface;

import java.util.Scanner;

public class MainOptionsMenu extends OptionsMenu{
    public MainOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);

        Scanner in = new Scanner(System.in);
        options.put(1, new Option( "Products",() -> {
            ProductsOptionsMenu pMenu = parentCLI.getProductsOptionsMenu();
            pMenu.displayOptions();
            int choice = in.nextInt();
            pMenu.chooseOption(choice);
        }));
        options.put(2, new Option( "Categories",() -> {
            CategoriesOptionsMenu cMenu = parentCLI.getCategoriesOptionsMenu();
            cMenu.displayOptions();
            int choice = in.nextInt();
            cMenu.chooseOption(choice);
        }));
        options.put(3, new Option( "Discounts",() -> {
            DiscountsOptionsMenu dMenu = parentCLI.getDiscountsOptionsMenu();
            dMenu.displayOptions();
            int choice = in.nextInt();
            dMenu.chooseOption(choice);
        }));
        options.put(4, new Option( "Reports",() -> {
            ReportsOptionsMenu rMenu = parentCLI.getReportsOptionsMenu();
            rMenu.displayOptions();
            int choice = in.nextInt();
            rMenu.chooseOption(choice);
        }));
        options.put(5, new Option( "Quit",() -> {
            System.out.println("Exiting.");
            System.exit(0);
        }));
    }
}
