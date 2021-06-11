package PresentationLayer.Supplier.DataTransferObjects;

import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

public class SupplierProductDTO extends DataTransferObject {
    private final String name;
    private int quantity;
    private final int price;
    private final String supplierCN;

    public SupplierProductDTO(int id, String name, int quantity, int price, String supplierCN){
        super(id);
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.supplierCN = supplierCN;
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

    public String getSupplierCN() {
        return supplierCN;
    }
}
