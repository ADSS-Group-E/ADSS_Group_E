package PresentationLayer.Transport.OptionMenus;

import BusinessLayer.Workers_Transport.DeliveryPackage.DeliveryFacade;
import PresentationLayer.CommandLineInterface;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;
import PresentationLayer.Option;
import PresentationLayer.OptionsMenu;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DriversOptionsMenu extends OptionsMenu {
    private final DeliveryFacade deliveryFacade = DeliveryFacade.getInstance();

    public DriversOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);
        int i = 1;
        options.put(i++, new Option("Change License Expiration Date", this::changeLicenseExpiration));
        options.put(i++, new Option("Change License Type", this::changeLicenseType));
        options.put(i, new Option("Back", this::back));
    }

    public void changeLicenseExpiration() {
        String id, licenseExpDate;
        LocalDate date;
        System.out.println("Please enter the driver id");
        id = in.next();
        System.out.println("Please enter the new expiration date");
        licenseExpDate = in.next();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/ yyyy");
        date = LocalDate.parse(licenseExpDate, df);
        try {
            deliveryFacade.updateExpDate(id, date);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\nYou entered wrong details, please try again");
        }
    }

    public void changeLicenseType() {
        String id , licenseType;
        System.out.println("Please enter the driver id");
        id = in.next();
        System.out.println("Please enter the new license type");
        licenseType = in.next();
        try {
            deliveryFacade.updateLicenseType(id, licenseType);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\nYou entered wrong details, please try again");
        }
    }
}