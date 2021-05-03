package BussinessLayer.DeliveryPackage;

import java.util.*;

public class OrderController {

    private Map<String, Order> orders;
    private static OrderController orderController = null;

    private OrderController()
    {
        this.orders = new HashMap<>();
    }

    public static OrderController getInstance()
    {
        if(orderController == null)
            orderController = new OrderController();
        return orderController;
    }

    public Map<String, Order> getOrders()
    {
        return orders;
    }
    public Order getOrder(String id) throws Exception {
        if(!orders.containsKey(id))
            throw new Exception("The Order Doesn't Exists");
        return orders.get(id);
    }

    public Order createOrder(String id, Map<String, Integer> items, String supplierId, String locationId, double totalWeight) throws Exception {
        if(orders.containsKey(id))
            throw new Exception("The Order Already Exists");
        Order order = new Order(id, items, supplierId, locationId, totalWeight);
        return order;
    }

    public void addOrder(Order order) throws Exception {
        if(orders.containsKey(order.getId()))
            throw new Exception("Order Already Exists");
        this.orders.put(order.getId(), order);
    }

    public void removeOrder(Order order) throws Exception {
        if(!orders.containsKey(order.getId()))
            throw new Exception("The Order Doesn't Exists");
        this.orders.remove(order.getId());
    }

    public void addItem(String id, String item, int quantity) throws Exception {
        if(!orders.containsKey(id))
            throw new Exception("The Order Doesn't Exists");
        if(quantity <= 0)
            throw new Exception("The Quantity Is Illegal");
        if(orders.get(id).getItems().containsKey(item))
            throw new Exception("The Item Already Exists");
        orders.get(id).getItems().put(item, quantity);
    }
    public void removeItem(String id, String item) throws Exception
    {
        if(!orders.containsKey(id))
            throw new Exception("The Order Doesn't Exists");
        if(!orders.get(id).getItems().containsKey(item))
            throw new Exception("The Item Doesn't Exists");
        orders.get(id).getItems().remove(item);
    }
    public void updateQuantity(String id, String item, int quantity) throws Exception
    {
        if(!orders.containsKey(id))
            throw new Exception("The Order Doesn't Exists");
        if(quantity <= 0)
            throw new Exception("The Quantity Is Illegal");
        if(!orders.get(id).getItems().containsKey(item))
            throw new Exception("The Item Doesn't Exists");
        orders.get(id).getItems().put(item, quantity);
    }
    public void updateTotalWeight(String id, double totalWeight) throws Exception {
            if(!orders.containsKey(id))
                throw new Exception("The Order Doesn't Exists");
            orders.get(id).setWeight(totalWeight);
    }
}
