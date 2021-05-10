package DataAccessLayer.Inventory.DataAccessObjects;

import DataAccessLayer.Inventory.DBConnection;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductDAO extends DataAccessObject {


    public ProductDAO(DBConnection dbConnection) {
        super(dbConnection);
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
}
