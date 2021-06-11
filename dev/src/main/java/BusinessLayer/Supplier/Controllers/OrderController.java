package BusinessLayer.Supplier.Controllers;

import BusinessLayer.Inventory.Controllers.DomainController;
import BusinessLayer.Inventory.DomainObjects.DomainObject;
import BusinessLayer.Supplier.DomainObjects.*;
import DataAccessLayer.Supplier.DataAccessObjects.*;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Supplier.DataTransferObjects.*;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderController extends DomainController {

    private OrderItemGroupDAO orderItemGroupDAO;
    public OrderController(OrderDAO DAO) {
        super(DAO);

        orderItemGroupDAO= new OrderItemGroupDAO();
    }

    public void addOrder(Order order){
        add(order);
    }

    public void removeOrder(int id){
        remove(id);
    }

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
        return null;
    }


    // ---------------- TRANSLATED FUNCTIONALITY ------------------

    // Done


}
