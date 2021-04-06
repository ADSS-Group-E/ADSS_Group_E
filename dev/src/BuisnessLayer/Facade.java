package BuisnessLayer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Facade {

    private BranchController branchController;
    private ShiftController shiftController;
    private static Facade instance=null;

    private Facade() {
        this.branchController = BranchController.getInstance();
        this.shiftController = ShiftController.getInstance();
    }

    public static Facade getInstance(){
        if(instance==null){
            instance=new Facade();
        }
        return instance;
    }

    public void addBranch(int branchID,Worker branchManager,Worker activeHRD){
        if(!branchManager.getQualifications().contains(Qualifications.BranchManager))
            throw new IllegalArgumentException("The worker you sent is not a branch manager");

        branchController.addBranch(branchID,branchManager,activeHRD);
        shiftController.addBranch(branchID);
    }

    public Branch getBranch(int ID){
        return branchController.getBranch(ID);
    }

    public BranchController getBranchController() {
        return branchController;
    }

    public void setBranchController(BranchController branchController) {
        this.branchController = branchController;
    }

    public ShiftController getShiftController() {
        return shiftController;
    }

    public void setShiftController(ShiftController shiftController) {
        this.shiftController = shiftController;
    }

    public void addWorker(Worker worker,int branchID){
        branchController.addWorker(worker,branchID);
    }

    public void removeWorker(Worker worker,int branchID){
        branchController.removeWorker(worker,branchID);
    }

    public void removeBranch(int branchID){
        branchController.removeBranch(branchID);
        shiftController.removeBranch(branchID);
    }

    public List<Shift> getHistory(int branchID){
        return  branchController.getBranch(branchID).getWeeklyAssignmentsHistory();
    }

    public Shift getShift(int branchID,LocalDate date,ShiftType shiftType){
        return shiftController.getShift(branchID,date,shiftType);
    }

    public void createWeeklyAssignment(int branchID, LocalDate startDate, ShiftDemands[][] shiftDemands, List<Worker>workers , Worker shiftManager){

        shiftController.createWeeklyAssignment(branchID,startDate,shiftDemands,workers,shiftManager);
    }

    public void workerReplacement(int branchID,LocalDate date1, ShiftType shiftType1,LocalDate date2 ,ShiftType shiftType2,Worker worker1,Worker worker2,Worker branchManager) {
        shiftController.workerReplacement(branchID,date1,shiftType1,date2,shiftType2,worker1,worker2,branchManager);
    }

    public Worker findWorker(int branchID,String workerID){
        return branchController.findWorker(branchID,workerID);
    }

    public Worker findWorkerByID(String workerID){
        return branchController.findWorkerByID(workerID);
    }


    public void printWorkersAtShift(int branchID,LocalDate date,ShiftType shiftType){
        shiftController.printWorkersAtShift(branchID,date,shiftType);
    }

    public void showWorkers(int branchID){
        branchController.showWorkers(branchID);
    }

    public List<Qualifications> getWorkerQualifications(Worker worker) {
        return branchController.getWorkerQualifications(worker);
    }
    public Shift getShift(int branchID,LocalDate date, ShiftType shiftType){
        return shiftController.getShift(branchID,date,shiftType);
    }

    public void printWorker(Worker worker){
        System.out.println(worker.toString());
    }
}
