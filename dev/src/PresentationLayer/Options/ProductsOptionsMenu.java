package PresentationLayer.Options;

import PresentationLayer.CommandLineInterface;
import PresentationLayer.ProductDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
            //TODO Validate input
            Scanner in = new Scanner(System.in);
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
        }));

        options.put(i++, new Option( "Remove product",() -> {
            Scanner in = new Scanner(System.in);
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
        }));

        options.put(i++, new Option( "Add item",() -> {
            // TODO Check this
            Scanner in = new Scanner(System.in);
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
        }));

        options.put(i++, new Option( "Remove item",() -> {
            // TODO Check this
            Scanner in = new Scanner(System.in);
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
        }));

        options.put(i, new Option( "Back",() -> {
            System.out.println("Going back.");
            goBack=true;
        }));
    }
}
