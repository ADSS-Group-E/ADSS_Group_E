package BusinessLayer.Supplier;

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

    String[] toStringArray() {
        return new String[]{name, price + "", quantity + "", supplierCN + ""};
    }

    void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() { return quantity; }

    public int getPrice() { return price; }

    public String getName() { return name; }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Item){
            Item item = (Item)obj;
            return item.supplierCN == supplierCN & item.quantity == quantity & item.price == price
                    & item.name.equals(name);
        }
        else return false;
    }
}

