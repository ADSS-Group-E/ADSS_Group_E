package DataAccessLayer.Inventory.DataAccessObjects;


import DataAccessLayer.Inventory.DBConnection;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.ItemDTO;
import PresentationLayer.Inventory.DataTransferObjects.ReportDTO;

import java.sql.*;

public class ItemDAO extends DataAccessObject{

    public ItemDAO() {
        super();
    }

    public ItemDAO(String databaseUrl) {
        super(databaseUrl);
    }

    @Override
    protected PreparedStatement createInsertPreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        return null;
    }

    @Override
    protected String createGetString(int id) {
        return null;
    }

    @Override
    <T extends DataTransferObject> T resultToDTO(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    String createSelectAllString() {
        return null;
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
