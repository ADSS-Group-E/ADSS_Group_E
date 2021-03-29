package SupplierModule.BusinessLayer;

public class Item {
    private String name;
    private int price;
    private int quantity;
    private int supplierCN;

    public Item(String name, int price, int quantity, int supplierCN) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.supplierCN = supplierCN;
    }
}

