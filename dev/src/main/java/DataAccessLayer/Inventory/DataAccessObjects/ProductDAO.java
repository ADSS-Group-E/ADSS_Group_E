package DataAccessLayer.Inventory.DataAccessObjects;

import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;

import java.sql.*;

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
        ProductDTO productDTO = (ProductDTO) dataTransferObject;

        String sql = "INSERT INTO " + tableName + " (ID, name, storeLocation, storageLocation, " +
                "manufacturer, sellPrice, minAmount, categoryID, sellDiscountID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement pstmt = this.c.prepareStatement(sql);
        pstmt.setInt(1,productDTO.getPid());
        pstmt.setString(2, productDTO.getName());
        pstmt.setString(3, productDTO.getStorageLocation());
        pstmt.setString(4, productDTO.getStoreLocation());
        pstmt.setString(5, productDTO.getManufacturer());
        pstmt.setDouble(6, productDTO.getSellingPrice());
        pstmt.setInt(7,productDTO.getMinAmount());
        pstmt.setInt(8,productDTO.getCategoryId());
        if (productDTO.getDiscountID() == null)
            pstmt.setNull(9,Types.INTEGER);
        else
            pstmt.setInt(9, productDTO.getDiscountID());
        return pstmt;
    }

    @Override
    protected PreparedStatement createUpdatePreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        ProductDTO productDTO = (ProductDTO) dataTransferObject;

        String sql = "UPDATE " + tableName + " set name=?, storeLocation=?, storageLocation=?, " +
                "manufacturer=?, sellPrice=?, minAmount=?, categoryID=?, sellDiscountID=? " +
                "WHERE ID=?;";
        PreparedStatement pstmt = this.c.prepareStatement(sql);
        int i = 1;
        pstmt.setString(i++, productDTO.getName());
        pstmt.setString(i++, productDTO.getStoreLocation());
        pstmt.setString(i++, productDTO.getStorageLocation());
        pstmt.setString(i++, productDTO.getManufacturer());
        pstmt.setDouble(i++, productDTO.getSellingPrice());
        pstmt.setInt(i++,productDTO.getMinAmount());
        pstmt.setInt(i++,productDTO.getCategoryId());
        if (productDTO.getDiscountID() == null)
            pstmt.setNull(i++,Types.INTEGER);
        else
            pstmt.setInt(i++, productDTO.getDiscountID());

        pstmt.setInt(i++,productDTO.getPid());

        return pstmt;
    }


    @Override
    ProductDTO resultToDTO(ResultSet resultSet) throws SQLException {
        return new ProductDTO(  resultSet.getInt("ID"),
                                resultSet.getString("name"),
                                resultSet.getString("storeLocation"),
                                resultSet.getString("storageLocation"),
                                resultSet.getString("manufacturer"),
                                resultSet.getDouble("sellPrice"),
                                resultSet.getInt("minAmount"),
                                resultSet.getInt("categoryID"),
                                resultSet.getInt("sellDiscountID"));
    }
}
