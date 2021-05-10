package DataAccessLayer.Inventory;

import DataAccessLayer.Inventory.DataAccessObjects.*;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataController {
    private CategoryDAO category;
    private DiscountDAO discount;
    private ItemDAO item;
    private ProductDAO product;
    private ReportDAO report;

    public DataController() {
        this.category = new CategoryDAO();
        this.discount = new DiscountDAO();
        this.item = new ItemDAO();
        this.product = new ProductDAO();
        this.report = new ReportDAO();
    }

    private DBConnection db = () -> {
        // SQLite connection string
        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:module.db");
            c.setAutoCommit(false);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return c;
    };
}

