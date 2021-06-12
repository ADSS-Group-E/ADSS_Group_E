package BusinessLayer.Supplier.DomainObjects;

import BusinessLayer.Inventory.DomainObjects.DomainObject;
import DataAccessLayer.Supplier.DataAccessObjects.SupplierItemGroupDAO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierItemGroupDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierProductDTO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class SupplierProduct extends DomainObject {
    private final int pid;
    private final String name;
    private int quantity;
    private final double price;
    private final String supplierCN;
    private HashMap<Integer, SupplierItemGroup> items;
    private SupplierItemGroupDAO supplierItemGroupDAO;
    private boolean loaded = false;

    public SupplierProduct(int id, int pid, String name, int quantity, double price, String supplierCN){
        super(id);
        this.pid = pid;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.supplierCN = supplierCN;
        items = new HashMap<>();

        supplierItemGroupDAO = new SupplierItemGroupDAO();
    }

    public SupplierProduct(SupplierProductDTO other){
        super(other.getId());
        this.pid = other.getPid();
        this.name = other.getName();
        this.quantity = other.getQuantity();
        this.price = other.getPrice();
        this.supplierCN = other.getSupplierCN();
        items = new HashMap<>();

        supplierItemGroupDAO = new SupplierItemGroupDAO();
    }

    public int getPid() {
        return pid;
    }

    public String getSupplierCN() {
        return supplierCN;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void loadItems(){
        // Load a product's itemGroups
        if (!loaded){
            ArrayList<SupplierItemGroupDTO> supplierItemGroupDTOS = supplierItemGroupDAO.selectByProduct(this.getId());
            HashMap<Integer,SupplierItemGroup> items = new HashMap<>();

            for (SupplierItemGroupDTO supplierItemGroupDTO:
                    supplierItemGroupDTOS) {
                SupplierItemGroup supplierItemGroup = new SupplierItemGroup(supplierItemGroupDTO);
                items.put(supplierItemGroup.getId(), supplierItemGroup);
            }

            this.items = items;

            loaded = true;
        }
    }

    // -------------------- ADDERS ------------------------

    public void addItemGroup( SupplierItemGroup supplierItemGroup){
        SupplierItemGroupDTO supplierItemGroupDTO = new SupplierItemGroupDTO(supplierItemGroup);
        supplierItemGroupDTO.setSupplierProductId(this.getId());

        int id = supplierItemGroupDAO.insertWithAI(supplierItemGroupDTO);
        if (id != -1){
            supplierItemGroup.setId(id);
            items.put(supplierItemGroup.getId(), supplierItemGroup);
        }
    }

    // -------------------- REMOVERS ------------------------

    public void removeItemGroup(int id){
        if (supplierItemGroupDAO.delete(id))
            items.remove(id);
    }

    // -------------------- GETTERS ------------------------

    public SupplierItemGroup getItemGroup (int id){
        return items.get(id);
    }

    public ArrayList<SupplierItemGroup> getAllItemGroups(){
        return new ArrayList<>(items.values());
    }
}
