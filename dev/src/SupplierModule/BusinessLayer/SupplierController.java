package SupplierModule.BusinessLayer;

import SupplierModule.DataAccessLayer.DataController;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class SupplierController {
    private ArrayList<Supplier> suppliers;
    private DataController data;
    private final int maxDiscount = 100;

    public SupplierController() {
        suppliers = new ArrayList<>();
        data = new DataController();
    }

    public void initialize() {
        suppliers = new ArrayList<>();
        data = new DataController();
        ArrayList<String[]> supplierOneItems = new ArrayList<>();//supplier one items
        supplierOneItems.add(new String[]{"Corn", 10 + "", 1500 + "", 5554 + ""});
        supplierOneItems.add(new String[]{"Popcorn", 20 + "", 500 + "", 5666 + ""});
        supplierOneItems.add(new String[]{"Candy Corn", 30 + "", 200 + "", 8989 + ""});
        ArrayList<String[]> supplierTwoItems = new ArrayList<>();//supplier two items
        supplierTwoItems.add(new String[]{"Hot Dog", 10 + "", 1500 + "", 100 + ""});
        supplierTwoItems.add(new String[]{"Corn Dog", 20 + "", 500 + "", 206 + ""});
        supplierTwoItems.add(new String[]{"Dog", 3000 + "", 10 + "", 5041 + ""});
        ArrayList<String[]> contacts = new ArrayList<>();
        contacts.add(new String[]{"Tzahi", "tzahi@tzahi.com"});
        HashMap<Integer, Integer> discounts = new HashMap<>();
        discounts.put(1000, 10);
        discounts.put(2000, 15);
        discounts.put(4000, 20);
        register("Amazon", 10, "Cheque", "8145441/24", supplierOneItems, contacts, 3000, 500, discounts);
        register("Google", 54, "Paypal", "15144/455", supplierTwoItems, contacts, 500, 500, discounts);
        ArrayList<String[]> orderItems = new ArrayList<>();
        orderItems.add(new String[]{"Candy Corn", 30 + "", 100 + "", 8989 + ""});
        createOrder(0, false, true, orderItems);
        ArrayList<String[]> orderItems2 = new ArrayList<>();
        orderItems.add(new String[]{"Dog", 3000 + "", 1 + "", 5041 + ""});
        createOrder(1, true, true, orderItems2);
    }

    public ArrayList<String[]> getSuppliersInfo() {
        ArrayList<String[]> suppliersInfo = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            String[] info = {supplier.getName(), supplier.getCompanyNumber(), supplier.getPaymentMethod(), supplier.getBankAccount()};
            suppliersInfo.add(info);
        }
        return suppliersInfo;
    }

    public void register(String name, int companyNumber, String paymentMethod,
                 String bankAccount, ArrayList<String[]> items, ArrayList<String[]> contacts){
        Item[] realItems = new Item[items.size()];
        Contact[] realContacts = new Contact[contacts.size()];
        for(int i = 0; i < realItems.length; i++){
            realItems[i] = new Item(items.get(i)[0], Integer.parseInt(items.get(i)[1]), Integer.parseInt(items.get(i)[2]), Integer.parseInt(items.get(i)[3]));
        }
        for(int i = 0; i < realContacts.length; i++){
            realContacts[i] = new Contact(contacts.get(i)[0], contacts.get(i)[1]);
        }
        suppliers.add(new Supplier(name, companyNumber, paymentMethod, bankAccount, Arrays.asList(realItems), Arrays.asList(realContacts)));
    }

    public void register(String name, int companyNumber, String paymentMethod, String bankAccount,
                         ArrayList<String[]> items, ArrayList<String[]> contacts, int regCostumer,
                         int minPrice, HashMap<Integer, Integer> discountSteps){
        Item[] realItems = new Item[items.size()];
        Contact[] realContacts = new Contact[contacts.size()];
        for(int i = 0; i < realItems.length; i++){
            realItems[i] = new Item(items.get(i)[0], Integer.parseInt(items.get(i)[1]), Integer.parseInt(items.get(i)[2]), Integer.parseInt(items.get(i)[3]));
        }
        for(int i = 0; i < realContacts.length; i++){
            realContacts[i] = new Contact(contacts.get(i)[0], contacts.get(i)[1]);
        }
        QuantityWriter writer = new QuantityWriter(discountSteps, regCostumer, minPrice);
        suppliers.add(new Supplier(name, companyNumber, paymentMethod, bankAccount, Arrays.asList(realItems), Arrays.asList(realContacts), writer));
    }

    public int createOrder(int supplierNum, boolean needsDelivery, boolean constantDelivery, ArrayList<String[]> items){
        Item[] realItems = new Item[items.size()];
        for(int i = 0; i < realItems.length; i++){
            realItems[i] = new Item(items.get(i)[0], Integer.parseInt(items.get(i)[1]), Integer.parseInt(items.get(i)[2]), Integer.parseInt((items.get(i)[3])));
        }
        Order order = new Order(constantDelivery, needsDelivery, Arrays.asList(realItems));
        suppliers.get(supplierNum).addOrder(order);
        return suppliers.get(supplierNum).calcPrice(order);
    }

    public ArrayList<String[]> getRegularOrdersToStrings() {
        ArrayList<String[]> regOrders = new ArrayList<>();
        for (Supplier s : suppliers) {
            ArrayList<Order> tempOrders = s.getOrders();
            for (Order tempOrder : tempOrders) {
                if (tempOrder.isConstantDelivery()) {
                    ArrayList<String> items = tempOrder.getOrderItemsToString();
                    String[] bools = {"Weekly Delivery: " + tempOrder.isConstantDelivery(), "Delivery Needed: " + tempOrder.isNeedsDelivery() + "\nItems:"};
                    String[] order = Stream.of(bools, items.toArray()).flatMap(Stream::of).toArray(String[]::new);
                    regOrders.add(order);
                }
            }
        }
        return regOrders;
    }

    public ArrayList<String[]> getAllItems() {
        ArrayList<String[]> regItems = new ArrayList<>();
        for (int supplierNum = 0; supplierNum < suppliers.size(); supplierNum++) {
            ArrayList<Order> tempOrders = suppliers.get(supplierNum).getOrders();
            for (int orderNum = 0; orderNum < tempOrders.size(); orderNum++) {
                Order tempOrder = tempOrders.get(orderNum);
                for (int i = 0; i < tempOrder.getOrderItems().size(); i++) {
                    Item item = tempOrder.getOrderItems().get(i);
                    String[] itemString = {supplierNum + "", orderNum + "", i + "", item.getName(),
                            item.getPrice() + "", item.getQuantity() + ""};
                    regItems.add(itemString);
                }
            }
        }
        return regItems;
    }

    public String[] getSpecificItem(int supplierNum, int itemNum) {
        if (supplierNum >= suppliers.size() || supplierNum < 0) return new String[]{};
        List<Item> items = suppliers.get(supplierNum).getItems();
        if (itemNum >= items.size() || itemNum < 0) return new String[]{};
        return items.get(itemNum).toStringArray();
    }

    public boolean updateSellerItemQuantity(int supplierNum, int itemNum, int quantity) {
        if (supplierNum >= suppliers.size() || supplierNum < 0) return false;
        List<Item> items = suppliers.get(supplierNum).getItems();
        if (itemNum >= items.size() || itemNum < 0) return false;
        Item item = items.get(itemNum);
        if (quantity <= 0 || quantity > item.getQuantity()) return false;
        item.setQuantity(item.getQuantity() - quantity);
        return true;
    }

    public boolean updateOrderItemQuantity(int supplierNum, int orderNum, int itemNum, int newQuantity) {
        if (supplierNum >= suppliers.size() || supplierNum < 0) return false;
        ArrayList<Order> orders = suppliers.get(supplierNum).getOrders();
        if (orderNum >= orders.size() || orderNum < 0) return false;
        Order order = orders.get(orderNum);
        if (itemNum >= order.getOrderItems().size() || itemNum < 0) return false;
        Item item = order.getOrderItems().get(itemNum);
        int oldQuantity = item.getQuantity();
        if (newQuantity <= 0) return false;
            item.setQuantity(newQuantity);
        int supQuantity = suppliers.get(supplierNum).getItems().get(orderNum).getQuantity() - newQuantity + oldQuantity;
        if (supQuantity < 0) return false;
        suppliers.get(supplierNum).getItems().get(orderNum).setQuantity(supQuantity);
        return true;
    }

    public boolean deleteCostumerItem(int supplierNum, int orderNum, int itemNum) {
        if (supplierNum >= suppliers.size() || supplierNum < 0) return false;
        ArrayList<Order> orders = suppliers.get(supplierNum).getOrders();
        if (orderNum >= orders.size() || orderNum < 0) return false;
        Order order = orders.get(orderNum);
        if (itemNum >= order.getOrderItems().size() || itemNum < 0) return false;
        ArrayList<Item> items = new ArrayList<>(order.getOrderItems());
        int oldQuantity = items.get(itemNum).getQuantity();
        items.remove(itemNum);
        order.setOrderItems(items);
        suppliers.get(supplierNum).getItems().get(itemNum).setQuantity(suppliers.get(supplierNum).getItems().get(itemNum).getQuantity()
                 + oldQuantity);
        return true;
    }

    public boolean contains(String[] equal, ArrayList<String[]> list) {
        for (String[] object : list) {
            boolean equals = true;
            for (int i = 0; i < equal.length; i++)
                if (!equal[i].equalsIgnoreCase(object[i])) {
                    equals = false;
                    i = object.length;
                }
            if (equals)
                return true;
        }
        return false;
    }

    public ArrayList<String> getItemsFromSupplier(int supplierNum) {
        if (supplierNum >= suppliers.size() || supplierNum < 0) return null;
        ArrayList<String> regOrders = new ArrayList<>();
        List<Item> items = suppliers.get(supplierNum).getItems();
        for (Item item : items) {
            regOrders.add(item.toString());
        }
        return regOrders;
    }

    public QuantityWriter getQuantityWriter(int idx){
        return suppliers.get(idx).getQuantityWriter();
    }

    public Order getOrderFromSupplier(int supIdx, int orderIdx){
        return suppliers.get(supIdx).getOrders().get(orderIdx);
    }

    public int getMaxDiscount() {
        return maxDiscount;
    }
}

