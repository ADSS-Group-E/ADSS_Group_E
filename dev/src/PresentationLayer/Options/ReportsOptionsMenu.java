package PresentationLayer.Options;

import PresentationLayer.CommandLineInterface;

import java.util.ArrayList;
import java.util.Scanner;

public class ReportsOptionsMenu extends OptionsMenu{
    public ReportsOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);
        int i = 1;
        Scanner in = new Scanner(System.in);
        options.put(i++, new Option("Get report", () -> {

        }));

        options.put(i++, new Option("List all reports", () -> {

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

                    String report = parentCLI.getFacade().generateStockReport(cids,pids);
                    System.out.println(report);
                    break;
                case 2:
                    //Invalids report
                    System.out.println("To be implemented");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

            // Currently don't support historical data, so reports will be a snapshot of the current situation
        }));

        options.put(i++, new Option("Remove report", () -> {

        }));

        options.put(i, new Option( "Back",() -> System.out.println("Going back.")));
    }
}
