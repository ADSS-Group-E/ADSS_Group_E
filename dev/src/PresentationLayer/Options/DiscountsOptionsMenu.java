package PresentationLayer.Options;

import PresentationLayer.CommandLineInterface;
import PresentationLayer.DiscountDTO;
import PresentationLayer.ProductDTO;

import java.util.ArrayList;
import java.util.Scanner;

public class DiscountsOptionsMenu extends OptionsMenu{

    public DiscountsOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);
        int i = 1;
        options.put(i++, new Option("Get discount", () -> {
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter the discount id for the discount you wish to display:");
            int did = in.nextInt();
            DiscountDTO discount = parentCLI.getFacade().getDiscount(did);
            System.out.println(discount);
        }));

        options.put(i++, new Option("List all discounts", () -> {
            ArrayList<DiscountDTO> DTOlist = parentCLI.getFacade().getDiscountList();
            System.out.printf("%-10s %s%n", "DID","Name");
            DTOlist.forEach((DTO)->System.out.printf("%-10s %s%n", DTO.getDid(),DTO.getName()));
        }));

        options.put(i++, new Option("Add discount", () -> {
            //TODO implement
        }));

        options.put(i++, new Option("Remove discount", () -> {
            //TODO implement
        }));

        options.put(i, new Option( "Back",() -> {
            System.out.println("Going back.");
            goBack=true;
        }));
    }
}
