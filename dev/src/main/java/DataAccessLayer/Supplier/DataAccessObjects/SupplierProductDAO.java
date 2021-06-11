package DataAccessLayer.Supplier.DataAccessObjects;

import DataAccessLayer.Inventory.DataAccessObjects.DataAccessObject;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Supplier.DataTransferObjects.OrderDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierProductDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SupplierProductDAO extends DataAccessObject {
    public SupplierProductDAO(String databaseUrl) {
        super(databaseUrl);
        tableName = "SupplierProduct";
    }

    public SupplierProductDAO() {
        super();
        tableName = "SupplierProduct";
    }

    @Override
    protected PreparedStatement createInsertPreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        SupplierProductDTO supplierProductDTO = (SupplierProductDTO) dataTransferObject;

        String sql = "INSERT INTO " + tableName + " ("+ primaryKey + ", PID, companyNumber, name, quantity, price, supplierCN) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement pstmt = this.c.prepareStatement(sql);
        int i=1;
        pstmt.setInt(i++,supplierProductDTO.getId());
        pstmt.setInt(i++,supplierProductDTO.getPid());
        pstmt.setInt(i++,supplierProductDTO.getCompanyNumber());
        pstmt.setString(i++, supplierProductDTO.getName());
        pstmt.setInt(i++, supplierProductDTO.getQuantity());
        pstmt.setDouble(i++, supplierProductDTO.getPrice());
        pstmt.setString(i++, supplierProductDTO.getSupplierCN());
        return pstmt;
    }

    @Override
    protected PreparedStatement createUpdatePreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        return null;
    }

    @Override
    protected SupplierProductDTO resultToDTO(ResultSet resultSet) throws SQLException {
        return new SupplierProductDTO(  resultSet.getInt(primaryKey),
                resultSet.getInt("PID"),
                resultSet.getInt("companyNumber"),
                resultSet.getString("name"),
                resultSet.getInt("quantity"),
                resultSet.getDouble("price"),
                resultSet.getString("bankAccount"));
    }

    public ArrayList<SupplierProductDTO> selectBySupplier(int pid){
        try {
            c = this.connect();
            Statement stmt = c.createStatement();
            String sql = String.format("SELECT * FROM %s WHERE companyNumber = %d", tableName, pid);
            ResultSet result = stmt.executeQuery(sql);

            ArrayList<SupplierProductDTO> dataTransferObjects = new ArrayList<>();
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
