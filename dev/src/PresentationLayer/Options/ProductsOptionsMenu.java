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
            System.out.println("Please enter the pid and the name of the product you wish to add");
            int pid = in.nextInt();
            String name = in.nextLine();
            System.out.println("Please enter the storage location and the store location of the product you wish to add");
            String storageLocation = in.nextLine();
            String storeLocation = in.nextLine();
            System.out.println("Please enter the amount in storage and amount in store of the product you wish to add");
            int amountInStorage = in.nextInt();
            int amountInStore = in.nextInt();
            System.out.println("Please enter the manufacturer of the product you wish to add");
            String manufacturer = in.nextLine();
            System.out.println("Please enter the buying price and the selling price of the product you wish to add");
            double buyingPrice = in.nextDouble();
            double sellingPrice = in.nextDouble();
            System.out.println("Please enter the minimum amount of the product you wish to add");
            int minAmount = in.nextInt();
            System.out.println("Please enter the cid of the category of the product you wish to add");
            int CIDCategory = in.nextInt();
            System.out.println("Please enter the cid of the SUB category of the product you wish to add");
            int CIDSubCategory = in.nextInt();
            System.out.println("Please enter the cid of the SUB SUB category of the product you wish to add");
            int CIDSubSubCategory = in.nextInt();

            // TODO add Category to product
            // parentCLI.getFacade().addProduct(pid, name, storageLocation, storeLocation, amountInStorage, amountInStore, manufacturer, buyingPrice, sellingPrice, minAmount, , , );
        }));

        options.put(i++, new Option( "Remove product",() -> {
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter the product id for the product you wish to remove:");
            int pid = in.nextInt();
            System.out.println("Are you sure you want to remove the product? type \"1\" to remove.");
            int verify = in.nextInt();
            if (verify == 1) {
                parentCLI.getFacade().removeProduct(pid);
            }
            else {
                System.out.println("Product did not removed");
            }
        }));

        options.put(i++, new Option( "Add item",() -> {
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter the PRODUCT id of the item you wish to add:");
            int pid = in.nextInt();
            System.out.println("Please enter the ITEM id of the item you wish to add:");
            int id = in.nextInt();
            System.out.println("Please enter the expiration date of the item you wish to add by the format dd-mm-yyyy:");
            String expiration = in.nextLine();
            // TODO get date from user and insert to product
            // Item item = new Item(pid, dateTime);
            System.out.println("Do you add the item to the sore or to the storage? type \"1\" for store, \"2\" for storage");
            /*
            int storeStorage = in.nextInt();
            if (storeStorage == 1) {
                 parentCLI.getFacade().addItemToStore(pid, id, dateTime);
             }
             else if (storeStorage == 2) {
                 parentCLI.getFacade().addItemToStorage(pid, id, dateTime);
             }
            System.out.println("The item id is " + id + "and the expiration date is: " + dateTime);
             */
        }));

        options.put(i++, new Option( "Remove item",() -> {
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter the PRODUCT id of the item you wish to remove:");
            int pid = in.nextInt();
            System.out.println("Please enter the id of the item you wish to remove:");
            int id = in.nextInt();
            System.out.println("Are you sure you want to remove the product? type \"1\" to remove.");
            int verify = in.nextInt();
            if (verify == 1) {
                parentCLI.getFacade().removeItem(pid, id);
            }
            else {
                System.out.println("Product did not removed");
            }
        }));
        options.put(i, new Option( "Back",() -> System.out.println("Going back.")));
    }
}
