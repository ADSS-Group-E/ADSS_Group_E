package SupplierModule.ServiceLayer;

import SupplierModule.BusinessLayer.SupplierController;

import java.util.ArrayList;

class ServiceController {
    private static ServiceController instance = null;

    private ServiceController(){

    }

    private SupplierController supplierController;

    public void register(String name, int companyNumber, String paymentMethod, String bankAccount) {
        supplierController.register(name, companyNumber, paymentMethod, bankAccount);
    }

    public void createOrder(int supplierNum, String deliOptions, String items) {
        supplierController.createOrder(supplierNum, deliOptions, items);
    }

    ArrayList<String[]> getSuppliersInfo() {
        return supplierController.getSuppliersInfo();
    }

    public void getRegularOrders() {

    }

    public void getItemsFromSupplier() {

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