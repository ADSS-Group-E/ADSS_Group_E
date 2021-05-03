package BuisnessLayer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Branch {

    private int branchID;
    private Worker branchManager;
    private Worker activeHRD;
    private List<Shift> weeklyAssignmentsHistory ;
    private List<Worker> workersList;
    private List<Worker> formerWorkers;
    private Shift[][] assignmentsBoard;

    public List<Worker> getWorkersList() {
        return workersList;
    }

    public void setWorkersList(List<Worker> workersList) {
        this.workersList = workersList;
    }

    public List<Worker> getFormerWorkers() {
        return formerWorkers;
    }

    public void setFormerWorkers(List<Worker> formerWorkers) {
        this.formerWorkers = formerWorkers;
    }

    public Branch(int branchID, Worker branchManager, Worker activeHRD){
        this.branchID=branchID;
        this.branchManager=branchManager;
        this.activeHRD=activeHRD;
        weeklyAssignmentsHistory=new LinkedList<>();
        workersList=new LinkedList<>();
        formerWorkers=new LinkedList<>();
        assignmentsBoard=new Shift[7][2];
        for(int i=0;i<7;i++){
            for(int j=0;j<2;j++){
                assignmentsBoard[i][j]=null;
            }
        }
    }


    public Branch(int branchID, Worker branchManager, Worker activeHRD, List<Shift> weeklyAssignmentsHistory, List<Worker> workersList, Shift[][] assignmentsBoard) {
        this.branchID = branchID;
        if(!branchManager.getQualifications().contains(Qualifications.BranchManager))
            branchManager.getQualifications().add(Qualifications.BranchManager);
        this.branchManager = branchManager;

        if(!activeHRD.getQualifications().contains(Qualifications.Human_Resources_Director))
            activeHRD.getQualifications().add(Qualifications.Human_Resources_Director);
        this.activeHRD = activeHRD;

        this.weeklyAssignmentsHistory = weeklyAssignmentsHistory;
        this.workersList = workersList;

        this.assignmentsBoard=new Shift[7][2];
        for(int i=0;i<assignmentsBoard.length;i++){
            for(int j=0;j<assignmentsBoard[i].length;j++){
                this.assignmentsBoard[i][j]=assignmentsBoard[i][j];
            }
        }

        this.assignmentsBoard = assignmentsBoard;
        this.formerWorkers=new ArrayList<>();
    }

    public Branch(int branchID, Worker branchManager, Worker activeHRD, List<Shift> weeklyAssignmentsHistory, List<Worker> workersList, List<Worker> formerWorkers, Shift[][] assignmentsBoard) {
        this.branchID = branchID;
        if(!branchManager.getQualifications().contains(Qualifications.BranchManager))
            branchManager.getQualifications().add(Qualifications.BranchManager);
        this.branchManager = branchManager;

        if(!activeHRD.getQualifications().contains(Qualifications.Human_Resources_Director))
            activeHRD.getQualifications().add(Qualifications.Human_Resources_Director);
        this.activeHRD = activeHRD;

        this.weeklyAssignmentsHistory = weeklyAssignmentsHistory;
        this.workersList = workersList;

        this.assignmentsBoard=new Shift[7][2];
        for(int i=0;i<assignmentsBoard.length;i++){
            for(int j=0;j<assignmentsBoard[i].length;j++){
                this.assignmentsBoard[i][j]=assignmentsBoard[i][j];
            }
        }

        this.assignmentsBoard = assignmentsBoard;
        this.formerWorkers = formerWorkers;
    }

    public void showWorkers(){
        int i=1;
        for(Worker worker : workersList){
            System.out.println(i+")"+"name:"+worker.getFirstName()+" "+worker.getLastName()+" ID:"+worker.getID());
            i++;
        }
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

    public Worker FindWorker(String workerID) {
        for(Worker worker : workersList){
            if(worker.getID().equals(workerID))
                return worker;
        }
        return null;
    }
    public Worker FindFormerWorker(String workerID) {
        for(Worker worker : formerWorkers){
            if(worker.getID().equals(workerID))
                return worker;
        }
        return null;
    }
    public void fireWorker(Worker worker){
        if(workersList.remove(worker))
            formerWorkers.add(worker);
    }
}
