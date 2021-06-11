package BusinessLayer.Supplier.Controllers;

import BusinessLayer.Inventory.Controllers.DomainController;
import BusinessLayer.Inventory.DomainObjects.*;
import BusinessLayer.Supplier.DomainObjects.*;
import DataAccessLayer.Inventory.DataAccessObjects.DataAccessObject;
import DataAccessLayer.Supplier.DataAccessObjects.*;
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
    QuantityWriterDAO quantityWriterDAO;

    public SupplierController(SupplierDAO DAO) {
        super(DAO);
        supplierProductDAO = new SupplierProductDAO();
        orderDAO = new OrderDAO();
        contactDAO = new ContactDAO();
        quantityWriterDAO = new QuantityWriterDAO();
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

        QuantityWriterDTO quantityWriterDTO = quantityWriterDAO.get(supplierDTO.getQuantityWriterId());
        QuantityWriter quantityWriter = new QuantityWriter(quantityWriterDTO);


        Supplier supplier = new Supplier(supplierDTO);
        supplier.loadProducts(products);
        supplier.loadOrders(orders);
        supplier.loadContacts(contacts);
        supplier.loadQuantityWriter(quantityWriter);

        return supplier;
    }

    @Override
    protected DataTransferObject buildDtoFromDomainObject(DomainObject domainObject) {
        return null;
    }


}
