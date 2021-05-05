package DataAccessLayer.Inventory;


import PresentationLayer.Inventory.DataTransferObjects.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Item {
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
    void insert(ItemDTO item) {
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("INSERT INTO Item (ID, ExpirationDate) " +
                    "VALUES (%d, %s);", item.getId(), item.getExpiration());
            stmt.executeUpdate(sql);
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}
