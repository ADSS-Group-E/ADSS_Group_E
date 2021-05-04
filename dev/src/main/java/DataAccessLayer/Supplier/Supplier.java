package DataAccessLayer.Supplier;

import PresentationLayer.Supplier.DataTransferObjects.SupplierDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

class Supplier {
    private DBConnection db;
    private Connection c = null;
    private Statement stmt = null;

    Supplier(DBConnection dbConnection) {
        db = dbConnection;
    }

    private void close() throws SQLException {
        stmt.close();
        c.commit();
        c.close();
        c  = null;
        stmt = null;
    }

    void insert(SupplierDTO sup) {
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("INSERT INTO Supplier (companyNumber, name, paymentMethod, bankAccount) " +
                    "VALUES (%d, %s, %s, %s );", sup.getCompanyNumber(), sup.getName(), sup.getPaymentMethod(), sup.getBankAccount());
            stmt.executeUpdate(sql);
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}

