package SupplierModule.BusinessLayer;

import SupplierModule.DataAccessLayer.DataController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class SupplierController {
    private ArrayList<Supplier> suppliers;
    private DataController data;

    public SupplierController() {
        suppliers = new ArrayList<>();
        data = new DataController();
    }

    public ArrayList<String[]> getSuppliersInfo() {
        ArrayList<String[]> suppliersInfo = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            String[] info = {supplier.getName(), supplier.getCompanyNumber(), supplier.getPaymentMethod()};
            suppliersInfo.add(info);
        }
        return suppliersInfo;
    }

    public void register(String name, int companyNumber, String paymentMethod, String bankAccount, ArrayList<String[]> items, ArrayList<String[]> contacts){
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

    public void createOrder(int supplierNum, boolean needsDelivery, boolean constantDelivery, ArrayList<String[]> items){
        Item[] realItems = new Item[items.size()];
        for(int i = 0; i < realItems.length; i++){
            realItems[i] = new Item(items.get(i)[0], Integer.parseInt(items.get(i)[1]), Integer.parseInt(items.get(i)[2]), Integer.parseInt((items.get(i)[3])));
        }
        suppliers.get(supplierNum).addOrder(new Order(constantDelivery, needsDelivery, Arrays.asList(realItems)));
    }

    public ArrayList<String[]> getRegularOrders() {
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

    public String[] getSpecificItem(int supplierNum, int itemNum) {
        if (supplierNum >= suppliers.size() || supplierNum < 0) return new String[]{};
        List<Item> items = suppliers.get(supplierNum).getItems();
        if (itemNum >= items.size() || itemNum < 0) return new String[]{};
        return items.get(itemNum).toStringArray();
    }

    public boolean updateItemQuantity(int supplierNum, int itemNum, int quantity) {
        if (supplierNum >= suppliers.size() || supplierNum < 0) return false;
        List<Item> items = suppliers.get(supplierNum).getItems();
        if (itemNum >= items.size() || itemNum < 0) return false;
        Item item = items.get(itemNum);
        if (quantity < 0 || quantity > item.getQuantity()) return false;
        item.setQuantity(item.getQuantity() - quantity);
        return true;
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
}

