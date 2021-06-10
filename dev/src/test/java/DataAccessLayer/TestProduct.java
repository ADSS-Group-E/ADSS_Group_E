package DataAccessLayer;

import DataAccessLayer.Inventory.DataAccessObjects.ProductDAO;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;

public class TestProduct extends TestDAO {

    @Override
    public void setUp() throws Exception
    {
        table = "Product";
        dataAccessObject = new ProductDAO(url);
        super.setUp();
    }

    @Override
    DataTransferObject createSampleDTO() {
        return new ProductDTO(2, "Test Milk", "AB01","B13", "Test Company", 10.1, 5,2, -1);
    }


}