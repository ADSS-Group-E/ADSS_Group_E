package SupplierModule.BusinessLayer;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private boolean constantDelivery;
    private boolean needsDelivery;
    private List<Item> orderItems;

    public Order(boolean constantDelivery, boolean needsDelivery, List<Item> orderItems) {
        this.constantDelivery = constantDelivery;
        this.needsDelivery = needsDelivery;
        this.orderItems = orderItems;
    }
}

