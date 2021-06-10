package PresentationLayer.Transport.OptionMenus;

import BusinessLayer.Workers_Transport.DeliveryPackage.DeliveryFacade;
import BusinessLayer.Workers_Transport.Response;
import PresentationLayer.CommandLineInterface;
import PresentationLayer.Option;
import PresentationLayer.OptionsMenu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TrucksOptionsMenu extends OptionsMenu {
    private final DeliveryFacade deliveryFacade = DeliveryFacade.getInstance();

    public TrucksOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);
        int i = 1;
        options.put(i++, new Option("Create New Truck", this::createNewTruck));
        options.put(i++, new Option("Remove Truck", this::removeTruck));
        options.put(i++, new Option("Display Trucks", this::displayTrucks));
        options.put(i, new Option("Back", this::back));
    }

    private void displayTrucks() {
        try{
            deliveryFacade.printTrucks();
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    private void removeTruck() {
        String id;
        Response res;
        System.out.println("Please enter the truck id that you want to erase from the system");
        id = in.next();
        try{
            res= deliveryFacade.removeTruck(id);
            if (res.isErrorOccurred()){
                System.out.println(res.getErrorMessage());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }

    }


    public void createNewTruck(){
        String id, name;
        double netoWeight, totalWeight;
        Response res;
        System.out.println("Please enter truck details: truck id, model, neto weight, total weight");
        id = in.next();
        name = in.next();
        netoWeight = in.nextDouble();
        totalWeight = in.nextDouble();
        try{
            res= deliveryFacade.createTruck(id, name, netoWeight, totalWeight);
            if (res.isErrorOccurred()){
                System.out.println(res.getErrorMessage());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }
}
