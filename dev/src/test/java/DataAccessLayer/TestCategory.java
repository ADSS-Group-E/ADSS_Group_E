package DataAccessLayer;

import DataAccessLayer.Inventory.DataAccessObjects.CategoryDAO;
import DataAccessLayer.Inventory.DataAccessObjects.ProductDAO;
import PresentationLayer.Inventory.DataTransferObjects.CategoryDTO;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;

public class TestCategory extends TestDAO {

    @Override
    public void setUp() throws Exception
    {
        table = "Category";

        dataAccessObject = new CategoryDAO(url);
        super.setUp();
    }

    @Override
    DataTransferObject createSampleDTO() {
        return new CategoryDTO(2,"<500 ML",1);
    }


}