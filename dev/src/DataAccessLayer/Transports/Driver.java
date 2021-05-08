package DataAccessLayer.Transports;

import BussinessLayer.WorkersPackage.Worker;
import DataAccessLayer.Repo;

import java.sql.*;
import java.util.List;

public class Driver {

    /*
    "CREATE TABLE IF NOT EXISTS Drivers" +
                    "(ID TEXT PRIMARY KEY NOT NULL," +
                    "License_Type VARCHAR (10)   NOT NULL, " +
                    "Expiration_Date DATE NOT NULL," +
                    "STATUS INT NOT NULL,"+
                    "FOREIGN KEY (ID) REFERENCES Workers (ID) ON DELETE CASCADE )"
     */

    public static void insertDriver(DTO.Driver d) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String query = "INSERT OR IGNORE INTO Drivers VALUES (?, ?, ?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,d.id );
            stmt.setString(2, d.lType);
            stmt.setDate(3, (Date) d.expDate);
            int status= d.status == true ? 1 : 0;
            stmt.setInt(4,status);
            stmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            throw e;
        }
    }

    /*
        public Driver(Worker w,String licenseType, Date expLicenseDate) {
        super(w.getFirstName(), w.getLastName(), w.getID(), w.getBankAccount(), w.getHiringConditions(), w.getAvailableWorkDays(),w.getQualifications());
     */
    public static BussinessLayer.DriverPackage.Driver checkDriver(String id) throws Exception {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From Drivers WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,id);

            ResultSet results = pst.executeQuery();
            if(results.next()==false)
                return null;
            Worker e= DataAccessLayer.Workers.Workers.getWorker(id) ;
            //boolean status= results.getInt(4) == 1 ? true : false;

            return new BussinessLayer.DriverPackage.Driver(e,results.getString("License_Type"),results.getDate("Expiration_Date"));
        } catch (Exception e) {
            throw e;
        }

    }

    public static void updateExpDate(String id, Date expDate) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE Drivers SET Expiration_Date =? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setDate(1,expDate);
            pst.setString(2,id);
            pst.executeUpdate();

        } catch (Exception e) {
            throw e;        }
    }

    public static void updateLicenseType(String id, String type) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE Drivers SET License_Type =? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,type);
            pst.setString(2,id);
            pst.executeUpdate();

        } catch (Exception e) {
            throw e;        }
    }

    public static void deleteDriver(String id) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "DELETE FROM Drivers WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,id);
            pst.executeUpdate();

        } catch (Exception e) {
            throw e;        }
    }
    public static void updateStatus(String id,boolean status) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE Drivers SET STATUS =? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            int statuss = status == true ? 1 : 0;
            pst.setInt(1,statuss);
            pst.setString(2,id);

            pst.executeUpdate();

        } catch (Exception e) {
            throw e;        }
    }

    public static void printDrivers(String id1) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From Drivers WHERE ID=? ";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,id1);
            ResultSet results = pst.executeQuery();
            while (results.next()) {
                String licensType = results.getString(2);
                Date expDAte = results.getDate(3);
                System.out.println("license type: "+licensType+"\nexpiration Date: "+expDAte);
            }



        } catch (Exception e) {
            throw e;
        }
    }
}
