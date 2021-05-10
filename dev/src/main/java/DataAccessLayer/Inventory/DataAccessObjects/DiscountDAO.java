package DataAccessLayer.Inventory.DataAccessObjects;

import DataAccessLayer.Inventory.DBConnection;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.DiscountDTO;
import PresentationLayer.Inventory.DataTransferObjects.ReportDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DiscountDAO extends DataAccessObject{

    public DiscountDAO(DBConnection dbConnection) {
        super(dbConnection);
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
    String createInsertString(DataTransferObject discount) {
        return createInsertString((DiscountDTO) discount);
    }

    String createInsertString(DiscountDTO discount) {
        return String.format("INSERT INTO Discount (ID, name, discPrecentage, startDate, endDate, type) " +
                "VALUES (%d, %s, %f, %s, %s, %s);", discount.getDid(), discount.getName(), discount.getDiscountPercent(), discount.getStartDate(), discount.getEndDate(), discount.getType());
    }
}
