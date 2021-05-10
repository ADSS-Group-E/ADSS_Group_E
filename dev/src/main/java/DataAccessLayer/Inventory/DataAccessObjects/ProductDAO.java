package DataAccessLayer.Inventory.DataAccessObjects;

import DataAccessLayer.Inventory.DBConnection;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends DataAccessObject {


    public ProductDAO(DBConnection dbConnection) {
        super(dbConnection);
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
