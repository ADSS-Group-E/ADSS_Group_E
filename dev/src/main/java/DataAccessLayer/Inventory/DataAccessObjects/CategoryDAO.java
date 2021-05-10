package DataAccessLayer.Inventory.DataAccessObjects;

import DataAccessLayer.Inventory.DBConnection;
import PresentationLayer.Inventory.DataTransferObjects.CategoryDTO;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CategoryDAO extends DataAccessObject{

    public CategoryDAO(DBConnection dbConnection) {
        super(dbConnection);
    }


    @Override
    String createInsertString(DataTransferObject category) {
        return createInsertString((CategoryDTO) category);
    }

    String createInsertString(CategoryDTO category) {
        return String.format("INSERT INTO Category (ID, name, superID) " +
                "VALUES (%d, %s, %d);", category.getCid(), category.getName(), category.getSuperCategoryId());
    }

}
