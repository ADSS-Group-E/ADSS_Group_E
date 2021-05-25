package DataAccessLayer.Inventory.DataAccessObjects;

import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;

import java.sql.*;
import java.util.ArrayList;

public class ProductDAO extends DataAccessObject {

    public ProductDAO(String databaseUrl) {
        super(databaseUrl);
        tableName = "Product";
    }

    public ProductDAO() {
        super();
        tableName = "Product";
    }

    @Override
    protected PreparedStatement createInsertPreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        return createInsertPreparedStatement((ProductDTO) dataTransferObject);
    }


    protected PreparedStatement createInsertPreparedStatement(ProductDTO productDTO) throws SQLException {
        String sql = "INSERT INTO Product (ID, name, storeLocation, storageLocation, " +
                "manufacturer, buyPrice, sellPrice, minAmount, categoryID, buyDiscountID, sellDiscountID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement pstmt = this.c.prepareStatement(sql);
        pstmt.setInt(1,productDTO.getPid());
        pstmt.setString(2, productDTO.getName());
        pstmt.setString(3, productDTO.getStorageLocation());
        pstmt.setString(4, productDTO.getStoreLocation());
        pstmt.setString(5, productDTO.getManufacturer());
        pstmt.setDouble(6, productDTO.getBuyingPrice());
        pstmt.setDouble(7, productDTO.getSellingPrice());
        pstmt.setInt(8,productDTO.getMinAmount());
        pstmt.setInt(9,productDTO.getCategoryId());
        if (productDTO.getBuyingDiscountID() == null)
            pstmt.setNull(10,Types.INTEGER);
        else
            pstmt.setInt(10, productDTO.getBuyingDiscountID());
        if (productDTO.getSellingDiscountID() == null)
            pstmt.setNull(11,Types.INTEGER);
        else
            pstmt.setInt(11, productDTO.getSellingDiscountID());
        return pstmt;
    }


    public ArrayList<ProductDTO> selectAll () {
        return (ArrayList<ProductDTO>) selectAllGeneric();
    }

    @Override
    ProductDTO resultToDTO(ResultSet resultSet) throws SQLException {
        return new ProductDTO(  resultSet.getInt("ID"),
                                resultSet.getString("name"),
                                resultSet.getString("storageLocation"),
                                resultSet.getString("storeLocation"),
                                resultSet.getString("manufacturer"),
                                resultSet.getDouble("buyPrice"),
                                resultSet.getDouble("sellPrice"),
                                resultSet.getInt("minAmount"),
                                resultSet.getInt("categoryID"),
                                resultSet.getInt("buyDiscountID"),
                                resultSet.getInt("sellDiscountID"));
    }
}
