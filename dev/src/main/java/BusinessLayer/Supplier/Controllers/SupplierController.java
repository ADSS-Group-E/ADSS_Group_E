package BusinessLayer.Supplier.Controllers;

import BusinessLayer.Inventory.Controllers.DomainController;
import BusinessLayer.Inventory.DomainObjects.*;
import BusinessLayer.Supplier.DomainObjects.Contact;
import BusinessLayer.Supplier.DomainObjects.Order;
import BusinessLayer.Supplier.DomainObjects.Supplier;
import BusinessLayer.Supplier.DomainObjects.SupplierProduct;
import DataAccessLayer.Inventory.DataAccessObjects.DataAccessObject;
import DataAccessLayer.Supplier.DataAccessObjects.SupplierDAO;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.ItemGroupDTO;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;
import PresentationLayer.Supplier.DataTransferObjects.*;

import java.util.ArrayList;
import java.util.HashMap;

public class SupplierController extends DomainController {

    public SupplierController(SupplierDAO DAO) {
        super(DAO);
    }

    @Override
    protected DomainObject buildDomainObjectFromDto(DataTransferObject dataTransferObject) {
        SupplierDTO supplierDTO = (SupplierDTO) dataTransferObject;

        // Load a supplier's products but don't populate them
        ArrayList<SupplierProductDTO> supplierProductDTOS = supplierProductDAO.selectBySupplier(supplierDTO.getId());
        HashMap<Integer, SupplierProduct> products = new HashMap<>();
        for (SupplierProductDTO supplierProductDTO:
                supplierProductDTOS) {
            SupplierProduct supplierProduct = new SupplierProduct(supplierProductDTO);
            products.put(supplierProduct.getId(), supplierProduct);
        }

        // Load a supplier's orders but don't populate them
        ArrayList<OrderDTO> orderDTOs = orderDAO.selectBySupplier(supplierDTO.getId());
        HashMap<Integer, Order> orders = new HashMap<>();
        for (OrderDTO orderDTO:
                orderDTOs) {
            Order order = new Order(orderDTO);
            orders.put(order.getId(), order);
        }

        // Load a supplier's contacts
        ArrayList<ContactDTO> contactDTOS = contactDAO.selectBySupplier(supplierDTO.getId());
        HashMap<Integer, Contact> contacts = new HashMap<>();
        for (ContactDTO contactDTO:
                contactDTOS) {
            Contact contact = new Contact(contactDTO);
            contacts.put(contact.getId(), contact);
        }

        Supplier supplier = new Supplier(supplierDTO);
        supplier.loadProducts(products);
        supplier.loadOrders(orders);
        supplier.loadContacts(contacts);

        return supplier;
    }

    @Override
    protected DataTransferObject buildDtoFromDomainObject(DomainObject domainObject) {
        return null;
    }
}
