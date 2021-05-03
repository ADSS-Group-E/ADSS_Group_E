package PresentationLayer;

import BuisnessLayer.Shift;
import BuisnessLayer.Worker;

import java.util.List;

public class BranchDTO {
    private int branchID;
    private WorkerDTO branchManager;
    private WorkerDTO activeHRD;
    private List<ShiftDTO> weeklyAssignmentsHistory ;
    private List<WorkerDTO> workersList;
    private List<WorkerDTO> formerWorkers;
    private ShiftDTO[][] assignmentsBoard;

    public BranchDTO(int branchID, WorkerDTO branchManager, WorkerDTO activeHRD, List<ShiftDTO> weeklyAssignmentsHistory, List<WorkerDTO> workersList, List<WorkerDTO> formerWorkers, ShiftDTO[][] assignmentsBoard) {
        this.branchID = branchID;
        this.branchManager = branchManager;
        this.activeHRD = activeHRD;
        this.weeklyAssignmentsHistory = weeklyAssignmentsHistory;
        this.workersList = workersList;
        this.formerWorkers = formerWorkers;
        this.assignmentsBoard = assignmentsBoard;
    }

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }

    public WorkerDTO getBranchManager() {
        return branchManager;
    }

    public void setBranchManager(WorkerDTO branchManager) {
        this.branchManager = branchManager;
    }

    public WorkerDTO getActiveHRD() {
        return activeHRD;
    }

    public void setActiveHRD(WorkerDTO activeHRD) {
        this.activeHRD = activeHRD;
    }

    public List<ShiftDTO> getWeeklyAssignmentsHistory() {
        return weeklyAssignmentsHistory;
    }

    public void setWeeklyAssignmentsHistory(List<ShiftDTO> weeklyAssignmentsHistory) {
        this.weeklyAssignmentsHistory = weeklyAssignmentsHistory;
    }

    public List<WorkerDTO> getWorkersList() {
        return workersList;
    }

    public void setWorkersList(List<WorkerDTO> workersList) {
        this.workersList = workersList;
    }

    public List<WorkerDTO> getFormerWorkers() {
        return formerWorkers;
    }

    public void setFormerWorkers(List<WorkerDTO> formerWorkers) {
        this.formerWorkers = formerWorkers;
    }

    public ShiftDTO[][] getAssignmentsBoard() {
        return assignmentsBoard;
    }

    public void setAssignmentsBoard(ShiftDTO[][] assignmentsBoard) {
        this.assignmentsBoard = assignmentsBoard;
    }
}
