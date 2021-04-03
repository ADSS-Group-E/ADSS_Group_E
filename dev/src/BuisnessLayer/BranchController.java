package BuisnessLayer;

import java.util.LinkedList;
import java.util.List;

public class BranchController {
    private static BranchController instance=null;
    private List<Branch> branchList;


    private BranchController(){
        branchList=new LinkedList<>();
    }

    public static BranchController getInstance(){
        if(instance==null){
            instance=new BranchController();
        }
        return instance;
    }

    public void addBranch(Branch branch){
        branchList.add(branch);
    }

    public void removeBranch(int branchID){
        for(Branch b : branchList){
            if(b.getBranchID()==branchID)
                branchList.remove(b);
        }
    }

    public Branch getBranch(int ID){
        for(Branch branch : branchList){
            if(branch.getBranchID()==ID)
                return branch;
        }
        return null;
    }

    public void addWorker(Worker worker,int branchID){
        if(getBranch(branchID).FindWorker(worker)!=null){
            getBranch(branchID).FindWorker(worker).setCurrentWorker(true);
        }else {
            worker.setCurrentWorker(true);
            getBranch(branchID).addWorker(worker);
        }
    }

    public void removeWorker(Worker worker,int branchID){
        if(getBranch(branchID).FindWorker(worker)!=null){
            getBranch(branchID).FindWorker(worker).setCurrentWorker(false);
        }
    }




}
