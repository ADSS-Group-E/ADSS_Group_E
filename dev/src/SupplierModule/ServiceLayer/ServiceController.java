package SupplierModule.ServiceLayer;

import SupplierModule.BusinessLayer.SupplierController;

import java.util.ArrayList;

class ServiceController {
    private SupplierController supplierController;

    public void register() {

    }

    public void createOrder() {

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
}

