package DataAccessLayer.Inventory.DataAccessObjects;

import DataAccessLayer.Inventory.DBConnection;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DataAccessObject {
    DBConnection db;
    Connection c = null;
    Statement stmt = null;

    DataAccessObject(DBConnection db) {this.db = db;}

    private void close() throws SQLException {
        stmt.close();
        c.commit();
        c.close();
        c  = null;
        stmt = null;
    }

    void insert(DataTransferObject dataTransferObject) {
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = createInsertString(dataTransferObject);
            stmt.executeUpdate(sql);
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    abstract String createInsertString(DataTransferObject dataTransferObject);
}
