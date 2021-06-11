package BusinessLayer.Supplier.DomainObjects;

import BusinessLayer.Inventory.DomainObjects.DomainObject;
import PresentationLayer.Supplier.DataTransferObjects.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Supplier extends DomainObject {

    private final String name;
    private final String bankAccount;
    private final String paymentMethod;
    private QuantityWriter quantityWriter; //TODO
    private HashMap<Integer,Order> orders;
    private HashMap<Integer,SupplierProduct> products;
    private HashMap<Integer,Contact> contacts;

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

    public void loadOrders(HashMap<Integer, Order> orders) {
        this.orders = orders;
    }

    public void loadProducts(HashMap<Integer, SupplierProduct> products) {
        this.products = products;
    }

    public void loadContacts(HashMap<Integer, Contact> contacts) {
        this.contacts = contacts;
    }

    public void addOrder(Order order){

    }


}

