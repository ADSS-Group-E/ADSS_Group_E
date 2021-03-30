package SupplierModule.BusinessLayer;

import SupplierModule.DataAccessLayer.DataController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SupplierController {
    private ArrayList<Supplier> suppliers;
    private DataController data;

    public SupplierController() {
        suppliers = new ArrayList<>();
        data = new DataController();
        suppliers.add(new Supplier("Pepsi", 1, "1234560", "Paypal", new ArrayList<>(),
                new ArrayList<>(), new QuantityWriter(new HashMap<>(), 2, 20000)));
    }

    public ArrayList<String[]> getSuppliersInfo() {
        ArrayList<String[]> suppliersInfo = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            String[] info = {supplier.getName(), supplier.getCompanyNumber(), supplier.getPaymentMethod()};
            suppliersInfo.add(info);
        }
        return suppliersInfo;
    }

    public void register(String name, int companyNumber, String paymentMethod, String bankAccount, List<String> items, List<String> contacts){
        Item[] realItems = new Item[items.size()];
        Contact[] realContacts = new Contact[contacts.size()];
        for(int i = 0; i < realItems.length; i++){
            realItems[i] = new Item(items.get(i).split(",")[0], Integer.parseInt(items.get(i).split(",")[1]), Integer.parseInt(items.get(i).split(",")[2]), Integer.parseInt(items.get(i).split(",")[3]));
        }
        for(int i = 0; i < realContacts.length; i++){
            realContacts[i] = new Contact(contacts.get(i).split(",")[0], contacts.get(i).split(",")[1]);
        }
        suppliers.add(new Supplier(name, companyNumber, paymentMethod, bankAccount, Arrays.asList(realItems), Arrays.asList(realContacts)));
    }

    public void createOrder(int supplierNum, String deliOptions, List<String> items){
        boolean needsDelivery = Boolean.parseBoolean(deliOptions.split(",")[0]);
        boolean constantDelivery = Boolean.parseBoolean(deliOptions.split(",")[1]);
        Item[] realItems = new Item[items.size()];
        for(int i = 0; i < realItems.length; i++){
            realItems[i] = new Item(items.get(i).split("|")[0], Integer.parseInt(items.get(i).split("|")[1]), Integer.parseInt(items.get(i).split("|")[2]), Integer.parseInt((items.get(i).split("|")[3])));
        }
        suppliers.get(supplierNum).addOrder(new Order(constantDelivery, needsDelivery, Arrays.asList(realItems)));
    }
}

