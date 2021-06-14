package PresentationLayer.Inventory.OptionMenus;

import PresentationLayer.CommandLineInterface;
import PresentationLayer.Option;
import PresentationLayer.OptionsMenu;

/**
 * This class represents the main menu of the system.
 *
 * It lists the four main modules - products, categories, discounts and reports
 * and prompts the user to enter their selection accordingly.
 *
 * It then activates the next sub-menu of the system based on the user's selection.
 *  */

public class MainInventoryOptionsMenu extends OptionsMenu {
    private final ProductsOptionsMenu productsOptionsMenu;
    private final CategoriesOptionsMenu categoriesOptionsMenu;
    private final DiscountsOptionsMenu discountsOptionsMenu;
    private final ReportsOptionsMenu reportsOptionsMenu;

    public MainInventoryOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);

        productsOptionsMenu = new ProductsOptionsMenu(parentCLI);
        categoriesOptionsMenu = new CategoriesOptionsMenu(parentCLI);
        discountsOptionsMenu = new DiscountsOptionsMenu(parentCLI);
        reportsOptionsMenu = new ReportsOptionsMenu(parentCLI);

        int i=1;
        options.put(i++, new Option( "Products", productsOptionsMenu::enter));
        options.put(i++, new Option( "Categories", categoriesOptionsMenu::enter));
        options.put(i++, new Option( "Discounts", discountsOptionsMenu::enter));
        options.put(i++, new Option( "Reports", reportsOptionsMenu::enter));
        options.put(i++, new Option( "Back", this::back));
    }
}
