package DataAccessLayer.Inventory.DataAccessObjects;

import DataAccessLayer.Inventory.DBConnection;
import PresentationLayer.Inventory.DataTransferObjects.CategoryDTO;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;

import java.sql.*;
import java.util.ArrayList;

public class CategoryDAO extends DataAccessObject{


    public CategoryDAO(String databaseUrl) {
        super(databaseUrl);
        tableName = "Category";
    }

    @Override
    protected PreparedStatement createInsertPreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        return createInsertPreparedStatement((CategoryDTO) dataTransferObject);
    }

    protected PreparedStatement createInsertPreparedStatement(CategoryDTO categoryDTO) throws SQLException {
        String sql = "INSERT INTO " + tableName + " (ID, name, superID)  " +
                "VALUES (?, ?, ?);";
        PreparedStatement pstmt = this.c.prepareStatement(sql);
        pstmt.setInt(1,categoryDTO.getCid());
        pstmt.setString(2, categoryDTO.getName());
        if (categoryDTO.getSuperCategoryId() == null)
            pstmt.setNull(3,Types.INTEGER);
        else
            pstmt.setInt(3, categoryDTO.getSuperCategoryId());

        return pstmt;
    }

    public ArrayList<CategoryDTO> selectAll () {
        return (ArrayList<CategoryDTO>) selectAllGeneric();
    }

    public CategoryDAO() {
        super();
        tableName = "Category";
    }

    @Override
    CategoryDTO resultToDTO(ResultSet resultSet) throws SQLException {
        return new CategoryDTO(  resultSet.getInt("ID"),
                resultSet.getString("name"),
                resultSet.getInt("superID"));
    }

}
