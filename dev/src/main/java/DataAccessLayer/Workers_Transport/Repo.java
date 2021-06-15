package DataAccessLayer.Workers_Transport;

import java.io.File;
import java.sql.*;

public class Repo {
    private static final String databaseName = ":resource:module.db";

    public static Connection openConnection() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
            connection.createStatement().execute("PRAGMA foreign_keys = ON");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

}



