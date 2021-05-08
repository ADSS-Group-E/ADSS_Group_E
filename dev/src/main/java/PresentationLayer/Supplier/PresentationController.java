package PresentationLayer.Supplier;

import PresentationLayer.Supplier.DataTransferObjects.OrderDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierItemDTO;

import java.util.*;

class PresentationController {
    public static void main(String[] args) {
        InputService in = InputService.getInstance();
        OutputService out = OutputService.getInstance();
        ServiceController service = ServiceController.getInstance(); //initializes empty objects
        boolean intializeData = in.nextBoolean("Initialize Data? true/false: "); //ask user if to import existing data
        if (intializeData) //if yes
            service.initialize(); //import existing data
        PresentationHandler.getInstance().showOptions(); //show the user his menu
        //show user his options
        int input = in.nextInt(""); //gets menu option from user
        while (true) {
            //decrypts input
            switch (input) {
                case (0): {//register supplier
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
                    break;
                }
                case (1): {//create order
                    if (PresentationHandler.getInstance().showSupplierInfo()) { //if there are no suppliers to order from skip the case
                        ArrayList<SupplierItemDTO> supplierItems = null;
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
                        break;
                    }
                }
                case (2): { //get orders that come on a weekly basis
                    ArrayList<OrderDTO> orders = service.getRegularOrdersToStrings(); //gets data from the system
                    for (OrderDTO order : orders) {
                        out.println(String.format("[ID: %d, Date: %s, Needs Delivery: %s, Periodic Delivery: %s]", order.getId(), order.getDate(), order.getNeedsDelivery() == 1, order.getPeriodicDelivery() == 1));
                        out.println("Items:");
                        for (SupplierItemDTO item : order.getOrderItems()) {
                            out.println(String.format("[Name: %s, Price: %d, Quantity: %d, Supplier Catalog Number: %s", item.getName(), item.getPrice(), item.getQuantity(), item.getSupplierCN()));
                        }
                    }
                    break;
                }
                case (3): {
                    ArrayList<String[]> orders = service.getAllItems(); //gets all items
                    if (orders.size() != 0) { //if there are no orders the case skips
                        for (String[] item : orders) { //prints all of the items
                            out.println("[ID: " + item[0] + "\n" +
                                    "Name: " + item[1] + "\n" +
                                    "Price: " + item[2] + "\n" +
                                    "Quantity: " + item[3] + "]\n" +
                                    "SupplierCN: " + item[4] + "]\n");
                        }
                        int orderNumber;
                        int newQuantity = -1;
                        String[] orderToChange = {};
                        boolean contains = false;
                        while (!contains) {
                            orderNumber = in.nextInt("Select an item ID: ");
                            newQuantity = in.nextInt("Enter new quantity you wish to order for the item you chose: ");
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
                        if (!service.updateOrderItemQuantity(Integer.parseInt(orderToChange[0]),
                                newQuantity)) { //if can't update
                            out.println("The new quantity is illegal"); //the new quantity is illegal
                        }
                    }
                }
                case (4): {
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
            PresentationHandler.getInstance().showOptions();
            input = in.nextInt("");
        }
    }
}