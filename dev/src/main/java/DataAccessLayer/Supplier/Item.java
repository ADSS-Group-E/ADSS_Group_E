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
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("INSERT INTO SupplierItem (productID, name, quantity, price, supplierCN, companyNumber) " +
                    "VALUES (%d, '%s', %d, %d, '%s', %d);", item.getId(), item.getName(), item.getQuantity(), item.getPrice(), item.getSupplierCN(), item.getCompanyNumber());
            stmt.executeUpdate(sql);
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    void update(SupplierItemDTO item) {
        try {
            if (item.getOrderID() == -1) {
                c = db.connect();
                stmt = c.createStatement();
                String sql = String.format("Update SupplierItem SET quantity = %d WHERE " +
                        "productID = %d;", item.getQuantity(), item.getId());
                stmt.executeUpdate(sql);
                close();
            }
            else {
                c = db.connect();
                stmt = c.createStatement();
                String sql = String.format("Update OrderItems SET quantity = %d WHERE " +
                        "productID = %d AND orderID = %d;", item.getQuantity(), item.getId(), item.getOrderID());
                stmt.executeUpdate(sql);
                close();
            }
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    void delete(int id, int orderID) {
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("DELETE FROM OrderItems WHERE " +
                    "productID = %d AND orderID = %d;", id, orderID);
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
            String sql = "SELECT * FROM OrderItems;";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                item = new SupplierItemDTO(rs.getInt("productID"), rs.getInt("quantity"), rs.getInt("price"), rs.getInt("companyNumber"), rs.getInt("orderID"));
                result.add(item);
            }
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return result;
    }

    ArrayList<SupplierItemDTO> selectSC(int companyNumber) {
        SupplierItemDTO item;
        ArrayList<SupplierItemDTO> result = new ArrayList<>();
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("SELECT * FROM SupplierItem WHERE companyNumber = %d;", companyNumber);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                item = new SupplierItemDTO(rs.getInt("productID"), rs.getString("name"), rs.getInt("quantity"), rs.getInt("price"), rs.getString("supplierCN"), rs.getInt("companyNumber"));
                result.add(item);
            }
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return result;
    }

    SupplierItemDTO select(int id, int orderNum) {
        SupplierItemDTO item = null;
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("SELECT * FROM OrderItems WHERE " +
                    "productID = %d AND orderID = %d;", id, orderNum);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                item = new SupplierItemDTO(rs.getInt("productID"), rs.getInt("quantity"), rs.getInt("price"), rs.getInt("companyNumber"), rs.getInt("orderID"));
            }
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return item;
    }

    SupplierItemDTO supplierSelect(int id, int companyNumber) {
        SupplierItemDTO item = null;
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("SELECT * FROM SupplierItem WHERE " +
                    "productID = %d AND companynumber = %d;", id, companyNumber);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                item = new SupplierItemDTO(rs.getInt("productID"), rs.getString("name"), rs.getInt("quantity"), rs.getInt("price"), rs.getString("supplierCN"), rs.getInt("companyNumber"));
            }
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return item;
    }
}

