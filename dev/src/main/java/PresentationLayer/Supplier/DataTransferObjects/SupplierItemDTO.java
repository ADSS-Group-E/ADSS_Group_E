package PresentationLayer.Supplier.DataTransferObjects;

public class SupplierItemDTO {
    private int id;
    private final String name;
    private int quantity;
    private final int price;
    private final String supplierCN;
    private int orderID;
    private int companyNumber;

    public SupplierItemDTO(String name, int quantity, int price, String supplierCN) {
        this.id = -1;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.supplierCN = supplierCN;
        this.orderID = -1;
        this.companyNumber = -1;
    }

    public SupplierItemDTO(int id, String name, int quantity, int price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.supplierCN = "";
        this.orderID = -1;
        this.companyNumber = -1;
    }

    public SupplierItemDTO(String name, int quantity, int price, String supplierCN, int companyNumber) {
        this.id = -1;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.supplierCN = supplierCN;
        this.orderID = -1;
        this.companyNumber = companyNumber;
    }

    public SupplierItemDTO(int id, String name, int quantity, int price, String supplierCN) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.supplierCN = supplierCN;
        this.orderID = -1;
        this.companyNumber = -1;
    }

    public SupplierItemDTO(int id, String name, int quantity, int price, String supplierCN, int companyNumber) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.supplierCN = supplierCN;
        this.orderID = -1;
        this.companyNumber = companyNumber;
    }

    public SupplierItemDTO(int id, int quantity, int price, int companyNumber) {
        this.id = id;
        this.name = "";
        this.quantity = quantity;
        this.price = price;
        this.supplierCN = "";
        this.orderID = -1;
        this.companyNumber = companyNumber;
    }

    public SupplierItemDTO(int id, int quantity, int price, int companyNumber, int orderID) {
        this.id = id;
        this.name = "";
        this.quantity = quantity;
        this.price = price;
        this.supplierCN = "";
        this.orderID = orderID;
        this.companyNumber = companyNumber;
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

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getOrderID() { return orderID; }

    public void setOrderID(int orderID) { this.orderID = orderID; }

    public int getCompanyNumber() { return companyNumber; }

    public void setCompanyNumber(int companyNumber) { this.companyNumber = companyNumber; }
}
