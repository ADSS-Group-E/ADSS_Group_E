package DataAccessLayer.Inventory;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataController {
    private Category category;
    private Discount discount;
    private Item item;
    private Product product;
    private Report report;

    public DataController() {
        this.category = new Category(db);
        this.discount = new Discount(db);
        this.item = new Item(db);
        this.product = new Product(db);
        this.report = new Report(db);
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

