package PresentationLayer.Options;

import PresentationLayer.CommandLineInterface;
import PresentationLayer.ProductDTO;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represent the options menu of the reports.
 * The menu contains all the function that required:
 * Get report, List of all reports, generate report and remove report.
 * The instruction of this class can be found in the attached document.
 */

public class ReportsOptionsMenu extends OptionsMenu{
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
     * This function uses to get details of report by the ID of the report
     */
    public void getReport(){
        System.out.println("Please enter the report id for the report you wish to display:");
        int rid = in.nextInt();
        System.out.println(parentCLI.getFacade().getReport(rid));
    }

    /**
     * This function uses to get list of all the reports that exist
     */
    public void getReportList(){
        ArrayList<String> stringList = parentCLI.getFacade().getReportList();
        System.out.printf("%-10s%-20s%-20s%n", "RID","Created","Type");
        System.out.println(String.join("\n",stringList));
    }

    /**
     * This function uses to generate a new report
     * It's require to insert the type of the report, and the details by the type.
     * For stock report it's require category ID and product ID.
     */
    public void generateReport(){
        //Prompt for report type (stock or invalids)
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

                String stockReport = parentCLI.getFacade().generateStockReport(cids,pids);
                System.out.println(stockReport);
                break;
            case 2:
                String lowStockReport = parentCLI.getFacade().generateLowStockReport();
                System.out.println(lowStockReport);
                break;
            case 3:
                //Invalids report
                String invalidsReport = parentCLI.getFacade().generateInvalidsReport();
                System.out.println(invalidsReport);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    /**
     * This function uses to remove report by ID
     */
    public void removeReport(){
        System.out.println("Please enter the report id for the report you wish to remove:");
        int rid = in.nextInt();
        parentCLI.getFacade().removeReport(rid);
    }
}
