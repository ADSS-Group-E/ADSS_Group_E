package DataAccessLayer.Workers;

import BussinessLayer.DriverPackage.Driver;
import BussinessLayer.WorkersPackage.*;
import DataAccessLayer.Repo;
import PresentationLayer.WorkerDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

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
    CREATE TABLE IF NOT EXISTS Shifts (
                    	Date	DATE NOT NULL,
                    	ShiftType	TEXT NOT NULL,
                    	BranchID	INTEGER NOT NULL,
                    	ShiftManagerID	TEXT NOT NULL,
                    	DriverID	TEXT,
                    	FOREIGN KEY(ShiftManagerID) REFERENCES Workers(ID) NOT NULL ON DELETE CASCADE ,
                    	FOREIGN KEY(DriverID) REFERENCES Drivers(ID) ON DELETE CASCADE ,
                    	FOREIGN KEY (Date,ShiftType,BranchID) REFERENCES ShiftDemands(Date,ShiftType,BranchID) ON DELETE CASCADE ,
                    	PRIMARY KEY(Date,ShiftType,BranchID)
                    )
     */



    public static void insertShift(LocalDate localDate,String shiftType,int branchID,String shiftManagerID,String DriverID) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            Date date=Date.valueOf(localDate);
            String query = "INSERT OR IGNORE INTO Shifts VALUES (?, ?, ? , ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
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
     */



    /*
     CREATE TABLE IF NOT EXISTS workersAtShift (
                        Date	DATE NOT NULL,
                    	ShiftType	TEXT NOT NULL,
                    	BranchID	INTEGER NOT NULL,
                    	workerID	TEXT NOT NULL,
                    	workAs      TEXT NOT NULL,
                    	FOREIGN KEY (Date,ShiftType,BranchID) REFERENCES Shifts(Date,ShiftType,BranchID) ON DELETE CASCADE ,
                    	FOREIGN KEY(workerID,workAs) REFERENCES Qualifications(ID,Qualification) ON DELETE CASCADE ,
                    	PRIMARY KEY(Date,ShiftType,BranchID,WorkerID,workAs)
                    )
     */

    public static void insertWorkersAtShift(LocalDate localDate,String shiftType,int branchID,List<DTO.WorkAs>workers) throws SQLException{
        PreparedStatement stmt;
        String query="";
        try (Connection conn = Repo.openConnection()) {
            Date date=Date.valueOf(localDate);
            for(DTO.WorkAs workAs : workers){
                query = "INSERT OR IGNORE INTO workersAtShift VALUES (?, ?, ? , ? , ?)";
                stmt = conn.prepareStatement(query);
                stmt.setDate(1,  date);
                stmt.setString(2, shiftType);
                stmt.setInt(3, branchID);
                stmt.setString(4, workAs.workerID);
                stmt.setString(5, workAs.qualification);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        }
    }

    public static void setDeliveryRequired(LocalDate localDate,String shiftType,int branchID,boolean deliveryRequired) throws SQLException{
        try (Connection conn = Repo.openConnection()) {
            int required = deliveryRequired == true ? 1 : 0;
            String sql = """
                    UPDATE ShiftDemands
                    SET deliveryRequired = ?
                    WHERE ShiftDemands.Date=? AND ShiftDemands.ShiftType=? AND ShiftDemands.BranchID=?""";
            PreparedStatement pst = conn.prepareStatement(sql);
            Date date=Date.valueOf(localDate);
            pst.setInt(1,required);
            pst.setDate(2,  date);
            pst.setString(3, shiftType);
            pst.setInt(4, branchID);

            pst.executeUpdate();

        } catch (Exception e) {
            throw e;        }

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

    public static DTO.Shift getShiftDTO(LocalDate localDate, String shiftType, int branchID) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            Date date=Date.valueOf(localDate);
            String query = "SELECT ShiftManagerID DriverID FROM Shifts WHERE Date = ? AND ShiftType= ? AND BranchID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDate(1,  date);
            stmt.setString(2, shiftType);
            stmt.setInt(3, branchID);
            ResultSet results=stmt.executeQuery();

            if(results.next()){
                String shiftManagerID=results.getString("ShiftManagerID");
                String driverID=results.getString("DriverID");
                return new DTO.Shift(date,shiftType,shiftManagerID,driverID,branchID);
                // return Workers.getWorker(ID);
            }
            return null;

//            int branchIDsql=results.getInt("BranchID");
//            String shiftManagersql=results.getString("ShiftManagerID");
//            String driverIDsql=results.getString("DriverID");
//            Date sqlDate=results.getDate("Date");
//            boolean deliveryRequired = results.getInt("deliveryRequired") == 1 ? true : false;
//            ShiftType st= shiftType.equals("Morning") ? ShiftType.Morning :ShiftType.Evening;
//            return new DTO.Shift(sqlDate,shiftType,shiftManagersql,driverIDsql,branchIDsql);
        } catch (Exception e) {
            throw e;
        }
    }

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


    public static List<Worker> createShiftAssignment(LocalDate localDate, ShiftType shiftType, int branchID, List<Worker> workerList, Worker shiftManager, Driver driver) throws Exception{

        if(!shiftManager.getQualifications().contains(Qualifications.ShiftManager)){
            throw new IllegalArgumentException("The worker you assigned to be a shift manager is not qualified to this");
        }

        try{
            Workers.isBranchExists(branchID);
        }catch(Exception e){
            throw e;
        }

        int type = shiftType == ShiftType.Morning ? 0:1;
        if(shiftManager==null)
            throw new IllegalArgumentException("The shiftAssignment must contains a shift Manager");


        try(Connection conn = Repo.openConnection()){
            ShiftDemands shiftDemands=getShiftDemands(localDate,shiftType.name(),branchID);
            if(shiftDemands==null)
                throw new IllegalArgumentException("There is no shift demands for this shift");

            if(shiftDemands.getDeliveryRequired() && driver==null)
                throw new IllegalArgumentException("Shift with delivery must contain a driver");

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
                List<Qualifications> qualifications;
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

            List<DTO.WorkAs>workersList=new LinkedList<>();
            for(Worker w : cashiers){
                workersList.add(new DTO.WorkAs(w.getID(),"Cashier"));
            }
            for(Worker w : storeKeepers){
                workersList.add(new DTO.WorkAs(w.getID(),"Storekeeper"));
            }

            for(Worker w : arrangers){
                workersList.add(new DTO.WorkAs(w.getID(),"Arranger"));
            }

            for(Worker w : guards){
                workersList.add(new DTO.WorkAs(w.getID(),"Guard"));
            }

            for(Worker w : assistants){
                workersList.add(new DTO.WorkAs(w.getID(),"Assistant"));
            }

            if (arrangerAmount == 0 && assistantAmount == 0 && cashierAmount == 0 && guardAmount == 0 && storeKeeperAmount == 0){
                if(driver!=null)
                    insertShift(localDate,shiftType.name(),branchID,shiftManager.getID(),driver.getID());
                else
                    insertShift(localDate,shiftType.name(),branchID,shiftManager.getID(),null);
                insertWorkersAtShift(localDate,shiftType.name(),branchID,workersList);
            }else{
                throw new IllegalArgumentException("couldn't create the shift at : "+localDate+" in the "+shiftType.name().toLowerCase(Locale.ROOT)+ " because of lack in workers");
            }
            return workingList;

        }catch (Exception e){
            throw e;
        }

    }

    public static void createWeeklyAssignment(int branchID, LocalDate startDate, Worker branchManager, List<Worker>workers , List<Worker>shiftManagers, List<Driver> drivers) throws Exception{
        ShiftDemands[][] shiftDemands = new ShiftDemands[7][2];
        String shiftType;
        if(!branchManager.getQualifications().contains(Qualifications.BranchManager))
            throw new IllegalArgumentException("the worker is not qualified to be a branch manager");

        try {
            if(!Workers.getBranchManager(branchID).getID().equals(branchManager.getID()))
                throw new IllegalArgumentException("The branch manager you gave is not the branch manager of this branch");
        }catch (Exception e){
            throw e;
        }

        if (startDate.getDayOfWeek().getValue()!=7) throw new IllegalArgumentException("Weekly assignment must start on sunday");
        try{
            Workers.isBranchExists(branchID);

            for(int i=0;i<7;i++){
                for(int j=0;j<2;j++){
                    shiftType= j==0 ? "Morning" : "Evening";
                    shiftDemands[i][j]=getShiftDemands(startDate.plusDays(i),shiftType,branchID);
                }
            }


        }catch (Exception e){
            throw e;
        }

        for(int i=0;i<workers.size();i++)
            if(workers.get(i).getID().equals(branchManager.getID())) {
                workers.remove(i);
                break;
            }


        ShiftType type;
        Driver driver;
        Worker shiftManager;
        for(int i=0;i<7;i++){
            List<Worker>ableToWork=new LinkedList<>(workers);
            List <Driver> driversTemp=new LinkedList<>(drivers);
            List <Worker> shiftManagersTemp=new LinkedList<>(shiftManagers);
            for(int j=0;j<2;j++){
                if(j==0)
                    type=ShiftType.Morning;
                else
                    type=ShiftType.Evening;
                driver=null;
                if(shiftDemands[i][j]!=null && shiftDemands[i][j].getDeliveryRequired()) {
                    for (Driver d : driversTemp) {
                        if (d.getAvailableWorkDays().getFavoriteShifts()[i][j]) {
                            driver = d;
                            break;
                        }
                    }
                }
                if(shiftManagersTemp.isEmpty()){
                    System.out.println("cant create the shift of "+startDate.plusDays(i) + " in the "+type.name().toLowerCase(Locale.ROOT)+ " because there is no shift manager");
                    continue;
                }
                //throw new IllegalArgumentException("cant create a shift without a shift manager");
                shiftManager=shiftManagersTemp.remove(0);

                if (driver!=null) {
                    driversTemp.remove(driver);
                }
                if(shiftDemands[i][j]!=null&&shiftDemands[i][j].getDeliveryRequired()&& driver==null){
                    System.out.println("the shift of "+startDate.plusDays(i) + " in the "+type.name().toLowerCase(Locale.ROOT)+ " must contain a driver");
                    continue;
                }

                if (shiftDemands[i][j]!=null&&shiftDemands[i][j].getDeliveryRequired() && shiftDemands[i][j].getStoreKeeperAmount()<1){
                    System.out.println("This shift must contain storekeeper, because delivery is on the way to the store");
                    continue;
                }
                try{
                    ableToWork=createShiftAssignment(startDate,type,branchID,workers,shiftManager,driver);
                    //ableToWork=createShiftAssignment(startDate,type,branchID,ableToWork,branchManager, driver);
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
            startDate=startDate.plusDays(1);
        }
    }

    public static BussinessLayer.WorkersPackage.Worker getShiftManager(LocalDate localDate, String shiftType, int branchID) throws Exception {
        try{
            DTO.Shift shiftDTO=Shifts.getShiftDTO(localDate,shiftType,branchID);
            if(shiftDTO==null)
                throw new IllegalArgumentException("shiftDTO was not found");
            return Workers.getWorker(shiftDTO.shiftManagerID);
        }catch (Exception e){
            throw e;
        }
    }

    public static BussinessLayer.WorkersPackage.Worker getShiftDriver(LocalDate localDate, String shiftType, int branchID) throws Exception {
        try {
            DTO.Shift shiftDTO = Shifts.getShiftDTO(localDate, shiftType, branchID);
            return Workers.getWorker(shiftDTO.driverID);
        }catch(Exception e){
            throw e;
        }
    }


    public static void printWorkersAtShift(LocalDate localDate,String shiftType,int branchID)throws Exception{
        int i=1;

        try{

            if(!isShiftExists(localDate,shiftType,branchID)){
                System.out.println("There is no shift at "+localDate+" at the "+shiftType.toLowerCase(Locale.ROOT)+" in branch "+branchID);
            }



        }catch (Exception e){
            throw e;
        }


        Date date=Date.valueOf(localDate);
        System.out.println("Date: "+ date);
        System.out.println("Shift Type "+ shiftType);

        System.out.println("The workers of this shift are: ");
        try{
            Worker shiftManager= Shifts.getShiftManager(localDate,shiftType,branchID);
            System.out.println("The manager of this shift is: "+shiftManager.getFirstName()+" "+shiftManager.getLastName()+ "and his Id is:"+shiftManager.getID());
            Worker driver=Shifts.getShiftDriver(localDate,shiftType,branchID);
            if(driver!=null)
                System.out.println("The driver of this shift is: "+driver.getFirstName()+ " "+driver.getLastName()+ "and his Id is:"+driver.getID());
            List<Worker> arrangers=Workers.WorkersOfQualificationAtBranch(branchID,Qualifications.Arranger);
            List<Worker> Storekeepers=Workers.WorkersOfQualificationAtBranch(branchID,Qualifications.Storekeeper);
            List<Worker> Assistants=Workers.WorkersOfQualificationAtBranch(branchID,Qualifications.Assistant);
            List<Worker> Cashiers=Workers.WorkersOfQualificationAtBranch(branchID,Qualifications.Cashier);
            List<Worker> Guards=Workers.WorkersOfQualificationAtBranch(branchID,Qualifications.Guard);

            System.out.println("works as arranger :");
            for(Worker w : arrangers){
                System.out.println(i+")name:"+w.getFirstName()+" "+w.getLastName()+" ID:"+w.getID());
                i++;
            }

            System.out.println("works as storekeeper :");
            for(Worker w : Storekeepers){
                System.out.println(i+")name:"+w.getFirstName()+" "+w.getLastName()+" ID:"+w.getID());
                i++;
            }

            System.out.println("works as assistant :");
            for(Worker w : Assistants){
                System.out.println(i+")name:"+w.getFirstName()+" "+w.getLastName()+" ID:"+w.getID());
                i++;
            }

            System.out.println("works as cashier :");
            for(Worker w : Cashiers){
                System.out.println(i+")name:"+w.getFirstName()+" "+w.getLastName()+" ID:"+w.getID());
                i++;
            }

            System.out.println("works as guard :");
            for(Worker w : Guards){
                System.out.println(i+")name:"+w.getFirstName()+" "+w.getLastName()+" ID:"+w.getID());
                i++;
            }

        }catch(Exception e){
            throw e;
        }

    }

    public static boolean isShiftExists(LocalDate localDate, String shiftType, int branchID) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            Date date=Date.valueOf(localDate);
            String query = "SELECT ShiftManagerID DriverID FROM Shifts WHERE Date = ? AND ShiftType= ? AND BranchID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDate(1,  date);
            stmt.setString(2, shiftType);
            stmt.setInt(3, branchID);
            ResultSet results=stmt.executeQuery();
            return results.next();
        } catch (Exception e) {
            throw e;
        }

    }

}

