package DataAccessLayer.Transports;

import BussinessLayer.WorkersPackage.Worker;
import DataAccessLayer.Repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Location {

    public static void insertLocation(DTO.Location l) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String query = "INSERT OR IGNORE INTO Locations VALUES (?, ?, ? ,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, l.id);
            stmt.setString(2, l.name);
            stmt.setString(3, l.address);
            stmt.setString(4,l.telNumber);
            stmt.setString(5, l.contactName);
            stmt.setString(6, l.shippingArea);
            stmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            throw e;        }
    }

    public static BussinessLayer.DeliveryPackage.Location checkLocation(int id) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From Locations WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);

            ResultSet results = pst.executeQuery();
            if(results.next()==false)
                return null;
            return new BussinessLayer.DeliveryPackage.Location(results.getInt(1),results.getString(2),results.getString(3),results.getString(4),results.getString(5),results.getString(6));
        } catch (Exception e) {
            throw e;        }
    }

    public static void deleteLocation(int id) throws Exception {
        try (Connection conn = Repo.openConnection()) {
            String sql = "DELETE FROM Locations WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);

            pst.executeUpdate();

        } catch (Exception e) {
            throw new Exception("can't delete a Location that is used with a delivery/Orders.");
        }

    }

      public static void updateTel(int id, String tel) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE Locations SET TEL_NUMBER =? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,tel);
            pst.setInt(2,id);

            pst.executeUpdate();

        } catch (Exception e) {
            throw e;        }
    }

    public static void updateName(int id, String name) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE Locations SET CONTACT_NAME =? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,name);
            pst.setInt(2,id);

            pst.executeUpdate();

        } catch (Exception e) {
            throw e;        }
    }

    public static void printLocation() throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From Locations ";
            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet results = pst.executeQuery();
            while (results.next()) {
                int  id = results.getInt(1);
                String name = results.getString(2);
                String address=results.getString(3);
                String telNumber=results.getString(4);
                String conact=results.getString(5);
                String ship=results.getString(6);
                System.out.println("Location "+id+"\n"+"name: "+name+"\naddress: "+address+"\ntelephone number: "+telNumber+"\ncontact name: "+conact+"\nshipping area: "+ship+"\n");
            }


        } catch (Exception e) {
            throw e;
        }
    }
}
