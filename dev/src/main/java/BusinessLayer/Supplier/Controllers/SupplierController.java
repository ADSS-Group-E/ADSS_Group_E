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



    // -------------------- ADDERS ------------------------

    public void addSupplier(Supplier supplier){
        add(supplier);
    }

    public void addSupplierContact(int supplierId, Contact contact){
        Supplier supplier = getSupplier(supplierId);
        supplier.addContact(contact);
    }

    public void addSupplierProduct(int supplierId, SupplierProduct supplierProduct){
        Supplier supplier = getSupplier(supplierId);
        supplier.addProduct(supplierProduct);
    }

    public void addSupplierItemGroup(int supplierId, int pid, SupplierItemGroup supplierItemGroup){
        Supplier supplier = getSupplier(supplierId);
        supplier.addSupplierItemGroup(pid, supplierItemGroup);
    }

    public void setSupplierQuantityWriter(int supplierId, QuantityWriter quantityWriter){
        Supplier supplier = getSupplier(supplierId);
        supplier.setQuantityWriter(quantityWriter);
    }

    public void addSupplierQuantityWriterDiscountStep(int supplierId, DiscountStep discountStep){
        Supplier supplier = getSupplier(supplierId);
        supplier.addQuantityWriterDiscountStep(discountStep);
    }





    // -------------------- REMOVERS ------------------------

    public void removeSupplier(int id){
        remove(id);
    }

    public void removeSupplierContact(int supplierId, int contactId){
        Supplier supplier = getSupplier(supplierId);
        supplier.removeContact(contactId);
    }

    public void removeSupplierProduct(int supplierId, int pid){
        Supplier supplier = getSupplier(supplierId);
        supplier.removeSupplierProduct(pid);
    }

    public void removeSupplierProductItemGroup(int supplierId, int pid, int itemId){
        Supplier supplier = getSupplier(supplierId);
        supplier.removeSupplierProductItemGroup(pid, itemId);
    }

    public void removeSupplierQuantityWriter(int supplierId){
        Supplier supplier = getSupplier(supplierId);
        supplier.removeQuantityWriter();
    }





    // -------------------- GETTERS ------------------------

    public Supplier getSupplier(int id){
        return (Supplier) get(id);
    }

    public Contact getSupplierContact(int supplierId, int contactId){
        Supplier supplier = getSupplier(supplierId);
        return supplier.getContact(contactId);
    }

    public ArrayList<Contact> getAllSupplierContacts(int supplierNum) {
        Supplier supplier = getSupplier(supplierNum);
        return supplier.getAllContacts();
    }

    public SupplierProduct getSupplierProduct(int supplierNum, int id) {
        Supplier supplier = getSupplier(supplierNum);
        return supplier.getProduct(id);
    }

    public ArrayList<SupplierProduct> getAllSupplierProducts(int supplierNum) {
        Supplier supplier = getSupplier(supplierNum);
        return supplier.getAllProducts();
    }

    public SupplierItemGroup getSupplierProductItemGroup(int supplierId, int id, int itemId){
        Supplier supplier = getSupplier(supplierId);
        return supplier.getSupplierProductItemGroup(id, itemId);
    }

    public ArrayList<SupplierItemGroup> getAllSupplierProductItemGroups(int supplierId, int id){
        Supplier supplier = getSupplier(supplierId);
        return supplier.getAllSupplierProductItemGroups(id);
    }

    public QuantityWriter getSupplierQuantityWriter(int supplierId){
        Supplier supplier = getSupplier(supplierId);
        return supplier.getQuantityWriter();
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

        /*
        // Load a supplier's orders but don't populate them
        ArrayList<OrderDTO> orderDTOs = orderDAO.selectBySupplier(supplierDTO.getId());
        HashMap<Integer, Order> orders = new HashMap<>();
        for (OrderDTO orderDTO:
                orderDTOs) {
            Order order = new Order(orderDTO);
            order.loadItems(); // TODO Can implement lazy loading?
            orders.put(order.getId(), order);
        }
*/
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
        //supplier.loadOrders(orders);
        supplier.loadContacts(contacts);
        supplier.loadQuantityWriter(quantityWriter);

        return supplier;
    }

    public ArrayList<Supplier> getAllSuppliers() {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        ArrayList<DomainObject> domainObjects = getList();

        for (DomainObject domainObject:
                domainObjects) {
            suppliers.add((Supplier)domainObject);
        }

        return suppliers;
    }

    @Override
    protected DataTransferObject buildDtoFromDomainObject(DomainObject domainObject) {
        return new SupplierDTO((Supplier) domainObject);
    }

    // ---------------- TRANSLATED FUNCTIONALITY ------------------

    // Done

    public ArrayList<String[]> getSuppliersInfo() {
        ArrayList<String[]> suppliersInfo = new ArrayList<>();
        for (Supplier supplier : getAllSuppliers()) {
            String[] info = {supplier.getId() + "", supplier.getName(),  supplier.getPaymentMethod(), supplier.getBankAccount()};
            suppliersInfo.add(info);
        }
        return suppliersInfo;
    }


}
