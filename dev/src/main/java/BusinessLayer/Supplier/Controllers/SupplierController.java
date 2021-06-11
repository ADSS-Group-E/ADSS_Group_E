package BusinessLayer.Supplier.Controllers;

import BusinessLayer.Inventory.Controllers.DomainController;
import BusinessLayer.Inventory.DomainObjects.*;
import BusinessLayer.Supplier.DomainObjects.Contact;
import BusinessLayer.Supplier.DomainObjects.Order;
import BusinessLayer.Supplier.DomainObjects.Supplier;
import BusinessLayer.Supplier.DomainObjects.SupplierProduct;
import DataAccessLayer.Inventory.DataAccessObjects.DataAccessObject;
import DataAccessLayer.Supplier.DataAccessObjects.ContactDAO;
import DataAccessLayer.Supplier.DataAccessObjects.OrderDAO;
import DataAccessLayer.Supplier.DataAccessObjects.SupplierDAO;
import DataAccessLayer.Supplier.DataAccessObjects.SupplierProductDAO;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.ItemGroupDTO;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;
import PresentationLayer.Supplier.DataTransferObjects.*;

import java.util.ArrayList;
import java.util.HashMap;

public class SupplierController extends DomainController {

    SupplierProductDAO supplierProductDAO;
    OrderDAO orderDAO;
    ContactDAO contactDAO;

    public SupplierController(SupplierDAO DAO) {
        super(DAO);
        supplierProductDAO = new SupplierProductDAO();
        orderDAO = new OrderDAO();
        contactDAO = new ContactDAO();
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
            supplierProduct.loadItems();
            products.put(supplierProduct.getId(), supplierProduct);
        }

        // Load a supplier's orders but don't populate them
        ArrayList<OrderDTO> orderDTOs = orderDAO.selectBySupplier(supplierDTO.getId());
        HashMap<Integer, Order> orders = new HashMap<>();
        for (OrderDTO orderDTO:
                orderDTOs) {
            Order order = new Order(orderDTO);
            order.loadItems(); // TODO Can implement lazy loading?
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
