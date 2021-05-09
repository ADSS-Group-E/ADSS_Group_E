package DataAccessLayer.Supplier;

import PresentationLayer.Supplier.DataTransferObjects.OrderDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierItemDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

class Order {
    private DBConnection db;
    private Connection c = null;
    private Statement stmt = null;

    Order(DBConnection dbConnection) {
        db = dbConnection;
    }

    private void close() throws SQLException {
        stmt.close();
        c.commit();
        c.close();
        c  = null;
        stmt = null;
    }

    int insert(OrderDTO order) {
        int generatedId = -1;
        try {
            String[] key = {"orderID"};
            int id = -1;
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("INSERT INTO 'Order' (date, periodicDelivery, needsDelivery) " +
                    "VALUES ('%s', %d, %d);", order.getDate(), order.getPeriodicDelivery(), order.getNeedsDelivery());
            c.prepareStatement(sql, key);
            stmt.executeUpdate(sql);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
            ArrayList<SupplierItemDTO> items = order.getOrderItems();
            key = new String[]{"ID"};
            for (SupplierItemDTO item : items) {
                sql = String.format("INSERT INTO SupplierItem (name, quantity, price, supplierCN, orderID, companyNumber) " +
                                "VALUES ('%s', %d, %d, '%s', %d, %d);",
                        item.getName(), item.getQuantity(), item.getPrice(), item.getSupplierCN(), generatedId, item.getCompanyNumber());
                c.prepareStatement(sql, key);
                stmt.executeUpdate(sql);
                key = new String[]{"companyNumber"};
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
                sql = String.format("Update SupplierItem SET quantity = quantity - %d " +
                                "WHERE ID = %d AND orderID IS NULL;",
                        item.getQuantity(), id);
                stmt.executeUpdate(sql);
            }
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return generatedId;
    }

    void delete(OrderDTO order) {
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("DELETE FROM 'Order' WHERE " +
                    "orderID = %d;", order.getId());
            stmt.executeUpdate(sql);
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    ArrayList<OrderDTO> selectRO() {
        OrderDTO order;
        SupplierItemDTO item;
        HashMap<OrderDTO, ArrayList<SupplierItemDTO>> regularOrders = new HashMap<>();
        ArrayList<OrderDTO> result = new ArrayList<>();
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = "SELECT * FROM 'Order' LEFT JOIN SupplierItem SI on 'Order'.orderID = SI.orderID WHERE 'Order'.periodicDelivery = 1;";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                order = new OrderDTO(
                        rs.getInt("orderID"), rs.getString("date"), rs.getInt("periodicDelivery"), rs.getInt("needsDelivery"), new ArrayList<>()
                );
                if (!regularOrders.containsKey(order)) {
                    regularOrders.put(order, new ArrayList<>());
                }
                item = new SupplierItemDTO(rs.getInt("ID"), rs.getString("name"), rs.getInt("quantity"), rs.getInt("price"), rs.getString("supplierCN"));
                regularOrders.get(order).add(item);
            }
            for (OrderDTO orderDTO : regularOrders.keySet())
                result.add(new OrderDTO(orderDTO.getId(), orderDTO.getDate(), orderDTO.getPeriodicDelivery(), orderDTO.getNeedsDelivery(), regularOrders.get(orderDTO)));
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return result;
    }

    ArrayList<OrderDTO> select() {
        OrderDTO order;
        SupplierItemDTO item;
        HashMap<OrderDTO, ArrayList<SupplierItemDTO>> regularOrders = new HashMap<>();
        ArrayList<OrderDTO> result = new ArrayList<>();
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = "SELECT SupplierItem.* FROM 'Order' LEFT JOIN SupplierItem SI on 'Order'.orderID = SI.orderID;";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                order = new OrderDTO(
                        rs.getInt("orderID"), rs.getString("date"), rs.getInt("periodicDelivery"), rs.getInt("needsDelivery"), new ArrayList<>()
                );
                if (!regularOrders.containsKey(order)) {
                    regularOrders.put(order, new ArrayList<>());
                }
                item = new SupplierItemDTO(rs.getInt("ID"), rs.getString("name"), rs.getInt("quantity"), rs.getInt("price"), rs.getString("supplierCN"));
                regularOrders.get(order).add(item);
            }
            for (OrderDTO orderDTO : regularOrders.keySet())
                result.add(new OrderDTO(orderDTO.getId(), orderDTO.getDate(), orderDTO.getPeriodicDelivery(), orderDTO.getNeedsDelivery(), regularOrders.get(orderDTO)));
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return result;
    }

    OrderDTO select(int id) {
        OrderDTO order = null;
        SupplierItemDTO item;
        HashMap<OrderDTO, ArrayList<SupplierItemDTO>> regularOrders = new HashMap<>();
        ArrayList<OrderDTO> result = new ArrayList<>();
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("SELECT SupplierItem.* FROM 'Order' LEFT JOIN SupplierItem SI on 'Order'.orderID = SI.orderID WHERE 'Order'.orderID = %d;", id);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                order = new OrderDTO(
                        rs.getInt("orderID"), rs.getString("date"), rs.getInt("periodicDelivery"), rs.getInt("needsDelivery"), new ArrayList<>()
                );
                if (!regularOrders.containsKey(order)) {
                    regularOrders.put(order, new ArrayList<>());
                }
                item = new SupplierItemDTO(rs.getInt("ID"), rs.getString("name"), rs.getInt("quantity"), rs.getInt("price"), rs.getString("supplierCN"));
                regularOrders.get(order).add(item);
            }
            for (OrderDTO orderDTO : regularOrders.keySet())
                result.add(new OrderDTO(orderDTO.getId(), orderDTO.getDate(), orderDTO.getPeriodicDelivery(), orderDTO.getNeedsDelivery(), regularOrders.get(orderDTO)));
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return order;
    }
}