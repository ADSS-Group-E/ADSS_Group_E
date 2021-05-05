package DataAccessLayer.Supplier;

import PresentationLayer.Supplier.DataTransferObjects.SupplierItemDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class Item {
    private DBConnection db;
    private Connection c = null;
    private Statement stmt = null;

    Item(DBConnection dbConnection) {
        db = dbConnection;
    }

    private void close() throws SQLException {
        stmt.close();
        c.commit();
        c.close();
        c  = null;
        stmt = null;
    }

    void insert(SupplierItemDTO item) {
        try {
            String[] key = {"ID"};
            int generatedId = -1;
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("INSERT INTO Item (name) " +
                    "VALUES (%s);", item.getName());
            c.prepareStatement(sql, key);
            stmt.executeUpdate(sql);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
            item.setId(generatedId);
            sql = String.format("INSERT INTO SupplierItem (ID, name, quantity, price, supplierCN, orderID) " +
                    "VALUES (%d, %s, %d, %d, %s, NULL);", item.getId(), item.getName(), item.getQuantity(), item.getPrice(), item.getSupplierCN());
            stmt.executeUpdate(sql);
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    void update(SupplierItemDTO item) {
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("Update SupplierItem SET quantity = %d WHERE " +
                    "ID = %d;", item.getQuantity(), item.getId());
            stmt.executeUpdate(sql);
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    void delete(SupplierItemDTO item) {
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("DELETE FROM SupplierItem WHERE " +
                    "ID = %d;", item.getId());
            stmt.executeUpdate(sql);
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}

