package DataAccessLayer.Supplier.DataAccessObjects;

import DataAccessLayer.Inventory.DataAccessObjects.DataAccessObject;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Supplier.DataTransferObjects.SupplierItemGroupDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierProductDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SupplierItemGroupDAO extends DataAccessObject {
    public SupplierItemGroupDAO(String databaseUrl) {
        super(databaseUrl);
        tableName = "SupplierItemGroup";
    }

    public SupplierItemGroupDAO() {
        super();
        tableName = "SupplierItemGroup";
    }

    @Override
    protected PreparedStatement createInsertPreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        SupplierItemGroupDTO supplierItemGroupDTO = (SupplierItemGroupDTO) dataTransferObject;

        String sql = "INSERT INTO " + tableName + " ("+ primaryKey + ", supplierProductID, quantity, expiration) " +
                "VALUES (?, ?, ?, ?);";
        PreparedStatement pstmt = this.c.prepareStatement(sql);
        int i=1;
        pstmt.setInt(i++,supplierItemGroupDTO.getId());
        pstmt.setInt(i++,supplierItemGroupDTO.getSupplierProductId());
        pstmt.setInt(i++,supplierItemGroupDTO.getQuantity());
        pstmt.setString(i++, supplierItemGroupDTO.getExpiration().toString());

        return pstmt;
    }

    @Override
    protected PreparedStatement createUpdatePreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        return null;
    }

    @Override
    protected SupplierItemGroupDTO resultToDTO(ResultSet resultSet) throws SQLException {
        return new SupplierItemGroupDTO(  resultSet.getInt(primaryKey),
                resultSet.getInt("supplierProductID"),
                resultSet.getInt("quantity"),
                LocalDateTime.parse(resultSet.getString("expiration")));
    }

    public ArrayList<SupplierItemGroupDTO> selectByProduct(int pid){
        try {
            c = this.connect();
            Statement stmt = c.createStatement();
            String sql = String.format("SELECT * FROM %s WHERE supplierProductID = %d", tableName, pid);
            ResultSet result = stmt.executeQuery(sql);

            ArrayList<SupplierItemGroupDTO> dataTransferObjects = new ArrayList<>();
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
