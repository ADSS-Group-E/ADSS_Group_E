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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public abstract class TestDAO {

    protected IDatabaseTester databaseTester;
    protected DataAccessObject dataAccessObject;
    protected String path;
    protected String table;
    protected String dataPath = "dev/src/test/resources/data/";
    protected String url = "jdbc:sqlite:" +dataPath+ "testDatabase.db";
    protected String dtd = dataPath + "schema.dtd";

    @Before
    public void setUp() throws Exception
    {

        databaseTester = new JdbcDatabaseTester("org.sqlite.JDBC", url);

        // initialize your dataset here

        File dtdFile = new File(dtd);
        InputStream dtdStream = new FileInputStream(dtdFile);

        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setMetaDataSetFromDtd(dtdStream);

        // ...
        File initialFile = new File(path + "before" + table + ".xml");
        InputStream initialStream = new FileInputStream(initialFile);
        IDataSet dataSet = builder.build(initialStream);

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
        File dtdFile = new File(dtd);
        InputStream dtdStream = new FileInputStream(dtdFile);

        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setMetaDataSetFromDtd(dtdStream);
        // Load expected data from an XML dataset
        IDataSet expectedDataSet = builder.build(new File(path + "after" + action + table +".xml"));
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