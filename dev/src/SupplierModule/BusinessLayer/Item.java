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

    public String toString() {
        return "Name: " + name + "\nPrice: " + price + "\nQuantity: " + quantity + "\nCatalog Number: " + supplierCN;
    }

    public String[] toStringArray() {
        return new String[]{name, price + "", quantity + "", supplierCN + ""};
    }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getQuantity() { return quantity; }

    public int getPrice() { return price; }
}

