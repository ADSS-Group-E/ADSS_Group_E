package DataAccessLayer.Supplier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataController {
    private Contact contact;
    private Item items;
    private Order orders;
    private QuantityWriter quantityWriter;
    private Supplier supplier;

    public DataController() {
        this.contact = new Contact(db);
        this.items = new Item(db);
        this.orders = new Order(db);
        this.quantityWriter = new QuantityWriter(db);
        this.supplier = new Supplier(db);
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

