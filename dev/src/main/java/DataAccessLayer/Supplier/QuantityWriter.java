package DataAccessLayer.Supplier;

import PresentationLayer.Inventory.DataTransferObjects.QuantityWriterDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

class QuantityWriter {
    private DBConnection db;
    private Connection c = null;
    private Statement stmt = null;

    QuantityWriter(DBConnection dbConnection) {
        db = dbConnection;
    }

    private void close() throws SQLException {
        stmt.close();
        c.commit();
        c.close();
        c  = null;
        stmt = null;
    }

    void insert(QuantityWriterDTO quan) {
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("INSERT INTO QuantityWriter (minPriceDiscount, reuglarCostumerDiscount, companyNumber) " +
                    "VALUES (%d, %d, %d);", quan.getMinPriceDiscount(), quan.getRegularCostumerDiscount(), quan.getCompanyNumber());
            stmt.executeUpdate(sql);
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}

