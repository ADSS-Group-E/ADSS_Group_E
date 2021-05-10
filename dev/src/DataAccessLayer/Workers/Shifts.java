package DataAccessLayer.Workers;

import BussinessLayer.DriverPackage.Driver;
import BussinessLayer.WorkersPackage.*;
import DataAccessLayer.Repo;
import PresentationLayer.WorkerDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

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

    //TODO : check this func

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

    public static void insertWorkersAtShift(LocalDate localDate,String shiftType,int branchID,List<String>workersID) throws SQLException{
        PreparedStatement stmt;
        String query="";
        try (Connection conn = Repo.openConnection()) {
            Date date=Date.valueOf(localDate);
            for(String ID : workersID){
                query = "INSERT OR IGNORE INTO workersAtShift VALUES (?, ?, ? , ?)";
                stmt = conn.prepareStatement(query);
                stmt.setDate(1,  date);
                stmt.setString(2, shiftType);
                stmt.setInt(3, branchID);
                stmt.setString(4, ID);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        }
    }

            /*
             CREATE TABLE IF NOT EXISTS Shifts (
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

            /*
            CREATE TABLE IF NOT EXISTS workersAtShift (
                        Date	DATE NOT NULL,
                    	ShiftType	TEXT NOT NULL,
                    	BranchID	INTEGER NOT NULL,
                    	workerID	TEXT NOT NULL,
                    	FOREIGN KEY(Date) REFERENCES Shifts(Date) ON DELETE CASCADE ,
                    	FOREIGN KEY(ShiftType) REFERENCES Shifts(ShiftType) ON DELETE CASCADE ,
                    	FOREIGN KEY(BranchID) REFERENCES Shifts(BranchID) ON DELETE CASCADE ,
                    	FOREIGN KEY(workerID) REFERENCES Workers(ID) ON DELETE CASCADE ,
                    	PRIMARY KEY(Date,ShiftType,BranchID,WorkerID)
                    )
                    """;
             */

    public static BussinessLayer.WorkersPackage.ShiftDemands getShiftDemands(LocalDate localDate,String shiftType,int branchID) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            Date date=Date.valueOf(localDate);
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


    public static List<Worker> createShiftAssignment(LocalDate localDate, ShiftType shiftType, int branchID, List<Worker> workerList, Worker branchManager, Driver driver) throws Exception{
        int type = shiftType == ShiftType.Morning ? 0:1;
        if(branchManager==null)
            throw new IllegalArgumentException("The shiftAssignment must contains a branchManager");

        try(Connection conn = Repo.openConnection()){
            ShiftDemands shiftDemands=getShiftDemands(localDate,shiftType.name(),branchID);
            if(shiftDemands==null)
                throw new IllegalArgumentException("There is no shift demands for this shift");

            int dayOfWeek=localDate.getDayOfWeek().getValue();
            int ans=1;
            switch(dayOfWeek) {
                case 7:
                    ans = 1;
                    break;
                case 6:
                    ans = 7;
                    break;
                case 5:
                    ans = 6;
                    break;
                case 4:
                    ans = 5;
                    break;
                case 3:
                    ans = 4;
                    break;
                case 2:
                    ans = 3;
                    break;
                case 1:
                    ans = 2;
                    break;
            }


            int dayAtWeek = ans-1;
            int arrangerAmount = shiftDemands.getArrangerAmount();
            int assistantAmount = shiftDemands.getAssistantAmount();
            int cashierAmount = shiftDemands.getCashierAmount();
            int guardAmount = shiftDemands.getGuardAmount();
            int storeKeeperAmount = shiftDemands.getStoreKeeperAmount();
            List<Worker> arrangers = new LinkedList<>();
            List<Worker> assistants = new LinkedList<>();
            List<Worker> cashiers = new LinkedList<>();
            List<Worker> guards = new LinkedList<>();
            List<Worker> storeKeepers = new LinkedList<>();
            int typeOfShift = shiftType == ShiftType.Morning ? 0 : 1;
            List<Worker> notWorkYet = new LinkedList<>(workerList);
            List<Worker> workingList=new LinkedList<>();
            for (Worker w : workerList) {
                List<Qualifications> qualifications=null;
                qualifications = w.getQualifications();

                if (arrangerAmount > 0 && qualifications.contains(Qualifications.Arranger)) {
                    if (w.getAvailableWorkDays().getFavoriteShifts()[dayAtWeek][typeOfShift]) {
                        arrangerAmount--;
                        arrangers.add(w);
                        notWorkYet.remove(w);
                        workingList.add(w);
                    }
                } else if (assistantAmount > 0 && qualifications.contains(Qualifications.Assistant)) {
                    if (w.getAvailableWorkDays().getFavoriteShifts()[dayAtWeek][typeOfShift]) {
                        assistantAmount--;
                        assistants.add(w);
                        notWorkYet.remove(w);
                        workingList.add(w);
                    }
                } else if (cashierAmount > 0 && qualifications.contains(Qualifications.Cashier)) {
                    if (w.getAvailableWorkDays().getFavoriteShifts()[dayAtWeek][typeOfShift]) {
                        cashierAmount--;
                        cashiers.add(w);
                        notWorkYet.remove(w);
                        workingList.add(w);
                    }
                } else if (guardAmount > 0 && qualifications.contains(Qualifications.Guard)) {
                    if (w.getAvailableWorkDays().getFavoriteShifts()[dayAtWeek][typeOfShift]) {
                        guardAmount--;
                        guards.add(w);
                        notWorkYet.remove(w);
                        workingList.add(w);
                    }
                } else if (storeKeeperAmount > 0 && qualifications.contains(Qualifications.Storekeeper)) {
                    if (w.getAvailableWorkDays().getFavoriteShifts()[dayAtWeek][typeOfShift]) {
                        storeKeeperAmount--;
                        storeKeepers.add(w);
                        notWorkYet.remove(w);
                        workingList.add(w);
                    }
                }
                if (arrangerAmount == 0 && assistantAmount == 0 && cashierAmount == 0 && guardAmount == 0 && storeKeeperAmount == 0)
                    break;
            }

            List<String>workersID=new LinkedList<>();
            for(Worker w : cashiers)
                workersID.add(w.getID());
            for(Worker w : storeKeepers)
                workersID.add(w.getID());
            for(Worker w : arrangers)
                workersID.add(w.getID());
            for(Worker w : guards)
                workersID.add(w.getID());
            for(Worker w : assistants)
                workersID.add(w.getID());



            Shift shift = new Shift(localDate, shiftType, shiftDemands, cashiers, storeKeepers, arrangers, guards, assistants, branchManager,branchID, driver);
            if (arrangerAmount == 0 && assistantAmount == 0 && cashierAmount == 0 && guardAmount == 0 && storeKeeperAmount == 0){
                insertWorkersAtShift(localDate,shiftType.name(),branchID,workersID);
               // insertShift(localDate,shiftType.name(),branchID,);
            }

            return workingList;



        }catch (Exception e){
            throw e;
        }

    }

//    public static void createWeeklyAssignment(int branchID, LocalDate startDate, WorkerDTO branchManager, List<Driver> drivers) {
//        try (Connection conn = Repo.openConnection()) {
//            String query = "SELECT * FROM ShiftDemands WHERE Date = ? AND ShiftType= ? AND BranchID = ?";
//            PreparedStatement stmt = conn.prepareStatement(query);
//            stmt.setDate(1,  date);
//            stmt.setString(2, shiftType);
//            stmt.setInt(3, branchID);
//            ResultSet results=stmt.executeQuery();
//            if(!results.next())
//                return null;
//            int cashierAmount=results.getInt("cashierAmount");
//            int storeKeeperAmount=results.getInt("storeKeeperAmount");
//            int arrangerAmount=results.getInt("arrangerAmount");
//            int guardAmount=results.getInt("guardAmount");
//            int assistantAmount=results.getInt("assistantAmount");
//            Date sqlDate=results.getDate("Date");
//            boolean deliveryRequired = results.getInt("deliveryRequired") == 1 ? true : false;
//            ShiftType st= shiftType.equals("Morning") ? ShiftType.Morning :ShiftType.Evening;
//            return new BussinessLayer.WorkersPackage.ShiftDemands(convertToLocalDateViaSqlDate(sqlDate),cashierAmount,storeKeeperAmount,arrangerAmount,guardAmount,assistantAmount,st,deliveryRequired);
//        } catch (Exception e) {
//            throw e;
//        }
//    }

    }

