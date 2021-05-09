package DataAccessLayer.Inventory.DataAccessObjects;


import DataAccessLayer.Inventory.DBConnection;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.ItemDTO;
import PresentationLayer.Inventory.DataTransferObjects.ReportDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ItemDAO extends DataAccessObject{

    public ItemDAO(DBConnection dbConnection) {
            super(dbConnection);
    }

    @Override
    String createInsertString(DataTransferObject item) {
        return createInsertString((ItemDTO) item);
    }

    String createInsertString(ItemDTO item) {
        return String.format("INSERT INTO Item (ID, ExpirationDate) " +
                "VALUES (%d, %s);", item.getId(), item.getExpiration());
    }
}
