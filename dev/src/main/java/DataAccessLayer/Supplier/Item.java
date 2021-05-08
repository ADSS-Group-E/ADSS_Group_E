package DataAccessLayer.Supplier;

import PresentationLayer.Supplier.DataTransferObjects.SupplierItemDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

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
                    "VALUES ('%s');", item.getName());
            c.prepareStatement(sql, key);
            stmt.executeUpdate(sql);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
            item.setId(generatedId);
            sql = String.format("INSERT INTO SupplierItem (ID, name, quantity, price, supplierCN, orderID) " +
                    "VALUES (%d, '%s', %d, %d, '%s', NULL);", generatedId, item.getName(), item.getQuantity(), item.getPrice(), item.getSupplierCN());
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

    ArrayList<SupplierItemDTO> select() {
        SupplierItemDTO item;
        ArrayList<SupplierItemDTO> result = new ArrayList<>();
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = "SELECT * FROM SupplierItem WHERE orderID IS NOT NULL;";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                item = new SupplierItemDTO(rs.getInt("ID"), rs.getString("name"), rs.getInt("quantity"), rs.getInt("price"), rs.getString("supplierCN"));
                item.setOrderID(rs.getInt("orderID"));
                result.add(item);
            }
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return result;
    }

    SupplierItemDTO select(int id) {
        SupplierItemDTO item = null;
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("SELECT * FROM SupplierItem WHERE " +
                    "ID = %d AND orderID IS NOT NULL;", id);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                item = new SupplierItemDTO(rs.getInt("ID"), rs.getString("name"), rs.getInt("quantity"), rs.getInt("price"), rs.getString("supplierCN"), rs.getInt("orderID"));
            }
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return item;
    }

    SupplierItemDTO select(String name) {
        SupplierItemDTO item = null;
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("SELECT * FROM SupplierItem WHERE " +
                    "name = '%s' AND orderID IS NULL;", name);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                item = new SupplierItemDTO(rs.getInt("ID"), rs.getString("name"), rs.getInt("quantity"), rs.getInt("price"), rs.getString("supplierCN"));
            }
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return item;
    }
}

