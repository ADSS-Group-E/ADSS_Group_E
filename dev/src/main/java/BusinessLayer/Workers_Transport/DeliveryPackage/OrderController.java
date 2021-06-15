package BusinessLayer.Workers_Transport.DeliveryPackage;

import BusinessLayer.Supplier.SupplierController;
import DataAccessLayer.Workers_Transport.Transports.DTO;
import PresentationLayer.Supplier.DataTransferObjects.OrderDTO;

import java.sql.SQLException;
import java.util.*;

public class OrderController {

    private Map<Integer, Order> orders;
    private static OrderController orderController = null;
    private HashMap<Order,ArrayList<Integer>> ordersMap;

    private OrderController()
    {
        ordersMap = new HashMap<>();
        this.orders = new HashMap<>();
        SupplierController supplierController= new SupplierController();
        HashMap<OrderDTO,ArrayList<Integer>> temp=  supplierController.getDeliveryOrders();
        for(OrderDTO key :temp.keySet()){
            Order order = new Order(key);
            ordersMap.put(order,temp.get(key));
        }
    }

    public static OrderController getInstance()
    {
        if(orderController == null)
            orderController = new OrderController();
        return orderController;
    }

    public Map<Integer, Order> getOrders()
    {
        return orders;
    }
    public Order getOrder(int id) throws Exception {
        Order o= DataAccessLayer.Workers_Transport.Transports.Order.checkOrder(id);
        if(o==null)
            throw new Exception("the order doesn't exists");
        return o;
    }

    public Order createOrder(int id, Map<Integer, Integer> items, String supplierId, int locationId, double totalWeight) throws Exception {
        //if(DataAccessLayer.Workers_Transport.Transports.Order.checkOrder(id)!=null)
            //throw new Exception("the order already exists");
        if(items.isEmpty())
            throw new Exception("can't create an order with no items");
        Order order = new Order(id, items, supplierId, locationId, totalWeight);
        return order;
    }

    public void addOrder(Order order) throws Exception {
        //if(DataAccessLayer.Workers_Transport.Transports.Order.checkOrder(order.getId())!=null)
            //throw new Exception("the order already exists");
        //this.orders.put(order.getId(), order);
        DataAccessLayer.Workers_Transport.Transports.Order.insertOrder(new DTO.Order(order.getId(),order.getSupplierId(),order.getLocationId(),order.getTotalWeight()));
        for (Map.Entry<Integer,Integer > entry: order.getItems().entrySet()
        ) {
            DataAccessLayer.Workers_Transport.Transports.Order.insertItemsForOrders(new DTO.ItemsForOrders(order.getId(),entry.getKey(),entry.getValue()));
        }
    }

    public void removeOrder(Order order) throws Exception {
        if(DataAccessLayer.Workers_Transport.Transports.Order.checkOrder(order.getId())==null)
            throw new Exception("the order doesn't exists");
        this.orders.remove(order.getId());
        DataAccessLayer.Workers_Transport.Transports.Order.deleteOrder(order.getId());
    }

    public void addItem(int id, int item, int quantity) throws Exception {
        if(DataAccessLayer.Workers_Transport.Transports.Order.checkOrder(id)==null)
            throw new Exception("the order doesn't exists");
        if(quantity <= 0)
            throw new Exception("the quantity is illegal");
        if(DataAccessLayer.Workers_Transport.Transports.Order.checkItem(id,item))
            throw new Exception("item already exists in order");
        orders.get(id).getItems().put(item, quantity);
        DataAccessLayer.Workers_Transport.Transports.Order.insertItemsForOrders(new DTO.ItemsForOrders(id,item,quantity));
    }
    public void removeItem(int id, int item) throws Exception
    {
        Order o =DataAccessLayer.Workers_Transport.Transports.Order.checkOrder(id);

        if(o==null)
            throw new Exception("the order doesn't exists");
        if(!DataAccessLayer.Workers_Transport.Transports.Order.checkItem(id,item))
            throw new Exception("the item doesn't exists");
        if(o.getItems().size()==1)
            throw new Exception("orders can't have zero items");
        orders.get(id).getItems().remove(item);
        DataAccessLayer.Workers_Transport.Transports.Order.deleteItem(id,item);
    }
    public void updateQuantity(int id, int item, int quantity) throws Exception
    {
        if(DataAccessLayer.Workers_Transport.Transports.Order.checkOrder(id)==null)
            throw new Exception("the order doesn't exists");
        if(quantity <= 0)
            throw new Exception("the quantity is illegal");
        if(!DataAccessLayer.Workers_Transport.Transports.Order.checkItem(id,item))
            throw new Exception("item doesn't exists in order");
        DataAccessLayer.Workers_Transport.Transports.Order.checkOrder(id).getItems().put(item, quantity);
        DataAccessLayer.Workers_Transport.Transports.Order.updatQunt(id,item,quantity);
    }
    public void updateTotalWeight(int id, double totalWeight) throws Exception {
        if(DataAccessLayer.Workers_Transport.Transports.Order.checkOrder(id)==null)
            throw new Exception("the order doesn't exists");
        DataAccessLayer.Workers_Transport.Transports.Order.checkOrder(id).setTotalWeight(totalWeight);
        DataAccessLayer.Workers_Transport.Transports.Order.updateTotal(id,totalWeight);
    }
    public void printOrders() throws SQLException {
        DataAccessLayer.Workers_Transport.Transports.Order.printOrders();
    }
}
