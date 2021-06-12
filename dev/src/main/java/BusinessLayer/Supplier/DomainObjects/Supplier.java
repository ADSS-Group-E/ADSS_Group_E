package BusinessLayer.Supplier.DomainObjects;

import BusinessLayer.Inventory.DomainObjects.DomainObject;
import DataAccessLayer.Supplier.DataAccessObjects.ContactDAO;
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
    // Kept in the hashmap with PID, not the ID
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

    public void addSupplierItemGroup(int pid, SupplierItemGroup supplierItemGroup){
        SupplierProduct supplierProduct = getProduct(pid);
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

    public void removeContact(int pid){
        if (contactDAO.delete(pid))
            contacts.remove(pid);
    }

    public void removeSupplierProduct(int pid){
        if (supplierProductDAO.delete(pid))
            products.remove(pid);
    }

    public void removeSupplierProductItemGroup(int pid, int itemId){
        SupplierProduct supplierProduct = getProduct(pid);
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

    public SupplierProduct getProduct(int pid){
        return products.get(pid);
    }

    public ArrayList<SupplierProduct> getAllProducts(){
        return new ArrayList<>(products.values());
    }

    public SupplierItemGroup getSupplierProductItemGroup(int pid, int itemId){
        SupplierProduct supplierProduct = getProduct(pid);
        return supplierProduct.getItemGroup(itemId);
    }

    public ArrayList<SupplierItemGroup> getAllSupplierProductItemGroups(int pid){
        SupplierProduct supplierProduct = getProduct(pid);
        return supplierProduct.getAllItemGroups();
    }

    public QuantityWriter getQuantityWriter(){
        return this.quantityWriter;
    }

}

