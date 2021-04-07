package PresentationLayer.Options;

import PresentationLayer.CommandLineInterface;

public class ReportsOptionsMenu extends OptionsMenu{
    public ReportsOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);
        int i = 1;
        options.put(i++, new Option("Get report", () -> {

        }));

        options.put(i++, new Option("List all reports", () -> {

        }));

        options.put(i++, new Option("Generate report", () -> {
            //Prompt for report type (stock or invalids)
            // Currently don't support historical data, so reports will be a snapshot of the current situation
        }));

        options.put(i++, new Option("Remove report", () -> {

        }));

        options.put(i, new Option( "Back",() -> System.out.println("Going back.")));
    }
}
