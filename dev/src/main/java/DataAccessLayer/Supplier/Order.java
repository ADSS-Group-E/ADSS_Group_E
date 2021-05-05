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

    void insert(OrderDTO order) {
        try {
            String[] key = {"orderID"};
            int generatedId = -1;
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("INSERT INTO Order (orderID, date, periodicDelivery, needsDelivery) " +
                    "VALUES (%d, '%s', %d, %d);", order.getId(), order.getDate(), order.getPeriodicDelivery(), order.getNeedsDelivery());
            c.prepareStatement(sql, key);
            stmt.executeUpdate(sql);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
            ArrayList<SupplierItemDTO> items = order.getOrderItems();
            for (SupplierItemDTO item : items) {
                sql = String.format("INSERT INTO SupplierItem (ID, name, quantity, price, supplierCN, orderID) " +
                                "VALUES (%d, %s, %d, %d, %s, %d);",
                        item.getId(), item.getName(), item.getPrice(), item.getQuantity(), item.getSupplierCN(), generatedId);
                stmt.executeUpdate(sql);
                sql = String.format("Update SupplierItem SET quantity = quantity - %d " +
                                "WHERE ID = %d AND itemTag = 's';",
                        item.getQuantity(), item.getId());
                stmt.executeUpdate(sql);
            }
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    void delete(OrderDTO order) {
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("DELETE FROM Order WHERE " +
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
            String sql = "SELECT * FROM Order LEFT JOIN SupplierItem SI on Order.orderID = SI.orderID;";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                order = new OrderDTO(
                        rs.getInt("companyNumber"), rs.getString("date"), rs.getInt("periodicDelivery"), rs.getInt("needsDelivery"), new ArrayList<>()
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
}