package InventoryModule.PresentationLayer.Options;

import InventoryModule.PresentationLayer.CommandLineInterface;
import InventoryModule.PresentationLayer.DataTransferObjects.DiscountDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This class represents the options menu for Discounts.
 *
 * It displays a list of the various functions that a user can perform with Discounts
 * and prompts the user for their selection of which function to activate.
 *
 * The listed functions include - Get a Discount, List all Discounts, Add a Discount and Remove a Discount.
 *
 * Once the user has selected a function, the class then executes the required activity accordingly.
 */

public class DiscountsOptionsMenu extends OptionsMenu{

    // Display the Discount's list of functions and prompt the user for their selection.
    public DiscountsOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);
        int i = 1;
        options.put(i++, new Option("Get discount",this::getDiscount));

        options.put(i++, new Option("List all discounts", this::getDiscountList));

        options.put(i++, new Option("Add discount", this::addDiscount));

        options.put(i++, new Option("Remove discount", this::removeDiscount));

        options.put(i, new Option( "Back", this::back));
    }

    /**
     * Prompt the user for the Discount ID and then proceed to retrieve and display the Discount's details accordingly
     */
    public void getDiscount(){
        System.out.println("Please enter the Discount ID for the discount you wish to display:");
        int did = in.nextInt();
        DiscountDTO discount = parentCLI.getFacade().getDiscount(did);
        System.out.println(discount);
    }

    /**
     * Display a list of all the Discounts that exist
     */
    public void getDiscountList(){
        ArrayList<DiscountDTO> DTOlist = parentCLI.getFacade().getDiscountList();
        System.out.printf("%-10s %s%n", "DID","Name");
        DTOlist.forEach((DTO)->System.out.printf("%-10s %s%n", DTO.getDid(),DTO.getName()));
    }

    /**
     * This function adds a new Discount by prompting the user to enter the required information one field at a time.
     *
     * The required data includes Discount type (Buying or Selling), ID, name, actual discount percentage,
     * start and end dates (year, month and day),and the Category and Product ID's that the discount can be applied to.
     *
     * Once entered, the Facade function is called to save the newly input Discount information.
     */
    public void addDiscount(){
        System.out.println("Discount type?\n1 => Buying\n2 => Selling");
        int choice = in.nextInt();
        String type;
        switch (choice){
            case 1:
                type = "Buying";
                break;
            case 2:
                type = "Selling";
                break;
            default:
                System.out.println("Invalid Discount Type choice.");
                return;
        }

        // TODO validate entered input
        System.out.println("Please fill out the details for the new discount when prompted and press enter.");

        System.out.println("ID:");
        int did = in.nextInt();

        in.nextLine();
        System.out.println("Name:");
        String name = in.nextLine();

        System.out.println("Discount Percentage:");
        double discountPercentage = in.nextDouble()/100;

        System.out.println("Start Year:");
        int startYear = in.nextInt();

        System.out.println("Start Month:");
        int startMonth = in.nextInt();

        System.out.println("Start Day:");
        int startDay = in.nextInt();

        //Convert the entered data into a valid start date.
        LocalDateTime startDate = LocalDate.of(startYear,startMonth,startDay).atStartOfDay();

        System.out.println("End Year:");
        int endYear = in.nextInt();

        System.out.println("End Month:");
        int endMonth = in.nextInt();

        System.out.println("End Day:");
        int endDay = in.nextInt();

        //Convert the entered data into a valid end date.
        LocalDateTime endDate = LocalDate.of(endYear,endMonth,endDay).atStartOfDay();

        ArrayList<Integer> cids = new ArrayList<>();
        ArrayList<Integer> pids = new ArrayList<>();
        in.nextLine();
        System.out.println("Enter CIDs of categories you wish to include (separated by spaces), or press enter to skip.");
        String[] lineVector = in.nextLine().split(" ");
        for (String cid : lineVector) {
            try{
                if (!cid.equals(""))
                    cids.add(Integer.parseInt(cid));
            }
            catch (NumberFormatException e){
                System.out.println("Invalid CID input - " + e.getMessage());
            }
        }

        System.out.println("Enter PIDs of specific products you wish to include (separated by spaces), or press enter to skip.");
        lineVector = in.nextLine().split(" ");
        for (String pid : lineVector) {
            try{
                if (!pid.equals(""))
                    pids.add(Integer.parseInt(pid));
            }
            catch (NumberFormatException e){
                System.out.println("Invalid PID input - " + e.getMessage());
            }
        }

        if (cids.isEmpty() && pids.isEmpty()){
            System.out.println("At least one CID or PID must be entered for a new discount.");
            return;
        }

        // Add the new discount by calling the Facade function with the data the user just entered.
        parentCLI.getFacade().addDiscount(did,name,discountPercentage,startDate,endDate,cids,pids,type);
        System.out.println("The new discount was added successfully.");
    }

    /**
     * This function prompts the user to identify an existing discount by entering it's ID,
     * and then proceeds to remove the discount by calling the Facade function
     */
    public void removeDiscount(){
        System.out.println("Please enter the discount id you wish to remove:");
        int did = in.nextInt();
        System.out.println("Are you sure you want to remove the discount? Enter \"y\" to remove.");
        String verify = in.next().trim();
        if (verify.equals("y")) {
            // Remove the discount by calling the Facade function with the data the user just entered.
            parentCLI.getFacade().removeDiscount(did);
            System.out.println("The discount was removed successfully.");
        }
        else {
            System.out.println("Discount removal was cancelled.");
        }
    }
}
