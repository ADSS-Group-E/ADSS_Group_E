package PresentationLayer.Transport.OptionMenus;
import PresentationLayer.CommandLineInterface;

import PresentationLayer.Option;
import PresentationLayer.OptionsMenu;
import PresentationLayer.Workers.DataTransferObjects.QualificationsDTO;


public class MainTransportOptionsMenu extends OptionsMenu {
    private final DriversOptionsMenu driversOptionsMenu;
    private final TrucksOptionsMenu trucksOptionsMenu;
    private final LocationsOptionsMenu locationsOptionsMenu;
    private final OrdersOptionsMenu ordersOptionsMenu;
    private final DeliveriesOptionsMenu deliveriesOptionsMenu;

    public MainTransportOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);

        driversOptionsMenu = new DriversOptionsMenu(parentCLI);
        trucksOptionsMenu = new TrucksOptionsMenu(parentCLI);
        locationsOptionsMenu = new LocationsOptionsMenu(parentCLI);
        ordersOptionsMenu = new OrdersOptionsMenu(parentCLI);
        deliveriesOptionsMenu = new DeliveriesOptionsMenu(parentCLI);

        int i=1;
        options.put(i++, new Option( "Drivers", driversOptionsMenu::enter, QualificationsDTO.LogisticsManager));
        options.put(i++, new Option( "Trucks", trucksOptionsMenu::enter));
        options.put(i++, new Option( "Locations", locationsOptionsMenu::enter));
        options.put(i++, new Option( "Orders", ordersOptionsMenu::enter));
        options.put(i++, new Option( "Deliveries", deliveriesOptionsMenu::enter));
        options.put(i++, new Option( "Back", this::back));
    }
}