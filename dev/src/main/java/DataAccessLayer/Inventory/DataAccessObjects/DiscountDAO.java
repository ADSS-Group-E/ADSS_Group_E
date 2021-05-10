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

    public ArrayList<DiscountDTO> selectAll () {
        return (ArrayList<DiscountDTO>) selectAllGeneric();
    }

    @Override
    protected PreparedStatement createInsertPreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        return createInsertPreparedStatement((DiscountDTO)dataTransferObject);
    }

    protected PreparedStatement createInsertPreparedStatement(DiscountDTO discountDTO) throws SQLException {
        String sql = "INSERT INTO " + tableName + " (ID, name,discPercentage, startDate, endDate, type)  " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement pstmt = this.c.prepareStatement(sql);
        pstmt.setInt(1,discountDTO.getDid());
        pstmt.setString(2, discountDTO.getName());
        pstmt.setDouble(3,discountDTO.getDiscountPercent());
        pstmt.setString(4, discountDTO.getStartDate().toString());
        pstmt.setString(5, discountDTO.getEndDate().toString());
        pstmt.setString(6, discountDTO.getType());

        return pstmt;
    }

    @Override
    DiscountDTO resultToDTO(ResultSet resultSet) throws SQLException {
        return new DiscountDTO( resultSet.getInt("ID"),
                resultSet.getString("name"),
                resultSet.getDouble("discPercentage"),
                LocalDateTime.parse(resultSet.getString("startDate")),
                LocalDateTime.parse(resultSet.getString("endDate")),
                null,
                resultSet.getString("type"));
    }
}
