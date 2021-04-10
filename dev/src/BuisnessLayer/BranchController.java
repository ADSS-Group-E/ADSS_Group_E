package BuisnessLayer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BranchController {
    private List<Branch> branchList;

    public BranchController(){
        branchList=new LinkedList<>();
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
        if(w==null)
            throw new IllegalArgumentException("Worker doesnt exist");
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
        if(getBranch(branchID)!=null)
            throw new IllegalArgumentException("Cant create a branch that is already exists");
        if(!branchManager.getQualifications().contains(Qualifications.BranchManager))
            throw new IllegalArgumentException("The worker you entered as branch manager is not qualified as a branch manager");
        if(!activeHRD.getQualifications().contains(Qualifications.Human_Resources_Director))
            throw new IllegalArgumentException("The worker you entered as HRD is not qualified as a HRD");
        if(branchManager.getID().equals(activeHRD.getID()))
            throw new IllegalArgumentException("The branch manager and the HRD can't be the same person");
        Branch branch=new Branch(branchID,branchManager,activeHRD);
        branchList.add(branch);
    }

    public void removeBranch(int branchID){
        if(getBranch(branchID)==null)
            throw new IllegalArgumentException("cant remove the branch you entered from BranchController because it is not exist");

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
    public void isWorkerExist(String workerID){
        if(findWorkerByID(workerID)==null)
            throw new IllegalArgumentException("The worker ID: "+ workerID +" is not exist in the system");
    }
    public void isCurrentWorker(String workerID){
        if(findWorkerByID(workerID)!=null)
            throw new IllegalArgumentException("The worker ID: "+ workerID +" already exist in the system");
    }


    public void addWorker(Worker worker,int branchID){
        if(getBranch(branchID)==null)
            throw new IllegalArgumentException("cant add worker to a branch that never been created");
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
                throw new IllegalArgumentException("Can't add this worker,there is already a worker with this ID but with different details");
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
        if(!isRemoved)
            throw new IllegalArgumentException("cant remove the worker because the worker is not in exist in this branch");

        if(isRemoved){
            getBranch(branchID).getFormerWorkers().add(worker);
        }
    }
    public List<Qualifications> getWorkerQualifications(Worker worker){
        return worker.getQualifications();

    }


    public void displayWorkersByBranchID(int brID) {
        if(getBranch(brID)==null)
            throw new IllegalArgumentException("Can't display workers of branch that isn't exist");
        System.out.println("The workers at branch "+brID+" are:");
        int index=1;
        System.out.println("1) The Branch manager is: " + "Name:"+getBranch(brID).getBranchManager().getFirstName()+" "+getBranch(brID).getBranchManager().getLastName()+" ID:"+getBranch(brID).getBranchManager().getID() + " Qualifications:"+getBranch(brID).getBranchManager().getQualifications() );
        System.out.println("2) The HRD is: " +"Name:"+getBranch(brID).getActiveHRD().getFirstName()+" "+getBranch(brID).getActiveHRD().getLastName()+" ID:"+getBranch(brID).getActiveHRD().getID() + " Qualifications:"+getBranch(brID).getActiveHRD().getQualifications());
        for(Worker worker:getBranch(brID).getWorkersList()){
            if(!getBranch(brID).getBranchManager().getID().equals(worker.getID()) && !getBranch(brID).getActiveHRD().getID().equals(worker.getID()) )
            System.out.println(index+") Name:"+worker.getFirstName()+" "+worker.getLastName()+" ID:"+worker.getID() + " Qualifications:"+worker.getQualifications());
            index++;
        }
    }

    public int isAManagerOfBranch(String id) {
        for(Branch branch : branchList){
            if(branch.getBranchManager().getID().equals(id))
                return branch.getBranchID();
        }
        throw new IllegalArgumentException("The worker is not an active manager");
    }
}
