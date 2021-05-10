package DataAccessLayer;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.sqlite.SQLiteDataSource;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;

public class DataSourceDBUnitTest extends DataSourceBasedDBTestCase {

    private FlatXmlDataSet loadedDataSet;

    /** * Load the data which will be inserted for the test * @return IDataSet */
    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("data.xml"));
    }

    @Override
    protected DatabaseOperation getSetUpOperation() {
        return DatabaseOperation.REFRESH;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }

    @Test
    public void givenDataSetEmptySchema_whenDataSetCreated_thenTablesAreEqual() throws Exception {
        IDataSet expectedDataSet = getDataSet();
        ITable expectedTable = expectedDataSet.getTable("CLIENTS");
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("CLIENTS");
        assertEquals(expectedTable, actualTable);
    }

    @Override
    protected DataSource getDataSource() {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:testData.db");
        return ds;
    }
}