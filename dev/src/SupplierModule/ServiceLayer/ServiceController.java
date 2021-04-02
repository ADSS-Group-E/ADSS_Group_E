package SupplierModule.ServiceLayer;

import SupplierModule.BusinessLayer.SupplierController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class ServiceController {
    private static ServiceController instance = null;

    private ServiceController(){

    }

    private SupplierController supplierController;

    public void register(String name, int companyNumber, String paymentMethod, String bankAccount, ArrayList<String[]> items, ArrayList<String[]> contacts) {
        supplierController.register(name, companyNumber, paymentMethod, bankAccount, items, contacts);
    }

    public void register(String name, int companyNumber, String paymentMethod, String bankAccount,
                         ArrayList<String[]> items, ArrayList<String[]> contacts, int regCostumer,
                         int minPrice, HashMap<Integer, Integer> discountSteps) {
        supplierController.register(name, companyNumber, paymentMethod, bankAccount, items, contacts,
                regCostumer, minPrice, discountSteps);
    }

    public void createOrder(int supplierNum, boolean needsDelivery, boolean constantDelivery, ArrayList<String[]> items) {
        supplierController.createOrder(supplierNum, needsDelivery, constantDelivery, items);
    }

    ArrayList<String[]> getSuppliersInfo() {
        return supplierController.getSuppliersInfo();
    }

    public ArrayList<String[]> getRegularOrders() {
        return supplierController.getRegularOrders();
    }

    public ArrayList<String> getItemsFromSupplier(int supplierNum) {
        return supplierController.getItemsFromSupplier(supplierNum);
    }


    public String[] getSpecificItem (int supplierNum, int itemNum) {
        return supplierController.getSpecificItem(supplierNum, itemNum);
    }

    public boolean updateItemQuantity (int supplierNum, int itemNum, int quantity) {
        return supplierController.updateItemQuantity(supplierNum, itemNum, quantity);
    }

    void initialize() {
        supplierController = new SupplierController();
    }

    public static ServiceController getInstance(){
        if(instance == null)
            instance = new ServiceController();
        return instance;
    }
}