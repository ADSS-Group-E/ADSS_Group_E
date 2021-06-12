package BusinessLayer.Supplier.DomainObjects;

import BusinessLayer.Inventory.DomainObjects.DomainObject;
import DataAccessLayer.Supplier.DataAccessObjects.ContactDAO;
import DataAccessLayer.Supplier.DataAccessObjects.OrderItemGroupDAO;
import DataAccessLayer.Supplier.DataAccessObjects.QuantityWriterDAO;
import DataAccessLayer.Supplier.DataAccessObjects.SupplierProductDAO;
import PresentationLayer.Supplier.DataTransferObjects.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Supplier extends DomainObject {

    private final String name;
    private final String bankAccount;
    private final String paymentMethod;
    private QuantityWriter quantityWriter; //TODO
    //private HashMap<Integer,Order> orders;
    private HashMap<Integer,SupplierProduct> products;
    private HashMap<Integer,Contact> contacts;

    private SupplierProductDAO supplierProductDAO = new SupplierProductDAO();
    private ContactDAO contactDAO = new ContactDAO();
    private QuantityWriterDAO quantityWriterDAO = new QuantityWriterDAO();

    public Supplier(int id, String name, String bankAccount, String paymentMethod) {
        super(id);
        this.name = name;
        this.bankAccount = bankAccount;
        this.paymentMethod = paymentMethod;
    }

    public Supplier(SupplierDTO other){
        super(other.getId());
        this.name = other.getName();
        this.bankAccount = other.getBankAccount();;
        this.paymentMethod=other.getPaymentMethod();
    }

    public String getName() {
        return name;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    /*
    public void loadOrders(HashMap<Integer, Order> orders) {
        this.orders = orders;
    }
*/
    public void loadProducts(HashMap<Integer, SupplierProduct> products) {
        this.products = products;
    }

    public void loadContacts(HashMap<Integer, Contact> contacts) {
        this.contacts = contacts;
    }

    public void loadQuantityWriter (QuantityWriter quantityWriter) {this.quantityWriter = quantityWriter;}

    public void addOrder(Order order){

    }

    // -------------------- ADDERS ------------------------
    public void addContact(Contact contact){
        ContactDTO contactDTO = new ContactDTO(contact);
        contactDTO.setCompanyNumber(this.getId());

        int id = contactDAO.insertWithAI(contactDTO);
        if (id != -1){
            contact.setId(id);
            contacts.put(contact.getId(), contact);
        }
    }

    public void addProduct(SupplierProduct supplierProduct){
        SupplierProductDTO supplierProductDTO = new SupplierProductDTO(supplierProduct);
        supplierProductDTO.setCompanyNumber(this.getId());

        int id = supplierProductDAO.insertWithAI(supplierProductDTO);
        if (id != -1){
            supplierProduct.setId(id);
            products.put(supplierProduct.getId(), supplierProduct);
        }
    }

    public void addSupplierItemGroup(int id, SupplierItemGroup supplierItemGroup){
        SupplierProduct supplierProduct = getProduct(id);
        supplierProduct.addItemGroup(supplierItemGroup);
    }

    public void setQuantityWriter(QuantityWriter quantityWriter){
        QuantityWriterDTO quantityWriterDTO = new QuantityWriterDTO(quantityWriter);
        quantityWriterDTO.setCompanyNumber(this.getId());

        // If not null, need to delete in persistence and insert new one instead
        if(this.quantityWriter != null){
            QuantityWriterDTO temp = new QuantityWriterDTO(this.quantityWriter);
            temp.setCompanyNumber(this.getId());
            if (quantityWriterDAO.delete(this.quantityWriter.getId())){

                int id = quantityWriterDAO.insertWithAI(quantityWriterDTO);
                if (id != -1){
                    quantityWriter.setId(id);
                    this.quantityWriter = quantityWriter;
                }
                else{
                    // Insert failed after delete succeeded, so reinsert previous one.
                    quantityWriterDAO.insert(temp);
                }
            }
        }
        else{
            if (quantityWriterDAO.insert(quantityWriterDTO)){
                this.quantityWriter = quantityWriter;
            }
        }
    }

    public void addQuantityWriterDiscountStep(DiscountStep discountStep){
        this.quantityWriter.addDiscount(discountStep);
    }

    // -------------------- REMOVERS ------------------------

    public void removeContact(int id){
        if (contactDAO.delete(id))
            contacts.remove(id);
    }

    public void removeSupplierProduct(int id){
        if (supplierProductDAO.delete(id))
            products.remove(id);
    }

    public void removeSupplierProductItemGroup(int id, int itemId){
        SupplierProduct supplierProduct = getProduct(id);
        supplierProduct.removeItemGroup(itemId);
    }
    public void removeQuantityWriter(){
        if (quantityWriterDAO.delete(quantityWriter.getId()))
            this.quantityWriter = null;
    }

    // -------------------- GETTERS ------------------------

    public Contact getContact(int id){
        return contacts.get(id);
    }

    public ArrayList<Contact> getAllContacts(){
        return new ArrayList<Contact>(contacts.values());
    }

    public SupplierProduct getProduct(int id){
        return products.get(id);
    }

    public ArrayList<SupplierProduct> getAllProducts(){
        return new ArrayList<>(products.values());
    }

    public SupplierItemGroup getSupplierProductItemGroup(int id, int itemId){
        SupplierProduct supplierProduct = getProduct(id);
        return supplierProduct.getItemGroup(itemId);
    }

    public ArrayList<SupplierItemGroup> getAllSupplierProductItemGroups(int id){
        SupplierProduct supplierProduct = getProduct(id);
        return supplierProduct.getAllItemGroups();
    }

    public QuantityWriter getQuantityWriter(){
        return this.quantityWriter;
    }

    public int getMatchingId(int inventoryPid) {
        ArrayList<SupplierProduct> products = getAllProducts();
        for (SupplierProduct supplierProduct :
                products) {
            if (supplierProduct.getPid() == inventoryPid){
                return supplierProduct.getId();
            }
        }
        return -1;
    }

    // --------------------------


    public ArrayList<OrderItemGroup> makeOrderItemGroups(int supplierProductID, int quantity) {
        ArrayList<OrderItemGroup> orderItemGroups = new ArrayList<>();
        ArrayList<SupplierItemGroup> supplierItemGroups = getAllSupplierProductItemGroups(supplierProductID);
        SupplierProduct product = getProduct(supplierProductID);

        int total = 0;
        for (SupplierItemGroup supplierItemGroup: supplierItemGroups) {

            if (total + supplierItemGroup.getQuantity() > quantity){
                orderItemGroups.add(new OrderItemGroup(product.getPid(), quantity - total, product.getPrice(), supplierItemGroup.getExpiration()));
                return orderItemGroups;
            }
            else{
                orderItemGroups.add(new OrderItemGroup(product.getPid(), supplierItemGroup.getQuantity(), product.getPrice(), supplierItemGroup.getExpiration()));
                total += supplierItemGroup.getQuantity();
            }

        }
        return null;
    }
}

