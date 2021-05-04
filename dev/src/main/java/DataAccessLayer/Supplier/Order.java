package DataAccessLayer.Supplier;

import PresentationLayer.Supplier.DataTransferObjects.OrderDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierItemDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
                    "VALUES (%d, %s, %d, %d);", order.getId(), order.getDate(), order.getPeriodicDelivery(), order.getNeedsDelivery());
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
                c.prepareStatement(sql, key);
                stmt.executeUpdate(sql);
            }
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}

