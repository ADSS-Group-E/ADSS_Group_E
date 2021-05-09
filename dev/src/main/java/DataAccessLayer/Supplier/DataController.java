package DataAccessLayer.Supplier;

import PresentationLayer.Supplier.DataTransferObjects.OrderDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierItemDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DataController {
    private Contact contact;
    private Item items;
    private Order orders;
    private QuantityWriter quantityWriter;
    private Supplier supplier;

    private HashMap<Integer, SupplierDTO> suppliers = new HashMap<>();
    private HashMap<Integer, SupplierItemDTO> itemDTOs = new HashMap<>();
    private HashMap<Integer, OrderDTO> orderDTOs = new HashMap<>();

    public DataController() {
        this.contact = new Contact(db);
        this.items = new Item(db);
        this.orders = new Order(db);
        this.quantityWriter = new QuantityWriter(db);
        this.supplier = new Supplier(db);
    }

    private DBConnection db = () -> {
        // SQLite connection string
        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:module.db");
            c.setAutoCommit(false);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return c;
    };

    public SupplierDTO getSupplier(int id) {
        if (suppliers.get(id) == null) {
            return supplier.select(id);
        }
        else {
            return suppliers.get(id);
        }
    }

    public ArrayList<SupplierItemDTO> getSupplierItems(int id) { return items.selectSC(id); }

    public ArrayList<SupplierDTO> getSuppliers() {
        return supplier.select();
    }

    public ArrayList<SupplierItemDTO> getItems() {
        return items.select();
    }

    public void insert(SupplierDTO sup) {
        int id = supplier.insert(sup);
        if (id != -1)
            suppliers.put(id , sup);
    }

    public void insert(OrderDTO ord) {
        orders.insert(ord);
        if (ord.getId() != -1) {
            orderDTOs.put(ord.getId(), ord);
            for (SupplierItemDTO i : itemDTOs.values()) {
                if (i.getId() != -1)
                    itemDTOs.put(i.getId(), i);
            }
        }
    }

    public ArrayList<OrderDTO> selectRO() {
        return orders.selectRO();
    }

    public SupplierItemDTO select(int id) {
        if (itemDTOs.containsKey(id)) {
            return itemDTOs.get(id);
        }
        else {
            return items.select(id);
        }
    }

    public SupplierItemDTO select(String name) {
        return items.select(name);
    }

    public void update(SupplierItemDTO supplierItemDTO) {
        if (itemDTOs.containsKey(supplierItemDTO.getId())) {
            itemDTOs.get(supplierItemDTO.getId()).setQuantity(supplierItemDTO.getQuantity());
        }
        items.update(supplierItemDTO);
    }

    public void delete(int id) {
        itemDTOs.remove(id);
        for (OrderDTO ord : orderDTOs.values()) {
            for (SupplierItemDTO it : ord.getOrderItems()) {
                if (it.getId() == id)
                    ord.getOrderItems().remove(it);
            }
        }
        for (SupplierDTO sup : suppliers.values()) {
            for (OrderDTO ord : sup.getOrders()) {
                for (SupplierItemDTO it : ord.getOrderItems()) {
                    if (it.getId() == id)
                        ord.getOrderItems().remove(it);
                }
            }
        }
        items.delete(id);
    }

    public OrderDTO selectO(int id) {
        return orders.select(id);
    }
}

