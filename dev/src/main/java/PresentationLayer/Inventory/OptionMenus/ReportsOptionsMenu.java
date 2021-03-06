package PresentationLayer.Inventory.OptionMenus;

import BusinessLayer.OrderFromReportHandler;
import PresentationLayer.CommandLineInterface;
import PresentationLayer.Option;
import PresentationLayer.OptionsMenu;

import java.util.ArrayList;

/**
 * This class represents the options menu for Reports.
 *
 * It displays a list of the various functions that a user can perform with Reports
 * and prompts the user for their selection of which function to activate.
 *
 * The listed functions include - Get a Report, List all Discounts, Generate a Report and Remove a Report.
 *
 * Once the user has selected a function, the class then executes the required activity accordingly.
 */

// Display the Report's list of functions and prompt the user for their selection.
public class ReportsOptionsMenu extends OptionsMenu {
    OrderFromReportHandler orderFromReportHandler = parentCLI.getOrderFromReportHandler();

    public ReportsOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);
        int i = 1;

        options.put(i++, new Option("Get report",this::getReport));

        options.put(i++, new Option("List all reports", this::getReportList));

        options.put(i++, new Option("Generate report", this::generateReport));

        options.put(i++, new Option("Remove report",this::removeReport));

        options.put(i, new Option( "Back", this::back));
    }

    /**
     * Prompt the user for the Report ID and then proceed to retrieve and display the Report's details accordingly
     * Note that a report must first be generated before it can be retrieved.
     * After generating a report use the list report function to see the RID that was created for the report.
     */
    public void getReport(){
        System.out.println("Please enter the report id for the report you wish to display:");
        int rid = in.nextInt();
        System.out.println(parentCLI.getInventoryFacade().getReport(rid));
    }

    /**
     * Display a list of all the Reports that were generated
     */
    public void getReportList(){
        ArrayList<String> stringList = parentCLI.getInventoryFacade().getReportList();
        System.out.printf("%-10s%-20s%-20s%n", "RID","Created","Type");
        System.out.println(String.join("\n",stringList));
    }

    /**
     * This function prompts the user for the input required to generate a new report
     *
     * It requires to enter the type of the report, and then the report's details based on its type.
     *
     * For stock reports it requires a category ID and a product ID.
     *
     * After being generated successfully the report is stored with a unique RID
     */
    public void generateReport(){
        //Prompt for report type (stock, low stock or invalids)
        System.out.println("Report type?\n1 => Stock\n2 => Low Stock\n3 => Invalids");
        int choice = in.nextInt();
        in.nextLine();
        switch (choice){
            case 1:
                //Stock report
                ArrayList<Integer> cids = new ArrayList<>();
                ArrayList<Integer> pids = new ArrayList<>();
                System.out.println("Enter CIDs of categories you wish to include (separated by spaces), or press enter to skip.");
                String[] lineVector = in.nextLine().split(" ");
                for (String cid : lineVector) {
                    try{
                        if (!cid.equals(""))
                            cids.add(Integer.parseInt(cid));
                    }
                    catch (NumberFormatException e){
                        System.out.println("Invalid input - " + e.getMessage());
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
                        System.out.println("Invalid input - " + e.getMessage());
                    }
                }

                int stockRid = parentCLI.getInventoryFacade().generateStockReport(cids,pids);
                String stockReport = parentCLI.getInventoryFacade().getReport(stockRid);
                System.out.println(stockReport);
                break;
            case 2:
                int lowStockRid =parentCLI.getInventoryFacade().generateLowStockReport();
                String lowStockReport = parentCLI.getInventoryFacade().getReport(lowStockRid);
                System.out.println(lowStockReport);
                System.out.println("Would you like to generate an automatic order from suppliers based on this report? (y/n)");
                String verify = in.next().trim();
                if (!verify.equals("y")) {
                    break;
                }
                String proposedOrder = orderFromReportHandler.proposeOrderFromReport(lowStockRid);
                System.out.println(proposedOrder);
                System.out.println("Confirm this order: (y/n)");
                verify = in.next().trim();
                if (!verify.equals("y")) {
                    break;
                }
                System.out.println("Does this order need a delivery?: (y/n)");
                verify = in.next().trim();
                orderFromReportHandler.createOrderFromReport(lowStockRid, !verify.equals("y"));
                System.out.println("The order was created successfully.");
                break;
            case 3:
                //Invalids report
                int invalidsRid =parentCLI.getInventoryFacade().generateInvalidsReport();
                String invalidsReport = parentCLI.getInventoryFacade().getReport(invalidsRid);
                System.out.println(invalidsReport);
                break;
            default:
                System.out.println("Invalid Report Type choice.");
        }
    }

    /**
     * This function prompts the user to identify an existing report by entering it's ID,
     * and then proceeds to remove the report by calling the InventoryFacade function
     */
    public void removeReport(){
        System.out.println("Please enter the report id for the report you wish to remove:");
        int rid = in.nextInt();
        System.out.println("Are you sure you want to remove the report? Enter \"y\" to remove.");
        String verify = in.next().trim();
        if (verify.equals("y")) {
           // Remove the report by calling the InventoryFacade function with the data the user just entered.
           parentCLI.getInventoryFacade().removeReport(rid);
            System.out.println("The report was removed successfully.");
        }
        else {
            System.out.println("Report removal was cancelled.");
        }
    }
}
