package SupplierModule.ServiceLayer;

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
        while (input >= 0 && input < numOfOptions) {
            //decrypts input
            switch (input){
                case (0) :{//register supplier
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
                        int minPrice = in.nextInt("Minimum buy price for discount: ");
                        HashMap<Integer, Integer> discountSteps = createDiscountList();
                        service.register(name, companyNumber, paymentMethod, bankAccount, items, contacts, regCostumer, minPrice, discountSteps);
                    }
                    break;
                }
                case (1): {//order
                    showSupplierInfo();
                    ArrayList<String> supplierItems = null;
                    while (supplierItems == null) {
                        input = in.nextInt("Enter number of the supplier: ");
                        supplierItems = service.getItemsFromSupplier(input);
                    }
                    boolean constantDelivery = in.nextBoolean("\nConstant Delivery? true/false ");
                    boolean needsDelivery = in.nextBoolean("In need of delivery? ");
                    out.println("\nSupplier items: ");
                    out.println(showSupplierItems(supplierItems));
                    ArrayList<String[]> items = createItemList(input);
                    service.createOrder(input, needsDelivery, constantDelivery, items);
                    break;
                }
                case (2): {//
                    ArrayList<String[]> orders = service.getRegularOrders();
                    for (String[] order : orders) {
                        for (String s : order) {
                            out.println(s);
                        }
                    }
                }
            }
            showOptions();
            input = in.nextInt("");
        }
    }

    static ArrayList<String[]> createItemList(int supplierNum) {
        ArrayList<String[]> list = new ArrayList<>();
        String ans = "Y";
        while (!ans.equals("N")) {
            if (ans.equals("Y")) {
                int number;
                String[] item;
                OutputService.getInstance().println("\nItems: ");
                number = InputService.getInstance().nextInt("Enter Item Number: ");
                int quantity;
                quantity = InputService.getInstance().nextInt("Enter Item quantity: ");
                item = ServiceController.getInstance().getSpecificItem(supplierNum, number);
                while (!ServiceController.getInstance().updateItemQuantity(supplierNum, number, quantity) || item.length != 4) {
                    OutputService.getInstance().println("Please try again.");
                    number = InputService.getInstance().nextInt("Enter Item Number: ");
                    quantity = InputService.getInstance().nextInt("Enter Item quantity: ");
                    item = ServiceController.getInstance().getSpecificItem(supplierNum, number);
                }
                String[] order = {item[0], item[1], (quantity) + "", item[3]};
                list.add(order);
                OutputService.getInstance().println("Add another Item? N/Y ");
            }
            else {
                OutputService.getInstance().println("please try again");
            }
            ans = InputService.getInstance().next("");
        }
        return list;
    }

    static ArrayList<String[]> createItemList() {
        ArrayList<String[]> list = new ArrayList<>();
        ArrayList<String> used = new ArrayList<>();
        String ans = "Y";
        OutputService.getInstance().println("\nItems: ");
        while (!ans.equals("N")) {
            if (ans.equals("Y")) {
                String name = "";
                boolean contains = true;
                while (contains) {
                    name = InputService.getInstance().next("Enter Item Name: ");
                    if (!used.contains(name.toLowerCase())) {
                        used.add(name.toLowerCase());
                        contains = false;
                    }
                }
                String price = InputService.getInstance().nextInt("Enter Item price: ") + "";
                String quantity = InputService.getInstance().nextInt("Enter Item quantity: ") + "";
                String supplierCN = InputService.getInstance().nextInt("Enter Item catalog number: ") + "";
                String[] order = {name, price, quantity, supplierCN};
                list.add(order);
                OutputService.getInstance().println("Add an Item? N/Y ");
            }
            else {
                OutputService.getInstance().println("please try again");
            }
            ans = InputService.getInstance().next("");
        }
        return list;
    }

    static ArrayList<String[]> createContactList() {
        ArrayList<String[]> list = new ArrayList<>();
        String ans = "Y";
        while (!ans.equals("N")) {
            if (ans.equals("Y")) {
                String name = InputService.getInstance().next("Enter Contact Name: ");
                String email = InputService.getInstance().next("Enter Contact Email: ");
                String[] item = {name, email};
                list.add(item);
                OutputService.getInstance().print("Add another Contact? N/Y ");
            }
            else {
                OutputService.getInstance().println("Please try again");
            }
            ans = InputService.getInstance().next("");
        }
        return list;
    }

    static HashMap<Integer, Integer> createDiscountList() {
        HashMap<Integer, Integer> map = new HashMap<>();
        String ans = InputService.getInstance().next("Add discount steps? N/Y ");
        while (!ans.equals("N")) {
            if (ans.equals("Y")) {
                Integer cash = InputService.getInstance().nextInt("Enter amount of money: ");
                Integer discount = InputService.getInstance().nextInt("Enter amount of discount: %");
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

    static void showSupplierInfo(){
        ArrayList<String[]> sups = ServiceController.getInstance().getSuppliersInfo();
        for (int i = 0; i < sups.size(); i++) {
            OutputService.getInstance().println(i + ": " + Arrays.toString(sups.get(i)));
        }
    }

    static boolean manageQuantityWriter(){
        String ny = InputService.getInstance().next("Do you need a Quantity Writer? N/Y ");
        while (!ny.equals("N")) {
            if (ny.equals("Y")) {
                //add quantity writer
                return true;
            }
            ny = InputService.getInstance().next("Please try again");
        }
        //not to add
        return false;
    }

    static int showOptions(){
        String[] options = new String[]{
          "Add Supplier", "Create Order", "Get Weekly Orders"
        };
        OutputService.getInstance().println("Menu:");
        for(int i = 0; i < options.length; i++){
            OutputService.getInstance().println(i + ") " + options[i]);
        }
        return options.length;
    }

    static String showSupplierItems(ArrayList<String> items) {
        StringBuilder result = new StringBuilder();
        int i;
        for (i = 0; i < items.size() - 1; i++) {
            result.append(i).append(": ").append(items.get(i)).append("\n");
        }
        result.append(i).append(": ").append(items.get(i));
        return result.toString();
    }
}