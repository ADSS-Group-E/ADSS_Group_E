package BusinessLayer.Supplier.Controllers;

import BusinessLayer.Inventory.DomainObjects.DomainObject;
import BusinessLayer.Inventory.DomainObjects.Product;
import BusinessLayer.Supplier.DomainObjects.*;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;
import PresentationLayer.Supplier.DataTransferObjects.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class SupplierFacade {
    SupplierController supplierController;
    OrderController orderController;

    public SupplierFacade(SupplierController supplierController, OrderController orderController) {
        this.supplierController = supplierController;
        this.orderController = orderController;
    }

    // -----------------------------------------------------------------------
    // -------------------------- SUPPLIERS ----------------------------------
    // -----------------------------------------------------------------------


    // -------------------- ADDERS ------------------------

    public void addSupplier(SupplierDTO supplierDTO){
        Supplier supplier = new Supplier(supplierDTO);
        supplierController.addSupplier(supplier);
    }

    public void addSupplierContact(ContactDTO contactDTO){
        Contact contact = new Contact(contactDTO);
        supplierController.addSupplierContact(contactDTO.getCompanyNumber(), contact);
    }

    public void addSupplierProduct(SupplierProductDTO supplierProductDTO){
        SupplierProduct supplierProduct = new SupplierProduct(supplierProductDTO);
        supplierController.addSupplierProduct(supplierProductDTO.getCompanyNumber(), supplierProduct);
    }

    public void addSupplierItemGroup(int supplierId, SupplierItemGroupDTO supplierItemGroupDTO){
        SupplierItemGroup  supplierItemGroup= new SupplierItemGroup(supplierItemGroupDTO);
        supplierController.addSupplierItemGroup(supplierId, supplierItemGroupDTO.getSupplierProductId(), supplierItemGroup);
    }

    public void setSupplierQuantityWriter(QuantityWriterDTO quantityWriterDTO){
        QuantityWriter quantityWriter = new QuantityWriter(quantityWriterDTO);
        supplierController.setSupplierQuantityWriter(quantityWriterDTO.getCompanyNumber(), quantityWriter);
    }

    public void addSupplierQuantityWriterDiscountStep(int supplierId, DiscountStepDTO discountStepDTO){
        DiscountStep discountStep = new DiscountStep(discountStepDTO);
        supplierController.addSupplierQuantityWriterDiscountStep(supplierId, discountStep);
    }





    // -------------------- REMOVERS ------------------------

    public void removeSupplier(int id){
        supplierController.removeSupplier(id);
    }

    public void removeSupplierContact(int supplierId, int contactId){
        supplierController.removeSupplierContact(supplierId, contactId);
    }

    public void removeSupplierProduct(int supplierId, int pid){
        supplierController.removeSupplierProduct(supplierId, pid);
    }

    public void removeSupplierProductItemGroup(int supplierId, int pid, int itemId){
        supplierController.removeSupplierProductItemGroup(supplierId, pid, itemId);
    }

    public void removeSupplierQuantityWriter(int supplierId){
        supplierController.removeSupplierQuantityWriter(supplierId);
    }





    // -------------------- GETTERS ------------------------

    public SupplierDTO getSupplier(int id){
        return new SupplierDTO(supplierController.getSupplier(id));
    }

    public ArrayList<SupplierDTO> getAllSuppliers(){
        ArrayList<SupplierDTO> DTOlist = new ArrayList<>();
        //Turn products into DTOs
        ArrayList<Supplier> suppliers = supplierController.getAllSuppliers();
        if (suppliers == null){
            return null;
        }
        suppliers.forEach((supplier)-> DTOlist.add(new SupplierDTO(supplier)));
        return DTOlist;
    }

    public ContactDTO getSupplierContact(int supplierId, int contactId){
        ContactDTO contactDTO = new ContactDTO(supplierController.getSupplierContact(supplierId, contactId));
        contactDTO.setCompanyNumber(supplierId);
        return contactDTO;
    }

    public ArrayList<ContactDTO> getAllSupplierContacts(int supplierNum) {
        ArrayList<ContactDTO>  contactDTOS = new ArrayList<>();
        ArrayList<Contact> contacts = supplierController.getAllSupplierContacts(supplierNum);

        if (contacts == null){
            return null;
        }
        contacts.forEach((contact)-> {
            ContactDTO contactDTO = new ContactDTO(contact);
            contactDTO.setCompanyNumber(supplierNum);
            contactDTOS.add(contactDTO);
        });

        return contactDTOS;
    }

    public SupplierProductDTO getSupplierProduct(int supplierNum, int pid) {
        SupplierProductDTO supplierProductDTO = new SupplierProductDTO(supplierController.getSupplierProduct(supplierNum, pid));
        supplierProductDTO.setCompanyNumber(supplierNum);
        return supplierProductDTO;
    }

    public ArrayList<SupplierProductDTO> getAllSupplierProducts(int supplierNum) {
        ArrayList<SupplierProductDTO>  supplierProductDTOS = new ArrayList<>();
        ArrayList<SupplierProduct> supplierProducts = supplierController.getAllSupplierProducts(supplierNum);

        if (supplierProducts == null){
            return null;
        }
        supplierProducts.forEach((supplierProduct)-> {
            SupplierProductDTO supplierProductDTO = new SupplierProductDTO(supplierProduct);
            supplierProductDTO.setCompanyNumber(supplierNum);
            supplierProductDTOS.add(supplierProductDTO);
        });

        return supplierProductDTOS;
    }

    public SupplierItemGroupDTO getSupplierProductItemGroup(int supplierId, int pid, int itemId){
        SupplierItemGroupDTO supplierItemGroupDTO = new SupplierItemGroupDTO(supplierController.getSupplierProductItemGroup(supplierId, pid, itemId));
        supplierItemGroupDTO.setSupplierProductId(pid);
        return supplierItemGroupDTO;
    }

    public ArrayList<SupplierItemGroupDTO> getAllSupplierProductItemGroups(int supplierId, int pid){
        ArrayList<SupplierItemGroupDTO>  supplierItemGroupDTOS = new ArrayList<>();
        ArrayList<SupplierItemGroup> supplierItemGroups = supplierController.getAllSupplierProductItemGroups(supplierId, pid);

        if (supplierItemGroups == null){
            return null;
        }
        supplierItemGroups.forEach((supplierItemGroup)-> {
            SupplierItemGroupDTO supplierItemGroupDTO = new SupplierItemGroupDTO(supplierItemGroup);
            supplierItemGroupDTO.setSupplierProductId(pid);
            supplierItemGroupDTOS.add(supplierItemGroupDTO);
        });

        return supplierItemGroupDTOS;
    }

    public QuantityWriterDTO getSupplierQuantityWriter(int supplierId){
        QuantityWriterDTO quantityWriterDTO = new QuantityWriterDTO(supplierController.getSupplierQuantityWriter(supplierId));
        quantityWriterDTO.setCompanyNumber(supplierId);
        return quantityWriterDTO;
    }





    // --------------------------------------------------------------------
    // -------------------------- ORDERS ----------------------------------
    // --------------------------------------------------------------------

    // -------------------- ADDERS ------------------------

    public void addOrder(OrderDTO orderDTO){
        Order order = new Order(orderDTO);
        orderController.addOrder(order);
    }

    public void addOrderItemGroup(OrderItemGroupDTO orderItemGroupDTO){
        OrderItemGroup  orderItemGroup= new OrderItemGroup(orderItemGroupDTO);
        orderController.addOrderItemGroup(orderItemGroupDTO.getOrderId(), orderItemGroup);
    }



    // -------------------- REMOVERS ------------------------

    public void removeOrder(int id){
        orderController.removeOrder(id);
    }

    public void removeOrderItemGroup(int orderId, int itemId){
        orderController.removeOrderItemGroup(orderId,itemId);
    }



    // -------------------- GETTERS ------------------------

    public OrderDTO getOrder(int id){
        return new OrderDTO(orderController.getOrder(id));
    }

    public ArrayList<OrderDTO> getAllOrders() {
        ArrayList<Order> orders = orderController.getAllOrders();
        ArrayList<OrderDTO> DTOlist = new ArrayList<>();

        if (orders == null){
            return null;
        }
        orders.forEach((order)-> DTOlist.add(new OrderDTO(order)));
        return DTOlist;
    }

    public OrderItemGroupDTO getOrderItemGroup(int orderId, int itemId){
        OrderItemGroupDTO orderItemGroupDTO = new OrderItemGroupDTO(orderController.getOrderItemGroup(orderId, itemId));
        orderItemGroupDTO.setOrderId(orderId);
        return orderItemGroupDTO;
    }

    public ArrayList<OrderItemGroupDTO> getAllOrderItemGroups(int orderId){
        ArrayList<OrderItemGroupDTO>  orderItemGroupDTOS = new ArrayList<>();
        ArrayList<OrderItemGroup> orderItemGroups = orderController.getAllOrderItemGroups(orderId);

        if (orderItemGroups == null){
            return null;
        }
        orderItemGroups.forEach((orderItemGroup)-> {
            OrderItemGroupDTO orderItemGroupDTO = new OrderItemGroupDTO(orderItemGroup);
            orderItemGroupDTO.setOrderId(orderId);
            orderItemGroupDTOS.add(orderItemGroupDTO);
        });

        return orderItemGroupDTOS;
    }

    // -------------------------

    public String proposeOrder(int supplierId, HashMap<Integer, Integer> itemsAndTheirQuantities, LocalDateTime date, boolean periodicDelivery, boolean needsDelivery) {
        return orderController.proposeOrder(supplierId, itemsAndTheirQuantities, date, periodicDelivery, needsDelivery);
    }
}
