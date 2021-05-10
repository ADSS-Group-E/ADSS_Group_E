package DataAccessLayer;

import DataAccessLayer.Inventory.DataAccessObjects.ProductDAO;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class SampleTest {

    private IDatabaseTester databaseTester;
    private ProductDAO productDAO;

    @Before
    public void setUp() throws Exception
    {

        String url = "jdbc:sqlite:testData.db";
        databaseTester = new JdbcDatabaseTester("org.sqlite.JDBC",
                url);

        // initialize your dataset here

        // ...
        ReplacementDataSet dataSet = new ReplacementDataSet(
                new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                        .getResourceAsStream("testData.xml")));
        dataSet.addReplacementObject("[NULL]", null);

        databaseTester.setDataSet( dataSet );

        // will call default setUpOperation
        databaseTester.onSetup();

        productDAO = new ProductDAO(url);
    }

    @After
    public void tearDown() throws Exception
    {
        // will call default tearDownOperation
        databaseTester.onTearDown();
    }

    @Test
    public void TestInsert() throws Exception
    {
        // Execute the tested code that modify the database here
        ProductDTO productDTO = new ProductDTO(2, "Test Milk", "AB01","B13", "Test Company",10.5, 10.1, 5,2, -1, -1);
        productDAO.insert(productDTO);


        // Fetch database data after executing your code
        IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("Product");

        // Load expected data from an XML dataset
        ReplacementDataSet expectedDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(new File("expectedDataSet.xml")));
        expectedDataSet.addReplacementObject("[NULL]", null);
        ITable expectedTable = expectedDataSet.getTable("Product");

        // Assert actual database table match expected table
        Assertion.assertEquals(expectedTable, actualTable);
    }


}