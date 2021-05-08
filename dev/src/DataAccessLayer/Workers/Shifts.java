package DataAccessLayer.Workers;

import DataAccessLayer.Repo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Shifts {

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

    public static void insertShiftDemands(Date date,String shiftType,int cashierAmount,int storeKeeperAmount,int arrangerAmount,int guardAmount,int assistantAmount, int deliveryRequired) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String query = "INSERT OR IGNORE INTO ShiftDemands VALUES (?, ?, ? , ? , ? , ? , ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDate(1,  date);
            stmt.setString(2, shiftType);
            stmt.setInt(3, cashierAmount);
            stmt.setInt(4, storeKeeperAmount);
            stmt.setInt(5, arrangerAmount);
            stmt.setInt(6, guardAmount);
            stmt.setInt(7, assistantAmount);
            stmt.setInt(8, deliveryRequired);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    public static BussinessLayer.WorkersPackage.ShiftDemands getShiftDemands(Date date,String shiftType,int branchID) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String query = "SELECT * FROM ShiftDemands WHERE Date = ? AND ShiftType= ? AND BranchID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDate(1,  date);
            stmt.setString(2, shiftType);
            stmt.setInt(3, branchID);
            stmt.executeQuery();

        } catch (Exception e) {
            throw e;
        }
    }
}
