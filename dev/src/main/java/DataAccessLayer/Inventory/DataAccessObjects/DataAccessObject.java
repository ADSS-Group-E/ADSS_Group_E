package DataAccessLayer.Inventory.DataAccessObjects;

import DataAccessLayer.Inventory.DBConnection;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public abstract class DataAccessObject {
    DBConnection db;
    Connection c = null;
    Statement stmt = null;

    DataAccessObject(DBConnection db) {this.db = db;}

    private void close() throws SQLException {
        stmt.close();
        c.commit();
        c.close();
        c  = null;
        stmt = null;
    }

    public void insert(DataTransferObject dataTransferObject) {
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = createInsertString(dataTransferObject);
            stmt.executeUpdate(sql);
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public ArrayList<? extends DataTransferObject> selectAllGeneric (){
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = createSelectAllString();
            ResultSet result = stmt.executeQuery(sql);

            ArrayList<DataTransferObject> dataTransferObjects = new ArrayList<>();
            while (result.next()){
                dataTransferObjects.add(resultToDTO(result));
            }
            close();
            return dataTransferObjects;
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            // TODO - don't catch exceptions - need to display and disrupt
            return null;
        }
    }

    public <T extends DataTransferObject> T get(int id){
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = createGetString(id);
            ResultSet result = stmt.executeQuery(sql);
            T dataTransferObject = null;
            if (result.next()){
                dataTransferObject = resultToDTO(result);
            }
            close();
            return dataTransferObject;
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            // TODO - don't catch exceptions - need to display and disrupt
            return null;
        }
    }

    protected abstract String createGetString(int id);

    abstract <T extends DataTransferObject> T resultToDTO(ResultSet resultSet) throws SQLException;

    abstract String createSelectAllString();

    abstract String createInsertString(DataTransferObject dataTransferObject);
}
