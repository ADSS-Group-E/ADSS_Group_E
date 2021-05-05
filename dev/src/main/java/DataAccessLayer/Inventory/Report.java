package DataAccessLayer.Inventory;


import PresentationLayer.Inventory.DataTransferObjects.ReportDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Report {
    private DBConnection db;
    private Connection c = null;
    private Statement stmt = null;

    Report(DBConnection dbConnection) {
        db = dbConnection;
    }

    private void close() throws SQLException {
        stmt.close();
        c.commit();
        c.close();
        c  = null;
        stmt = null;
    }

    void insert(ReportDTO report) {
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("INSERT INTO Item (ID, creationDate, reportTag) " +
                    "VALUES (%d, %s, %s);", report.getRid(), report.getCreated(), report.getTag());
            stmt.executeUpdate(sql);
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}
