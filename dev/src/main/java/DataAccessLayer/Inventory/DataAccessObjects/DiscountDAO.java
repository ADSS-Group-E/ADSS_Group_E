package DataAccessLayer.Inventory.DataAccessObjects;

import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.DiscountDTO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DiscountDAO extends DataAccessObject{

    public DiscountDAO() {
        super();
        tableName = "Discount";
    }

    public DiscountDAO(String databaseUrl) {
        super(databaseUrl);
        tableName = "Discount";
    }
    

    @Override
    protected PreparedStatement createInsertPreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        DiscountDTO discountDTO = (DiscountDTO) dataTransferObject;

        String sql = "INSERT INTO " + tableName + " (ID, name,discPercentage, startDate, endDate)  " +
                "VALUES (?, ?, ?, ?, ?);";
        PreparedStatement pstmt = this.c.prepareStatement(sql);
        int i=1;
        pstmt.setInt(i++,discountDTO.getId());
        pstmt.setString(i++, discountDTO.getName());
        pstmt.setDouble(i++,discountDTO.getDiscountPercent());
        pstmt.setString(i++, discountDTO.getStartDate().toString());
        pstmt.setString(i++, discountDTO.getEndDate().toString());

        return pstmt;
    }

    @Override
    protected PreparedStatement createUpdatePreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        DiscountDTO discountDTO = (DiscountDTO) dataTransferObject;

        String sql = "UPDATE " + tableName + " set name=?, discPercentage=?, startDate=?, endDate=? " +
                "WHERE ID=?;";
        PreparedStatement pstmt = this.c.prepareStatement(sql);
        int i=1;
        pstmt.setString(i++, discountDTO.getName());
        pstmt.setDouble(i++,discountDTO.getDiscountPercent());
        pstmt.setString(i++, discountDTO.getStartDate().toString());
        pstmt.setString(i++, discountDTO.getEndDate().toString());
        pstmt.setInt(i++,discountDTO.getId());

        return pstmt;
    }

    @Override
    DiscountDTO resultToDTO(ResultSet resultSet) throws SQLException {
        return new DiscountDTO( resultSet.getInt("ID"),
                resultSet.getString("name"),
                resultSet.getDouble("discPercentage"),
                LocalDateTime.parse(resultSet.getString("startDate")),
                LocalDateTime.parse(resultSet.getString("endDate")));
    }
}
