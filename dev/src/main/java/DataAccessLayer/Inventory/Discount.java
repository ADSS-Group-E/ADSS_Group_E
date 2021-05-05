package DataAccessLayer.Inventory;

import PresentationLayer.Inventory.DataTransferObjects.DiscountDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

class Discount {
    private DBConnection db;
    private Connection c = null;
    private Statement stmt = null;

    Discount(DBConnection dbConnection) {
        db = dbConnection;
    }

    private void close() throws SQLException {
        stmt.close();
        c.commit();
        c.close();
        c  = null;
        stmt = null;
    }

    void insert(DiscountDTO discount) {
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("INSERT INTO Discount (ID, name, discPrecentage, startDate, endDate, type) " +
                    "VALUES (%d, %s, %f, %s, %s, %s);", discount.getDid(), discount.getName(), discount.getDiscountPercent(), discount.getStartDate(), discount.getEndDate(), discount.getType());
            stmt.executeUpdate(sql);
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}
