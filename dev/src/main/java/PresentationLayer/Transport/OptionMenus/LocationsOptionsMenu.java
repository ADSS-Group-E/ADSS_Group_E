package PresentationLayer.Transport.OptionMenus;

import BusinessLayer.Workers_Transport.DeliveryPackage.DeliveryFacade;
import BusinessLayer.Workers_Transport.Response;
import PresentationLayer.CommandLineInterface;
import PresentationLayer.Option;
import PresentationLayer.OptionsMenu;

public class LocationsOptionsMenu extends OptionsMenu {
    private final DeliveryFacade deliveryFacade = DeliveryFacade.getInstance();

    public LocationsOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);
        int i = 1;
        options.put(i++, new Option("Create New Location", this::createNewLocation));
        options.put(i++, new Option("Delete Location", this::deleteLocation));
        options.put(i++, new Option("Change Phone Number", this::changePhoneNumber));
        options.put(i++, new Option("Change Contact Name", this::changeContactName));
        options.put(i++, new Option("Display Location", this::displayLocation));
        options.put(i, new Option("Back", this::back));
    }

    private void displayLocation() {
        try{
            deliveryFacade.printLocations();
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    private void changeContactName() {
        String  s1;
        int id;

        System.out.println("please enter location id and the new contact name");
        id = in.nextInt();
        s1 = in.next();
        try{

            deliveryFacade.updateContactName(id, s1);
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    private void changePhoneNumber() {
        String  s1;
        int id;

        System.out.println("please enter location id and the new telephone number");
        id = in.nextInt();
        s1 = in.next();
        try{
            deliveryFacade.updateTelNumber(id, s1);

        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    private void deleteLocation() {
        int id;

        System.out.println("please enter the location id that you want to erase from the system");
        id = in.nextInt();
        try{
            deliveryFacade.removeLocation(id);

        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    private void createNewLocation() {
        String  name, licenseType, licenseExpDate, s1, s2;
        int id;

        System.out.println("please enter location details: location id, name, address, telephone number,\n" +
                "contact name, shipping area");
        id = in.nextInt();
        name = in.next();
        licenseType = in.next();
        licenseExpDate = in.next();
        s1 = in.next();
        s2 = in.next();
        try{
            deliveryFacade.createLocation(id, name, licenseType, licenseExpDate, s1, s2);
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }

    }
}

