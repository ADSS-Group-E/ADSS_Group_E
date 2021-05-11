package DataAccessLayer;

import DataAccessLayer.Inventory.DataAccessObjects.ProductDAO;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
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
import java.io.FileInputStream;
import java.io.InputStream;

public class TestProduct extends TestDAO {

    @Override
    public void setUp() throws Exception
    {
        table = "Product";
        path = "dev/src/test/resources/data/"+table+"/";
        dataAccessObject = new ProductDAO(url);
        super.setUp();
    }

    @Override
    DataTransferObject createSampleDTO() {
        return new ProductDTO(2, "Test Milk", "AB01","B13", "Test Company",10.5, 10.1, 5,2, -1, -1);
    }


}