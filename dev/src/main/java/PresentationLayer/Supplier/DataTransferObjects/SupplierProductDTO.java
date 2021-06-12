package PresentationLayer.Supplier.DataTransferObjects;

import BusinessLayer.Supplier.DomainObjects.SupplierProduct;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

public class SupplierProductDTO extends DataTransferObject {
    private final int pid;
    private int companyNumber;
    private final String name;
    private int quantity;
    private final double price;
    private final String supplierCN;

    public SupplierProductDTO(int id, int pid, int companyNumber, String name, int quantity, double price, String supplierCN){
        super(id);
        this.pid = pid;
        this.companyNumber = companyNumber;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.supplierCN = supplierCN;
    }

    public SupplierProductDTO(SupplierProduct other) {
        super(other.getId());
        this.pid = other.getPid();
        this.name = other.getName();
        this.quantity = other.getQuantity();
        this.price = other.getPrice();
        this.supplierCN = other.getSupplierCN();
    }

    public int getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(int companyNumber) {
        this.companyNumber = companyNumber;
    }

    public int getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getSupplierCN() {
        return supplierCN;
    }
}
