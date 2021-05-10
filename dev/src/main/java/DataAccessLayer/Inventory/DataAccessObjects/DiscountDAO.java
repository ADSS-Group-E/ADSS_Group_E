package DataAccessLayer.Inventory.DataAccessObjects;

import DataAccessLayer.Inventory.DBConnection;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.DiscountDTO;
import PresentationLayer.Inventory.DataTransferObjects.ReportDTO;

import java.sql.*;

public class DiscountDAO extends DataAccessObject{

    public DiscountDAO() {
        super();
    }

    public DiscountDAO(String databaseUrl) {
        super(databaseUrl);
    }

    @Override
    protected PreparedStatement createInsertPreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        return null;
    }

    @Override
    <T extends DataTransferObject> T resultToDTO(ResultSet resultSet) throws SQLException {
        return null;
    }

    String createInsertString(DiscountDTO discount) {
        return String.format("INSERT INTO Discount (ID, name, discPrecentage, startDate, endDate, type) " +
                "VALUES (%d, %s, %f, %s, %s, %s);", discount.getDid(), discount.getName(), discount.getDiscountPercent(), discount.getStartDate(), discount.getEndDate(), discount.getType());
    }
}
