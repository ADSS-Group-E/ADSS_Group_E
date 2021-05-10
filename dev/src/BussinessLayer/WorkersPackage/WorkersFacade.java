package BussinessLayer.WorkersPackage;

import BussinessLayer.DriverPackage.Driver;
import BussinessLayer.Response;
import BussinessLayer.ResponseT;
import DataAccessLayer.Repo;
import DataAccessLayer.Workers.Shifts;
import DataAccessLayer.Workers.Workers;
import PresentationLayer.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class WorkersFacade {

    private final BranchController branchController;
    private final ShiftController shiftController;
    private static WorkersFacade instance = null;

    private WorkersFacade() {
        this.branchController =new BranchController();
        this.shiftController = new ShiftController();
    }

    public static WorkersFacade getInstance() {
        if (instance == null) {
            instance = new WorkersFacade();
        }
        return instance;
    }

    /*public void addShiftDemands(int branchID, ShiftDemandsDTO[][] shiftDemandsDTO) {
        ShiftDemands[][] shiftDemands = new ShiftDemands[7][2];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                shiftDemands[i][j] = convertShiftDemandsToBusiness(shiftDemandsDTO[i][j]);
            }
            shiftController.addShiftDemands(branchID, shiftDemands);
        }
    }
*/
    public Response addShiftDemands(int branchID, LocalDate date, ShiftTypeDTO shiftType, ShiftDemandsDTO shiftDemands) {
        try {
            //shiftController.addShiftDemands(branchID, date, convertShiftTypeToBusiness(shiftType), convertShiftDemandsToBusiness(shiftDemands));
            Shifts.insertShiftDemands(date,shiftType.name(),branchID,shiftDemands.getCashierAmount(),shiftDemands.getStoreKeeperAmount(),shiftDemands.getArrangerAmount(),shiftDemands.getGuardAmount(),shiftDemands.getAssistantAmount(),shiftDemands.getDeliveryRequired()==true ? 1 : 0);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response resetShiftDemands(int branchID) {
        shiftController.resetShiftDemands(branchID);
        return new Response();
    }

    public Response addBranch(int branchID, WorkerDTO branchManager, WorkerDTO activeHRD) {
        try {
             branchController.addBranch(branchID, convertWorkerToBusiness(branchManager), convertWorkerToBusiness(activeHRD));
             shiftController.addBranch(branchID);
             addWorker(branchManager, branchID);
            addWorker(activeHRD, branchID);
            Workers.insertWorker(convertWorkerToBusiness(branchManager),branchID);
            Workers.insertWorker(convertWorkerToBusiness(activeHRD),branchID);
            Workers.addBranch(branchID,branchManager.getID(),activeHRD.getID());
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public ResponseT<BranchDTO> getBranch(int ID) {
        if(branchController.getBranch(ID)==null)
            return new ResponseT<BranchDTO>(null,"The branch you searched is not exist");
        return new ResponseT<BranchDTO>(convertBranchToDTO(branchController.getBranch(ID)));
    }

    public Response addWorker(WorkerDTO worker, int branchID) {
        try {
            //branchController.addWorker(convertWorkerToBusiness(worker), branchID);
            Workers.insertWorker(convertWorkerToBusiness(worker),branchID);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response removeWorker(WorkerDTO worker, int branchID) {
        try{
        branchController.removeWorker(convertWorkerToBusiness(worker), branchID);
        Workers.removeWorker(worker.getID());
        }catch(Exception e){
           return new Response(e.getMessage());
        }
        return new Response();
    }


    public Response removeBranch(int branchID) {
        try {
            branchController.removeBranch(branchID);
            shiftController.removeBranch(branchID);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

  /*  public List<ShiftDTO> getHistory(int branchID) {
        return convertListOfShiftsToDTO(branchController.getBranch(branchID).getWeeklyAssignmentsHistory());
    }

   */

    public ResponseT<ShiftDTO> getShift(int branchID, LocalDate date, ShiftTypeDTO shiftType) {
        try {
            shiftController.getShift(branchID, date, convertShiftTypeToBusiness(shiftType));
            convertShiftToDTO(shiftController.getShift(branchID, date, convertShiftTypeToBusiness(shiftType)));
        }catch(Exception e){
            return new ResponseT<ShiftDTO>(convertShiftToDTO(shiftController.getShift(branchID, date, convertShiftTypeToBusiness(shiftType))),e.getMessage());
        }
        return new ResponseT<ShiftDTO>(convertShiftToDTO(shiftController.getShift(branchID, date, convertShiftTypeToBusiness(shiftType))));
    }

    public Response createWeeklyAssignment(int branchID, LocalDate startDate, WorkerDTO branchManager, List<Driver> drivers) {
        try {
            //shiftController.createWeeklyAssignment(branchID, startDate, branchController.getBranch(branchID).getWorkersList(), convertWorkerToBusiness(branchManager), drivers);
           // Shifts.createWeeklyAssignment(branchID,startDate,branchManager,drivers);
            Shifts.createWeeklyAssignment(branchID,startDate,convertWorkerToBusiness(branchManager),Workers.getWorkersAtBranch(branchID),Workers.WorkersOfQualificationAtBranch(branchID,Qualifications.ShiftManager),);
            Shifts.createShiftAssignment(startDate,ShiftType.Morning,branchID,Workers.getWorkersAtBranch(branchID),convertWorkerToBusiness(branchManager), null);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response workerReplacement(int branchID, LocalDate date1, ShiftTypeDTO shiftType1, LocalDate date2, ShiftTypeDTO shiftType2, WorkerDTO worker1, WorkerDTO worker2, WorkerDTO branchManager) {
        try {
            if(branchController.getBranch(branchID).FindWorker(convertWorkerToBusiness(worker1))==null)
                return new Response("The first worker is not from branchID"+ branchID);
            if(branchController.getBranch(branchID).FindWorker(convertWorkerToBusiness(worker2))==null)
                return new Response("The second worker is not from branchID"+ branchID);
            shiftController.workerReplacement(branchID, date1, convertShiftTypeToBusiness(shiftType1), date2, convertShiftTypeToBusiness(shiftType2), convertWorkerToBusiness(worker1), convertWorkerToBusiness(worker2), convertWorkerToBusiness(branchManager));
        }catch (Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public ResponseT<WorkerDTO> findDTOWorker(int branchID, String workerID) {
        try {
            branchController.findWorker(branchID, workerID);
            convertWorkerToDTO(branchController.findWorker(branchID, workerID));
        }catch(Exception e){
            return new ResponseT<>(null,e.getMessage());
        }
        return new ResponseT<>(convertWorkerToDTO(branchController.findWorker(branchID, workerID)));
    }
    public Worker findBusinessWorker(int branchID, String workerID) {
        return branchController.findWorker(branchID, workerID);
    }

    public ResponseT<WorkerDTO> findDTOWorkerByID(String workerID) {
        try{
            //convertWorkerToDTO(branchController.findWorkerByID(workerID));
            //convertWorkerToDTO(Workers.getWorker(workerID));
            return new ResponseT<>(convertWorkerToDTO(Workers.getWorker(workerID)));
        }catch(Exception e){
            return new ResponseT<>(null,e.getMessage());
        }
        //return new ResponseT<>(convertWorkerToDTO(branchController.findWorkerByID(workerID)));
       // return new ResponseT<>(convertWorkerToDTO(branchController.findWorkerByID(workerID)));
    }
    private Worker findBusinessWorkerByID(String workerID) {
        return branchController.findWorkerByID(workerID);
    }

    public Response printWorkersAtShift(int branchID, LocalDate date, ShiftTypeDTO shiftType) {
        try{
            shiftController.printWorkersAtShift(branchID, date,convertShiftTypeToBusiness(shiftType));
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response showWorkers(int branchID) {
        try {
            //branchController.showWorkers(branchID);
            Workers.displayWorkersByBranchID(branchID);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public static Response displayFormerWorkersByBranchID(int brID){
        try {
            //branchController.showWorkers(branchID);
            Workers.displayFormerWorkersByBranchID(brID);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public ResponseT<List<QualificationsDTO>> getWorkerQualifications(WorkerDTO worker) {
        try{
            branchController.getWorkerQualifications(convertWorkerToBusiness(worker));
            List<String> names=Workers.getQualificationsFromDB(worker.getID());
            List<Qualifications>qualifications=new LinkedList<>();
            for(Qualifications q : Qualifications.values()){
                if(names.contains(q.name()))
                    qualifications.add(q);
            }
            return new ResponseT<>(convertListQualificationsToDTO(qualifications));
            //convertListQualificationsToDTO(branchController.getWorkerQualifications(convertWorkerToBusiness(worker)));
        }catch(Exception e){
            return new ResponseT<>(null,e.getMessage());
        }
        //return new ResponseT<>(convertListQualificationsToDTO(branchController.getWorkerQualifications(convertWorkerToBusiness(worker))));
    }

    public Response printWorker(WorkerDTO worker) {
        try {
            System.out.println(convertWorkerToBusiness(worker).toString());
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    /*public List<Worker> getQualification(Shift shift, Qualifications qualifications) {
        return shift.getWorkers().get(qualifications);
    }*/

    public ResponseT<WorkerDTO> getShiftManager(ShiftDTO shift) {
        try{
            shift.getShiftManager();
        }catch(Exception e){
            return new ResponseT<>(shift.getShiftManager(),e.getMessage());
        }
        return new ResponseT<>(shift.getShiftManager());
    }

    public Response printWorkersByQualification(QualificationsDTO qualifications, ShiftDTO shift) {
        try {
            convertShiftToBusiness(shift).printWorkersByQualification(convertQualificationsToBusiness(qualifications));
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public ShiftDemandsDTO[][] getShiftDemandsDTO(int branchID, LocalDate date_) {
        ShiftDemandsDTO[][] shiftDemandsDTOS = new ShiftDemandsDTO[7][2];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                shiftDemandsDTOS[i][j] = new ShiftDemandsDTO(shiftController.getShiftDemands(branchID, date_)[i][j]);
            }
        }

        return shiftDemandsDTOS;
    }

    public ShiftDemandsDTO getShiftDemandsDTO(BussinessLayer.WorkersPackage.ShiftDemands blShiftDemands) {
        LocalDate date = blShiftDemands.getDate();
        int cashierAmount = blShiftDemands.getCashierAmount();
        int storeKeeperAmount = blShiftDemands.getStoreKeeperAmount();
        int arrangerAmount = blShiftDemands.getArrangerAmount();
        int guardAmount = blShiftDemands.getGuardAmount();
        int assistantAmount = blShiftDemands.getAssistantAmount();

        return new ShiftDemandsDTO(date, cashierAmount, storeKeeperAmount, arrangerAmount, guardAmount, assistantAmount);
    }

    public void createWeeklyAssignment(int branchID, LocalDate startDate, List<Worker> workers, Worker branchManager, List<Driver> driver) {
        shiftController.createWeeklyAssignment(branchID, startDate, workers, branchManager, driver);
    }

    public Response printWeeklyAssignment(int branchID, LocalDate date) {
        try {
            shiftController.printWeeklyAssignment(branchID, date);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public ResponseT<BranchDTO> findBranchByWorker(WorkerDTO worker) {
        try{
            branchController.findBranchByWorker(worker.getID());
            convertBranchToDTO(branchController.findBranchByWorker(worker.getID()));
        }catch(Exception e){
            return new ResponseT<>(null,e.getMessage());
        }
        return new ResponseT<>(convertBranchToDTO(branchController.findBranchByWorker(worker.getID())));
    }

    public ResponseT<Integer> findBranchIDByWorker(String workerID) {
        try{
            return new ResponseT<>(Workers.findBranchIDByWorker(workerID));
        }catch(Exception e){
            return new ResponseT<>(null,e.getMessage());
        }
    }

    public ResponseT<Boolean> isBranchExists(int branchID){
        try{
            return new ResponseT<>(Workers.isBranchExists(branchID));
        }catch(Exception e){
            return new ResponseT<>(null,e.getMessage());
        }
    }

    public Response setWorkerFirstName(String WorkerID,String newFirstName) {
        try{
            Workers.updateFirstName(WorkerID,newFirstName);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Response setWorkerLastName(String ID, String lastName) {
        try{
            Workers.updateLastName(ID,lastName);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Response setBankAccount(String WorkerID,BankAccountDTO bankAccount) {
        try{
            Workers.updateBankAccount(WorkerID,bankAccount.getBankName(),bankAccount.getBranch(),bankAccount.getBankAccount());
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public ResponseT<BankAccountDTO> getWorkerBankAccount(String WorkerID) {
      BankAccountDTO bankAccount = null;
        try{
            bankAccount = convertBankAccountToDTO(findBusinessWorkerByID(WorkerID).getBankAccount());
        }
        catch (Exception e){
            return new ResponseT<>(null,e.getMessage());
        }
        return new ResponseT<>(bankAccount);
    }

    public Response setHiringConditions( String WorkerID,HiringConditionsDTO hiringConditions) {
        try{
            Workers.updateHiringConditions(WorkerID,hiringConditions.getSalaryPerHour(),hiringConditions.getFund(),hiringConditions.getVacationDays(),hiringConditions.getSickLeavePerMonth());
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public ResponseT<HiringConditionsDTO> getWorkerHiringConditions(String WorkerID) {
        HiringConditionsDTO hiringConditionsDTO;
        try{
            hiringConditionsDTO = convertHiringConditionsToDTO(findBusinessWorkerByID(WorkerID).getHiringConditions());
        }
        catch (Exception e){
            return new ResponseT<>(null,e.getMessage());
        }
        return new ResponseT<>(hiringConditionsDTO);
    }

    public ResponseT<String> getWorkerFirstName(String WorkerID) {
        String name;
        try{
             name = findBusinessWorkerByID(WorkerID).getFirstName();
        }catch(Exception e){
            return new ResponseT<>(null,e.getMessage());
        }
       return new ResponseT<>(findBusinessWorkerByID(WorkerID).getFirstName());
    }

    public ResponseT<String> getWorkerLastName(String ID) {
        String name;
        try{
            name = findBusinessWorkerByID(ID).getLastName();
        }catch(Exception e){
            return new ResponseT<>(null,e.getMessage());
        }
        return new ResponseT<>(name);
    }
    public Response setWorkerQualifications(String ID,List<QualificationsDTO> qualifications) {
        try{
            Workers.updateQualifications(ID,convertListQualificationsToBusiness(qualifications));
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response isExistingWorker(String ID){
        try{
            Workers.isWorkerExist(ID);
            return new Response();
        }catch(Exception e){
            return new Response(e.getMessage());
        }
    }

    public ResponseT<WorkerDTO> findWorkerBySerialNumber(int branchID, int serialNum){
        try{
            branchController.getBranch(branchID).getWorkersList().get(serialNum);
        }catch(Exception e){
            return new ResponseT<>(null,e.getMessage());
        }
        return new ResponseT<>(convertWorkerToDTO(branchController.getBranch(branchID).getWorkersList().get(serialNum)));
    }


    private AvailableWorkDaysDTO convertAvailableWorkDaysToDTO(AvailableWorkDays availableWorkDays){
        return new AvailableWorkDaysDTO(availableWorkDays.getFavoriteShifts(),availableWorkDays.getCantWork());
    }
    private AvailableWorkDays convertAvailableWorkDaysToBusiness(AvailableWorkDaysDTO availableWorkDays){
        return new AvailableWorkDays(availableWorkDays.getFavoriteShifts(), availableWorkDays.getCantWork());
    }


    private BankAccountDTO convertBankAccountToDTO(BankAccount bankAccount){
        return new BankAccountDTO(bankAccount.getBankName(), bankAccount.getBranch(), bankAccount.getBankAccount());
    }

    private BankAccount convertBankAccountToBusiness(BankAccountDTO bankAccountDTO){
        return new BankAccount(bankAccountDTO.getBankName(), bankAccountDTO.getBranch(), bankAccountDTO.getBankAccount());
    }

    private List<WorkerDTO> convertListOfWorkersToDTO(List<Worker> workers){
      if(workers == null)
          return null;
        List<WorkerDTO> list=new ArrayList<>();
        for(Worker worker: workers)
            list.add(convertWorkerToDTO(worker));
        return list;
    }

    private List<ShiftDTO> convertListOfShiftsToDTO(List<Shift> shifts){
        if(shifts == null)
            return null;
        List<ShiftDTO> list=new ArrayList<>();
        for(Shift shift: shifts)
            list.add(convertShiftToDTO(shift));
        return list;
    }

    private List<Shift> convertListOfShiftsToBusiness(List<ShiftDTO> shifts){
        if(shifts == null)
            return null;
        List<Shift> list=new ArrayList<>();
        for(ShiftDTO shift: shifts)
            list.add(convertShiftToBusiness(shift));
        return list;
    }

    private List<Worker> convertListOfWorkersToBusiness(List<WorkerDTO> workers){
        if(workers == null)
            return null;
        List<Worker> list=new ArrayList<>();
        for(WorkerDTO worker: workers)
            list.add(convertWorkerToBusiness(worker));
        return list;
    }

    private ShiftDTO[][] convertShiftDArrayToDTO(Shift[][] shifts){
        if(shifts == null)
            return null;
        ShiftDTO[][] shiftDTOS=new ShiftDTO[7][2];
        for(int i=0;i<shifts.length;i++){
            for(int j=0;j<shifts[i].length;j++){
                if(shifts[i][j]!=null)
                  shiftDTOS[i][j]=convertShiftToDTO(shifts[i][j]);
                else shiftDTOS[i][j]=null;
            }
        }
        return shiftDTOS;
    }

    private Shift[][] convertShiftDArrayToBusiness(ShiftDTO[][] shiftDTOS){
        if(shiftDTOS == null)
            return null;
        Shift[][] shift=new Shift[7][2];
        for(int i=0;i<shiftDTOS.length;i++){
            for(int j=0;j<shiftDTOS[i].length;i++){
                shift[i][j]=convertShiftToBusiness(shiftDTOS[i][j]);
            }
        }
        return shift;
    }

    private BranchDTO convertBranchToDTO(Branch branch){
        if(branch == null)
            return null;
        List<ShiftDTO>weeklyAssignmentHistory=new ArrayList<>();
        for(Shift shift : branch.getWeeklyAssignmentsHistory()){
            weeklyAssignmentHistory.add(convertShiftToDTO(shift));
        }
        return new BranchDTO(branch.getBranchID(),convertWorkerToDTO(branch.getBranchManager()), convertWorkerToDTO(branch.getActiveHRD()),weeklyAssignmentHistory, convertListOfWorkersToDTO(branch.getWorkersList()),convertListOfWorkersToDTO(branch.getFormerWorkers()),convertShiftDArrayToDTO(branch.getAssignmentsBoard()));
    }



    private Branch convertBranchToBusiness(BranchDTO branchDTO){
        if(branchDTO == null)
            return null;
        return new Branch(branchDTO.getBranchID(),convertWorkerToBusiness(branchDTO.getBranchManager()),convertWorkerToBusiness(branchDTO.getActiveHRD()),convertListOfShiftsToBusiness(branchDTO.getWeeklyAssignmentsHistory()),convertListOfWorkersToBusiness(branchDTO.getWorkersList()),convertListOfWorkersToBusiness(branchDTO.getFormerWorkers()),convertShiftDArrayToBusiness(branchDTO.getAssignmentsBoard()));
    }

    private HiringConditionsDTO convertHiringConditionsToDTO(HiringConditions hiringConditions){
        if(hiringConditions == null)
            return null;
        return new HiringConditionsDTO(hiringConditions.getSalaryPerHour(), hiringConditions.getFund(), hiringConditions.getVacationDays(), hiringConditions.getSickLeavePerMonth());
    }

    private HiringConditions convertHiringConditionsToBusiness(HiringConditionsDTO hiringConditionsDTO){
        if(hiringConditionsDTO == null)
            return null;
        return new HiringConditions(hiringConditionsDTO.getSalaryPerHour(), hiringConditionsDTO.getFund(), hiringConditionsDTO.getVacationDays(), hiringConditionsDTO.getSickLeavePerMonth());
    }

    private QualificationsDTO convertQualificationsToDTO(Qualifications qualifications){
        if(qualifications == null)
            return null;
        for(QualificationsDTO qualificationsDTO:QualificationsDTO.values()){
            if(qualificationsDTO.name().equals(qualifications.name()))
                return qualificationsDTO;
        }
        return null;
    }

    private Qualifications convertQualificationsToBusiness(QualificationsDTO qualificationsDTO){
        if(qualificationsDTO == null)
            return null;
        for(Qualifications qualifications:Qualifications.values()){
            if(qualifications.name().equals(qualificationsDTO.name()))
                return qualifications;
        }
        return null;
    }

    private EnumMap<QualificationsDTO,List<WorkerDTO>> convertQualificationEnumToDTO(EnumMap<Qualifications,List<Worker>> enumMap){
        if(enumMap == null)
            return null;
       EnumMap<QualificationsDTO,List<WorkerDTO>> enumMapAns = new EnumMap<QualificationsDTO, List<WorkerDTO>>(QualificationsDTO.class);
        for(Qualifications q : Qualifications.values()){
            enumMapAns.put(convertQualificationsToDTO(q),convertListOfWorkersToDTO(enumMap.get(q)));
        }
        return enumMapAns;
    }

    public ResponseT<ShiftDemandsDTO>getShiftDemands(LocalDate date,int branchID,ShiftTypeDTO shiftTypeDTO){
        try{
            shiftController.getShiftDemands(date,branchID,convertShiftTypeToBusiness(shiftTypeDTO));
        }catch(Exception e){
            return new ResponseT<>(null,e.getMessage());
        }
        return new ResponseT<>(convertShiftDemandsToDTO(shiftController.getShiftDemands(date,branchID,convertShiftTypeToBusiness(shiftTypeDTO))));
    }

    private ShiftDTO convertShiftToDTO(Shift shift){
        if(shift == null)
            return null;
        return new ShiftDTO(shift.getDate(),convertShiftTypeToDTO(shift.getType()),convertShiftDemandsToDTO(shift.getDemands()),convertListOfWorkersToDTO(shift.getCashiers()),convertListOfWorkersToDTO(shift.getStoreKeepers()),convertListOfWorkersToDTO(shift.getArrangers()),convertListOfWorkersToDTO(shift.getGuards()),convertListOfWorkersToDTO(shift.getAssistants()),convertWorkerToDTO(shift.getShiftManager()),shift.getBranchID());

    }

    private Shift convertShiftToBusiness(ShiftDTO shiftDTO){
        if(shiftDTO == null)
            return null;
        Worker w= new Worker(shiftDTO.getDriver().getFirstName(),shiftDTO.getDriver().getLastName(),shiftDTO.getDriver().getID(),convertBankAccountToBusiness(shiftDTO.getDriver().getBankAccount()),convertHiringConditionsToBusiness(shiftDTO.getDriver().getHiringConditions()),convertAvailableWorkDaysToBusiness(shiftDTO.getDriver().getAvailableWorkDays()),convertListQualificationsToBusiness(shiftDTO.getDriver().getQualifications()));
        return new Shift(shiftDTO.getDate(),convertShiftTypeToBusiness(shiftDTO.getType()),convertShiftDemandsToBusiness(shiftDTO.getDemands()),convertListOfWorkersToBusiness(shiftDTO.getWorkers().get(QualificationsDTO.Cashier)),convertListOfWorkersToBusiness(shiftDTO.getWorkers().get(QualificationsDTO.Storekeeper)),convertListOfWorkersToBusiness(shiftDTO.getWorkers().get(QualificationsDTO.Arranger)),convertListOfWorkersToBusiness(shiftDTO.getWorkers().get(QualificationsDTO.Guard)),convertListOfWorkersToBusiness(shiftDTO.getWorkers().get(QualificationsDTO.Assistant)),convertWorkerToBusiness(shiftDTO.getShiftManager()),shiftDTO.getBranchID(),new Driver(w,shiftDTO.getDriver().getLicenseType(),shiftDTO.getDriver().getExpLicenseDate()));
    }

    private ShiftDemandsDTO convertShiftDemandsToDTO(ShiftDemands shiftDemands){
        if(shiftDemands == null)
            return null;
        return new ShiftDemandsDTO(shiftDemands.getDate(),shiftDemands.getCashierAmount(),shiftDemands.getStoreKeeperAmount(),shiftDemands.getArrangerAmount(),shiftDemands.getGuardAmount(),shiftDemands.getAssistantAmount());
    }

    private ShiftDemands convertShiftDemandsToBusiness(ShiftDemandsDTO shiftDemandsDTO){
        if(shiftDemandsDTO == null)
            return null;
        return new ShiftDemands(shiftDemandsDTO.getDate(),shiftDemandsDTO.getCashierAmount(),shiftDemandsDTO.getStoreKeeperAmount(),shiftDemandsDTO.getArrangerAmount(),shiftDemandsDTO.getGuardAmount(),shiftDemandsDTO.getAssistantAmount(),shiftDemandsDTO.getShiftType());
    }

    private ShiftTypeDTO convertShiftTypeToDTO(ShiftType shiftType){
        if(shiftType == null)
            return null;
        for(ShiftTypeDTO shiftTypeDTO : ShiftTypeDTO.values()){
            if(shiftTypeDTO.name().equals(shiftType.name()))
                return shiftTypeDTO;
        }
        return null;
    }

    private ShiftType convertShiftTypeToBusiness(ShiftTypeDTO shiftTypeDTO){
        if(shiftTypeDTO == null)
            return null;
        for(ShiftType shiftType : ShiftType.values()){
            if(shiftType.name().equals(shiftTypeDTO.name()))
                return shiftType;
        }
        return null;
    }

    private WorkerDTO convertWorkerToDTO(Worker worker){
        if(worker == null)
            return null;
        if(worker==null)
            throw new IllegalArgumentException("There is no such worker");
        List<QualificationsDTO>list=new ArrayList<>();
        for(Qualifications q:worker.getQualifications()){
            list.add(convertQualificationsToDTO(q));
        }
        return new WorkerDTO(worker.getFirstName(),worker.getLastName(),worker.getID(),convertBankAccountToDTO(worker.getBankAccount()),convertHiringConditionsToDTO(worker.getHiringConditions()),convertAvailableWorkDaysToDTO(worker.getAvailableWorkDays()),list);
    }

    public Worker convertWorkerToBusiness(WorkerDTO workerDTO){
        if(workerDTO == null)
            return null;
        List<Qualifications>list=new ArrayList<>();
        for(QualificationsDTO q:workerDTO.getQualifications()){
            list.add(convertQualificationsToBusiness(q));
        }
        return new Worker(workerDTO.getFirstName(),workerDTO.getLastName(),workerDTO.getID(),convertBankAccountToBusiness(workerDTO.getBankAccount()),convertHiringConditionsToBusiness(workerDTO.getHiringConditions()),convertAvailableWorkDaysToBusiness(workerDTO.getAvailableWorkDays()),list);
    }

    private ShiftDemands[][] convertShiftDemandsToBusiness(ShiftDemandsDTO [][]shiftDemands){
        if(shiftDemands == null)
            return null;
        ShiftDemands[][] sd = new ShiftDemands[7][2];
        for(int i=0; i<7 ; i++){
            for(int j=0; j<2 ; j++){
               sd[i][j] = convertShiftDemandsToBusiness(shiftDemands[i][j]);
            }
        }
        return sd;
    }

    private List<QualificationsDTO> convertListQualificationsToDTO(List <Qualifications> qualifications) {
        if(qualifications == null)
            return null;
        List<QualificationsDTO> list = new ArrayList<>();
        for (Qualifications q : qualifications) {
            list.add(convertQualificationsToDTO(q));
        }
        return list;
    }

    private List<Qualifications> convertListQualificationsToBusiness(List <QualificationsDTO> qualifications) {
        if(qualifications == null)
            return null;
        List<Qualifications> list = new ArrayList<>();
        for (QualificationsDTO q : qualifications) {
            list.add(convertQualificationsToBusiness(q));
        }
        return list;
    }


    public Response setAvailableWorkDays(int branchID,WorkerDTO workerDTO, AvailableWorkDaysDTO availableWorkDaysDTO) {

        try{
            findBusinessWorker(branchID,workerDTO.getID()).setAvailableWorkDays(convertAvailableWorkDaysToBusiness(availableWorkDaysDTO));
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response isLegalBranch(int branchID){
        try {
            branchController.isLegalBranch(branchID);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response isCurrentWorker(String workerID){
        try{
            branchController.isCurrentWorker(workerID);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
       return new Response();
    }

    public ResponseT<Boolean> isWorkerExist(String workerID){
        try{
            return new ResponseT<>(Workers.isWorkerExist(workerID));
        }catch(Exception e){
            return new ResponseT<>(false,e.getMessage());
        }
    }
    public ResponseT<Integer> isAManagerOfBranch(String ID) {
        try{
             branchController.isAManagerOfBranch(ID);
        }catch(Exception e){
            return new ResponseT<>(-1,e.getMessage());
        }
        return new ResponseT<>(branchController.isAManagerOfBranch(ID));
    }

    public ResponseT<Boolean> isHasManagerInQualifications(String ID) {
        try{
            findBusinessWorkerByID(ID).getQualifications().contains(Qualifications.BranchManager);
        }catch(Exception e){
            return new ResponseT<>(null,e.getMessage());
        }
        return new ResponseT<>(findBusinessWorkerByID(ID).getQualifications().contains(Qualifications.BranchManager));
    }
    public Response addQualification(String ID, QualificationsDTO q){
        try{
            if(findDTOWorkerByID(ID).isErrorOccurred())
                return new Response(findDTOWorkerByID(ID).getErrorMessage());
            Worker worker=convertWorkerToBusiness(findDTOWorkerByID(ID).getValue());
            if(q==QualificationsDTO.Human_Resources_Director&&worker.getQualifications().contains(Qualifications.BranchManager))
                return new Response("HRD can't be branch manager as well ");
            if(q==QualificationsDTO.BranchManager&&worker.getQualifications().contains(Qualifications.Human_Resources_Director))
                return new Response("Branch manager can't be HRD as well ");
            if(!worker.getQualifications().contains(convertQualificationsToBusiness(q)))
                Workers.insertWorkerQualification(ID,q.name());
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public ResponseT<Boolean> isHRD(String id) {
        try{
            findBusinessWorkerByID(id).getQualifications().contains(Qualifications.Human_Resources_Director);
        }catch(Exception e){
            return new ResponseT<>(null,e.getMessage());
        }
        return new ResponseT<>(findBusinessWorkerByID(id).getQualifications().contains(Qualifications.Human_Resources_Director));
    }

    public Response displayWorkersByBranchID(int brID) {
        try{
            //branchController.displayWorkersByBranchID(brID);
            Workers.displayWorkersByBranchID(brID);
        }catch (Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response removeWorker(String hrd_id) {
        try{
            Workers.removeWorker(hrd_id);
        }catch (Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public ResponseT<WorkerDTO> getBranchManager(int branchID) {
        try{
            return new ResponseT<>(convertWorkerToDTO(Workers.getBranchManager(branchID)));
        }catch (Exception e){
            return new ResponseT(null,e.getMessage());
        }

    }


    public ResponseT<WorkerDTO> getBranchHRD(int branchID){
        try{
            return new ResponseT<>(convertWorkerToDTO(Workers.getBranchHRD(branchID)));
        }catch (Exception e){
            return new ResponseT(null,e.getMessage());
        }

    }

    public Response changeWorkerBranch(String workerID, int newBranchID) {
        try{
            Workers.changeWorkerBranch(workerID,newBranchID);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public ResponseT<Integer> getBranchID(String workerID){
        try{
            return new ResponseT<>(Workers.getBranchID(workerID));
        }catch (Exception e){
            return new ResponseT<>(null,e.getMessage());
        }
    }

    public Response getBackToWork(String workerID) {
        try{
            Workers.getBackToWork(workerID);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }
}