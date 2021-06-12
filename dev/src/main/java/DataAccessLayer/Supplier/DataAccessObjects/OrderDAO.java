package DataAccessLayer.Supplier.DataAccessObjects;

import DataAccessLayer.Inventory.DataAccessObjects.DataAccessObject;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Supplier.DataTransferObjects.OrderDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

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

        String sql = "INSERT INTO " + tableName + " ("+ primaryKey + ", companyNumber, date, periodicDelivery, needsDelivery) " +
                "VALUES (?, ?, ?, ?, ?);";
        PreparedStatement pstmt = this.c.prepareStatement(sql);
        int i=1;
        pstmt.setInt(i++,orderDTO.getId());
        pstmt.setInt(i++,orderDTO.getCompanyNumber());
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
                resultSet.getInt("companyNumber"),
                LocalDateTime.parse(resultSet.getString("date")),
                resultSet.getBoolean("periodicDelivery"),
                resultSet.getBoolean("needsDelivery"));
    }

    // TODO Can make abstract and share
    public ArrayList<OrderDTO> selectBySupplier(int pid){
        try {
            c = this.connect();
            Statement stmt = c.createStatement();
            String sql = String.format("SELECT * FROM %s WHERE companyNumber = %d", tableName, pid);
            ResultSet result = stmt.executeQuery(sql);

            ArrayList<OrderDTO> dataTransferObjects = new ArrayList<>();
            while (result.next()){
                dataTransferObjects.add(resultToDTO(result));
            }
            close();
            return dataTransferObjects;
        }
        catch (SQLException e) {
            handleError(e);
            return null;
        }
    }
}
