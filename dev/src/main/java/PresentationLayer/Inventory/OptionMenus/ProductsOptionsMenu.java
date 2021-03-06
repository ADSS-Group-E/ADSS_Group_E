package PresentationLayer.Inventory.OptionMenus;

import BusinessLayer.AcceptOrderHandler;
import BusinessLayer.Workers_Transport.DeliveryPackage.DeliveryFacade;
import PresentationLayer.CommandLineInterface;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;
import PresentationLayer.Option;
import PresentationLayer.OptionsMenu;
import PresentationLayer.Workers.DataTransferObjects.QualificationsDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents the options menu for Products.
 *
 * It displays a list of the various functions that a user can perform with Products
 * and prompts the user for their selection of which function to activate.
 *
 * The listed functions include - Get a product, List all products, Add a product, Remove a product,
 * add an item and remove an item.
 *
 * Once the user has selected a function, the class then executes the required activity accordingly.
 */

// Display the Products list of functions and prompt the user for their selection.
public class ProductsOptionsMenu extends OptionsMenu {
    private final DeliveryFacade deliveryFacade = DeliveryFacade.getInstance();

    public ProductsOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);
        int i=1;
        options.put(i++, new Option( "Accept order and update inventory",this::acceptOrder, QualificationsDTO.Storekeeper));
        options.put(i++, new Option( "Get product",this::getProduct));
        options.put(i++, new Option( "List all products",this::getProductList));
        options.put(i++, new Option( "Add product",this::addProduct, QualificationsDTO.Storekeeper));
        options.put(i++, new Option( "Remove product",this::removeProduct, QualificationsDTO.Storekeeper));
        options.put(i++, new Option( "Add item",this::addItem, QualificationsDTO.Storekeeper));
        options.put(i++, new Option( "Remove item",this::removeItem, QualificationsDTO.Storekeeper));
        options.put(i, new Option( "Back", this::back));
    }

    public void acceptOrder(){
        AcceptOrderHandler handler = parentCLI.getAcceptOrderHandler();
        System.out.println("Orders currently in the system:");
        try{
            deliveryFacade.printOrders();
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nSomething went wrong with printing the orders.");
        }
        System.out.println("Please enter the order id for the order you wish to accept:");
        int orderId = in.nextInt();
        if(!handler.checkOrderExists(orderId))
        {
            System.out.println("Order ID did not match any existing orders.");
            return;
        }

        HashMap<Integer, Integer> dismissals = new HashMap<>();
        System.out.println("Were there damaged or missing goods which need to be dismissed? (y/n)");
        String verify = in.next().trim();
        if (verify.equals("y")) {
            do {
                System.out.println("Please enter the product id for the product you wish to dismiss:");
                int pid = in.nextInt();
                if (handler.checkOrderHasItem(orderId, pid)){
                    System.out.println("Please enter the amount you wish to dismiss:");
                    int amount = in.nextInt();
                    dismissals.put(pid, amount);
                }
                System.out.println("Are there more damaged or missing goods which need to be dismissed? (y/n)");
                verify = in.next().trim();
            } while (verify.equals("y"));
        }
        try{
            handler.acceptOrder(orderId, dismissals);
            System.out.println("Successfully accepted the order!");
        }
        catch (Exception e){
            System.err.println("Something went wrong when accepting the order: " + e);
        }

    }

    /**
     * Prompt the user for the Product ID and then proceed to retrieve and display the product's details accordingly
     */
    public void getProduct(){
        System.out.println("Please enter the product id for the product you wish to display:");
        int pid = in.nextInt();
        ProductDTO product = parentCLI.getInventoryFacade().getProduct(pid);
        System.out.println(product);
    }

    /**
     * Display a list of all the products that exist
     */
    public void getProductList(){
        ArrayList<ProductDTO> DTOlist = parentCLI.getInventoryFacade().getProductList();
        if (DTOlist != null){
            System.out.printf("%-10s %s%n", "PID","Name");
            DTOlist.forEach((DTO)->System.out.printf("%-10s %s%n", DTO.getPid(),DTO.getName()));
        }
    }

    /**
     * This function adds a new product by prompting the user to enter the required information one field at a time.
     *
     * The required data includes Product ID, name, store location, storage location, manufacturer,
     * buying and selling price, minimum amount and product category.
     *
     * Once entered, the InventoryFacade function is called to save the newly input product information.
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

        // Add the new product by calling the InventoryFacade function with the data the user just entered.
        parentCLI.getInventoryFacade().addProduct(new ProductDTO(pid, name, storageLocation, storeLocation , manufacturer, sellingPrice, minAmount,categoryId, -1));
        System.out.println("The new product was added successfully.");
    }

    /**
     * This function prompts the user to end the ID of a product they wish to remove,
     * and then proceeds to remove the product by calling the InventoryFacade function
     */
    public void removeProduct(){
        System.out.println("Please enter the product id for the product you wish to remove:");
        int pid = in.nextInt();
        System.out.println("Are you sure you want to remove the product? Enter \"y\" to remove.");
        String verify = in.next().trim();
        if (verify.equals("y")) {
            // Remove the product by calling the InventoryFacade function with the ID the user just entered.
            parentCLI.getInventoryFacade().removeProduct(pid);
            System.out.println("The product was removed successfully.");
        }
        else {
            System.out.println("Product removal was cancelled.");
        }
    }

    /**
     * This function adds a new item by prompting the user to enter the required information one field at a time.
     *
     * The required data includes Product ID, ItemGroup ID, Expiration date (Year, month, day) and the item's location.
     *
     * Once entered, the InventoryFacade function is called to save the newly input item information.
     */

    public void addItem(){
        System.out.println("Please fill out the details for the new discount when prompted and press enter.");

        System.out.println("Product ID:");
        int pid = in.nextInt();

        System.out.println("Quantity:");
        int quantity = in.nextInt();

        System.out.println("Price Bought At:");
        double priceBoughtAt = in.nextDouble();

        System.out.println("Expiration Year:");
        int expirationYear = in.nextInt();

        System.out.println("Expiration Month:");
        int expirationMonth = in.nextInt();

        System.out.println("Expiration Day:");
        int expirationDay = in.nextInt();

        //Convert the entered data into a valid expiration date.
        LocalDateTime expiration = LocalDate.of(expirationYear,expirationMonth,expirationDay).atStartOfDay();

        //Prompt the user for the item's location so it can be created accordingly
        System.out.println("ItemGroup location?\n1 => Store\n2 => Storage");
        int choice = in.nextInt();

        // Add the new item by calling the InventoryFacade function with the data the user just entered.
        switch (choice){
            case 1:
                parentCLI.getInventoryFacade().addItemToStore(pid,quantity, priceBoughtAt, expiration);
                System.out.println("The new item was added successfully.");
                break;
            case 2:
                parentCLI.getInventoryFacade().addItemToStorage(pid, quantity, priceBoughtAt, expiration);
                System.out.println("The new item was added successfully.");
                break;
            default:
                System.out.println("Invalid item location choice.");
        }
    }

    /**
     * This function prompts the user to identify an existing item by entering it's Product ID and ItemGroup ID,
     * and then proceeds to remove the item by calling the InventoryFacade function
     */
    public void removeItem(){
        System.out.println("Please enter the PRODUCT ID of the item you wish to remove:");
        int pid = in.nextInt();
        System.out.println("Please enter the ITEM ID of the item you wish to remove:");
        int id = in.nextInt();
        System.out.println("Are you sure you want to remove the item? Enter \"y\" to remove.");
        String verify = in.next().trim();
        if (verify.equals("y")) {
            // Remove the item by calling the InventoryFacade function with the data the user just entered.
            parentCLI.getInventoryFacade().removeItem(pid,id);
            System.out.println("The item was removed successfully.");
        }
        else {
            System.out.println("ItemGroup removal was cancelled.");
        }
    }
}
