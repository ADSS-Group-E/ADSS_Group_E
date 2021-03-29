package SupplierModule.BusinessLayer;

import java.util.ArrayList;

public class Order {
    private boolean constantDelivery;
    private boolean needsDelivery;
    private ArrayList<Item> orderItems;

    public Order(boolean constantDelivery, boolean needsDelivery, ArrayList<Item> orderItems) {
        this.constantDelivery = constantDelivery;
        this.needsDelivery = needsDelivery;
        this.orderItems = orderItems;
    }
}

