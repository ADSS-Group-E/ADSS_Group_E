package PresentationLayer;

import PresentationLayer.Inventory.OptionMenus.MainInventoryOptionsMenu;
import PresentationLayer.Supplier.SupplierOptionsMenu;
import PresentationLayer.Transport.OptionMenus.MainTransportOptionsMenu;
import PresentationLayer.Workers.OptionMenus.MainWorkersOptionsMenu;
import PresentationLayer.Workers.DataTransferObjects.QualificationsDTO;

import java.util.HashSet;

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
    private final MainWorkersOptionsMenu mainWorkersOptionsMenu;



    public MainOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);

        mainInventoryOptionsMenu = new MainInventoryOptionsMenu(parentCLI);
        supplierOptionsMenu = new SupplierOptionsMenu(parentCLI);
        mainTransportOptionsMenu = new MainTransportOptionsMenu(parentCLI);
        mainWorkersOptionsMenu = new MainWorkersOptionsMenu(parentCLI);

        HashSet<QualificationsDTO> storekeeperAndBranchManager = new HashSet<>();
        storekeeperAndBranchManager.add(QualificationsDTO.BranchManager);
        storekeeperAndBranchManager.add(QualificationsDTO.Storekeeper);

        HashSet<QualificationsDTO> logisticsAndBranchManager = new HashSet<>();
        logisticsAndBranchManager.add(QualificationsDTO.BranchManager);
        logisticsAndBranchManager.add(QualificationsDTO.LogisticsManager);


        int i = 1;
        options.put(i++, new Option( "Inventory options", mainInventoryOptionsMenu::enter, storekeeperAndBranchManager));
        options.put(i++, new Option( "Supplier options", supplierOptionsMenu::enter,  storekeeperAndBranchManager));
        options.put(i++, new Option( "Transport options", mainTransportOptionsMenu::enter, logisticsAndBranchManager));
        options.put(i++, new Option( "Workers options", mainWorkersOptionsMenu::enter));
        options.put(i++, new Option( "Log out",() -> {
            System.out.println("Logging out.");
            parentCLI.setLoggedInWorker(null);
            goBack=true;
        }));
    }
}
