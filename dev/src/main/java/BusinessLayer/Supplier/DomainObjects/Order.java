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
    private Supplier supplier;

    private HashMap<Integer, OrderItemGroup> orderItems;
    private OrderItemGroupDAO orderItemGroupDAO = new OrderItemGroupDAO();

    public Order(int id, LocalDateTime date, boolean periodicDelivery, boolean needsDelivery) {
        super(id);
        this.date = date;
        this.periodicDelivery = periodicDelivery;
        this.needsDelivery = needsDelivery;
        orderItems= new HashMap<>();
    }

    public Order(LocalDateTime date, boolean periodicDelivery, boolean needsDelivery) {
        super(-1);
        this.date = date;
        this.periodicDelivery = periodicDelivery;
        this.needsDelivery = needsDelivery;
        orderItems= new HashMap<>();
    }

    public Order(OrderDTO other){
        super(other.getId());
        this.date = other.getDate();
        this.periodicDelivery = other.getPeriodicDelivery();
        this.needsDelivery = other.getNeedsDelivery();
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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
        return orderItems;
    }

    public ArrayList<String> getOrderItemsToString(ArrayList<SupplierItemDTO> orderItems) {
        ArrayList<String> retItems = new ArrayList<>();
        for (SupplierItemDTO i : orderItems) {
            retItems.add(String.format("ID: %d\nName: %s\nPrice: %d\nQuantity: %d\nSupplier CN: %s",i.getId(), i.getName(), i.getPrice(), i.getQuantity(), i.getSupplierCN()));
        }
        return retItems;
    }


    // Calculates price based on sum of all items in the order
    public int getPrice() {
        int sum = 0;
        for (OrderItemGroup orderItemGroup : orderItems.values()) {
            sum += orderItemGroup.getQuantity() * orderItemGroup.getPriceBoughtAt();
        }
        return sum;
    }



    public void loadItems(HashMap<Integer, OrderItemGroup> orderItems) {
        this.orderItems = orderItems;
    }


    // -------------------- ADDERS ------------------------

    public void addOrderItemGroup(OrderItemGroup orderItemGroup) {
        OrderItemGroupDTO orderItemGroupDTO = new OrderItemGroupDTO(orderItemGroup);
        orderItemGroupDTO.setOrderId(this.getId());

        int id = orderItemGroupDAO.insertWithAI(orderItemGroupDTO);
        if (id != -1){
            orderItemGroup.setId(id);
            orderItems.put(orderItemGroup.getId(), orderItemGroup);
        }
    }

    // Not persisted
    public void proposeAddItemGroup(OrderItemGroup orderItemGroup) {
        orderItems.put(orderItemGroup.getPid(), orderItemGroup);
    }

    // -------------------- REMOVERS ------------------------

    public void removeOrderItemGroup(int id){
        if (orderItemGroupDAO.delete(id))
            orderItems.remove(id);
    }

    // -------------------- GETTERS ------------------------

    public OrderItemGroup getOrderItemGroup (int id){
        return orderItems.get(id);
    }

    public ArrayList<OrderItemGroup> getAllItemGroups(){
        return new ArrayList<>(orderItems.values());
    }
}

