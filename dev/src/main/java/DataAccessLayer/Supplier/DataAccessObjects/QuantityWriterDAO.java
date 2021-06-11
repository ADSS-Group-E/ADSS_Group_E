package DataAccessLayer.Supplier.DataAccessObjects;

import DataAccessLayer.Inventory.DataAccessObjects.DataAccessObject;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Supplier.DataTransferObjects.ContactDTO;
import PresentationLayer.Supplier.DataTransferObjects.QuantityWriterDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuantityWriterDAO extends DataAccessObject {
    public QuantityWriterDAO(String databaseUrl) {
        super(databaseUrl);
        tableName = "QuantityWriter";
    }

    public QuantityWriterDAO() {
        super();
        tableName = "QuantityWriter";
    }

    @Override
    protected PreparedStatement createInsertPreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        QuantityWriterDTO quantityWriterDTO = (QuantityWriterDTO) dataTransferObject;

        String sql = "INSERT INTO " + tableName + " ("+ primaryKey + ",companyNumber, regularCustomerDiscount, minPriceDiscount) " +
                "VALUES (?, ?, ?, ?);";
        PreparedStatement pstmt = this.c.prepareStatement(sql);
        int i=1;
        pstmt.setInt(i++,quantityWriterDTO.getId());
        pstmt.setInt(i++,quantityWriterDTO.getCompanyNumber());
        pstmt.setInt(i++, quantityWriterDTO.getRegularCostumerDiscount());
        pstmt.setInt(i++, quantityWriterDTO.getRegularCostumerDiscount());
        return pstmt;
    }

    @Override
    protected PreparedStatement createUpdatePreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        return null;
    }

    @Override
    protected QuantityWriterDTO resultToDTO(ResultSet resultSet) throws SQLException {
        return new QuantityWriterDTO(  resultSet.getInt(primaryKey),
                resultSet.getInt("companyNumber"),
                resultSet.getInt("regularCustomerDiscount"),
                resultSet.getInt("minPriceDiscount"));
    }
}
