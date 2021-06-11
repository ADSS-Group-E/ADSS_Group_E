package DataAccessLayer.Supplier.DataAccessObjects;

import DataAccessLayer.Inventory.DataAccessObjects.DataAccessObject;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Supplier.DataTransferObjects.ContactDTO;
import PresentationLayer.Supplier.DataTransferObjects.DiscountStepDTO;
import PresentationLayer.Supplier.DataTransferObjects.QuantityWriterDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DiscountStepDAO extends DataAccessObject {
    public DiscountStepDAO(String databaseUrl) {
        super(databaseUrl);
        tableName = "DiscountStep";
    }

    public DiscountStepDAO() {
        super();
        tableName = "DiscountStep";
    }

    @Override
    protected PreparedStatement createInsertPreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        DiscountStepDTO discountStepDTO = (DiscountStepDTO) dataTransferObject;

        String sql = "INSERT INTO " + tableName + " ("+ primaryKey + ",stepPrice, percentage, quantityWriterID) " +
                "VALUES (?, ?, ?, ?);";
        PreparedStatement pstmt = this.c.prepareStatement(sql);
        int i=1;
        pstmt.setInt(i++,discountStepDTO.getId());
        pstmt.setInt(i++,discountStepDTO.getStepPrice());
        pstmt.setInt(i++, discountStepDTO.getPercentage());
        pstmt.setInt(i++, discountStepDTO.getQuantityWriterId());
        return pstmt;
    }

    @Override
    protected PreparedStatement createUpdatePreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        return null;
    }

    @Override
    protected DiscountStepDTO resultToDTO(ResultSet resultSet) throws SQLException {
        return new DiscountStepDTO(  resultSet.getInt(primaryKey),
                resultSet.getInt("stepPrice"),
                resultSet.getInt("percentage"),
                resultSet.getInt("quantityWriterID"));
    }

    public ArrayList<DiscountStepDTO> selectByQuantityWriter(int pid){
        try {
            c = this.connect();
            Statement stmt = c.createStatement();
            String sql = String.format("SELECT * FROM %s WHERE quantityWriterID = %d", tableName, pid);
            ResultSet result = stmt.executeQuery(sql);

            ArrayList<DiscountStepDTO> dataTransferObjects = new ArrayList<>();
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
