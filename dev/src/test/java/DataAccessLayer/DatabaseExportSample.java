package DataAccessLayer;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.database.search.TablesDependencyHelper;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseExportSample
{
    public static void main(String[] args) throws Exception
    {
        // database connection
        Class driverClass = Class.forName("org.sqlite.JDBC");
        Connection jdbcConnection = DriverManager.getConnection("jdbc:sqlite:copy:resource:module.db");
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);



        // write DTD file
        //FlatDtdDataSet.write(connection.createDataSet(), new FileOutputStream("schema.dtd"));

        //String status = "before";
        String status = "after";
        //String action = "Insert";
        String action = "Remove";
        String table = "Category";

        String path = "dev/src/test/resources/data/"+table+"/";

        // dependent tables database export: export table X and all tables that
        // have a PK which is a FK on X, in the right order for insertion
        // String[] depTableNames = TablesDependencyHelper.getAllDependentTables( connection, table );
        //IDataSet depDataSet = connection.createDataSet( depTableNames );
        IDataSet depDataSet = connection.createDataSet();
        // write DTD file
        //FlatDtdDataSet.write(depDataSet, new FileOutputStream("schema.dtd"));
        FlatXmlDataSet.write(depDataSet, new FileOutputStream(path + status + action + table + ".xml"));
    }
}