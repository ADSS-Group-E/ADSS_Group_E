package DataAccessLayer.Transports;

import BussinessLayer.WorkersPackage.Worker;
import DataAccessLayer.Repo;

import java.sql.*;
import java.util.List;

public class Driver {

    public static void insertDriver(DTO.Driver d) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String query = "INSERT OR IGNORE INTO Drivers VALUES (?, ?, ?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1,d.id );
            stmt.setString(2, d.lType);
            stmt.setDate(3, (Date) d.expDate);
            stmt.setBoolean(4,d.status);
            stmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            throw e;
        }
    }

    public static BussinessLayer.DriverPackage.Driver checkDriver(int id) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From Drivers WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);

            ResultSet results = pst.executeQuery();
            if(results.next()==false)
                return null;
            Employee e= DL.Employees.Employee.checkEmployee(id);
            return new BL.Transports.DriverPackage.Driver(e.getName(),id,e.getBankAccount(),e.getStartWorkingDate(),e.getSalary(),e.getVacationDays(),e.getRoles(),e.getConstrains(),results.getString(2),results.getDate(3),results.getBoolean(4));
        } catch (Exception e) {
            throw e;
        }

    }

    public static void updateExpDate(int id, Date expDate) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE Drivers SET Expiration_Date =? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setDate(1,expDate);
            pst.setInt(2,id);
            pst.executeUpdate();

        } catch (Exception e) {
            throw e;        }
    }

    public static void updateLicenseType(int id, String type) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE Drivers SET License_Type =? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,type);
            pst.setInt(2,id);
            pst.executeUpdate();

        } catch (Exception e) {
            throw e;        }
    }

    public static void deleteDriver(int id) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "DELETE FROM Drivers WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            pst.executeUpdate();

        } catch (Exception e) {
            throw e;        }
    }
    public static void updateStatus(int id,boolean status) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE Drivers SET STATUS =? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setBoolean(1,status);
            pst.setInt(2,id);

            pst.executeUpdate();

        } catch (Exception e) {
            throw e;        }
    }

    public static void printDrivers(int id1) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From Drivers WHERE ID=? ";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id1);
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
