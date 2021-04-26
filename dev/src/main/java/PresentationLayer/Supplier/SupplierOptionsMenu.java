package PresentationLayer.Supplier;

import PresentationLayer.CommandLineInterface;
import PresentationLayer.Option;
import PresentationLayer.OptionsMenu;

import java.util.ArrayList;
import java.util.HashMap;



public class SupplierOptionsMenu extends OptionsMenu {
    public SupplierOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);
        int i=1;
        options.put(i++, new Option( "Add supplier",this::addSupplier));
        options.put(i++, new Option( "Create order",this::createOrder));
        options.put(i++, new Option( "Get weekly orders",this::getWeeklyOrders));
        options.put(i++, new Option( "Update order item quantity",this::updateQuantity));
        options.put(i++, new Option( "Delete item from order",this::deleteItem));
        options.put(i, new Option( "Back", this::back));
    }

    InputService in = InputService.getInstance();
    OutputService out = OutputService.getInstance();
    ServiceController service = ServiceController.getInstance(); //initializes empty objects


    public void addSupplier(){

        //get supplier information
        String name = in.next("Enter supplier name: ");
        int companyNumber = in.nextInt("Enter company number: ");
        String paymentMethod = in.next("Enter payment method: ");
        String bankAccount = in.next("Enter bank account: ");
        ArrayList<String[]> items = PresentationHandler.getInstance().createItemList(); //creates item list
        ArrayList<String[]> contacts = PresentationHandler.getInstance().createContactList(); //creates contact list
        boolean hasQuantityWriter = PresentationHandler.getInstance().manageQuantityWriter(); //asks if Quantity Writer is needed
        if (!hasQuantityWriter) //if not
            service.register(name, companyNumber, paymentMethod, bankAccount, items, contacts); //register without him
        else { //if does need quantity writer
            int regCostumer = in.nextInt("Regular costumer discount: %"); //asks for discount given to a regular costumer
            while (regCostumer >= service.getMaxDiscount()) { //while the max discount is illegal
                out.println("Please try again.");
                regCostumer = in.nextInt("Regular costumer discount: %"); //try again
            }
            int minPrice = in.nextInt("Minimum buy price for discount: "); //minimum order price for discount to happen
            HashMap<Integer, Integer> discountSteps = PresentationHandler.getInstance().createDiscountList(); //creates a discount list
            service.register(name, companyNumber, paymentMethod, bankAccount, items, contacts, regCostumer, minPrice, discountSteps); //registers the supplier
        }
    }

    public void createOrder(){
        if (PresentationHandler.getInstance().showSupplierInfo()) { //if there are no suppliers to order from skip the case
            int input = 0;
            ArrayList<String> supplierItems = null;
            while (supplierItems == null) { //while the supplier number is illegal
                input = in.nextInt("Enter number of the supplier: ");
                supplierItems = service.getItemsFromSupplier(input);
            }
            boolean constantDelivery = in.nextBoolean("\nConstant Delivery? true/false "); //constant delivery?
            boolean needsDelivery = in.nextBoolean("In need of delivery? true/false "); //if the costumer needs us to transfer his items
            out.println("\nSupplier items: ");
            out.println(PresentationHandler.getInstance().showSupplierItems(supplierItems)); //show the supplier items
            ArrayList<String[]> items = PresentationHandler.getInstance().createItemList(input); //create item list
            out.println("Total Order price: " + service.createOrder(input, needsDelivery, constantDelivery, items) + ""); //create order and print it's total price
        }
    }

    public void getWeeklyOrders(){
        ArrayList<String[]> orders = service.getRegularOrdersToStrings(); //gets data from the system
        for (String[] order : orders) {
            for (String s : order) { //print all orders
                out.println(s);
            }
        }
    }

    public void updateQuantity(){
        ArrayList<String[]> orders = service.getAllItems(); //gets all items
        if (orders.size() != 0) { //if there are no orders the case skips
            for (int i = 0; i < orders.size(); i++) { //prints all of the items
                String[] item = orders.get(i);
                out.println(i + ") [Supplier Number: " + item[0] + "\n" +
                        "Name: " + item[3] + "\n" +
                        "Price: " + item[4] + "\n" +
                        "Quantity: " + item[5] + "]\n");
            }
            int orderNumber = in.nextInt("Select an item Number: ");
            int newQuantity = in.nextInt("Enter new quantity you wish to order for the item you chose: ");
            if (orderNumber >= 0 && orderNumber < orders.size()) { //get order number
                String[] orderToChange = orders.get(orderNumber);
                if (!service.updateOrderItemQuantity(Integer.parseInt(orderToChange[0]), Integer.parseInt(orderToChange[1]),
                        Integer.parseInt(orderToChange[2]), newQuantity)) { //if can't update
                    out.println("The new quantity is illegal"); //the new quantity is illegal
                }
            } else { //illegal item number
                out.println("The item number is illegal");
            }
        }
    }

    public void deleteItem(){
        ArrayList<String[]> orders = service.getAllItems(); //gets all items
        if (orders.size() != 0) { //if there are no order we skip the case
            for (int i = 0; i < orders.size(); i++) {
                String[] item = orders.get(i);
                out.println(i + ") [Supplier Number: " + item[0] + "\n" +
                        "Name: " + item[3] + "\n" +
                        "Price: " + item[4] + "\n" +
                        "Quantity: " + item[5] + "]\n");
            }
            int orderNumber = in.nextInt("Select an item Number: ");
            if (orderNumber >= 0 && orderNumber < orders.size()) {
                String[] orderToChange = orders.get(orderNumber);
                if (!service.deleteOrderItem(Integer.parseInt(orderToChange[0]), Integer.parseInt(orderToChange[1]),
                        Integer.parseInt(orderToChange[2]))) {
                    out.println("The new quantity is illegal");
                }
            } else {
                out.println("The item number is illegal");
            }
        }
    }
}
