package DataAccessLayer.Inventory.DataAccessObjects;


import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.DiscountDTO;
import PresentationLayer.Inventory.DataTransferObjects.ItemGroupDTO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ItemGroupDAO extends DataAccessObject{

    public ItemGroupDAO() {
        super();
        tableName = "ItemGroup";
    }

    public ItemGroupDAO(String databaseUrl) {
        super(databaseUrl);
        tableName = "ItemGroup";
    }

    @Override
    protected PreparedStatement createInsertPreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        ItemGroupDTO itemGroupDTO = (ItemGroupDTO) dataTransferObject;

        String sql = "INSERT INTO " + tableName + " (PID, quantity, location, priceBoughtAt, expiration)  " +
                "VALUES (?, ?, ?, ?, ?);";
        PreparedStatement pstmt = this.c.prepareStatement(sql);
        pstmt.setInt(1,itemGroupDTO.getPid());
        pstmt.setInt(2, itemGroupDTO.getQuantity());
        pstmt.setInt(3,itemGroupDTO.getLocation().ordinal());
        pstmt.setDouble(4, itemGroupDTO.getPriceBoughtAt());
        pstmt.setString(5, itemGroupDTO.getExpiration().toString());

        return pstmt;
    }

    @Override
    protected PreparedStatement createUpdatePreparedStatement(DataTransferObject dataTransferObject) throws SQLException {
        return null;
    }

    @Override
    protected ItemGroupDTO resultToDTO(ResultSet resultSet) throws SQLException {
        ItemGroupDTO itemGroupDTO = new ItemGroupDTO( resultSet.getInt("ID"),
                resultSet.getInt("PID"),
                ItemGroupDTO.Location.values()[resultSet.getInt("location")],
                resultSet.getInt("quantity"),
                resultSet.getDouble("priceBoughtAt"),
                LocalDateTime.parse(resultSet.getString("expiration")));
        return itemGroupDTO;
    }

    public ArrayList<ItemGroupDTO> selectByProduct(int pid){
        try {
            c = this.connect();
            Statement stmt = c.createStatement();
            String sql = String.format("SELECT * FROM %s WHERE PID = %d", tableName, pid);
            ResultSet result = stmt.executeQuery(sql);

            ArrayList<ItemGroupDTO> dataTransferObjects = new ArrayList<>();
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
