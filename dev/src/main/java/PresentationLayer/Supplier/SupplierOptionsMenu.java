package PresentationLayer.Supplier;

import PresentationLayer.CommandLineInterface;
import PresentationLayer.Option;
import PresentationLayer.OptionsMenu;
import PresentationLayer.Supplier.DataTransferObjects.OrderDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierItemDTO;

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

    private InputService in = InputService.getInstance();
    private OutputService out = OutputService.getInstance();
    private ServiceController service = ServiceController.getInstance(); //initializes empty objects


    private void addSupplier(){

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

    private void createOrder(){
        if (PresentationHandler.getInstance().showSupplierInfo()) { //if there are no suppliers to order from skip the case
            int input = 0;
            ArrayList<SupplierItemDTO> supplierItems = new ArrayList<>();
            while (supplierItems.size() == 0) { //while the supplier number is illegal
                input = in.nextInt("Enter company number of the supplier: ");
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

    private void getWeeklyOrders(){
        ArrayList<OrderDTO> orders = service.getRegularOrdersToStrings(); //gets data from the system
        for (OrderDTO order : orders) {
            out.println(String.format("[ID: %d, Date: %s, Needs Delivery: %s, Periodic Delivery: %s]", order.getId(), order.getDate(), order.getNeedsDelivery() == 1, order.getPeriodicDelivery() == 1));
            out.println("Items:");
            for (SupplierItemDTO item : order.getOrderItems()) {
                out.println(String.format("ID: %s\nPrice: %d\nQuantity: %d\nCompany Number: %d\n\n]", item.getId(), item.getPrice(), item.getQuantity(), item.getCompanyNumber()));
            }
        }
    }

    private void updateQuantity(){
        ArrayList<String[]> orders = service.getAllItems(); //gets all items
        if (orders.size() != 0) { //if there are no orders the case skips
            for (String[] item : orders) { //prints all of the items
                out.println("[ID: " + item[0] + "\n" +
                        "Price: " + item[1] + "\n" +
                        "Quantity: " + item[2] + "]\n" +
                        "Company Number: " + item[3] + "]\n" +
                        "Order ID: " + item[4] + "]\n");
            }
            int orderNumber;
            int newQuantity;
            String[] orderToChange = {};
            boolean contains = false;
            while (!contains) {
                orderNumber = in.nextInt("Select an item ID: ");
                for (String[] item : orders) {
                    if (item[0].equals(orderNumber + "")) {
                        contains = true;
                        orderToChange = item;
                        break;
                    }
                }
                if (!contains)
                    out.println("Illegal Arguments, please try again.");
            }
            newQuantity = in.nextInt("Enter new quantity you wish to order for the item you chose: ");
            if (!service.updateOrderItemQuantity(Integer.parseInt(orderToChange[0]), Integer.parseInt(orderToChange[4]),
                    newQuantity)) { //if can't update
                out.println("The new quantity is illegal"); //the new quantity is illegal
            }
        }
    }

    private void deleteItem(){
        ArrayList<String[]> orders = service.getAllItems(); //gets all items
        if (orders.size() != 0) { //if there are no order we skip the case
            for (String[] item : orders) { //prints all of the items
                out.println("[ID: " + item[0] + "\n" +
                        "Price: " + item[1] + "\n" +
                        "Quantity: " + item[2] + "]\n" +
                        "Company Number: " + item[3] + "]\n" +
                        "Order ID: " + item[4] + "]\n");
            }
            boolean contains = false;
            String[] orderToChange = {};
            while (!contains) {
                int orderNumber = in.nextInt("Select an item ID: ");
                for (String[] item : orders) {
                    if (item[0].equals(orderNumber + "")) {
                        contains = true;
                        orderToChange = item;
                        break;
                    }
                }
                if (!contains) {
                    out.println("Illegal Arguments, please try again.");
                }
                if (!service.deleteOrderItem(Integer.parseInt(orderToChange[0]), Integer.parseInt(orderToChange[4]))) {
                    out.println("The new quantity is illegal");
                }
            }
        }
    }
}
