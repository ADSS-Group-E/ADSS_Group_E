package DataAccessLayer.Supplier.DataAccessObjects;

import DataAccessLayer.Inventory.DataAccessObjects.DataAccessObject;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Supplier.DataTransferObjects.OrderItemGroupDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierItemGroupDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class OrderItemGroupDAO extends DataAccessObject {
    public OrderItemGroupDAO(String databaseUrl) {
        super(databaseUrl);
        tableName = "OrderItemGroup";
    }

    public OrderItemGroupDAO() {
        super();
        tableName = "OrderItemGroup";
    }

    @Override
    protected PreparedStatement createInsertPreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        OrderItemGroupDTO orderItemGroupDTO = (OrderItemGroupDTO) dataTransferObject;

        String sql = "INSERT INTO " + tableName + " ("+ primaryKey + ", PID, orderID, quantity, priceBoughtAt, expiration) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement pstmt = this.c.prepareStatement(sql);
        int i=1;
        pstmt.setInt(i++,orderItemGroupDTO.getId());
        pstmt.setInt(i++,orderItemGroupDTO.getPid());
        pstmt.setInt(i++,orderItemGroupDTO.getOrderId());
        pstmt.setInt(i++,orderItemGroupDTO.getQuantity());
        pstmt.setDouble(i++,orderItemGroupDTO.getPriceBoughtAt());
        pstmt.setString(i++, orderItemGroupDTO.getExpiration().toString());

        return pstmt;
    }

    @Override
    protected PreparedStatement createUpdatePreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        return null;
    }

    @Override
    protected OrderItemGroupDTO resultToDTO(ResultSet resultSet) throws SQLException {
        return new OrderItemGroupDTO(  resultSet.getInt(primaryKey),
                resultSet.getInt("PID"),
                resultSet.getInt("orderID"),
                resultSet.getInt("quantity"),
                resultSet.getDouble("priceBoughtAt"),
                LocalDateTime.parse(resultSet.getString("expiration")));
    }
}
