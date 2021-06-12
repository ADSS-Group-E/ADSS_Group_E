package PresentationLayer.Supplier.OptionMenus;

import BusinessLayer.Supplier.SupplierController;
import PresentationLayer.CommandLineInterface;
import PresentationLayer.Option;
import PresentationLayer.OptionsMenu;
import PresentationLayer.Supplier.InputService;
import PresentationLayer.Supplier.OutputService;
import PresentationLayer.Supplier.PresentationHandler;
import PresentationLayer.Supplier.ServiceController;

import java.util.ArrayList;
import java.util.HashMap;

public class SupplierProductOptionsMenu extends OptionsMenu {
    public SupplierProductOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);
        int i = 1;

        // TODO Implement
        options.put(i++, new Option("Add supplier product", this::addProduct));
        options.put(i++, new Option("List all products of a specific supplier", this::listProducts));
        options.put(i, new Option("Back", this::back));
    }

    private InputService in = InputService.getInstance();
    private OutputService out = OutputService.getInstance();
    private ServiceController service = new ServiceController(new SupplierController()); //initializes empty objects


}
