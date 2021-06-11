package BusinessLayer.Supplier;

import BusinessLayer.Inventory.DomainObjects.DomainObject;
import PresentationLayer.Supplier.DataTransferObjects.*;

import java.util.ArrayList;
import java.util.List;

class Supplier extends DomainObject {

    private final String name;
    private final String bankAccount;
    private final String paymentMethod;
    private QuantityWriter quantityWriter; //TODO
    private ArrayList<Order> orders;
    private ArrayList<SupplierProduct> products;
    private ArrayList<Contact> contacts;

    public Supplier(int id, String name, String bankAccount, String paymentMethod) {
        super(id);
        this.name = name;
        this.bankAccount = bankAccount;
        this.paymentMethod = paymentMethod;
    }

    public Supplier(SupplierDTO other){
        super(other.getId());
        this.name = other.getName();
        this.bankAccount = other.getBankAccount();;
        this.paymentMethod=other.getPaymentMethod();
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
}

