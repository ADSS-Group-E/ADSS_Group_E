package SupplierModule.BusinessLayer;

import java.util.ArrayList;

class Supplier {
    private ArrayList<Item> items;
    private ArrayList<Contact> contacts;
    private QuantityWriter quantityWriter;
    private ArrayList<Order> orders;
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

