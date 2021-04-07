package SupplierModule.ServiceLayer;

import SupplierModule.BusinessLayer.SupplierController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class ServiceController {
    private static ServiceController instance = null;

    private ServiceController(){

    }

    private SupplierController supplierController;

    void register(String name, int companyNumber, String paymentMethod, String bankAccount, ArrayList<String[]> items, ArrayList<String[]> contacts) {
        supplierController.register(name, companyNumber, paymentMethod, bankAccount, items, contacts);
    }

    void register(String name, int companyNumber, String paymentMethod, String bankAccount,
                  ArrayList<String[]> items, ArrayList<String[]> contacts, int regCostumer,
                  int minPrice, HashMap<Integer, Integer> discountSteps) {
        supplierController.register(name, companyNumber, paymentMethod, bankAccount, items, contacts,
                regCostumer, minPrice, discountSteps);
    }

    int createOrder(int supplierNum, boolean needsDelivery, boolean constantDelivery, ArrayList<String[]> items) {
        return supplierController.createOrder(supplierNum, needsDelivery, constantDelivery, items);
    }

    int getMaxDiscount() {
        return supplierController.getMaxDiscount();
    }

    ArrayList<String[]> getSuppliersInfo() {
        return supplierController.getSuppliersInfo();
    }

    ArrayList<String[]> getRegularOrdersToStrings() {
        return supplierController.getRegularOrdersToStrings();
    }

    ArrayList<String> getItemsFromSupplier(int supplierNum) {
        return supplierController.getItemsFromSupplier(supplierNum);
    }

    ArrayList<String[]> getRegularItems() {
        return supplierController.getRegularItems();
    }

    String[] getSpecificItem(int supplierNum, int itemNum) {
        return supplierController.getSpecificItem(supplierNum, itemNum);
    }

    boolean updateSellerItemQuantity(int supplierNum, int itemNum, int quantity) {
        return supplierController.updateSellerItemQuantity(supplierNum, itemNum, quantity);
    }

    boolean updateOrderItemQuantity(int supplierNum, int orderNum, int itemNum, int quantity) {
        return supplierController.updateOrderItemQuantity(supplierNum, orderNum, itemNum, quantity);
    }

    boolean deleteOrderItem(int supplierNum, int orderNum, int itemNum) {
        return supplierController.deleteCostumerItem(supplierNum, orderNum, itemNum);
    }

    boolean contains(String[] equal, ArrayList<String[]> list) {
        return supplierController.contains(equal, list);
    }

    void initialize() {
        supplierController = new SupplierController();
    }

    static ServiceController getInstance(){
        if(instance == null)
            instance = new ServiceController();
        return instance;
    }
}