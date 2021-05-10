package DataAccessLayer.Inventory.DataAccessObjects;


import DataAccessLayer.Inventory.DBConnection;
import PresentationLayer.Inventory.DataTransferObjects.CategoryDTO;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.ReportDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportDAO extends DataAccessObject{

    public ReportDAO(DBConnection dbConnection) {
        super(dbConnection);
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
