package SupplierModule.BusinessLayer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

class Order {
    private boolean constantDelivery;
    private boolean needsDelivery;
    private List<Item> orderItems;
    private ZonedDateTime date;

    Order(boolean constantDelivery, boolean needsDelivery, List<Item> orderItems) {
        this.constantDelivery = constantDelivery;
        this.needsDelivery = needsDelivery;
        this.orderItems = orderItems;
        this.date = ZonedDateTime.now();
    }

    boolean isConstantDelivery() {
        return constantDelivery;
    }

    boolean isNeedsDelivery() {
        return needsDelivery;
    }

    List<Item> getOrderItems() {
        return orderItems;
    }

    void setOrderItems(List<Item> orderItems) {
        this.orderItems = orderItems;
    }

    ArrayList<String> getOrderItemsToString() {
        ArrayList<String> retItems = new ArrayList<>();
        for (Item i : orderItems) {
            retItems.add(i.toString());
        }
        return retItems;
    }

    int getPrice() {
        int sum = 0;
        for (Item i : orderItems) {
            sum += i.getQuantity() * i.getPrice();
        }
        return sum;
    }
}

