package PresentationLayer;

import PresentationLayer.Inventory.OptionMenus.MainInventoryOptionsMenu;
import PresentationLayer.Supplier.SupplierOptionsMenu;
import PresentationLayer.Transport.OptionMenus.MainTransportOptionsMenu;

/**
 * This class represents the main menu of the system.
 *
 * It lists the four main modules - products, categories, discounts and reports
 * and prompts the user to enter their selection accordingly.
 *
 * It then activates the next sub-menu of the system based on the user's selection.
 *  */

public class MainOptionsMenu extends OptionsMenu {
    private final MainInventoryOptionsMenu mainInventoryOptionsMenu;
    private final SupplierOptionsMenu supplierOptionsMenu;
    private final MainTransportOptionsMenu mainTransportOptionsMenu;


    public MainOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);

        mainInventoryOptionsMenu = new MainInventoryOptionsMenu(parentCLI);
        supplierOptionsMenu = new SupplierOptionsMenu(parentCLI);
        mainTransportOptionsMenu = new MainTransportOptionsMenu(parentCLI);

        int i = 1;
        options.put(i++, new Option( "Inventory options", mainInventoryOptionsMenu::enter));
        options.put(i++, new Option( "Supplier options", supplierOptionsMenu::enter));
        options.put(i++, new Option( "Transport options", mainTransportOptionsMenu::enter));
        options.put(i++, new Option( "Log out",() -> {
            System.out.println("Logging out.");
            parentCLI.setLoggedInWorker(null);
            goBack=true;
        }));
    }
}
