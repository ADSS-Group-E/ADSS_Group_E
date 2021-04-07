package SupplierModule.ServiceLayer;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

class PresentationController {
    public static void main(String[] args) {
        InputService in = InputService.getInstance();
        OutputService out = OutputService.getInstance();
        ServiceController service = ServiceController.getInstance();
        service.initialize();
        final int numOfOptions = showOptions();
        //show user his options
        int input = in.nextInt("");
        while (true) {
            //decrypts input
            switch (input) {
                case (0): {//register supplier
                    String name = in.next("Enter supplier name: ");
                    int companyNumber = in.nextInt("Enter company number: ");
                    String paymentMethod = in.next("Enter payment method: ");
                    String bankAccount = in.next("Enter bank account: ");
                    ArrayList<String[]> items = createItemList();
                    ArrayList<String[]> contacts = createContactList();
                    boolean hasQuantityWriter = manageQuantityWriter();
                    if (!hasQuantityWriter)
                        service.register(name, companyNumber, paymentMethod, bankAccount, items, contacts);
                    else {
                        int regCostumer = in.nextInt("Regular costumer discount: %");
                        while (regCostumer >= service.getMaxDiscount()) {
                            out.println("Please try again.");
                            regCostumer = in.nextInt("Regular costumer discount: %");
                        }
                        int minPrice = in.nextInt("Minimum buy price for discount: ");
                        HashMap<Integer, Integer> discountSteps = createDiscountList();
                        service.register(name, companyNumber, paymentMethod, bankAccount, items, contacts, regCostumer, minPrice, discountSteps);
                    }
                    break;
                }
                case (1): {//order
                    if (showSupplierInfo()) {
                        ArrayList<String> supplierItems = null;
                        while (supplierItems == null) {
                            input = in.nextInt("Enter number of the supplier: ");
                            supplierItems = service.getItemsFromSupplier(input);
                        }
                        boolean constantDelivery = in.nextBoolean("\nConstant Delivery? true/false ");
                        boolean needsDelivery = in.nextBoolean("In need of delivery? true/false ");
                        out.println("\nSupplier items: ");
                        out.println(showSupplierItems(supplierItems));
                        ArrayList<String[]> items = createItemList(input);
                        out.println("Total Order price: " + service.createOrder(input, needsDelivery, constantDelivery, items) + "");
                        break;
                    }
                }
                case (2): {//
                    ArrayList<String[]> orders = service.getRegularOrdersToStrings();
                    for (String[] order : orders) {
                        for (String s : order) {
                            out.println(s);
                        }
                    }
                    break;
                }
                case (3): {
                    ArrayList<String[]> orders = service.getRegularItems();
                    if (orders.size() != 0) {
                        for (int i = 0; i < orders.size(); i++) {
                            String[] item = orders.get(i);
                            out.println(i + ") [Supplier Number: " + item[0] + "\n" +
                                    "Name: " + item[3] + "\n" +
                                    "Price: " + item[4] + "\n" +
                                    "Quantity: " + item[5] + "]\n");
                        }
                        int orderNumber = in.nextInt("Select an item Number: ");
                        int newQuantity = in.nextInt("Enter new quantity you wish to order for the item you chose: ");
                        if (orderNumber >= 0 && orderNumber < orders.size()) {
                            String[] orderToChange = orders.get(orderNumber);
                            if (!service.updateOrderItemQuantity(Integer.parseInt(orderToChange[0]), Integer.parseInt(orderToChange[1]),
                                    Integer.parseInt(orderToChange[2]), newQuantity)) {
                                out.println("The new quantity is illegal");
                            }
                        } else {
                            out.println("The item number is illegal");
                        }
                        break;
                    }
                }
                case (4): {
                    ArrayList<String[]> orders = service.getRegularItems();
                    if (orders.size() != 0) {
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
            showOptions();
            input = in.nextInt("");
        }
    }

    private static ArrayList<String[]> createItemList(int supplierNum) {
        ArrayList<String[]> list = new ArrayList<>();
        String ans = "Y";
        while (!ans.equalsIgnoreCase("N")) {
            if (ans.equalsIgnoreCase("Y")) {
                int number;
                String[] item;
                OutputService.getInstance().println("\nItems: ");
                number = InputService.getInstance().nextInt("Enter Item Number: ");
                int quantity;
                quantity = InputService.getInstance().nextInt("Enter Item quantity: ");
                item = ServiceController.getInstance().getSpecificItem(supplierNum, number);
                while (!ServiceController.getInstance().updateSellerItemQuantity(supplierNum, number, quantity) || item.length != 4) {
                    OutputService.getInstance().println("Please try again.");
                    number = InputService.getInstance().nextInt("Enter Item Number: ");
                    quantity = InputService.getInstance().nextInt("Enter Item quantity: ");
                    item = ServiceController.getInstance().getSpecificItem(supplierNum, number);
                }
                String[] order = {item[0], item[1], (quantity) + "", item[3]};
                boolean notExists = true;
                for (String[] existingOrder : list) {
                    if (existingOrder[0].equals(order[0])) {
                        notExists = false;
                        break;
                    }
                }
                if (notExists)
                    list.add(order);
                else {
                    OutputService.getInstance().println("This item already exists.");
                }
                OutputService.getInstance().println("Add another Item? N/Y ");
            }
            else {
                OutputService.getInstance().println("please try again");
            }
            ans = InputService.getInstance().next("");
        }
        return list;
    }

    private static ArrayList<String[]> createItemList() {
        ArrayList<String[]> list = new ArrayList<>();
        String ans = "Y";
        OutputService.getInstance().println("Items: ");
        while (!ans.equalsIgnoreCase("N")) {
            if (ans.equalsIgnoreCase("Y")) {
                OutputService.getInstance().println("Existing items:");
                for(String[] item : list) {
                    OutputService.getInstance().println("[Name: " + item[0] + "]");
                }
                String name = InputService.getInstance().next("Enter Item Name: ");
                String[] equalBy = {name};
                if (ServiceController.getInstance().contains(equalBy, list)) {
                    OutputService.getInstance().println("This item already exists.");
                }
                else {
                    String price = InputService.getInstance().nextInt("Enter Item price: ") + "";
                    String quantity = InputService.getInstance().nextInt("Enter Item quantity: ") + "";
                    String supplierCN = InputService.getInstance().nextInt("Enter Item catalog number: ") + "";
                    String[] order = {name, price, quantity, supplierCN};
                    list.add(order);
                }
                OutputService.getInstance().println("Add another Item? N/Y ");
            }
            else {
                OutputService.getInstance().println("please try again");
            }
            ans = InputService.getInstance().next("");
        }
        return list;
    }

    private static ArrayList<String[]> createContactList() {
        ArrayList<String[]> list = new ArrayList<>();
        String ans = "Y";
        while (!ans.equalsIgnoreCase("N")) {
            if (ans.equalsIgnoreCase("Y")) {
                OutputService.getInstance().println("Existing contacts: ");
                for (String[] contact : list) {
                    OutputService.getInstance().println("[Name: " + contact[0] + ", Email: " + contact[1] + "]");
                }
                String name = InputService.getInstance().next("Enter Contact Name: ");
                String email = InputService.getInstance().next("Enter Contact Email: ");
                String[] item = {name, email};
                if (!ServiceController.getInstance().contains(item, list))
                    list.add(item);
                else {
                    OutputService.getInstance().println("This contact already exists.");
                }
                OutputService.getInstance().print("Add another Contact? N/Y ");
            }
            else {
                OutputService.getInstance().println("Please try again.\n");
            }
            ans = InputService.getInstance().next("");
        }
        return list;
    }

    private static HashMap<Integer, Integer> createDiscountList() {
        int maxDiscount = ServiceController.getInstance().getMaxDiscount();
        HashMap<Integer, Integer> map = new HashMap<>();
        String ans = InputService.getInstance().next("Add discount steps? N/Y ");
        while (!ans.equalsIgnoreCase("N")) {
            if (ans.equalsIgnoreCase("Y")) {
                OutputService.getInstance().println("Existing discounts: ");
                for (Integer i : map.keySet()) {
                    OutputService.getInstance().println("[Price: " + i + ", Discount: %" + map.get(i) + "]");
                }
                int cash = InputService.getInstance().nextInt("Enter amount of money: ");
                while (map.containsKey(cash)) {
                    OutputService.getInstance().println("Please try again.");
                    cash = InputService.getInstance().nextInt("Enter amount of money: %");
                }
                int discount = InputService.getInstance().nextInt("Enter amount of discount: %");
                while (discount >= ServiceController.getInstance().getMaxDiscount()) {
                    OutputService.getInstance().println("amount must be between 0-" + (ServiceController.getInstance().getMaxDiscount() - 1));
                    OutputService.getInstance().println("Please try again.");
                    discount = InputService.getInstance().nextInt("Enter amount of discount: %");
                }
                    map.put(cash, discount);
                    OutputService.getInstance().print("Add another step? N/Y ");
            }
            else {
                OutputService.getInstance().println("Please try again.");
            }
            ans = InputService.getInstance().next("");
        }
        return map;
    }

    private static boolean showSupplierInfo(){
        ArrayList<String[]> sups = ServiceController.getInstance().getSuppliersInfo();
        if (sups.size() == 0) return false;
        for (int i = 0; i < sups.size(); i++) {
            OutputService.getInstance().println(i + ": " + Arrays.toString(sups.get(i)));
        }
        return true;
    }

    private static boolean manageQuantityWriter(){
        String ny = InputService.getInstance().next("Do you need a Quantity Writer? N/Y ");
        while (!ny.equalsIgnoreCase("N")) {
            if (ny.equalsIgnoreCase("Y")) {
                //add quantity writer
                return true;
            }
            ny = InputService.getInstance().next("Please try again");
        }
        //not to add
        return false;
    }

    private static int showOptions(){
        String[] options = new String[]{
          "Add Supplier", "Create Order", "Get Weekly Orders", "Update Order Item Quantity", "Delete Item From Order"
        };
        OutputService.getInstance().println("Menu:");
        for(int i = 0; i < options.length; i++){
            OutputService.getInstance().println(i + ") " + options[i]);
        }
        return options.length;
    }

    private static String showSupplierItems(ArrayList<String> items) {
        StringBuilder result = new StringBuilder();
        int i;
        for (i = 0; i < items.size() - 1; i++) {
            result.append(i).append(": ").append(items.get(i)).append("\n\n");
        }
        result.append(i).append(": ").append(items.get(i));
        return result.toString();
    }
}