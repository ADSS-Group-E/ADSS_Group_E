package DataAccessLayer.Workers;

import DataAccessLayer.Repo;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Workers {

//    public static void insertWorker(DTO.Worker worker) throws SQLException {
//        try (Connection conn = Repo.openConnection()) {
//            String query = "INSERT OR IGNORE INTO Employees VALUES (?, ?, ? ,?,?,?)";
//            PreparedStatement stmt = conn.prepareStatement(query);
//            stmt.setInt(1, worker.ID);
//            stmt.setString(2, worker.Name);
//            stmt.setInt(3, worker.bankAccount);
//            stmt.setDate(4, Date.valueOf(worker.startWorkingDate));
//            stmt.setInt(5, worker.salary);
//            stmt.setInt(6, worker.vacationDays);
//            stmt.executeUpdate();
//            conn.close();
//        } catch (Exception e) {
//            throw e;
//        }
//    }


    public static void updateBankAccount(String id, String bankName,String branch,String bankAccount) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE BankAccounts SET Bank_Name =? , Branch =? , BankAccountID = ? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,bankName);
            pst.setString(2,branch);
            pst.setString(3,bankAccount);
            pst.setString(4,id);
            pst.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    public static void insertWorkerQualification(String WorkerID,String qualification) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String query = "INSERT OR IGNORE INTO EmployeesRoles VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,  WorkerID);
            stmt.setString(2, qualification);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean validID(int id) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From Employees WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);

            ResultSet results = pst.executeQuery();
            if(results.next()==false)
                return true;
        } catch (Exception e) {
            throw e;
        }
        return false;
    }

    public static void updateFirstName(String id, String FirstName) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE Workers SET First_Name =? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,FirstName);
            pst.setString(2,id);
            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void updateLastName(String id, String LastName) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE Workers SET Last_Name =? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,LastName);
            pst.setString(2,id);
            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void updateHiringConditions(String ID, double salaryPerHour,String fund,int vacationDays,int sickLeavePerMonth) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE HiringConditions SET salaryPerHour =? , fund =? , vacationDays =?, sickLeavePerMonth =? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setDouble(1,salaryPerHour);
            pst.setString(2,fund);
            pst.setInt(3,vacationDays);
            pst.setInt(4,sickLeavePerMonth);
            pst.setString(5,ID);
            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void removeFavoriteShift(String WorkerID,int day,int shiftType) throws SQLException {
        String dayName="";
        switch (day){
            case 1:
                dayName="Sunday";
                break;
            case 2:
                dayName="Monday";
                break;
            case 3:
                dayName="Tuesday";
                break;
            case 4:
                dayName="Wednesday";
                break;
            case 5:
                dayName="Thursday";
                break;
            case 6:
                dayName="Friday";
                break;
            case 7:
                dayName="Saturday";
                break;
        }
        String ShiftTypeName = shiftType==0 ? "Morning" : "Evening";


        try (Connection conn = Repo.openConnection()) {
            String sql = "DELETE FROM AvailableWorkingDays WHERE ID=? AND IsAvailable=1 AND Day=? AND Shift_Type=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,WorkerID);
            pst.setString(2,dayName);
            pst.setString(3,ShiftTypeName);
            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }


    public static void removeCantWork(String WorkerID,int day,int shiftType) throws SQLException {
        String dayName=convertIntToDayName(day);
        String ShiftTypeName = shiftType==0 ? "Morning" : "Evening";


        try (Connection conn = Repo.openConnection()) {
            String sql = "DELETE FROM AvailableWorkingDays WHERE ID=? AND IsAvailable=0 AND Day=? AND Shift_Type=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,WorkerID);
            pst.setString(2,dayName);
            pst.setString(3,ShiftTypeName);
            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    //boolean favoriteShifts [][]
    public static String convertIntToDayName(int day){
        String dayName="";
        switch (day){
            case 1:
                dayName="Sunday";
                break;
            case 2:
                dayName="Monday";
                break;
            case 3:
                dayName="Tuesday";
                break;
            case 4:
                dayName="Wednesday";
                break;
            case 5:
                dayName="Thursday";
                break;
            case 6:
                dayName="Friday";
                break;
            case 7:
                dayName="Saturday";
                break;
        }
        return dayName;
    }

    public static int convertDayNameToInt(String day){
        int res=1;
        switch (day){
            case "Sunday":
                res=1;
                break;
            case "Monday":
                res=2;
                break;
            case "Tuesday":
                res=3;
                break;
            case "Wednesday":
                res=4;
                break;
            case "Thursday":
                res=5;
                break;
            case "Friday":
                res=6;
                break;
            case "Saturday":
                res=7;
                break;
        }
        return res;
    }

    public static boolean[][] getFavoriteDays(String id) throws SQLException {
        boolean res[][]=new boolean[7][2];
        for(int i=0;i<res.length;i++)
            for(int j=0;j<res.length;j++)
                res[i][j]=false;

        int shiftType=0;
        int day=0;
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From AvailableWorkingDays WHERE ID=? AND IsAvailable=1";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,id);
            ResultSet results = pst.executeQuery();
            while (results.next()) {
                shiftType=results.getString("Shift_Type").equals("Morning") ? 0 : 1;
                day=convertDayNameToInt(results.getString("Day"));
                res[day][shiftType]=true;
            }
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    public static boolean[][] getCantWorkDays(String id) throws SQLException {
        boolean res[][]=new boolean[7][2];
        for(int i=0;i<res.length;i++)
            for(int j=0;j<res.length;j++)
                res[i][j]=false;

        int shiftType=0;
        int day=0;
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From AvailableWorkingDays WHERE ID=? AND IsAvailable=0";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,id);
            ResultSet results = pst.executeQuery();
            while (results.next()) {
                shiftType=results.getString("Shift_Type").equals("Morning") ? 0 : 1;
                day=convertDayNameToInt(results.getString("Day"));
                res[day][shiftType]=true;
            }
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    public static void updateVacationDays(String id, int vacationDays) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE HiringConditions SET vacationDays =? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,vacationDays);
            pst.setString(2,id);
            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void updateSickLeavePerMonth(String id, int sickLeavePerMonth) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE HiringConditions SET sickLeavePerMonth =? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,sickLeavePerMonth);
            pst.setString(2,id);
            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void updateFund(String id, String fund) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE HiringConditions SET fund =? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,fund);
            pst.setString(2,id);
            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void updateSalaryPerHour(String id, double salaryPerHour) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE HiringConditions SET salaryPerHour =? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setDouble(1,salaryPerHour);
            pst.setString(2,id);
            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    /*
     CREATE TABLE IF NOT EXISTS "AvailableWorkingDays" (
                            	"ID"	TEXT NOT NULL,
                            	"Day"	TEXT NOT NULL,
                            	"Shift_Type"	TEXT NOT NULL,
                            	"IsAvailable"	INTEGER NOT NULL,
                            	PRIMARY KEY("ID","Day","Shift_Type")
                            )
     */

    public static void insertWorkerAvailableDays(String ID,boolean [][]favoriteDays,boolean[][] cantWork) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String query1,query2;
            PreparedStatement stmt1,stmt2;
            for(int i=0;i<7;i++)
                for(int j=0;j<2;j++) {
                    if (favoriteDays[i][j]) {
                        query1 = "INSERT OR IGNORE INTO AvailableWorkingDays VALUES (?, ?,? , 1)";
                        stmt1 = conn.prepareStatement(query1);
                        stmt1.setString(1, ID);
                        stmt1.setString(2, convertIntToDayName(i + 1));
                        if (j == 0)
                            stmt1.setString(3, "Morning");
                        else
                            stmt1.setString(3, "Evening");
                        stmt1.executeUpdate();
                    }

                    if(cantWork[i][j]){
                        query2 = "INSERT OR IGNORE INTO AvailableWorkingDays VALUES (?, ?,? , 0)";
                        stmt2 = conn.prepareStatement(query2);
                        stmt2.setString(1, ID);
                        stmt2.setString(2, convertIntToDayName(i + 1));
                        if (j == 0)
                            stmt2.setString(3, "Morning");
                        else
                            stmt2.setString(3, "Evening");
                        stmt2.executeUpdate();
                    }

                }
        } catch (Exception e) {
            throw e;
        }
    }

//    public static boolean CheckConstraint(String ID,int day,int shiftType) throws SQLException {
//        try (Connection conn = Repo.openConnection()) {
//            String sql = "SELECT Date From EmployeesShifts WHERE ID=? AND Kind=?";
//            PreparedStatement pst = conn.prepareStatement(sql);
//            pst.setInt(1,er.ID);
//            pst.setString(2,er.KindConstraint);
//
//            ResultSet results = pst.executeQuery();
//            LinkedList<Date> dates = new LinkedList<>();
//            while(results.next()==true)
//                dates.add(results.getDate(1));
//            for (int i = 0; i<dates.size(); i++)
//            {
//                if(dates.get(i).toLocalDate().getDayOfWeek().toString() == er.DayConstraint)
//                    return false;
//            }
//            return true;
//        } catch (Exception e) {
//            throw e;
//        }
//    }

}
