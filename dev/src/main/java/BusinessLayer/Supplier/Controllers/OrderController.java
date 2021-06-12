package BusinessLayer.Supplier.Controllers;

import BusinessLayer.Inventory.Controllers.DomainController;
import BusinessLayer.Inventory.DomainObjects.DomainObject;
import BusinessLayer.Supplier.DomainObjects.*;
import DataAccessLayer.Supplier.DataAccessObjects.*;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Supplier.DataTransferObjects.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class OrderController extends DomainController {

    Order proposedOrder;
    private SupplierController supplierController;

    private OrderItemGroupDAO orderItemGroupDAO;
    public OrderController(OrderDAO DAO) {
        super(DAO);

        orderItemGroupDAO= new OrderItemGroupDAO();
    }

    public OrderController(SupplierController supplierController) {
        super(new OrderDAO());

        orderItemGroupDAO= new OrderItemGroupDAO();
        this.supplierController = supplierController;
    }





    // -------------------- ADDERS ------------------------

    public void addOrder(Order order){
        int id = addWithAI(order);


        if (id != -1){
            ArrayList<OrderItemGroup> orderItemGroups = order.getAllItemGroups();
            if (orderItemGroups == null)
                return;
            orderItemGroups.forEach((orderItemGroup)-> addOrderItemGroup(order.getId(), orderItemGroup));
        }
    }

    public void addOrderItemGroup(int orderId, OrderItemGroup orderItemGroup){
        Order order = getOrder(orderId);
        order.addOrderItemGroup( orderItemGroup);
    }



    // -------------------- REMOVERS ------------------------

    public void removeOrder(int id){
        remove(id);
    }

    public void removeOrderItemGroup(int orderId, int itemId){
        Order order = getOrder(orderId);
        order.removeOrderItemGroup(itemId);
    }



    // -------------------- GETTERS ------------------------

    public Order getOrder(int id){
        return (Order) get(id);
    }

    public ArrayList<Order> getAllOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        ArrayList<DomainObject> domainObjects = getList();

        for (DomainObject domainObject:
                domainObjects) {
            orders.add((Order)domainObject);
        }

        return orders;
    }

    public OrderItemGroup getOrderItemGroup(int orderId, int itemId){
        Order order = getOrder(orderId);
        return order.getOrderItemGroup(itemId);
    }

    public ArrayList<OrderItemGroup> getAllOrderItemGroups(int orderId){
        Order order = getOrder(orderId);
        return order.getAllItemGroups();
    }


    // ------------------------------------------

    public String proposeOrder(int supplierId, HashMap<Integer, Integer> itemsAndTheirQuantities, LocalDateTime date, boolean periodicDelivery, boolean needsDelivery){
        proposedOrder =supplierController.proposeOrder(supplierId, itemsAndTheirQuantities, date, periodicDelivery, needsDelivery);
        if (proposedOrder != null){
            return proposedOrder.toString();
        }
        else
            return null;
    }

    public void confirmOrder(){
        addOrder(proposedOrder);
    }

    public void cancelOrder(){
        proposedOrder = null;
    }

    @Override
    protected DomainObject buildDomainObjectFromDto(DataTransferObject dataTransferObject) {
        OrderDTO orderDTO = (OrderDTO) dataTransferObject;

        ArrayList<OrderItemGroupDTO> orderItemGroupDTOS = orderItemGroupDAO.selectByOrder(orderDTO.getId());
        HashMap<Integer,OrderItemGroup> items = new HashMap<>();

        for (OrderItemGroupDTO orderItemGroupDTO:
                orderItemGroupDTOS) {
            OrderItemGroup orderItemGroup = new OrderItemGroup(orderItemGroupDTO);
            items.put(orderItemGroup.getId(), orderItemGroup);
        }

        Order order = new Order(orderDTO);
        order.loadItems(items);

        return order;
    }

    @Override
    protected DataTransferObject buildDtoFromDomainObject(DomainObject domainObject) {
        return new OrderDTO((Order) domainObject);
    }


    // ---------------- TRANSLATED FUNCTIONALITY ------------------

    // Done


}
