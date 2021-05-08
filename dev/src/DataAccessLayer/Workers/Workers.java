package DataAccessLayer.Workers;

import BussinessLayer.WorkersPackage.*;
import DataAccessLayer.Repo;
import PresentationLayer.WorkerDTO;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Workers {



    public static void removeWorker(String WorkerID) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String query = "UPDATE Workers SET isWorking = 0 WHERE ID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,WorkerID);
            stmt.executeUpdate();

//            query="DELETE * FROM BankAccounts Where ID = ?";
//            stmt = conn.prepareStatement(query);
//            stmt.setString(1,WorkerID);
//            stmt.executeUpdate();
//
//            query="DELETE * FROM HiringConditions Where ID = ?";
//            stmt = conn.prepareStatement(query);
//            stmt.setString(1,WorkerID);
//            stmt.executeUpdate();
//
//            query="DELETE * FROM AvailableWorkingDays Where ID = ?";
//            stmt = conn.prepareStatement(query);
//            stmt.setString(1,WorkerID);
//            stmt.executeUpdate();
//
//            query="DELETE * FROM Qualifications Where ID = ?";
//            stmt = conn.prepareStatement(query);
//            stmt.setString(1,WorkerID);
//            stmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        }

    }


//    CREATE TABLE IF NOT EXISTS "Workers" (
//            "ID"	TEXT NOT NULL,
//            "BranchID"	INTEGER NOT NULL,
//            "First_Name"	INTEGER NOT NULL,
//            "Last_Name"	INTEGER NOT NULL,
//            "Start_Working_Day"	DATE NOT NULL,
//            "isWorking" INTEGER NOT NULL
//    PRIMARY KEY("ID")
//                        )

    public static void insertWorker(BussinessLayer.WorkersPackage.Worker worker,int branchID) throws SQLException {

        try (Connection conn = Repo.openConnection()) {
            String query = "INSERT OR IGNORE INTO Workers VALUES (?, ?, ? ,? ,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, worker.getID());
            stmt.setInt(2, branchID);
            stmt.setString(3, worker.getFirstName());
            stmt.setString(4, worker.getLastName());
            Date date=Date.valueOf(worker.getStartWorkingDay());
            stmt.setDate(5,date);
            stmt.setInt(6, 1);
            stmt.executeUpdate();

            BussinessLayer.WorkersPackage.BankAccount bankAccount= worker.getBankAccount();
            query="INSERT OR IGNORE INTO BankAccounts VALUES (?, ?, ? ,?)";
            stmt= conn.prepareStatement(query);
            stmt.setString(1, worker.getID());
            stmt.setString(2, bankAccount.getBankName());
            stmt.setString(3, bankAccount.getBranch());
            stmt.setString(4, bankAccount.getBankAccount());
            stmt.executeUpdate();

            BussinessLayer.WorkersPackage.HiringConditions hiringConditions= worker.getHiringConditions();
            query="INSERT OR IGNORE INTO HiringConditions VALUES (?, ?, ? ,? ,?)";
            stmt= conn.prepareStatement(query);
            stmt.setString(1, worker.getID());
            stmt.setDouble(2, hiringConditions.getSalaryPerHour());
            stmt.setString(3, hiringConditions.getFund());
            stmt.setInt(4, hiringConditions.getVacationDays());
            stmt.setInt(5, hiringConditions.getSickLeavePerMonth());
            stmt.executeUpdate();

            BussinessLayer.WorkersPackage.AvailableWorkDays availableWorkDays= worker.getAvailableWorkDays();
            String day="",shiftType="";
            for(int i=0;i<availableWorkDays.getFavoriteShifts().length;i++){
                for(int j=0;j<availableWorkDays.getFavoriteShifts()[i].length;j++){

                    if(availableWorkDays.getFavoriteShifts()[i][j]) {
                        day = convertIntToDayName(i + 1);
                        shiftType = j == 0 ? "Morning" : "Evening";
                        query = "INSERT OR IGNORE INTO AvailableWorkingDays VALUES (?, ?, ? ,?)";
                        stmt = conn.prepareStatement(query);
                        stmt.setString(1, worker.getID());
                        stmt.setString(2, day);
                        stmt.setString(3, shiftType);
                        stmt.setInt(4, 1);
                        stmt.executeUpdate();
                    }
                }
            }

            for(int i=0;i<availableWorkDays.getCantWork().length;i++){
                for(int j=0;j<availableWorkDays.getCantWork()[i].length;j++){

                    if(availableWorkDays.getCantWork()[i][j]) {
                        day = convertIntToDayName(i + 1);
                        shiftType = j == 0 ? "Morning" : "Evening";
                        query = "INSERT OR IGNORE INTO AvailableWorkingDays VALUES (?, ?, ? ,?)";
                        stmt = conn.prepareStatement(query);
                        stmt.setString(1, worker.getID());
                        stmt.setString(2, day);
                        stmt.setString(3, shiftType);
                        stmt.setInt(4, 0);
                        stmt.executeUpdate();
                    }
                }
            }

            List<Qualifications>qualifications=worker.getQualifications();
            for(Qualifications q : Qualifications.values()){
                if(qualifications.contains(q)){
                    query = "INSERT OR IGNORE INTO Qualifications VALUES (?, ?)";
                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, worker.getID());
                    stmt.setString(2, q.name());
                    stmt.executeUpdate();
                }
            }

        } catch (Exception e) {
            throw e;
        }
    }


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


    public static void deleteEmployee(String ID) throws Exception {
        try (Connection conn = Repo.openConnection()) {
            String sql = "DELETE FROM Workers WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,ID);
            pst.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    //----------------------------------------------------------------------------------------------
    /*
     CREATE TABLE IF NOT EXISTS "AvailableWorkingDays" (
                            	"ID"	TEXT NOT NULL,
                            	"Day"	TEXT NOT NULL,
                            	"Shift_Type"	TEXT NOT NULL,
                            	"IsAvailable"	INTEGER NOT NULL,
                            	PRIMARY KEY("ID","Day","Shift_Type")
                            )
     */
    public static void deleteFavoriteDay(String ID, String day, String shiftType) throws Exception {
        try (Connection conn = Repo.openConnection()) {
            String sql = "DELETE FROM AvailableWorkingDays WHERE ID=? AND Day=? AND Shift_Type=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,ID);
            pst.setString(2,day);
            pst.setString(3,shiftType);
            pst.executeUpdate();

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    /*
    CREATE TABLE IF NOT EXISTS "Qualifications" (
                                	"ID"	TEXT NOT NULL,
                                	"Qualification"	TEXT NOT NULL,
                                	FOREIGN KEY (ID) REFERENCES Workers(ID) ON DELETE CASCADE ,
                                	PRIMARY KEY("ID","Qualification")
                                )
     */

    public static void deleteQualification(String ID, String qualification) throws Exception {
        try (Connection conn = Repo.openConnection()) {
            String sql = "DELETE FROM Qualifications WHERE ID=? AND Qualification=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,ID);
            pst.setString(2,qualification);
            pst.executeUpdate();

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    public static boolean CheckQualification(String ID, String qualification) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT Qualification From Qualifications WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,ID);

            ResultSet results = pst.executeQuery();
            LinkedList<String> qualifications = new LinkedList<>();
            while(results.next()==true)
                qualifications.add(results.getString(1));
            return qualifications.contains(qualification);
        } catch (Exception e) {
            throw e;
        }
    }

    public static LinkedList<String> getQualificationsFromDB(String ID) throws Exception {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From Qualifications WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,ID);

            ResultSet results = pst.executeQuery();
            LinkedList<String>res=new LinkedList<>();
            while(results.next()){
                res.add(results.getString("Qualification"));
            }
               return res;
        } catch (Exception e) {
            throw e;
        }
    }


    public static String getFirstNameFromDB(String ID) throws Exception {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From Workers WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,ID);

            ResultSet results = pst.executeQuery();
            if(!results.next())
                return null;

            return results.getString("First_Name");
        } catch (Exception e) {
            throw e;
        }
    }

    public static String getLastNameFromDB(String ID) throws Exception {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From Workers WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,ID);

            ResultSet results = pst.executeQuery();
            if(!results.next())
                return null;

            return results.getString("Last_Name");
        } catch (Exception e) {
            throw e;
        }
    }

    public static LocalDate getStartWorkingDateFromDB(String ID) throws Exception {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From Workers WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,ID);

            ResultSet results = pst.executeQuery();
            if(!results.next())
                return null;
            Date date=results.getDate("Start_Working_Day");
            //convert Date to LocalDate
            return new java.sql.Date(date.getTime()).toLocalDate();
        } catch (Exception e) {
            throw e;
        }
    }


    public static BussinessLayer.WorkersPackage.BankAccount getBankAccountFromDB(String ID) throws Exception {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From BankAccounts WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,ID);

            ResultSet results = pst.executeQuery();
            if(!results.next())
                return null;

            return new BussinessLayer.WorkersPackage.BankAccount(results.getString("Bank_Name"),results.getString("Branch"), results.getString("BankAccountID"));
        } catch (Exception e) {
            throw e;
        }
    }

    public static BussinessLayer.WorkersPackage.HiringConditions getHiringConditionsFromDB(String ID) throws Exception {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From HiringConditions WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,ID);

            ResultSet results = pst.executeQuery();
            if(!results.next())
                return null;

            double salaryPerHour=results.getDouble("salaryPerHour");
            String fund=results.getString("fund");
            int vacationDays=results.getInt("vacationDays");
            int sickLeavePerMonth=results.getInt("sickLeavePerMonth");

            return new BussinessLayer.WorkersPackage.HiringConditions(salaryPerHour,fund,vacationDays,sickLeavePerMonth);
        } catch (Exception e) {
            throw e;
        }
    }


    public static BussinessLayer.WorkersPackage.AvailableWorkDays getAvailableWorkDaysFromDB(String ID) throws Exception {
        try (Connection conn = Repo.openConnection()) {
            boolean[][] favorite=getFavoriteDays(ID);
            boolean[][] cantWork=getCantWorkDays(ID);
            Boolean[][] favoriteBoolean=new Boolean[favorite.length][];
            Boolean[][] cantWorkBoolean=new Boolean[cantWork.length][];
            for(int i=0;i<favorite.length;i++) {
                favoriteBoolean[i] = new Boolean[favorite[i].length];
                for (int j = 0; j < favorite[i].length; j++) {
                    favoriteBoolean[i][j] = favorite[i][j];
                }
            }

            for(int i=0;i<cantWork.length;i++) {
                cantWorkBoolean[i] = new Boolean[cantWork[i].length];
                for (int j = 0; j < cantWork[i].length; j++) {
                    cantWorkBoolean[i][j] = cantWork[i][j];
                }
            }
            return new BussinessLayer.WorkersPackage.AvailableWorkDays(favoriteBoolean,cantWorkBoolean);
        } catch (Exception e) {
            throw e;
        }
    }



    public static BussinessLayer.WorkersPackage.Worker getWorker(String ID) throws Exception {
        try (Connection conn = Repo.openConnection()) {
            String FirstName=getFirstNameFromDB(ID);
            String LastName=getLastNameFromDB(ID);
            BussinessLayer.WorkersPackage.BankAccount bankAccount=getBankAccountFromDB(ID);
            BussinessLayer.WorkersPackage.HiringConditions hiringConditions=getHiringConditionsFromDB(ID);
            BussinessLayer.WorkersPackage.AvailableWorkDays availableWorkDays=getAvailableWorkDaysFromDB(ID);
            List<String>names=getQualificationsFromDB(ID);
            List<Qualifications>qualifications=new LinkedList<>();
            for(Qualifications q :Qualifications.values()){
                if(names.contains(q.name()))
                    qualifications.add(q);
            }
            return new Worker(FirstName,LastName,ID,bankAccount,hiringConditions,availableWorkDays,qualifications);
        } catch (Exception e) {
            throw e;
        }
    }

    public static void displayWorkersByBranchID(int brID) throws Exception {

        List<Worker>workers=new LinkedList<>();

        try(Connection conn = Repo.openConnection()){

            String sql = "SELECT ID From Workers WHERE BranchID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,brID);
            ResultSet results = pst.executeQuery();
            while(results.next()){
                workers.add(getWorker(results.getString("ID")));
            }

        }catch(Exception e){
            throw e;
        }

        if(workers.isEmpty()) {
            System.out.println("Can't display workers of branch that isn't exist");
            return ;
        }

        System.out.println("The workers at branch "+brID+" are:");
        int index=1;


        System.out.println("1) The Branch manager is: " + "Name:"+getBranch(brID).getBranchManager().getFirstName()+" "+getBranch(brID).getBranchManager().getLastName()+" ID:"+getBranch(brID).getBranchManager().getID() + " Qualifications:"+getBranch(brID).getBranchManager().getQualifications() );
        System.out.println("2) The HRD is: " +"Name:"+getBranch(brID).getActiveHRD().getFirstName()+" "+getBranch(brID).getActiveHRD().getLastName()+" ID:"+getBranch(brID).getActiveHRD().getID() + " Qualifications:"+getBranch(brID).getActiveHRD().getQualifications());
        for(Worker worker:getBranch(brID).getWorkersList()){
            if(!getBranch(brID).getBranchManager().getID().equals(worker.getID()) && !getBranch(brID).getActiveHRD().getID().equals(worker.getID()) )
                System.out.println(index+") Name:"+worker.getFirstName()+" "+worker.getLastName()+" ID:"+worker.getID() + " Qualifications:"+worker.getQualifications());
            index++;
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
