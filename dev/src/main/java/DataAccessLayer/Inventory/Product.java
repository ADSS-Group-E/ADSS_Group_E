package DataAccessLayer.Inventory;

import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Product {
    private DBConnection db;
    private Connection c = null;
    private Statement stmt = null;

    Product(DBConnection dbConnection) {
        db = dbConnection;
    }

    private void close() throws SQLException {
        stmt.close();
        c.commit();
        c.close();
        c  = null;
        stmt = null;
    }
    void insert(ProductDTO product) {
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("INSERT INTO Product (ID, name, storeLocation, storageLocation, " +
                    "manufacturer, buyPrice, sellPrice, minAmount, categoryDTO, buyDiscountID, sellDiscountID) " +
                    "VALUES (%d, %s, %s, %s, %s, %f, %f, %d, %d, %f, %f);", product.getPid(), product.getName(), product.getStorageLocation(),
            product.getStoreLocation(), product.getManufacturer(), product.getBuyingPrice(), product.getSellingPrice(), product.getMinAmount(),
                    product.getCategoryId(), product.getBuyingPriceAfterDiscount(), product.getSellingPriceAfterDiscount());
            stmt.executeUpdate(sql);
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}
