package DataAccessLayer.Inventory.DataAccessObjects;


import DataAccessLayer.Inventory.DBConnection;
import PresentationLayer.Inventory.DataTransferObjects.CategoryDTO;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.ReportDTO;

import java.sql.*;

public class ReportDAO extends DataAccessObject{

    public ReportDAO() {
        super();
    }

    public ReportDAO(String databaseUrl) {
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
    String createInsertString(DataTransferObject report) {
        return createInsertString((ReportDTO) report);
    }

    String createInsertString(ReportDTO report) {
        return String.format("INSERT INTO Item (ID, creationDate, reportTag) " +
                "VALUES (%d, %s, %s);", report.getRid(), report.getCreated(), report.getTag());
    }
}
