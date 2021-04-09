package BuisnessLayer;

import java.util.ArrayList;
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


    public Worker findWorker(int branchID,String workerID){
        Branch branch=getBranch(branchID);
        return branch.FindWorker(workerID);
    }
    public Worker findWorkerByID(String ID){
        Worker w=null;
        for(Branch branch : branchList){
            w = findWorker(branch.getBranchID(),ID);
            if(w!=null)
                break;
        }
        return w;
    }

    public Branch findBranchByWorker(String WorkerID){
        Worker w=null;
        Branch branchByWorker=null;
        for(Branch branch : branchList){
            w = findWorker(branch.getBranchID(),WorkerID);
            if(w!=null){
                branchByWorker = branch;
                break;}
        }
        return branchByWorker;
    }
    public void showWorkers(int branchID){
        getBranch(branchID).showWorkers();
    }

    public void addBranch(int branchID,Worker branchManager,Worker activeHRD){
        if(!branchManager.getQualifications().contains(Qualifications.BranchManager))
            throw new IllegalArgumentException("The worker you entered is not qualified as a branch manager");
        if(!activeHRD.getQualifications().contains(Qualifications.Human_Resources_Director))
            throw new IllegalArgumentException("The worker you entered is not qualified as a HRD");
        Branch branch=new Branch(branchID,branchManager,activeHRD);
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

    public void isLegalBranch(int branchID){
        if(getBranch(branchID)==null)
            throw new IllegalArgumentException("The branch ID: "+ branchID +" is not exist in the system");
    }
    public void isLegalWorker(String workerID){
        if(findWorkerByID(workerID)==null)
            throw new IllegalArgumentException("The worker ID: "+ workerID +" is not exist in the system");
    }


    public void addWorker(Worker worker,int branchID){
        boolean found=false;
        Worker w1 = getBranch(branchID).FindWorker(worker.getID());
        if(w1!=null&& !worker.equals(w1))
            throw new IllegalArgumentException("Different workers with same ID");
        else if(w1!=null&& worker.equals(w1)){
            System.out.println("You tried to add a worker that is already exists so we just update the available days of it");
            w1.setAvailableWorkDays(worker.getAvailableWorkDays());
        }else {
            isLegalBranch(branchID);
            w1 = getBranch(branchID).FindFormerWorker(worker.getID());
            if(w1!=null&& !worker.equals(w1))
                throw new IllegalArgumentException("Different workers with same ID");
            else if(w1!=null&& worker.equals(w1)) {
                getBranch(branchID).getFormerWorkers().remove(w1);
                getBranch(branchID).addWorker(worker);
            }
            else if(w1==null)
                getBranch(branchID).addWorker(worker);

        }


    }


    public void removeWorker(Worker worker,int branchID){
        Boolean isRemoved=getBranch(branchID).getWorkersList().remove(worker);
        if(isRemoved){
            getBranch(branchID).getFormerWorkers().add(worker);
        }
    }
    public List<Qualifications> getWorkerQualifications(Worker worker){
        return worker.getQualifications();

    }



}
