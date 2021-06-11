package BusinessLayer.Supplier;

import BusinessLayer.Inventory.DomainObjects.DomainObject;
import PresentationLayer.Supplier.DataTransferObjects.SupplierItemDTO;

public class SupplierProduct extends DomainObject {
    private final String name;
    private int quantity;
    private final int price;

    public SupplierProduct(int id, String name, int quantity, int price){
        super(id);
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public SupplierProduct(SupplierItemDTO other){
        super(other.getId());
        this.name = other.getName();
        this.quantity = other.getQuantity();
        this.price = other.getPrice();
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }
}
