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
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter the details of the product you wish to add, by order: \n" +
                    "pid, name, storage location, store location, manufacturer, buying price, selling price, " +
                    "minimum amount, category, sub category, sub sub category");
            // TODO Do we need also storage & store? not sure.
            // How can we find Category object by his name?
            int pid = in.nextInt();
            String name = in.nextLine();
            String storageLocation = in.nextLine();
            String storeLocation = in.nextLine();
            String manufacturer = in.nextLine();
            double buyingPrice = in.nextDouble();
            double sellingPrice = in.nextDouble();
            int minAmount = in.nextInt();

            // How do I do that?! ->
            // Category category = in.next();
            // Category subCategory = in.next();
            // Category subSubCategory = in.next();
        }));

        options.put(i++, new Option( "Remove product",() -> {
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter the product id for the product you wish to remove:");
            int pid = in.nextInt();
            ProductDTO product = parentCLI.getFacade().removeProduct();
            //TODO implementProduct remove in Facade

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
