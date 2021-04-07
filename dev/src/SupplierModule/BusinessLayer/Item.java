package SupplierModule.BusinessLayer;

public class Item {
    private String name;
    private int price;
    private int quantity;
    private int supplierCN;

    Item(String name, int price, int quantity, int supplierCN) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.supplierCN = supplierCN;
    }

    public String toString() {
        return "Name: " + name + "\nPrice: " + price + "\nQuantity: " + quantity + "\nCatalog Number: " + supplierCN;
    }

    String[] toStringArray() {
        return new String[]{name, price + "", quantity + "", supplierCN + ""};
    }

    void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    int getQuantity() { return quantity; }

    int getPrice() { return price; }

    String getName() { return name; }
}

