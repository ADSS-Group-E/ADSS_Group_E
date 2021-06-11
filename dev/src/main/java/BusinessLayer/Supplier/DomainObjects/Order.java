package BusinessLayer.Supplier.DomainObjects;

import BusinessLayer.Inventory.DomainObjects.DomainObject;
import DataAccessLayer.Supplier.DataAccessObjects.OrderItemGroupDAO;
import PresentationLayer.Supplier.DataTransferObjects.OrderDTO;
import PresentationLayer.Supplier.DataTransferObjects.OrderItemGroupDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierItemDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierItemGroupDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Order extends DomainObject {
    private final LocalDateTime date;
    private final boolean periodicDelivery;
    private final boolean needsDelivery;
    private HashMap<Integer, OrderItemGroup> orderItems;

    private OrderItemGroupDAO orderItemGroupDAO;

    private boolean loaded = false;

    public Order(int id, LocalDateTime date, boolean periodicDelivery, boolean needsDelivery) {
        super(id);
        this.date = date;
        this.periodicDelivery = periodicDelivery;
        this.needsDelivery = needsDelivery;
        orderItems= new HashMap<>();

        orderItemGroupDAO = new OrderItemGroupDAO();
    }

    public Order(OrderDTO other){
        super(other.getId());
        this.date = other.getDate();
        this.periodicDelivery = other.getPeriodicDelivery();
        this.needsDelivery = other.getNeedsDelivery();

        orderItemGroupDAO = new OrderItemGroupDAO();

    }

    public LocalDateTime getDate() {
        return date;
    }

    public boolean getPeriodicDelivery() {
        return periodicDelivery;
    }

    public boolean getNeedsDelivery() {
        return needsDelivery;
    }

    public HashMap<Integer, OrderItemGroup> getOrderItems() {
        loadItems();
        return orderItems;
    }

    public ArrayList<String> getOrderItemsToString(ArrayList<SupplierItemDTO> orderItems) {
        ArrayList<String> retItems = new ArrayList<>();
        for (SupplierItemDTO i : orderItems) {
            retItems.add(String.format("ID: %d\nName: %s\nPrice: %d\nQuantity: %d\nSupplier CN: %s",i.getId(), i.getName(), i.getPrice(), i.getQuantity(), i.getSupplierCN()));
        }
        return retItems;
    }

    public int getPrice(OrderDTO order) {
        int sum = 0;
        for (SupplierItemDTO supplierItemDTO : order.getOrderItems()) {
            sum += supplierItemDTO.getQuantity() * supplierItemDTO.getPrice();
        }
        return sum;
    }

    public void loadItems(){
        // Load a product's itemGroups
        if (!loaded){
            ArrayList<OrderItemGroupDTO> orderItemGroupDTOS = orderItemGroupDAO.selectByOrder(this.getId());
            HashMap<Integer,OrderItemGroup> items = new HashMap<>();

            for (OrderItemGroupDTO orderItemGroupDTO:
                    orderItemGroupDTOS) {
                OrderItemGroup orderItemGroup = new OrderItemGroup(orderItemGroupDTO);
                items.put(orderItemGroup.getId(), orderItemGroup);
            }

            this.orderItems = items;

            loaded = true;
        }
    }
}

