package BusinessLayer.Supplier;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private boolean constantDelivery;
    private boolean needsDelivery;
    private List<Item> orderItems;
    private ZonedDateTime date;

    public Order(boolean constantDelivery, boolean needsDelivery, List<Item> orderItems) {
        this.constantDelivery = constantDelivery;
        this.needsDelivery = needsDelivery;
        this.orderItems = orderItems;
        this.date = ZonedDateTime.now();
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

    public void setOrderItems(List<Item> orderItems) {
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

