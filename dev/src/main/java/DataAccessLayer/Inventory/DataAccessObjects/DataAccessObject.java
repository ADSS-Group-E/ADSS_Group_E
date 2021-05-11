package DataAccessLayer.Inventory.DataAccessObjects;

import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

import java.sql.*;
import java.util.ArrayList;

public abstract class DataAccessObject {
    Connection c = null;
    String databaseUrl;
    protected String tableName;

    public DataAccessObject() {
        this.databaseUrl = "jdbc:sqlite::resource:module.db";
    }

    public DataAccessObject(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    private void close() throws SQLException {
        c.commit();
        c.close();
        c  = null;
    }

    public void insert(DataTransferObject dataTransferObject) {
        try {
            c = this.connect();
            PreparedStatement stmt = createInsertPreparedStatement(dataTransferObject);
            stmt.executeUpdate();
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            try {
                c.close();
            }
            catch (SQLException exception) {
                System.err.println( exception.getClass().getName() + ": " + exception.getMessage() );
            }
        }
    }

    protected abstract PreparedStatement createInsertPreparedStatement(DataTransferObject dataTransferObject) throws SQLException;

    public ArrayList<? extends DataTransferObject> selectAllGeneric (){
        try {
            c = this.connect();
            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM " + tableName;
            ResultSet result = stmt.executeQuery(sql);

            ArrayList<DataTransferObject> dataTransferObjects = new ArrayList<>();
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

    public <T extends DataTransferObject> T get(int id){
        try {
            c = this.connect();
            Statement stmt = c.createStatement();
            String sql = String.format("SELECT * FROM %s WHERE ID = %d", tableName, id);;
            ResultSet result = stmt.executeQuery(sql);
            T dataTransferObject = null;
            if (result.next()){
                dataTransferObject = resultToDTO(result);
            }
            close();
            return dataTransferObject;
        }
        catch (SQLException e) {
            handleError(e);
            return null;
        }
    }

    public void delete(int id){
        try {
            c = this.connect();
            Statement stmt = c.createStatement();
            String sql = "DELETE FROM " + tableName + " WHERE ID = " + id;
            stmt.executeUpdate(sql);
            close();
        }
        catch (SQLException e) {
            handleError(e);
        }
    }

    private void handleError(SQLException e) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        try {
            c.close();
        }
        catch (SQLException exception) {
            System.err.println( exception.getClass().getName() + ": " + exception.getMessage() );
        }
    }

    abstract <T extends DataTransferObject> T resultToDTO(ResultSet resultSet) throws SQLException;


    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite::resource:module.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseUrl);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
