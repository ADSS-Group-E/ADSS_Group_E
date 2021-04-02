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

    public boolean isConstantDelivery() {
        return constantDelivery;
    }

    public boolean isNeedsDelivery() {
        return needsDelivery;
    }

    public List<Item> getOrderItems() {
        return orderItems;
    }

    public ArrayList<String> getOrderItemsToString() {
        ArrayList<String> retItems = new ArrayList<>();
        for (Item i : orderItems) {
            retItems.add(i.toString());
        }
        return retItems;
    }
}

