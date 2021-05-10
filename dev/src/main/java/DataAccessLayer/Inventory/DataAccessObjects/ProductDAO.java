package DataAccessLayer.Inventory.DataAccessObjects;

import DataAccessLayer.Inventory.DBConnection;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends DataAccessObject {
    public ProductDAO(String databaseUrl) {
        super(databaseUrl);
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

    public ProductDAO() {
        super();
    }

    @Override
    protected String createGetString(int id) {
        return  String.format("SELECT * FROM Product WHERE ID = %d", id);
    }

    @Override
    String createSelectAllString() {
        return "SELECT * FROM Product";
    }

    @Override
    String createInsertString(DataTransferObject productDTO) {
        return createInsertString((ProductDTO) productDTO);
    }

    String createInsertString(ProductDTO productDTO) {
        return String.format("INSERT INTO Product (ID, name, storeLocation, storageLocation, " +
                        "manufacturer, buyPrice, sellPrice, minAmount, categoryDTO, buyDiscountID, sellDiscountID) " +
                        "VALUES (%d, %s, %s, %s, %s, %f, %f, %d, %d, %f, %f);", productDTO.getPid(), productDTO.getName(), productDTO.getStorageLocation(),
                productDTO.getStoreLocation(), productDTO.getManufacturer(), productDTO.getBuyingPrice(), productDTO.getSellingPrice(), productDTO.getMinAmount(),
                productDTO.getCategoryId(), productDTO.getBuyingPriceAfterDiscount(), productDTO.getSellingPriceAfterDiscount());
    }



    public ArrayList<ProductDTO> selectAll () {
        ArrayList<ProductDTO> productDTOS = (ArrayList<ProductDTO>) selectAllGeneric();
        return productDTOS;
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
