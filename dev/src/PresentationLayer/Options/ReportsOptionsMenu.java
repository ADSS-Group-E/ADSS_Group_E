package PresentationLayer.Options;

import PresentationLayer.CommandLineInterface;
import PresentationLayer.ProductDTO;

import java.util.ArrayList;
import java.util.Scanner;

public class ReportsOptionsMenu extends OptionsMenu{
    public ReportsOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);
        int i = 1;
        Scanner in = new Scanner(System.in);
        options.put(i++, new Option("Get report", () -> {
            System.out.println("Please enter the report id for the report you wish to display:");
            int rid = in.nextInt();
            System.out.println(parentCLI.getFacade().getReport(rid));
        }));

        options.put(i++, new Option("List all reports", () -> {

            ArrayList<String> stringList = parentCLI.getFacade().getReportList();
            System.out.printf("%-10s%-20s%-20s%n", "RID","Created","Type");
            System.out.println(String.join("\n",stringList));
        }));

        options.put(i++, new Option("Generate report", () -> {
            //Prompt for report type (stock or invalids)
            System.out.println("Enter 1 for stock report, 2 for invalids report");
            int choice = in.nextInt();
            in.nextLine();
            switch (choice){
                case 1:
                    //Stock report
                    ArrayList<Integer> cids = new ArrayList<>();
                    ArrayList<Integer> pids = new ArrayList<>();
                    System.out.println("Enter CIDs of categories you wish to include (separated by spaces), or press enter to skip.");
                    // TODO need to test skipping, and check input
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
                                cids.add(Integer.parseInt(pid));
                        }
                        catch (NumberFormatException e){
                            System.out.println("Invalid input - " + e.getMessage());
                        }
                    }

                    String stockReport = parentCLI.getFacade().generateStockReport(cids,pids);
                    System.out.println(stockReport);
                    break;
                case 2:
                    //Invalids report
                    String invalidsReport = parentCLI.getFacade().generateInvalidsReport();
                    System.out.println(invalidsReport);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

            // Currently don't support historical data, so reports will be a snapshot of the current situation
        }));

        options.put(i++, new Option("Remove report", () -> {

        }));

        options.put(i, new Option( "Back",() -> {
            System.out.println("Going back.");
            goBack=true;
        }));
    }
}
