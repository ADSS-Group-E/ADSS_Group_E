package DataAccessLayer;

import DataAccessLayer.Inventory.DataAccessObjects.DataAccessObject;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class TestDAO {

    protected IDatabaseTester databaseTester;
    protected DataAccessObject dataAccessObject;
    protected String table;
    protected String dataPath = "data/";
    protected String url = "jdbc:sqlite::resource:data/testDatabase.db";
    protected String dtd = dataPath + "schema.dtd";

    @Before
    public void setUp() throws Exception
    {

        databaseTester = new JdbcDatabaseTester("org.sqlite.JDBC", url);

        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setMetaDataSetFromDtd(getClass().getClassLoader().getResourceAsStream(dtd));

        IDataSet dataSet = builder.build(getClass().getClassLoader().getResourceAsStream(dataPath + table + "/before" + table + ".xml"));

        databaseTester.setDataSet( dataSet );

        // will call default setUpOperation
        databaseTester.onSetup();
    }

    @After
    public void tearDown() throws Exception
    {
        // will call default tearDownOperation
        databaseTester.onTearDown();
    }

    private ITable getExpectedTable(String action) throws Exception{

        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setMetaDataSetFromDtd(getClass().getClassLoader().getResourceAsStream(dtd));
        // Load expected data from an XML dataset
        IDataSet expectedDataSet = builder.build(getClass().getClassLoader().getResourceAsStream(dataPath + table + "/after" + action + table +".xml"));

        return expectedDataSet.getTable(table);
    }


    @Test
    public void TestInsert() throws Exception
    {
        DataTransferObject dataTransferObject = createSampleDTO();
        // Execute the tested code that modify the database here
        dataAccessObject.insert(dataTransferObject);


        // Fetch database data after executing your code
        IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable(table);

        String action = "Insert";
        ITable expectedTable = getExpectedTable(action);

        // Assert actual database table match expected table
        Assertion.assertEquals(expectedTable, actualTable);
    }

    abstract DataTransferObject createSampleDTO();


    @Test
    public void TestRemove() throws Exception
    {

        dataAccessObject.delete(1);


        // Fetch database data after executing your code
        IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable(table);


        String action = "Remove";

        ITable expectedTable = getExpectedTable(action);

        // Assert actual database table match expected table
        Assertion.assertEquals(expectedTable, actualTable);
    }


}