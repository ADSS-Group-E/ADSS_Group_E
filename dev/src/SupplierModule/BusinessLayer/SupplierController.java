package SupplierModule.BusinessLayer;

import SupplierModule.DataAccessLayer.DataController;

import java.util.ArrayList;

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
}

