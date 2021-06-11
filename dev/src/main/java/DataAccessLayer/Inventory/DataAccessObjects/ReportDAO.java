package DataAccessLayer.Inventory.DataAccessObjects;


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
    protected PreparedStatement createUpdatePreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        return null;
    }


    @Override
    protected  <T extends DataTransferObject> T resultToDTO(ResultSet resultSet) throws SQLException {
        return null;
    }

    String createInsertString(ReportDTO report) {
        return String.format("INSERT INTO ItemGroup (ID, creationDate, reportTag) " +
                "VALUES (%d, %s, %s);", report.getRid(), report.getCreated(), report.getTag());
    }
}
