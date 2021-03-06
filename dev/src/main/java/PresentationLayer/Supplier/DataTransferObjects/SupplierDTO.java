package PresentationLayer.Supplier.DataTransferObjects;

import java.util.ArrayList;

public class SupplierDTO {
    private final int companyNumber;
    private final String name;
    private final String bankAccount;
    private final String paymentMethod;
    private QuantityWriterDTO quantityWriter;
    private ArrayList<OrderDTO> orders;
    private ArrayList<SupplierItemDTO> items;
    private ArrayList<ContactDTO> contacts;
    private ArrayList<Integer> supplyDays;

    public SupplierDTO(int companyNumber, String name, String bankAccount, String paymentMethod) {
        this.companyNumber = companyNumber;
        this.name = name;
        this.bankAccount = bankAccount;
        this.paymentMethod = paymentMethod;
        this.quantityWriter = null;
        orders = new ArrayList<>();
        items = new ArrayList<>();
        contacts = new ArrayList<>();
        supplyDays = new ArrayList<>();
    }

    public int getCompanyNumber() {
        return companyNumber;
    }

    public String getName() {
        return name;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public ArrayList<OrderDTO> getOrders() { return orders; }

    public ArrayList<SupplierItemDTO> getItems() { return items; }

    public QuantityWriterDTO getQuantityWriter() { return quantityWriter; }

    public ArrayList<ContactDTO> getContacts() { return contacts; }

    public void setItems(ArrayList<SupplierItemDTO> items) {
        if (this.items.equals(new ArrayList<>()))
            this.items = items;
    }

    public void setOrders(ArrayList<OrderDTO> orders) { this.orders = orders; }

    public void setQuantityWriter(QuantityWriterDTO quantityWriter) {
            this.quantityWriter = quantityWriter;
    }

    public void setContacts(ArrayList<ContactDTO> contacts) {
        if (this.contacts.equals(new ArrayList<>()))
            this.contacts = contacts;
    }

    public void setSupplyDays(ArrayList<Integer> supplyDays) {
        this.supplyDays = supplyDays;
    }

    public ArrayList<Integer> getSupplyDays() {
        return supplyDays;
    }
}
