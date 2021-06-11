package DataAccessLayer.Supplier.DataAccessObjects;

import DataAccessLayer.Inventory.DataAccessObjects.DataAccessObject;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Supplier.DataTransferObjects.OrderDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class OrderDAO extends DataAccessObject {
    public OrderDAO(String databaseUrl) {
        super(databaseUrl);
        tableName = "SOrder";
        primaryKey = "orderID";
    }

    public OrderDAO() {
        super();
        tableName = "SOrder";
        primaryKey = "orderID";

    }

    @Override
    protected PreparedStatement createInsertPreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        OrderDTO orderDTO = (OrderDTO) dataTransferObject;

        String sql = "INSERT INTO " + tableName + " ("+ primaryKey + ",date, periodicDelivery, needsDelivery) " +
                "VALUES (?, ?, ?, ?);";
        PreparedStatement pstmt = this.c.prepareStatement(sql);
        int i=1;
        pstmt.setInt(i++,orderDTO.getId());
        pstmt.setString(i++,orderDTO.getDate().toString());
        pstmt.setBoolean(i++, orderDTO.getPeriodicDelivery());
        pstmt.setBoolean(i++, orderDTO.getNeedsDelivery());
        return pstmt;
    }

    @Override
    protected PreparedStatement createUpdatePreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        return null;
    }

    @Override
    protected OrderDTO resultToDTO(ResultSet resultSet) throws SQLException {
        return new OrderDTO(  resultSet.getInt(primaryKey),
                LocalDateTime.parse(resultSet.getString("date")),
                resultSet.getBoolean("periodicDelivery"),
                resultSet.getBoolean("needsDelivery"));
    }
}
