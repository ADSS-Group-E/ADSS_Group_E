package PresentationLayer.Options;

import BusinessLayer.Category;
import BusinessLayer.Item;
import BusinessLayer.Product;
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

            int pid = in.nextInt();
            String name = in.nextLine();
            String storageLocation = in.nextLine();
            String storeLocation = in.nextLine();
            String manufacturer = in.nextLine();
            double buyingPrice = in.nextDouble();
            double sellingPrice = in.nextDouble();
            int minAmount = in.nextInt();

            // TODO How can I find Category object by his name? ->
            Category category = null;
            Category subCategory = null;
            Category subSubCategory = null;

            Product p = new Product(pid, name, storageLocation, storeLocation, manufacturer,
                    buyingPrice, sellingPrice, minAmount, category, subCategory, subSubCategory);
            // ProductDTO product = parentCLI.getFacade().addProduct(p);

        }));

        options.put(i++, new Option( "Remove product",() -> {
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter the product id for the product you wish to remove:");
            int pid = in.nextInt();
            System.out.println("Are you sure you want to remove the product? type \"1\" to remove.");
            int verify = in.nextInt();
            if (verify == 1) {
                // ProductDTO product = parentCLI.getFacade().removeProduct(pid);
            }
            else {
                System.out.println("Product did not removed");
            }
            //TODO implement Product remove in Facade
        }));

        options.put(i++, new Option( "Add item",() -> {
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter the item id and the expiration date for the item you wish to add:");
            int pid = in.nextInt();
            String expiration = in.nextLine();
            // Item item = new Item(pid, expiration);
            System.out.println("Do you add the item to the sore or to the storage? type \"1\" for store, \"2\" for storage");
            int storeStorage = in.nextInt();
            if (storeStorage == 1) {
                // ProductDTO product = parentCLI.getFacade().addItemToStore(pid, expiration);
            }
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
