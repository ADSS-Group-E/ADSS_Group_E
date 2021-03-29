package SupplierModule.DataAccessLayer;

import java.util.ArrayList;

public class DataController {
    private Contact contact;
    private Item items;
    private Order orders;
    private QuantityWriter quantityWriter;
    private Supplier supplier;

    public DataController() {
        this.contact = new Contact();
        this.items = new Item();
        this.orders = new Order();
        this.quantityWriter = new QuantityWriter();
        this.supplier = new Supplier();
    }
}

