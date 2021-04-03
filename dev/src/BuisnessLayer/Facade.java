package BuisnessLayer;

import java.time.LocalDate;
import java.util.List;

public class Facade {

    private BranchController branchController;
    private ShiftController shiftController;

    public Facade() {
        this.branchController = BranchController.getInstance();
        this.shiftController = ShiftController.getInstance();
    }

    public void addBranch(Branch branch){
        branchController.addBranch(branch);
        shiftController.addBranch(branch.getBranchID());
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

    public List<Shift> getHistory(){
        return  null;
    }

    public void createWeeklyAssignment(int branchID, LocalDate startDate, ShiftDemands[][] shiftDemands, List<Worker>workers , Worker shiftManager){
        shiftController.createWeeklyAssignment(branchID,startDate,shiftDemands,workers,shiftManager);
    }



}
