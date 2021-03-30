package SupplierModule.BusinessLayer;

import SupplierModule.DataAccessLayer.DataController;

import java.util.ArrayList;
import java.util.Arrays;

public class SupplierController {
    private ArrayList<Supplier> suppliers;
    private DataController data;

    public SupplierController() {
        suppliers = new ArrayList<>();
        data = new DataController();
        suppliers.add(new Supplier(1, "1234560", "Paypal", new ArrayList<>(),
                new ArrayList<>(), "Pepsi", new QuantityWriter(new int[1][2], 2, 20000)));
    }

    public ArrayList<String[]> getSuppliersInfo() {
        ArrayList<String[]> suppliersInfo = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            String[] info = {supplier.getName(), supplier.getCompanyNumber(), supplier.getPaymentMethod()};
            suppliersInfo.add(info);
        }
        return suppliersInfo;
    }

    public void register(String name, int companyNumber, String paymentMethod, String bankAccount){
        suppliers.add(new Supplier(name, companyNumber, paymentMethod, bankAccount));
    }

    public void createOrder(int supplierNum, String deliOptions, String items){
        boolean needsDelivery = Boolean.parseBoolean(deliOptions.split(",")[0]);
        boolean constantDelivery = Boolean.parseBoolean(deliOptions.split(",")[1]);
        String[] itemlist = items.split(",");
        Item[] realItems = new Item[itemlist.length];
        for(int i = 0; i < realItems.length; i++){
            realItems[i] = new Item(itemlist[i].split("|")[0], Integer.parseInt(itemlist[i].split("|")[1]), Integer.parseInt(itemlist[i].split("|")[2]), Integer.parseInt((itemlist[i].split("|")[3]))supplierNum);
        }
        suppliers.get(supplierNum).addOrder(new Order(constantDelivery, needsDelivery, Arrays.asList(realItems)));
    }
}

