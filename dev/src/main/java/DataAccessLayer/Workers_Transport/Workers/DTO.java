package DataAccessLayer.Workers_Transport.Workers;

import BusinessLayer.Workers_Transport.DriverPackage.Driver;
import BusinessLayer.Workers_Transport.WorkersPackage.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;

public class DTO {

    public enum ShiftType {
        Morning,
        Evening
    }

    public static class WorkAs{
        protected String workerID;
        protected String qualification;

        public WorkAs(String workerID,String qualification){
            this.workerID = workerID;
            this.qualification=qualification;
        }
    }

    public static class AvailableWorkDays{
        protected String WorkerID;
        protected Boolean favoriteShifts[][];
        protected Boolean cantWork[][];

        public AvailableWorkDays(String WorkerID) {
            this.WorkerID=WorkerID;
            favoriteShifts=new Boolean[7][2];
            cantWork=new Boolean[7][2];
            for(int i=0;i<7;i++) {
                for (int j = 0; j < 2; j++) {
                    favoriteShifts[i][j] = false;
                    cantWork[i][j] = false;
                }
            }
        }
        public AvailableWorkDays(String WorkerID,Boolean[][] favoriteShifts, Boolean[][] cantWork) {
            this.WorkerID=WorkerID;
            this.cantWork=new Boolean[7][2];
            this.favoriteShifts=new Boolean[7][2];
            for(int i=0;i<7;i++){
                for(int j=0;j<2;j++){
                    this.cantWork[i][j]=cantWork[i][j];
                    this.favoriteShifts[i][j]=favoriteShifts[i][j];
                }
            }
        }


    }

    public static class BankAccount{
        protected String WorkerID;
        protected String bankName;
        protected String branch;
        protected String bankAccount;

        public BankAccount(String workerID, String bankName, String branch, String bankAccount) {
            WorkerID = workerID;
            this.bankName = bankName;
            this.branch = branch;
            this.bankAccount = bankAccount;
        }
    }

    public static class Worker{
        protected  int branchID;
        protected  String ID;
        protected String FirstName;
        protected String LastName;
        protected Date startWorkingDay;
        protected boolean isFired;

        public Worker(int branchID, String ID, String firstName, String lastName, Date startWorkingDay, boolean isFired) {
            this.branchID = branchID;
            this.ID = ID;
            FirstName = firstName;
            LastName = lastName;
            this.startWorkingDay = startWorkingDay;
            this.isFired = isFired;
        }

        public Worker(int branchID, String ID, String firstName, String lastName, Date startWorkingDay) {
            this.branchID = branchID;
            this.ID = ID;
            FirstName = firstName;
            LastName = lastName;
            this.startWorkingDay = startWorkingDay;
            isFired=false;
        }
    }

    public static class Shift{
        protected Date date;
        protected String shiftType;
        protected String shiftManagerID;
        protected String driverID;
        protected int branchID;
        protected ShiftType type;
        protected BusinessLayer.Workers_Transport.WorkersPackage.ShiftDemands demands;


        protected EnumMap<Qualifications,List<BusinessLayer.Workers_Transport.WorkersPackage.Worker>> workers;


        public Shift(Date date, String shiftType, String shiftManagerID, String driverID, int branchID) {
            this.date = date;
            this.shiftType = shiftType;
            this.shiftManagerID = shiftManagerID;
            this.driverID = driverID;
            this.branchID = branchID;
        }
    }

    public static class ShiftDemands{
        protected int branchID;
        protected Date date;
        protected ShiftType shiftType;
        protected int cashierAmount;
        protected int storeKeeperAmount;
        protected int arrangerAmount;
        protected int guardAmount;
        protected int assistantAmount;
        protected boolean deliveryRequired;

        public ShiftDemands(int branchID, Date date, ShiftType shiftType, int cashierAmount, int storeKeeperAmount, int arrangerAmount, int guardAmount, int assistantAmount, boolean deliveryRequired) {
            this.branchID = branchID;
            this.date = date;
            this.shiftType = shiftType;
            this.cashierAmount = cashierAmount;
            this.storeKeeperAmount = storeKeeperAmount;
            this.arrangerAmount = arrangerAmount;
            this.guardAmount = guardAmount;
            this.assistantAmount = assistantAmount;
            this.deliveryRequired = deliveryRequired;
        }

        public ShiftDemands(int branchID, Date date, ShiftType shiftType, int cashierAmount, int storeKeeperAmount, int arrangerAmount, int guardAmount, int assistantAmount) {
            this.branchID = branchID;
            this.date = date;
            this.shiftType = shiftType;
            this.cashierAmount = cashierAmount;
            this.storeKeeperAmount = storeKeeperAmount;
            this.arrangerAmount = arrangerAmount;
            this.guardAmount = guardAmount;
            this.assistantAmount = assistantAmount;
            this.deliveryRequired=false;
        }
    }

    public static class HiringConditions{
        protected String WorkerID;
        protected double salaryPerHour;
        protected String fund;
        protected int vacationDays;
        protected int sickLeavePerMonth;

        public HiringConditions(String workerID, double salaryPerHour, String fund, int vacationDays, int sickLeavePerMonth) {
            WorkerID = workerID;
            this.salaryPerHour = salaryPerHour;
            this.fund = fund;
            this.vacationDays = vacationDays;
            this.sickLeavePerMonth = sickLeavePerMonth;
        }
    }



    public static class Branch{
        protected int branchID;
        protected Worker branchManager;
        protected Worker activeHRD;
        protected List<Shift> weeklyAssignmentsHistory ;
        protected List<Worker> workersList;
        protected List<Worker> formerWorkers;
        protected Shift[][] assignmentsBoard;

    }


}
