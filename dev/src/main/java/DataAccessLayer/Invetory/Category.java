package DataAccessLayer.Invetory;

import PresentationLayer.Inventory.DataTransferObjects.CategoryDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

class Category {
    private DBConnection db;
    private Connection c = null;
    private Statement stmt = null;

    Category(DBConnection dbConnection) {
        db = dbConnection;
    }

    private void close() throws SQLException {
        stmt.close();
        c.commit();
        c.close();
        c  = null;
        stmt = null;
    }

    void insert(CategoryDTO category) {
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("INSERT INTO Category (ID, name, superID) " +
                    "VALUES (%d, %s, %d);", category.getCid(), category.getName(), category.getSuperCategoryId());
            stmt.executeUpdate(sql);
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}
