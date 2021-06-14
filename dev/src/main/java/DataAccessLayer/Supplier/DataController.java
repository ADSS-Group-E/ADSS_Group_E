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
            c = DriverManager.getConnection("jdbc:sqlite::resource:module.db");
            c.setAutoCommit(false);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
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

    public ArrayList<SupplierItemDTO> getSupplierItems(int id) {
        return items.selectSC(id);
    }

    public ArrayList<SupplierDTO> getSuppliers() {
        return supplier.select();
    }

    public ArrayList<SupplierItemDTO> getItems() {
        return items.select();
    }

    public int chooseBestSupplier(ArrayList<String[]> itemList) {
        StringBuilder query = new StringBuilder("(");
        int counter = 0;
        for (String[] i : itemList) {
            query.append(String.format("%s,", i[0]));
            counter++;
        }
        query = new StringBuilder(query.substring(0, query.length() - 1) + ")");
        return items.chooseBestSupplier(query.toString(), counter);
    }

    public HashMap<OrderDTO, ArrayList<Integer>> getDeliveryOrders() {
        HashMap<OrderDTO, ArrayList<Integer>> result = new HashMap<>();
        ArrayList<OrderDTO> deliveryOrders =  orders.selectDO();
        for (OrderDTO ord : deliveryOrders) {
            result.put(ord, orders.selectSupplyDays(ord.getOrderItems().get(0).getCompanyNumber()));
        }
        return result;
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

    public SupplierItemDTO select(int id, int orderNum) {
        if (itemDTOs.containsKey(id)) {
            return itemDTOs.get(id);
        }
        else {
            return items.select(id, orderNum);
        }
    }

    public SupplierItemDTO supplierSelect(int id, int companyNumber) {
        return items.supplierSelect(id, companyNumber);
    }

    public void update(SupplierItemDTO supplierItemDTO) {
        if (itemDTOs.containsKey(supplierItemDTO.getId())) {
            itemDTOs.get(supplierItemDTO.getId()).setQuantity(supplierItemDTO.getQuantity());
        }
        items.update(supplierItemDTO);
    }

    public void delete(int id, int orderID) {
        itemDTOs.remove(id);
        for (OrderDTO ord : orderDTOs.values()) {
            if (ord.getId() == orderID)
                for (SupplierItemDTO it : ord.getOrderItems()) {
                    if (it.getId() == id)
                        ord.getOrderItems().remove(it);
                }
        }
        for (SupplierDTO sup : suppliers.values()) {
            for (OrderDTO ord : sup.getOrders()) {
                if (ord.getId() == orderID) {
                    for (SupplierItemDTO it : ord.getOrderItems()) {
                        if (it.getId() == id) {
                            ord.getOrderItems().remove(it);
                        }
                    }
                }
            }
        }
        items.delete(id, orderID);
    }

    public OrderDTO selectO(int id) {
        return orders.select(id);
    }

    public void cancelOrders(ArrayList<Integer> orderIDs) {
        StringBuilder set = new StringBuilder("(");
        for (int i : orderIDs) {
            set.append(i).append(", ");
        }
        set = new StringBuilder(set.substring(0, set.length() - 2) + ")");
        orders.delete(set.toString());
    }
}

