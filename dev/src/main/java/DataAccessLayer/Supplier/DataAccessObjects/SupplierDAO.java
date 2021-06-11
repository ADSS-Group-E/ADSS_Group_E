package DataAccessLayer.Supplier.DataAccessObjects;

import DataAccessLayer.Inventory.DataAccessObjects.DataAccessObject;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class SupplierDAO extends DataAccessObject {

    public SupplierDAO(String databaseUrl) {
        super(databaseUrl);
        tableName = "Supplier";
        primaryKey = "companyNumber";
    }

    public SupplierDAO() {
        super();
        tableName = "Supplier";
        primaryKey = "companyNumber";
    }

    @Override
    protected PreparedStatement createInsertPreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        SupplierDTO supplierDTO = (SupplierDTO) dataTransferObject;

        String sql = "INSERT INTO " + tableName + " ("+ primaryKey + ", name, paymentMethod, bankAccount) " +
                "VALUES (?, ?, ?, ?);";
        PreparedStatement pstmt = this.c.prepareStatement(sql);
        pstmt.setInt(1,supplierDTO.getCompanyNumber());
        pstmt.setString(2, supplierDTO.getName());
        pstmt.setString(3, supplierDTO.getPaymentMethod());
        pstmt.setString(4, supplierDTO.getBankAccount());
        return pstmt;
    }

    @Override
    protected PreparedStatement createUpdatePreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        return null;
    }

    @Override
    protected SupplierDTO resultToDTO(ResultSet resultSet) throws SQLException {
        return new SupplierDTO(  resultSet.getInt(primaryKey),
                resultSet.getString("name"),
                resultSet.getString("paymentMethod"),
                resultSet.getString("bankAccount"));
    }
}
