package BusinessLayer.Supplier.DomainObjects;

import BusinessLayer.Inventory.DomainObjects.DomainObject;
import PresentationLayer.Supplier.DataTransferObjects.SupplierItemDTO;

import java.util.ArrayList;

public class SupplierProduct extends DomainObject {
    private final String name;
    private int quantity;
    private final int price;
    private final String supplierCN;
    private ArrayList<SupplierItemGroup> items;

    public SupplierProduct(int id, String name, int quantity, int price, String supplierCN){
        super(id);
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.supplierCN = supplierCN;
        items = new ArrayList<>();
    }

    public SupplierProduct(SupplierItemDTO other){
        super(other.getId());
        this.name = other.getName();
        this.quantity = other.getQuantity();
        this.price = other.getPrice();
        this.supplierCN = other.getSupplierCN();
        items = new ArrayList<>();
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

    // TODO load items
}
