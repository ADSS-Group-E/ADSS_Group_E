package DataAccessLayer.Workers;

import BussinessLayer.WorkersPackage.Shift;
import BussinessLayer.WorkersPackage.Worker;

import java.util.List;

public class DTO {

    public static class AvailableWorkDays{
        protected Boolean favoriteShifts[][];
        protected Boolean cantWork[][];

        public AvailableWorkDays() {
            favoriteShifts=new Boolean[7][2];
            cantWork=new Boolean[7][2];
            for(int i=0;i<7;i++) {
                for (int j = 0; j < 2; j++) {
                    favoriteShifts[i][j] = false;
                    cantWork[i][j] = false;
                }
            }
        }
        public AvailableWorkDays(Boolean[][] favoriteShifts, Boolean[][] cantWork) {
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
        protected String bankName;
        protected String branch;
        protected String bankAccount;

        public BankAccount(String bankName, String branch, String bankAccount) {
            this.bankName = bankName;
            this.branch = branch;
            this.bankAccount = bankAccount;
        }
    }

    public static class Worker{

    }

    public static class HiringConditions{
        protected double salaryPerHour;
        protected String fund;
        protected int vacationDays;
        protected int sickLeavePerMonth;

        public HiringConditions(double salaryPerHour, String fund, int vacationDays, int sickLeavePerMonth) {
            this.salaryPerHour = salaryPerHour;
            this.fund = fund;
            this.vacationDays = vacationDays;
            this.sickLeavePerMonth = sickLeavePerMonth;
        }
    }

//    public static class Branch{
//        protected int branchID;
//        protected Worker branchManager;
//        protected Worker activeHRD;
//        protected List<Shift> weeklyAssignmentsHistory ;
//        protected List<Worker> workersList;
//        protected List<Worker> formerWorkers;
//        protected Shift[][] assignmentsBoard;
//
//    }


}
