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

            // Currently don't support historical data, so reports will be a snapshot of the current situation
        }));

        options.put(i++, new Option("Remove report", () -> {
            System.out.println("Please enter the report id for the report you wish to remove:");
            int rid = in.nextInt();
            parentCLI.getFacade().removeReport(rid);
        }));

        options.put(i, new Option( "Back",() -> {
            System.out.println("Going back.");
            goBack=true;
        }));
    }
}
