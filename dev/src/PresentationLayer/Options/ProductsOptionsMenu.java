package PresentationLayer.Options;

import PresentationLayer.CommandLineInterface;
import PresentationLayer.ProductDTO;

import java.util.ArrayList;
import java.util.Scanner;

public class ProductsOptionsMenu extends OptionsMenu{
    public ProductsOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);
        int i=1;
        options.put(i++, new Option( "Get product",() -> {
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter the product id for the product you wish to display:");
            int pid = in.nextInt();
            ProductDTO product = parentCLI.getFacade().getProduct(pid);
            System.out.println(product);
        }));
        options.put(i++, new Option( "List all products",() -> {

            ArrayList<ProductDTO> DTOlist = parentCLI.getFacade().getProductList();
            System.out.printf("%-10s %s%n", "PID","Name");
            DTOlist.forEach((DTO)->System.out.printf("%-10s %s%n", DTO.getPid(),DTO.getName()));
        }));

        options.put(i++, new Option( "Add product",() -> {
            //Prompt for all necessary fields
        }));

        options.put(i++, new Option( "Remove product",() -> {
            //Prompt for pid
            //Warning message asking to confirm deletion
        }));

        options.put(i++, new Option( "Add item",() -> {
            //Prompt for pid
            //Prompt for store or storage
            //Prompt for item id
            //Prompt for expiration

        }));

        options.put(i++, new Option( "Remove item",() -> {
            //Prompt for pid
            //Prompt for item id
            //Warning message asking to confirm deletion
        }));

        // TODO AddProduct, AddItemTo, remove
        //
        options.put(i, new Option( "Back",() -> System.out.println("Going back.")));
    }
}
