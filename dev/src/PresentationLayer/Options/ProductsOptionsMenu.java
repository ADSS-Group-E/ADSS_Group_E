package PresentationLayer.Options;

import PresentationLayer.CommandLineInterface;
import PresentationLayer.ProductDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents the options menu of the products.
 * The menu contains all the function that required:
 * Get product, List of all products, Add product, remove product, add item and remove item.
 * The instruction of this class can be found in the attached document.
 */

public class ProductsOptionsMenu extends OptionsMenu{
    public ProductsOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);
        int i=1;
        options.put(i++, new Option( "Get product",this::getProduct));
        options.put(i++, new Option( "List all products",this::getProductList));
        options.put(i++, new Option( "Add product",this::addProduct));
        options.put(i++, new Option( "Remove product",this::removeProduct));
        options.put(i++, new Option( "Add item",this::addItem));
        options.put(i++, new Option( "Remove item",this::removeItem));
        options.put(i, new Option( "Back", this::back));
    }

    /**
     * This function uses to get details of product by the ID of the product
     */
    public void getProduct(){
        System.out.println("Please enter the product id for the product you wish to display:");
        int pid = in.nextInt();
        ProductDTO product = parentCLI.getFacade().getProduct(pid);
        System.out.println(product);
    }

    /**
     * This function uses to get list of all the products that exist
     */
    public void getProductList(){
        ArrayList<ProductDTO> DTOlist = parentCLI.getFacade().getProductList();
        System.out.printf("%-10s %s%n", "PID","Name");
        DTOlist.forEach((DTO)->System.out.printf("%-10s %s%n", DTO.getPid(),DTO.getName()));
    }

    /**
     * This function uses to add a new product.
     * It's require to insert ID, name, store location, storage location, manufacturer, buying and selling price
     * minimum amount, and catogory of the product.
     */
    public void addProduct(){

        //TODO Validate input
        System.out.println("Please fill out the details for the new product when prompted and press enter.");

        System.out.println("ID:");
        int pid = in.nextInt();

        in.nextLine();
        System.out.println("Name:");
        String name = in.nextLine();

        System.out.println("Storage Location:");
        String storageLocation = in.nextLine();

        System.out.println("Store Location:");
        String storeLocation = in.nextLine();

        System.out.println("Manufacturer:");
        String manufacturer = in.nextLine();

        System.out.println("Buying Price:");
        double buyingPrice = in.nextDouble();

        System.out.println("Selling Price:");
        double sellingPrice = in.nextDouble();

        System.out.println("Minimum Amount:");
        int minAmount = in.nextInt();

        System.out.println("Category ID:");
        int categoryId = in.nextInt();

        parentCLI.getFacade().addProduct(new ProductDTO(pid, name, storageLocation, storeLocation , manufacturer, buyingPrice, sellingPrice, minAmount,categoryId));
    }

    /**
     * This function uses to remove product by ID
     */
    public void removeProduct(){
        System.out.println("Please enter the product id for the product you wish to remove:");
        int pid = in.nextInt();
        System.out.println("Are you sure you want to remove the product? Enter \"y\" to remove.");
        String verify = in.next().trim();
        if (verify.equals("y")) {
            parentCLI.getFacade().removeProduct(pid);
        }
        else {
            System.out.println("Cancelled.");
        }
    }

    /**
     * This function uses to add a new item.
     * It's require to insert PRODUCT ID, ITEM ID and expiration date.
     */
    public void addItem(){
        System.out.println("Please fill out the details for the new discount when prompted and press enter.");

        System.out.println("Product ID:");
        int pid = in.nextInt();

        System.out.println("Item ID:");
        int id = in.nextInt();

        System.out.println("Expiration Year:");
        int expirationYear = in.nextInt();

        System.out.println("Expiration Month:");
        int expirationMonth = in.nextInt();

        System.out.println("Expiration Day:");
        int expirationDay = in.nextInt();

        LocalDateTime expiration = LocalDate.of(expirationYear,expirationMonth,expirationDay).atStartOfDay();

        System.out.println("Item location?\n1 => Store\n2 => Storage");
        int choice = in.nextInt();
        switch (choice){
            case 1:
                parentCLI.getFacade().addItemToStore(pid,id,expiration);
                break;
            case 2:
                parentCLI.getFacade().addItemToStorage(pid,id,expiration);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    /**
     * This function uses to remove item by product ID and item ID
     */
    public void removeItem(){
        System.out.println("Please enter the PRODUCT ID of the item you wish to remove:");
        int pid = in.nextInt();
        System.out.println("Please enter the ITEM ID of the item you wish to remove:");
        int id = in.nextInt();
        System.out.println("Are you sure you want to remove the item? Enter \"y\" to remove.");
        String verify = in.next().trim();
        if (verify.equals("y")) {
            parentCLI.getFacade().removeItem(pid,id);
        }
        else {
            System.out.println("Cancelled.");
        }
    }
}
