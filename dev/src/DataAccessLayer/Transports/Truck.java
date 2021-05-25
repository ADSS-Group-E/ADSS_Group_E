package DataAccessLayer.Transports;

import BussinessLayer.WorkersPackage.Worker;
import DataAccessLayer.Repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Truck {

    public static void insertTruck(DTO.Truck t) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String query = "INSERT OR IGNORE INTO Trucks VALUES (?, ?, ? ,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, t.id);
            stmt.setString(2, t.model);
            stmt.setDouble(3, t.netoWeight);
            stmt.setDouble(4, t.totalWeight);
            stmt.setBoolean(5, t.isUsed);
            stmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            throw e;        }
    }

    public static BussinessLayer.DeliveryPackage.Truck checkTruck(String id) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From Trucks WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,id);

            ResultSet results = pst.executeQuery();
            if(results.next()==false)
                return null;
            return new BussinessLayer.DeliveryPackage.Truck(results.getString(1),results.getString(2),results.getDouble(3),results.getDouble(4),results.getBoolean(5));
        } catch (Exception e) {
            throw e;        }

            }

    public static void deleteTruck(String id) throws Exception {
        try (Connection conn = Repo.openConnection()) {
            String sql = "DELETE FROM Trucks WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,id);

            pst.executeUpdate();

        } catch (Exception e) {
            throw new Exception("can't delete a Truck that is used with a delivery.");
        }

    }

    public static void updateUsed(String id, boolean status) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE Trucks SET ISUSED =? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setBoolean(1,status);
            pst.setString(2,id);

            pst.executeUpdate();

        } catch (Exception e) {
            throw e;        }
    }

    public static void printTrucks() throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From Trucks ";
            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet results = pst.executeQuery();
            while (results.next()) {
                String  id = results.getString(1);
                String model = results.getString(2);
                double netoWeight=results.getDouble(3);
                double total=results.getDouble(4);
                System.out.println("Truck "+id+"\n"+"model: "+model+"\nneto weight: "+netoWeight+"\ntotal weight: "+total+"\n");
            }


        } catch (Exception e) {
            throw e;
        }
    }
}
