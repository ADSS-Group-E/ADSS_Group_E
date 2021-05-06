package BussinessLayer;

import BussinessLayer.DeliveryPackage.*;
import BussinessLayer.DriverPackage.Driver;
import BussinessLayer.WorkersPackage.Qualifications;
import BussinessLayer.WorkersPackage.Worker;
import BussinessLayer.WorkersPackage.WorkersFacade;
import PresentationLayer.*;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    public Response createWeeklyAssignment(int branchID, LocalDate startDate, WorkerDTO branchManager, List<Driver> drivers) {
        return wFacade.createWeeklyAssignment( branchID,  startDate,  branchManager, drivers);
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

    public void createWeeklyAssignment(int branchID, LocalDate startDate, List<Worker> workers, Worker branchManager,List<Driver> drivers) {
         wFacade.createWeeklyAssignment( branchID,  startDate, workers, branchManager, drivers);
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

    public Response setHiringConditions(HiringConditionsDTO hiringConditions, String WorkerID) {
        return wFacade.setHiringConditions(hiringConditions,WorkerID);
    }

    public Response setWorkerQualifications(List<QualificationsDTO> qualifications, String ID) {
        return wFacade.setWorkerQualifications( qualifications,  ID);
    }

    public Response isExistingWorker(String ID){
        return wFacade.isExistingWorker( ID);
    }

    public ResponseT<WorkerDTO> findWorkerBySerialNumber(int branchID, int serialNum){
        return wFacade.findWorkerBySerialNumber( branchID,  serialNum);
    }

    public Response setAvailableWorkDays(int branchID,WorkerDTO workerDTO, AvailableWorkDaysDTO availableWorkDaysDTO) {
        return wFacade.setAvailableWorkDays( branchID, workerDTO,  availableWorkDaysDTO);
    }

    public Response isLegalBranch(int branchID){
        return wFacade.isLegalBranch( branchID);
    }

    public Response isWorkerExist(String workerID){
        return wFacade.isWorkerExist( workerID);
    }

    public ResponseT<Integer> isAManagerOfBranch(String ID) {
        return wFacade.isAManagerOfBranch( ID);
    }

    public Response addQualification(String ID, QualificationsDTO q){
        return wFacade.addQualification( ID,  q);
    }

    public ResponseT<Boolean> isHRD(String id) {
        return wFacade.isHRD(id);
    }

    public Response displayWorkersByBranchID(int brID) {
        return wFacade.displayWorkersByBranchID(brID);
    }

    //////////////////////////////////////////////////////////////////////////

    public Response createDelivery(String id, Date deliveryDate, Time leavingTime, String driverId, String srcLocation, List<String> targets, String truckId, List<String> orders)
    {
       try{
           dFacade.createDelivery(id,deliveryDate,leavingTime,driverId,srcLocation,targets,truckId, orders);
       }catch(Exception e){
            return new Response(e.getMessage());
       }
       return new Response();
    }

    public Response getDelivery(String id)  {
        try{
            dFacade.getDelivery(id);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response getLocation(String id)  {
        try{
            dFacade.getLocation(id);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }



    public Response addDelivery(Delivery delivery)  {
        try{
            dFacade.addDelivery(delivery);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response removeDelivery(String id)  {
        try{
            dFacade.removeDelivery(id);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response updateDeliveryDate(String id, Date deliveryDay) {
        try{
            dFacade.updateDeliveryDate( id,  deliveryDay);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response updateLeavingTime(String id, Time leavingTime)  {
        try{
            dFacade.updateLeavingTime( id, leavingTime);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response updateDriverId(String id, String driverId)  {
        try{
            dFacade.updateDriverId(id,driverId);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response removeOrderAndLocation(String id, String locationId, String orderId) {
        try{
            dFacade.removeOrderAndLocation(id, locationId, orderId);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response addOrderAndLocation(String id, String locationId, String orderId){
        try{
            dFacade.addOrderAndLocation(id,locationId,orderId);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response updateWeight(String id, double weight){
        try{
            dFacade.updateWeight( id, weight);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response updateTruckId(String id, String truckId){
        try{
            dFacade.updateTruckId( id, truckId);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response updateStatus(String id, String status){
        try{
            dFacade.updateStatus( id, status);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response createOrder(String id, Map<String, Integer> items, String supplierId, String locationId, double totalWeight) {
        try{
            dFacade.createOrder( id, items,  supplierId,  locationId,  totalWeight);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Map<String, Order> getOrders()
    {
        return dFacade.getOrders();
    }


    public Response removeOrder(String id){
        try{
            dFacade.removeOrder(id);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response addItem(String id, String item, int quantity){
        try{
            dFacade.addItem( id,  item, quantity);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Response removeItem(String id, String item){
        try{
            dFacade.removeItem(id,item);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Response updateQuantity(String id, String item, int quantity){
        try{
            dFacade.updateQuantity(id,item,quantity);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Response updateTotalWeight(String id, double totalWeight) {
        try{
            dFacade.updateTotalWeight(id,totalWeight);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Response createLocation(String id, String name, String address, String telNumber, String contactName, String shippingArea) {
        try{
            dFacade.createLocation( id,  name,  address,  telNumber,  contactName,  shippingArea);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Response removeLocation(String id){
        try{
            dFacade.removeLocation(id);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response updateTelNumber(String id, String telNumber){
        try{
            dFacade.updateTelNumber( id,  telNumber);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Response updateContactName(String id, String contactName){
        try{
            dFacade.updateContactName( id,  contactName);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response createTruck(String id, String model, double netoWeight, double totalWeight){
        try{
            dFacade.createTruck( id,  model,  netoWeight,  totalWeight);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response addTruck(Truck truck){
        try{
            dFacade.addTruck(truck);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response removeTruck(String id){
        try{
            dFacade.removeTruck(id);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response createDriver(WorkerDTO w, String licenseType, Date expLicenseDate){
        try{
            dFacade.createDriver(wFacade.convertWorkerToBusiness(w),  licenseType,  expLicenseDate);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Response getDriver(String id)  {
        try{
            dFacade.getDriver(id);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Response removeDriver(String id){
        try{
            dFacade.removeDriver(id);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response updateExpDate(String id, Date expLicenseDate){
        try{
            dFacade.removeDriver(id);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Response updateLicenseType(String id, String licenseType){
        try{
            dFacade.updateLicenseType( id,  licenseType);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Response displayDeliveries(){
        try{
            dFacade.displayDeliveries();
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }
}
