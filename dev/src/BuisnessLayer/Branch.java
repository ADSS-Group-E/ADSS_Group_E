package BuisnessLayer;

import java.util.List;

public class Branch {

    private int branchID;
    private Worker branchManager;
    private Worker activeHRD;
    private List<Shift> weeklyAssignmentsHistory ;
    private List<Worker> workersList;
    private Shift[][] assignmentsBoard;

    public Branch(int branchID, Worker branchManager, Worker activeHRD, List<Shift> weeklyAssignmentsHistory, List<Worker> workersList, Shift[][] assignmentsBoard) {
        this.branchID = branchID;
        this.branchManager = branchManager;
        this.activeHRD = activeHRD;
        this.weeklyAssignmentsHistory = weeklyAssignmentsHistory;
        this.workersList = workersList;
        this.assignmentsBoard = assignmentsBoard;
    }

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }

    public Worker getBranchManager() {
        return branchManager;
    }

    public void setBranchManager(Worker branchManager) {
        this.branchManager = branchManager;
    }

    public Worker getActiveHRD() {
        return activeHRD;
    }

    public void setActiveHRD(Worker activeHRD) {
        this.activeHRD = activeHRD;
    }

    public List<Shift> getWeeklyAssignmentsHistory() {
        return weeklyAssignmentsHistory;
    }

    public void setWeeklyAssignmentsHistory(List<Shift> weeklyAssignmentsHistory) {
        this.weeklyAssignmentsHistory = weeklyAssignmentsHistory;
    }

    public List<Worker> getCurrentWorkersList() {
        return workersList;
    }

    public void setCurrentWorkersList(List<Worker> currentWorkersList) {
        this.workersList = currentWorkersList;
    }

    public Shift[][] getAssignmentsBoard() {
        return assignmentsBoard;
    }

    public void setAssignmentsBoard(Shift[][] assignmentsBoard) {
        this.assignmentsBoard = assignmentsBoard;
    }

    public void addWorker(Worker worker) {
        workersList.add(worker);
    }

    public Worker FindWorker(Worker worker){
        for(Worker w: workersList){
            if(w.getID().equals(worker.getID()))
                return w;
        }
        return null;
    }
}
