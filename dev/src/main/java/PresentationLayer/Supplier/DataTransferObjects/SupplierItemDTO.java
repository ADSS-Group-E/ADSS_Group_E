package PresentationLayer.Supplier.DataTransferObjects;

public class SupplierItemDTO {
    private int id;
    private final String name;
    private final int quantity;
    private final int price;
    private final String supplierCN;

    public SupplierItemDTO(String name, int quantity, int price, String supplierCN) {
        this.id = -1;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.supplierCN = supplierCN;
    }

    public SupplierItemDTO(int id, String name, int quantity, int price, String supplierCN) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.supplierCN = supplierCN;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSupplierCN() {
        return supplierCN;
    }

    public void setId(int id) {
        if (this.id == -1)
            this.id = id;
    }
}
