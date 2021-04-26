package PresentationLayer.Supplier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PresentationHandler {
    private static PresentationHandler instance = null;
    private ServiceController service = ServiceController.getInstance();
    private InputService inputService = InputService.getInstance();
    private OutputService outputService = OutputService.getInstance();

    private PresentationHandler(){
        service.initialize();
    }

    static PresentationHandler getInstance(){ //singleton method to make sure there is only one instance
        if(instance == null)
            instance = new PresentationHandler();
        return instance;
    }

    public ArrayList<String[]> createItemList(int supplierNum) {
        //creates a list of items using input from the user to create an order from the supplier whose index is supplierNum
        ArrayList<String[]> list = new ArrayList<>();
        String ans = "Y";
        while (!ans.equalsIgnoreCase("N")) { //while the user response is not N
            if (ans.equalsIgnoreCase("Y")) { //if it's Y
                int number;
                String[] item;
                outputService.println("\nItems: ");
                for(String[] realItem : list) { //prints existing items
                    outputService.println("[Name: " + realItem[0] + "]");
                }
                number = inputService.nextInt("Enter Item Number: "); //user enters number
                int quantity;
                quantity = inputService.nextInt("Enter Item quantity: "); //user enters quantity
                item = service.getSpecificItem(supplierNum, number); //we get the rest of the item from our data
                while (item.length != 4) {
                    //while we didn't recieve an item or the quantity can't be taken from the supplier
                    outputService.println("Please try again.");
                    number = inputService.nextInt("Enter Item Number: "); //user enters new number
                    quantity = inputService.nextInt("Enter Item quantity: "); //user enters new quantity
                    item = service.getSpecificItem(supplierNum, number); //we get the rest of the item from our data
                }
                String[] order = {item[0], item[1], (quantity) + "", item[3]}; //we create a string order using our data and the new quantity
                boolean notExists = true;
                for (String[] existingOrder : list) {
                    if (existingOrder[0].equals(order[0])) { //if the item doesn't exist in our list already
                        notExists = false;
                        break;
                    }
                }
                if (notExists) { //if the item doesn't exist
                    if (service.updateSellerItemQuantity(supplierNum, number, quantity))
                        list.add(order);
                }
                else { //it exists
                    outputService.println("This item already exists.");
                }
                outputService.println("Add another Item? N/Y ");
            }
            else { //else it's illegal input
                outputService.println("please try again");
            }
            ans = inputService.next(""); //get N or Y again
        }
        return list;
    }

    public ArrayList<String[]> createItemList() {
        //create a list of items for the supplier to supply
        ArrayList<String[]> list = new ArrayList<>();
        String ans = "Y";
        outputService.println("Items: ");
        while (!ans.equalsIgnoreCase("N")) { //while the input is not N
            if (ans.equalsIgnoreCase("Y")) { //if it's Y
                outputService.println("Existing items:");
                for(String[] item : list) { //prints existing items
                    outputService.println("[Name: " + item[0] + "]");
                }
                String name = inputService.next("Enter Item Name: "); //gets item name from user
                String[] equalBy = {name};
                if (service.contains(equalBy, list)) { //checks if the item exists in the order using it's name
                    outputService.println("This item already exists.");
                }
                else { //if it doesn't exist
                    //user enters the rest of the data
                    String price = inputService.nextInt("Enter Item price: ") + "";
                    String quantity = inputService.nextInt("Enter Item quantity: ") + "";
                    String supplierCN = inputService.nextInt("Enter Item catalog number: ") + "";
                    String[] order = {name, price, quantity, supplierCN}; //we create an order an add it to the list
                    list.add(order);
                }
                outputService.println("Add another Item? N/Y ");
            }
            else {
                outputService.println("please try again");
            }
            ans = inputService.next("");
        }
        return list;
    }

    public ArrayList<String[]> createContactList() {
        //creates a list of contacts for the supplier
        ArrayList<String[]> list = new ArrayList<>();
        String ans = "Y";
        while (!ans.equalsIgnoreCase("N")) {
            if (ans.equalsIgnoreCase("Y")) {
                outputService.println("Existing contacts: ");
                for (String[] contact : list) { //prints existing contacts
                    outputService.println("[Name: " + contact[0] + ", Email: " + contact[1] + "]");
                }
                //get new contact information
                String name = inputService.next("Enter Contact Name: ");
                String email = inputService.next("Enter Contact Email: ");
                String[] item = {name, email}; //create a contact
                if (!service.contains(item, list)) //if the contact is not in the list
                    list.add(item);
                else { //the contact is in the list
                    outputService.println("This contact already exists.");
                }
                outputService.print("Add another Contact? N/Y "); //we ask user to add another contact
            }
            else {
                outputService.println("Please try again.\n");
            }
            ans = inputService.next("");
        }
        return list;
    }

    public HashMap<Integer, Integer> createDiscountList() {
        // creates a discount step list for the quantity writer
        int maxDiscount = service.getMaxDiscount(); //gets a constant number that is in the system, usually 100 that suggests 100% discount is max
        HashMap<Integer, Integer> map = new HashMap<>();
        String ans = inputService.next("Add discount steps? N/Y "); //asks user if to add another discount
        while (!ans.equalsIgnoreCase("N")) {
            if (ans.equalsIgnoreCase("Y")) {
                outputService.println("Existing discounts: ");
                for (Integer i : map.keySet()) { //print existing discount
                    outputService.println("[Price: " + i + ", Discount: %" + map.get(i) + "]");
                }
                int cash = inputService.nextInt("Enter amount of money: ");
                while (map.containsKey(cash)) { //if there is a step containing the amount of money
                    outputService.println("Please try again.");
                    cash = inputService.nextInt("Enter amount of money: %");
                }
                int discount = inputService.nextInt("Enter amount of discount: %");
                while (discount >= service.getMaxDiscount()) {
                    outputService.println("amount must be between 0-" + (service.getMaxDiscount() - 1)); //prints what the currect range the discount should be
                    outputService.println("Please try again.");
                    discount = inputService.nextInt("Enter amount of discount: %");
                }
                map.put(cash, discount); //if there is no step for the amount of money we add it
                outputService.print("Add another step? N/Y "); //asks the user for another step to add
            }
            else {
                outputService.println("Please try again.");
            }
            ans = inputService.next("");
        }
        return map;
    }

    public boolean showSupplierInfo(){
        ArrayList<String[]> sups = service.getSuppliersInfo();
        if (sups.size() == 0) return false; //we return false to release the user from picking a supplier when no supplier exists
        for (int i = 0; i < sups.size(); i++) { //prints the suppliers info with an index the user has to put to pick a supplier
            outputService.println(i + ": " + Arrays.toString(sups.get(i)));
        }
        return true;
    }

    public boolean manageQuantityWriter(){
        //asks the user if he needs a quantity writer, and return true if Y and false if N
        String ny = inputService.next("Do you need a Quantity Writer? N/Y ");
        while (!ny.equalsIgnoreCase("N")) {
            if (ny.equalsIgnoreCase("Y")) {
                //add quantity writer
                return true;
            }
            ny = inputService.next("Please try again");
        }
        //not to add
        return false;
    }

    public int showOptions(){
        //prints the options of the menu
        String[] options = new String[]{
                "Add Supplier", "Create Order", "Get Weekly Orders", "Update Order Item Quantity", "Delete Item From Order"
        };
        outputService.println("Menu:");
        for(int i = 0; i < options.length; i++){
            outputService.println(i + ") " + options[i]);
        }
        return options.length;
    }

    public String showSupplierItems(ArrayList<String> items) {
        //prints to screen the items we got from the supplier
        StringBuilder result = new StringBuilder();
        int i;
        for (i = 0; i < items.size() - 1; i++) {
            result.append(i).append(": ").append(items.get(i)).append("\n\n"); //appends indexes to the items
        }
        result.append(i).append(": ").append(items.get(i)); //appends last item without \n\n
        return result.toString();
    }
}
