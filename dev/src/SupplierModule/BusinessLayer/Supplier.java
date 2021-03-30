package SupplierModule.BusinessLayer;

import java.util.ArrayList;
import java.util.List;

class Supplier {
    private List<Item> items;
    private List<Contact> contacts;
    private QuantityWriter quantityWriter;
    private List<Order> orders;
    private int companyNumber;
    private String bankAccount;
    private String paymentMethod;
    private String name;

    Supplier(int companyNumber, String bankAccount, String paymentMethod, ArrayList<Item> items,
             ArrayList<Contact> contacts, String name, QuantityWriter quantityWriter) {
        this.companyNumber = companyNumber;
        this.bankAccount = bankAccount;
        this.paymentMethod = paymentMethod;
        this.items = items;
        this.contacts = contacts;
        this.name = name;
        this.orders = new ArrayList<>();
        this.quantityWriter = quantityWriter;
    }

    Supplier(String name, int companyNumber, String paymentMethod, String bankAccount, ArrayList<Item> items, ArrayList<Contact> contacts){
        this.name = name;
        this.companyNumber = companyNumber;
        this.paymentMethod = paymentMethod;
        this.bankAccount = bankAccount;
        this.items = items;
        this.contacts = contacts;
        quantityWriter = null;
        orders = new ArrayList<>();
    }

    void addOrder(Order or){
        orders.add(or);
    }

    String getCompanyNumber() {
        return companyNumber + "";
    }

    String getPaymentMethod() {
        return paymentMethod;
    }

    String getName() {
        return name;
    }
}

