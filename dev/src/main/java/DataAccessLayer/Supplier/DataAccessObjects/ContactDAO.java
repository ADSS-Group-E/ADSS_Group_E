package DataAccessLayer.Supplier.DataAccessObjects;

import DataAccessLayer.Inventory.DataAccessObjects.DataAccessObject;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.ItemGroupDTO;
import PresentationLayer.Supplier.DataTransferObjects.ContactDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierProductDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ContactDAO extends DataAccessObject {
    public ContactDAO(String databaseUrl) {
        super(databaseUrl);
        tableName = "Contact";
    }

    public ContactDAO() {
        super();
        tableName = "Contact";
    }

    @Override
    protected PreparedStatement createInsertPreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        ContactDTO contactDTO = (ContactDTO) dataTransferObject;

        String sql = "INSERT INTO " + tableName + " ("+ primaryKey + ",companyNumber, name, email) " +
                "VALUES (?, ?, ?, ?);";
        PreparedStatement pstmt = this.c.prepareStatement(sql);
        int i=1;
        pstmt.setInt(i++,contactDTO.getId());
        pstmt.setInt(i++,contactDTO.getCompanyNumber());
        pstmt.setString(i++, contactDTO.getName());
        pstmt.setString(i++, contactDTO.getEmail());
        return pstmt;
    }

    @Override
    protected PreparedStatement createUpdatePreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        return null;
    }

    @Override
    protected ContactDTO resultToDTO(ResultSet resultSet) throws SQLException {
        return new ContactDTO(  resultSet.getInt(primaryKey),
                resultSet.getInt("companyNumber"),
                resultSet.getString("name"),
                resultSet.getString("email"));
    }

    public ArrayList<ContactDTO> selectBySupplier(int pid){
        try {
            c = this.connect();
            Statement stmt = c.createStatement();
            String sql = String.format("SELECT * FROM %s WHERE companyNumber = %d", tableName, pid);
            ResultSet result = stmt.executeQuery(sql);

            ArrayList<ContactDTO> dataTransferObjects = new ArrayList<>();
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
