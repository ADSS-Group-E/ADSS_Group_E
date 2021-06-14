package BusinessLayer.Workers_Transport;

import BusinessLayer.Workers_Transport.DeliveryPackage.*;
import BusinessLayer.Workers_Transport.DriverPackage.Driver;
import BusinessLayer.Workers_Transport.WorkersPackage.Qualifications;
import BusinessLayer.Workers_Transport.WorkersPackage.Worker;
import BusinessLayer.Workers_Transport.WorkersPackage.WorkersFacade;
import DataAccessLayer.Workers_Transport.Transports.DTO;
import DataAccessLayer.Workers_Transport.Transports.Drivers;
import PresentationLayer.Workers_Transport.*;

import java.sql.SQLException;
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

    public Response addBranch(int branchID, WorkerDTO branchManager, WorkerDTO activeHRD,WorkerDTO logisticsManager) {
        return wFacade.addBranch(branchID, branchManager, activeHRD,logisticsManager);
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

    public Response createWeeklyAssignment(int branchID, LocalDate startDate, WorkerDTO branchManager/*, List<Driver> drivers*/) {
        return wFacade.createWeeklyAssignment( branchID,  startDate,  branchManager/*, drivers*/);
    }

    public Response workerReplacement(int branchID1, int branchID2, LocalDate date1,LocalDate date2, String shiftType1, String shiftType2, String worker1, String worker2) {
        return wFacade.workerReplacement( branchID1, branchID2, date1,  date2,  shiftType1,
                shiftType2,  worker1,  worker2);
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

    // display former workers at brID
    public Response displayFormerWorkersByBranchID(int brID){
        return wFacade.displayFormerWorkersByBranchID(brID);
    }

    public ResponseT<List<QualificationsDTO>> getWorkerQualifications(WorkerDTO worker) {
        return wFacade.getWorkerQualifications(worker);
    }

    public Response printWorker(WorkerDTO worker) {
        return wFacade.printWorker(worker);
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


    public Response setWorkerLastName(String ID, String lastName) {
        return wFacade.setWorkerLastName( ID,  lastName);
    }


    public Response setBankAccount(String WorkerID,BankAccountDTO bankAccount) {
        return wFacade.setBankAccount(WorkerID,bankAccount);
    }

    public Response setHiringConditions(String WorkerID,HiringConditionsDTO hiringConditions) {
        return wFacade.setHiringConditions(WorkerID,hiringConditions);
    }

    public Response setWorkerQualifications(String ID,List<QualificationsDTO> qualifications) {
        return wFacade.setWorkerQualifications(ID,qualifications);
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

    public ResponseT<Boolean> isWorkerExist(String workerID){
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



    public Response createDelivery(String id, Date deliveryDate, Time leavingTime, String driverId, int srcLocation, List<Integer> targets, String truckId, List<Integer> orders)
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

    public Response getLocation(int id)  {
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

    public Response removeOrderAndLocation(String id, Integer locationId, Integer orderId) {
        try{
            dFacade.removeOrderAndLocation(id, locationId, orderId);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response addOrderAndLocation(String id, int locationId, int orderId){
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

    public Response createOrder(int id, Map<String, Integer> items, String supplierId, Integer locationId, double totalWeight) {
        try{
            dFacade.createOrder( id, items,  supplierId,  locationId,  totalWeight);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Map<Integer, Order> getOrders()
    {
        return dFacade.getOrders();
    }


    public Response removeOrder(Integer id){
        try{
            dFacade.removeOrder(id);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response addItem(Integer id, String item, int quantity){
        try{
            dFacade.addItem( id,  item, quantity);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Response removeItem(Integer id, String item){
        try{
            dFacade.removeItem(id,item);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Response updateQuantity(Integer id, String item, int quantity){
        try{
            dFacade.updateQuantity(id,item,quantity);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Response updateTotalWeight(Integer id, double totalWeight) {
        try{
            dFacade.updateTotalWeight(id,totalWeight);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Response createLocation(int id, String name, String address, String telNumber, String contactName, String shippingArea) {
        try{
            dFacade.createLocation( id,  name,  address,  telNumber,  contactName,  shippingArea);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Response removeLocation(int id){
        try{
            dFacade.removeLocation(id);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response updateTelNumber(Integer id, String telNumber){
        try{
            dFacade.updateTelNumber( id,  telNumber);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Response updateContactName(Integer id, String contactName){
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

    public Response createDriver(DTO.Driver driver, int branchID){
        try{
            dFacade.createDriver(driver, branchID);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Response getDriver(String id)  {
        try{
            //dFacade.getDriver(id);
            Drivers.getDriver(id);
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

    public ResponseT<Integer> findBranchIDByWorker(String id) {
        return wFacade.findBranchIDByWorker(id);
    }

    public ResponseT<Boolean> isBranchExists(int branchID){
        return wFacade.isBranchExists(branchID);
    }

    public Response removeWorker(String hrd_id) {
        return wFacade.removeWorker(hrd_id);
    }

    public ResponseT<WorkerDTO> getBranchManager(int branchID) {
        return wFacade.getBranchManager(branchID);
    }

    public ResponseT<WorkerDTO> getBranchHRD(int branchID){
        return wFacade.getBranchHRD(branchID);
    }

    public Response changeWorkerBranch(String workerID, int newBranchID) {
        return wFacade.changeWorkerBranch(workerID,newBranchID);
    }

    public ResponseT<Integer> getBranchID(String workerID){
        return wFacade.getBranchID(workerID);
    }

    public Response getBackToWork(String workerID) {
        return wFacade.getBackToWork(workerID);
    }







}
