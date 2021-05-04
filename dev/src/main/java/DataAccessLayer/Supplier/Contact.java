package DataAccessLayer.Supplier;

import PresentationLayer.Inventory.DataTransferObjects.ContactDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

class Contact {
    private DBConnection db;
    private Connection c = null;
    private Statement stmt = null;

    Contact(DBConnection dbConnection) {
        db = dbConnection;
    }

    private void close() throws SQLException {
        stmt.close();
        c.commit();
        c.close();
        c  = null;
        stmt = null;
    }

    void insert(ContactDTO contact) {
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("INSERT INTO Contact (name, email) " +
                    "VALUES (%s, %s);", contact.getName(), contact.getEmail());
            stmt.executeUpdate(sql);
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}

