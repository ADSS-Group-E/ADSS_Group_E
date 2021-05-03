package BussinessLayer;

import BussinessLayer.DeliveryPackage.DeliveryFacade;
import BussinessLayer.WorkersPackage.Response;
import BussinessLayer.WorkersPackage.ResponseT;
import BussinessLayer.WorkersPackage.Worker;
import BussinessLayer.WorkersPackage.WorkersFacade;
import PresentationLayer.*;

import java.time.LocalDate;
import java.util.List;

public class Facade {
    private WorkersFacade wFacade=WorkersFacade.getInstance();
    private DeliveryFacade dFacade=DeliveryFacade.getInstance();

    public Response addShiftDemands(int branchID, LocalDate date, ShiftTypeDTO shiftType, ShiftDemandsDTO shiftDemands){
        return wFacade.addShiftDemands(branchID, date, shiftType, shiftDemands);
    }

    public Response resetShiftDemands(int branchID) {
        return wFacade.resetShiftDemands(branchID);
    }

    public Response addBranch(int branchID, WorkerDTO branchManager, WorkerDTO activeHRD) {
        return wFacade.addBranch(branchID, branchManager, activeHRD);
    }

    public ResponseT<BranchDTO> getBranch(int ID) {
        return wFacade.getBranch(ID);
    }

    public Response addWorker(WorkerDTO worker, int branchID) {
        return wFacade.addWorker( worker,  branchID);
    }

    public Response removeWorker(WorkerDTO worker, int branchID) {
        return wFacade.removeWorker( worker,  branchID);
    }

    public Response removeBranch(int branchID) {
        return wFacade.removeBranch( branchID);
    }

    public ResponseT<ShiftDTO> getShift(int branchID, LocalDate date, ShiftTypeDTO shiftType) {
        return wFacade.getShift( branchID,  date,  shiftType);
    }

    public Response createWeeklyAssignment(int branchID, LocalDate startDate, WorkerDTO branchManager) {
        return wFacade.createWeeklyAssignment( branchID,  startDate,  branchManager);
    }

    public Response workerReplacement(int branchID, LocalDate date1, ShiftTypeDTO shiftType1, LocalDate date2, ShiftTypeDTO shiftType2, WorkerDTO worker1, WorkerDTO worker2, WorkerDTO branchManager) {
        return wFacade.workerReplacement( branchID,  date1,  shiftType1,  date2,  shiftType2,  worker1,  worker2,  branchManager);
    }

    public ResponseT<WorkerDTO> findDTOWorker(int branchID, String workerID) {
        return wFacade.findDTOWorker( branchID,  workerID);
    }
    public Worker findBusinessWorker(int branchID, String workerID) {
        return wFacade.findBusinessWorker( branchID,  workerID);
    }

    public ResponseT<WorkerDTO> findDTOWorkerByID(String workerID) {
        return wFacade.findDTOWorkerByID( workerID);
    }

    public Response printWorkersAtShift(int branchID, LocalDate date, ShiftTypeDTO shiftType) {
        return wFacade.printWorkersAtShift( branchID,  date,  shiftType);
    }

    public Response showWorkers(int branchID) {
        return wFacade.showWorkers( branchID);
    }

    public ResponseT<List<QualificationsDTO>> getWorkerQualifications(WorkerDTO worker) {
        return wFacade.getWorkerQualifications(worker);
    }

    public Response printWorker(WorkerDTO worker) {
        return wFacade.getWorkerQualifications(worker);
    }

    public ResponseT<WorkerDTO> getShiftManager(ShiftDTO shift) {
        return wFacade.getShiftManager(shift);
    }

    public Response printWorkersByQualification(QualificationsDTO qualifications, ShiftDTO shift) {
        return wFacade.printWorkersByQualification( qualifications,  shift);
    }

    public void createWeeklyAssignment(int branchID, LocalDate startDate, List<Worker> workers, Worker branchManager) {
         wFacade.createWeeklyAssignment( branchID,  startDate, workers, branchManager);
    }

    public Response printWeeklyAssignment(int branchID, LocalDate date) {
        return wFacade.printWeeklyAssignment( branchID,  date);
    }

    public ResponseT<BranchDTO> findBranchByWorker(WorkerDTO worker) {
        return wFacade.findBranchByWorker(worker);
    }

    public Response setWorkerFirstName(String WorkerID,String newFirstName) {
        return wFacade.setWorkerFirstName( WorkerID, newFirstName);
    }


    public Response setWorkerLastName(String lastName, String ID) {
        return wFacade.setWorkerLastName( lastName,  ID);
    }


    public Response setBankAccount(BankAccountDTO bankAccount, String WorkerID) {
        return wFacade.setBankAccount( bankAccount,  WorkerID);
    }
}
