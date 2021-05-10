package DataAccessLayer.Workers;

import BussinessLayer.WorkersPackage.ShiftDemands;
import BussinessLayer.WorkersPackage.ShiftType;
import DataAccessLayer.Repo;

import java.sql.*;
import java.time.LocalDate;

public class Shifts {



    /*
    Date	DATE NOT NULL,
                    	ShiftType	TEXT NOT NULL,
                    	cashierAmount	INTEGER NOT NULL,
                    	storeKeeperAmount	INTEGER NOT NULL,
                    	arrangerAmount	INTEGER NOT NULL,
                    	guardAmount	INTEGER NOT NULL,
                    	assistantAmount	INTEGER NOT NULL,
                    	deliveryRequired	INTEGER NOT NULL,
     */

    /*
    CREATE TABLE IF NOT EXISTS ShiftDemands (
                                        	Date	DATE NOT NULL,
                                        	ShiftType	TEXT NOT NULL,
                                        	cashierAmount	INTEGER NOT NULL,
                                        	storeKeeperAmount	INTEGER NOT NULL,
                                        	arrangerAmount	INTEGER NOT NULL,
                                        	guardAmount	INTEGER NOT NULL,
                                        	assistantAmount	INTEGER NOT NULL,
                                        	deliveryRequired	INTEGER NOT NULL,
                                        	BranchID            INTEGER NOT NULL,
                                        	FOREIGN KEY (Date,ShiftType,BranchID) REFERENCES Shifts(Date,ShiftType,BranchID) ON DELETE CASCADE ,
                                        	PRIMARY KEY(Date,ShiftType,BranchID)
                                        )
     */

    public static void insertShiftDemands(LocalDate localDate,String shiftType,int branchID,int cashierAmount,int storeKeeperAmount,int arrangerAmount,int guardAmount,int assistantAmount, int deliveryRequired) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String query = "INSERT OR IGNORE INTO ShiftDemands VALUES (?, ?, ? , ? , ? , ? , ?, ? , ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            Date date=Date.valueOf(localDate);
            stmt.setDate(1,  date);
            stmt.setString(2, shiftType);
            stmt.setInt(3, cashierAmount);
            stmt.setInt(4, storeKeeperAmount);
            stmt.setInt(5, arrangerAmount);
            stmt.setInt(6, guardAmount);
            stmt.setInt(7, assistantAmount);
            stmt.setInt(8, deliveryRequired);
            stmt.setInt(9, branchID);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }
    /*
    CREATE TABLE Shifts (
                    	Date	DATE NOT NULL,
                    	ShiftType	TEXT NOT NULL,
                    	BranchID	INTEGER NOT NULL,
                    	ShiftManagerID	TEXT NOT NULL,
                    	DriverID	TEXT,
                    	FOREIGN KEY(ShiftManagerID) REFERENCES Workers(ID) ON DELETE CASCADE ,
                    	FOREIGN KEY(DriverID) REFERENCES Drivers(ID) ON DELETE CASCADE ,
                    	PRIMARY KEY(Date,ShiftType,BranchID)
                    )
     */



    public static void insertShift(LocalDate localDate,String shiftType,int branchID,String shiftManagerID,String DriverID) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String query = "INSERT OR IGNORE INTO Shifts VALUES (?, ?, ? , ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            Date date=Date.valueOf(localDate);
            stmt.setDate(1,  date);
            stmt.setString(2, shiftType);
            stmt.setInt(3, branchID);
            stmt.setString(4, shiftManagerID);
            stmt.setString(5, DriverID);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

     /*
     CREATE TABLE IF NOT EXISTS ShiftDemands (
                                        	Date	DATE NOT NULL,
                                        	ShiftType	TEXT NOT NULL,
                                        	cashierAmount	INTEGER NOT NULL,
                                        	storeKeeperAmount	INTEGER NOT NULL,
                                        	arrangerAmount	INTEGER NOT NULL,
                                        	guardAmount	INTEGER NOT NULL,
                                        	assistantAmount	INTEGER NOT NULL,
                                        	deliveryRequired	INTEGER NOT NULL,
                                        	BranchID            INTEGER NOT NULL,
                                        	FOREIGN KEY (Date,ShiftType,BranchID) REFERENCES Shifts(Date,ShiftType,BranchID) ON DELETE CASCADE ,
                                        	PRIMARY KEY(Date,ShiftType,BranchID)
                                        )
     */

    public static LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public static BussinessLayer.WorkersPackage.ShiftDemands getShiftDemands(Date date,String shiftType,int branchID) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String query = "SELECT * FROM ShiftDemands WHERE Date = ? AND ShiftType= ? AND BranchID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDate(1,  date);
            stmt.setString(2, shiftType);
            stmt.setInt(3, branchID);
            ResultSet results=stmt.executeQuery();
            if(!results.next())
                return null;
            int cashierAmount=results.getInt("cashierAmount");
            int storeKeeperAmount=results.getInt("storeKeeperAmount");
            int arrangerAmount=results.getInt("arrangerAmount");
            int guardAmount=results.getInt("guardAmount");
            int assistantAmount=results.getInt("assistantAmount");
            Date sqlDate=results.getDate("Date");
            boolean deliveryRequired = results.getInt("deliveryRequired") == 1 ? true : false;
            ShiftType st= shiftType.equals("Morning") ? ShiftType.Morning :ShiftType.Evening;
            return new BussinessLayer.WorkersPackage.ShiftDemands(convertToLocalDateViaSqlDate(sqlDate),cashierAmount,storeKeeperAmount,arrangerAmount,guardAmount,assistantAmount,st,deliveryRequired);
        } catch (Exception e) {
            throw e;
        }
    }
}
