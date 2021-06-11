package PresentationLayer.Supplier.DataTransferObjects;

import DataAccessLayer.Inventory.DataAccessObjects.DataAccessObject;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

import java.util.ArrayList;

public class SupplierDTO extends DataTransferObject {
    private final String name;
    private final String bankAccount;
    private final String paymentMethod;
    private QuantityWriterDTO quantityWriter;
    private ArrayList<OrderDTO> orders;
    private ArrayList<SupplierItemDTO> items;
    private ArrayList<ContactDTO> contacts;

    public SupplierDTO(int companyNumber, String name, String bankAccount, String paymentMethod) {
        super(companyNumber); // TODO ID things
        this.name = name;
        this.bankAccount = bankAccount;
        this.paymentMethod = paymentMethod;
        this.quantityWriter = null;
        orders = new ArrayList<>();
        items = new ArrayList<>();
        contacts = new ArrayList<>();
    }

    public int getCompanyNumber() {
        return getId();
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
}
