package BuisnessLayer;

import PresentationLayer.*;

import java.time.LocalDate;
import java.util.*;

import static PresentationLayer.Main.createDate;

public class Facade {

    private BranchController branchController;
    private ShiftController shiftController;
    private static Facade instance = null;

    private Facade() {
        this.branchController = BranchController.getInstance();
        this.shiftController = ShiftController.getInstance();
    }

    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }

    public void addShiftDemands(int branchID, ShiftDemandsDTO[][] shiftDemandsDTO) {
        ShiftDemands[][] shiftDemands = new ShiftDemands[7][2];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                shiftDemands[i][j] = convertShiftDemandsToBusiness(shiftDemandsDTO[i][j]);
            }
            shiftController.addShiftDemands(branchID, shiftDemands);
        }
    }

    public void addShiftDemands(int branchID, LocalDate date, ShiftTypeDTO shiftType, ShiftDemandsDTO shiftDemands) {
        shiftController.addShiftDemands(branchID, date, convertShiftTypeToBusiness(shiftType), convertShiftDemandsToBusiness(shiftDemands));
    }

    public void resetShiftDemands(int branchID) {
        shiftController.resetShiftDemands(branchID);
    }

    public void addBranch(int branchID, WorkerDTO branchManager, WorkerDTO activeHRD) {
        if (!branchManager.getQualifications().contains(QualificationsDTO.BranchManager))
            throw new IllegalArgumentException("The worker you sent is not a branch manager");

        branchController.addBranch(branchID, convertWorkerToBusiness(branchManager), convertWorkerToBusiness(activeHRD));
        shiftController.addBranch(branchID);
        addWorker(branchManager, branchID);
        addWorker(activeHRD, branchID);
    }

    public BranchDTO getBranch(int ID) {
        return convertBranchToDTO(branchController.getBranch(ID));
    }

    public void addWorker(WorkerDTO worker, int branchID) {
        branchController.addWorker(convertWorkerToBusiness(worker), branchID);
    }

    public void removeWorker(WorkerDTO worker, int branchID) {
        branchController.removeWorker(convertWorkerToBusiness(worker), branchID);
    }

    public void removeBranch(int branchID) {
        branchController.removeBranch(branchID);
        shiftController.removeBranch(branchID);
    }

    public List<ShiftDTO> getHistory(int branchID) {
        return convertListOfShiftsToDTO(branchController.getBranch(branchID).getWeeklyAssignmentsHistory());
    }

    public ShiftDTO getShift(int branchID, LocalDate date, ShiftTypeDTO shiftType) {
        return convertShiftToDTO(shiftController.getShift(branchID, date, convertShiftTypeToBusiness(shiftType)));
    }

    public void createWeeklyAssignment(int branchID, LocalDate startDate, WorkerDTO branchManager) {
        shiftController.createWeeklyAssignment(branchID, startDate, branchController.getBranch(branchID).getWorkersList(),convertWorkerToBusiness(branchManager));
    }

    public void workerReplacement(int branchID, LocalDate date1, ShiftTypeDTO shiftType1, LocalDate date2, ShiftTypeDTO shiftType2, WorkerDTO worker1, WorkerDTO worker2, WorkerDTO branchManager) {
        shiftController.workerReplacement(branchID, date1,convertShiftTypeToBusiness(shiftType1), date2,convertShiftTypeToBusiness(shiftType2),convertWorkerToBusiness(worker1),convertWorkerToBusiness(worker2),convertWorkerToBusiness(branchManager));
    }

    public WorkerDTO findDTOWorker(int branchID, String workerID) {
        return convertWorkerToDTO(branchController.findWorker(branchID, workerID));
    }
    private Worker findBusinessWorker(int branchID, String workerID) {
        return branchController.findWorker(branchID, workerID);
    }

    public WorkerDTO findDTOWorkerByID(String workerID) {
        return convertWorkerToDTO(branchController.findWorkerByID(workerID));
    }
    private Worker findBusinessWorkerByID(String workerID) {
        return branchController.findWorkerByID(workerID);
    }

    public void printWorkersAtShift(int branchID, LocalDate date, ShiftTypeDTO shiftType) {
        shiftController.printWorkersAtShift(branchID, date,convertShiftTypeToBusiness(shiftType));
    }

    public void showWorkers(int branchID) {
        branchController.showWorkers(branchID);
    }

    public List<QualificationsDTO> getWorkerQualifications(WorkerDTO worker) {

        return convertListQualificationsToDTO(branchController.getWorkerQualifications(convertWorkerToBusiness(worker)));
    }

    public void printWorker(WorkerDTO worker) {
        System.out.println(convertWorkerToBusiness(worker).toString());
    }

    public List<Worker> getQualification(Shift shift, Qualifications qualifications) {
        return shift.getWorkers().get(qualifications);
    }

    public WorkerDTO getShiftManager(ShiftDTO shift) {
        return shift.getShiftManager();
    }

    public void printWorkersByQualification(QualificationsDTO qualifications, ShiftDTO shift) {
        convertShiftToBusiness(shift).printWorkersByQualification(convertQualificationsToBusiness(qualifications));
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

    public ShiftDemandsDTO getShiftDemandsDTO(BuisnessLayer.ShiftDemands blShiftDemands) {
        LocalDate date = blShiftDemands.getDate();
        int cashierAmount = blShiftDemands.getCashierAmount();
        int storeKeeperAmount = blShiftDemands.getStoreKeeperAmount();
        int arrangerAmount = blShiftDemands.getArrangerAmount();
        int guardAmount = blShiftDemands.getGuardAmount();
        int assistantAmount = blShiftDemands.getAssistantAmount();

        return new ShiftDemandsDTO(date, cashierAmount, storeKeeperAmount, arrangerAmount, guardAmount, assistantAmount);
    }

    public void createWeeklyAssignment(int branchID, LocalDate startDate, List<Worker> workers, Worker branchManager) {
        shiftController.createWeeklyAssignment(branchID, startDate, workers, branchManager);
    }

    public void printWeeklyAssignment(int branchID, LocalDate date) {
        shiftController.printWeeklyAssignment(branchID, date);
    }

    public BranchDTO findBranchByWorker(WorkerDTO worker) {
        return convertBranchToDTO(branchController.findBranchByWorker(worker.getID()));
    }

    public void setWorkerFirstName(String WorkerID,String newFirstName) {
        try{
            findBusinessWorkerByID(WorkerID).setFirstName(newFirstName);
        }catch(Exception e){

        }
    }
    public void setWorkerLastName(String lastName, String ID) {
        try{
            findBusinessWorkerByID(ID).setLastName(lastName);
        }catch(Exception e){

        }
    }


    public void setBankAccount(BankAccountDTO bankAccount, String WorkerID) {
        try{
            findBusinessWorkerByID(WorkerID).setBankAccount(convertBankAccountToBusiness(bankAccount));
        }catch(Exception e){

        }
    }

    public BankAccountDTO getWorkerBankAccount(String WorkerID) {
      BankAccountDTO bankAccount = null;
        try{
            bankAccount = convertBankAccountToDTO(findBusinessWorkerByID(WorkerID).getBankAccount());
      }
        catch (Exception e){

        }
        return bankAccount;
    }

    public void setHiringConditions(HiringConditionsDTO hiringConditions, String WorkerID) {
        try{
            findBusinessWorkerByID(WorkerID).setHiringConditions(convertHiringConditionsToBusiness(hiringConditions));
        }catch(Exception e){

        }
    }

    public HiringConditionsDTO getWorkerHiringConditions(String WorkerID) {
        HiringConditionsDTO hiringConditionsDTO = null;
        try{
            hiringConditionsDTO = convertHiringConditionsToDTO(findBusinessWorkerByID(WorkerID).getHiringConditions());
        }
        catch (Exception e){

        }
        return hiringConditionsDTO;
    }

    public String getWorkerFirstName(String WorkerID) {
        String name = null;
        try{
             name = findBusinessWorkerByID(WorkerID).getFirstName();
        }catch(Exception e){

        }
       return name;
    }

    public String getWorkerLastName(String ID) {
        String name = null;
        try{
            name = findBusinessWorkerByID(ID).getLastName();
        }catch(Exception e){

        }
        return name;
    }
    public void setWorkerQualifications(List<QualificationsDTO> qualifications, String ID) {
        try{
            findBusinessWorkerByID(ID).setQualifications(convertListQualificationsToBusiness(qualifications));
        }catch(Exception e){

        }
    }

    public void isExistingWorker(String ID){
        if(findDTOWorkerByID(ID)!=null)
            throw new IllegalArgumentException("The worker: "+ ID + " is already exist in the system");
    }

    public WorkerDTO findWorkerBySerialNumber(int branchID, int serialNum){
        return convertWorkerToDTO(branchController.getBranch(branchID).getWorkersList().get(serialNum));
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
        List<WorkerDTO> list=new ArrayList<>();
        for(Worker worker: workers)
            list.add(convertWorkerToDTO(worker));
        return list;
    }

    private List<ShiftDTO> convertListOfShiftsToDTO(List<Shift> shifts){
        List<ShiftDTO> list=new ArrayList<>();
        for(Shift shift: shifts)
            list.add(convertShiftToDTO(shift));
        return list;
    }

    private List<Shift> convertListOfShiftsToBusiness(List<ShiftDTO> shifts){
        List<Shift> list=new ArrayList<>();
        for(ShiftDTO shift: shifts)
            list.add(convertShiftToBusiness(shift));
        return list;
    }

    private List<Worker> convertListOfWorkersToBusiness(List<WorkerDTO> workers){
        List<Worker> list=new ArrayList<>();
        for(WorkerDTO worker: workers)
            list.add(convertWorkerToBusiness(worker));
        return list;
    }

    private ShiftDTO[][] convertShiftDArrayToDTO(Shift[][] shifts){
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
        Shift[][] shift=new Shift[7][2];
        for(int i=0;i<shiftDTOS.length;i++){
            for(int j=0;j<shiftDTOS[i].length;i++){
                shift[i][j]=convertShiftToBusiness(shiftDTOS[i][j]);
            }
        }
        return shift;
    }

    private BranchDTO convertBranchToDTO(Branch branch){
        List<ShiftDTO>weeklyAssignmentHistory=new ArrayList<>();
        for(Shift shift : branch.getWeeklyAssignmentsHistory()){
            weeklyAssignmentHistory.add(convertShiftToDTO(shift));
        }
        return new BranchDTO(branch.getBranchID(),convertWorkerToDTO(branch.getBranchManager()), convertWorkerToDTO(branch.getActiveHRD()),weeklyAssignmentHistory, convertListOfWorkersToDTO(branch.getWorkersList()),convertListOfWorkersToDTO(branch.getFormerWorkers()),convertShiftDArrayToDTO(branch.getAssignmentsBoard()));
    }



    private Branch convertBranchToBusiness(BranchDTO branchDTO){
        return new Branch(branchDTO.getBranchID(),convertWorkerToBusiness(branchDTO.getBranchManager()),convertWorkerToBusiness(branchDTO.getActiveHRD()),convertListOfShiftsToBusiness(branchDTO.getWeeklyAssignmentsHistory()),convertListOfWorkersToBusiness(branchDTO.getWorkersList()),convertListOfWorkersToBusiness(branchDTO.getFormerWorkers()),convertShiftDArrayToBusiness(branchDTO.getAssignmentsBoard()));
    }

    private HiringConditionsDTO convertHiringConditionsToDTO(HiringConditions hiringConditions){
        return new HiringConditionsDTO(hiringConditions.getSalaryPerHour(), hiringConditions.getFund(), hiringConditions.getVacationDays(), hiringConditions.getSickLeavePerMonth());
    }

    private HiringConditions convertHiringConditionsToBusiness(HiringConditionsDTO hiringConditionsDTO){
        return new HiringConditions(hiringConditionsDTO.getSalaryPerHour(), hiringConditionsDTO.getFund(), hiringConditionsDTO.getVacationDays(), hiringConditionsDTO.getSickLeavePerMonth());
    }

    private QualificationsDTO convertQualificationsToDTO(Qualifications qualifications){
        for(QualificationsDTO qualificationsDTO:QualificationsDTO.values()){
            if(qualificationsDTO.name().equals(qualifications.name()))
                return qualificationsDTO;
        }
        return null;
    }

    private Qualifications convertQualificationsToBusiness(QualificationsDTO qualificationsDTO){
        for(Qualifications qualifications:Qualifications.values()){
            if(qualifications.name().equals(qualificationsDTO.name()))
                return qualifications;
        }
        return null;
    }

    private EnumMap<QualificationsDTO,List<WorkerDTO>> convertQualificationEnumToDTO(EnumMap<Qualifications,List<Worker>> enumMap){
       EnumMap<QualificationsDTO,List<WorkerDTO>> enumMapAns = new EnumMap<QualificationsDTO, List<WorkerDTO>>(QualificationsDTO.class);
        for(Qualifications q : Qualifications.values()){
            enumMapAns.put(convertQualificationsToDTO(q),convertListOfWorkersToDTO(enumMap.get(q)));
        }
        return enumMapAns;
    }

    private ShiftDTO convertShiftToDTO(Shift shift){
        return new ShiftDTO(shift.getDate(),convertShiftTypeToDTO(shift.getType()),convertShiftDemandsToDTO(shift.getDemands()),convertQualificationEnumToDTO(shift.getWorkers()),convertWorkerToDTO(shift.getShiftManager()),shift.getBranchID());

    }

    private Shift convertShiftToBusiness(ShiftDTO shiftDTO){
        return new Shift(shiftDTO.getDate(),convertShiftTypeToBusiness(shiftDTO.getType()),convertShiftDemandsToBusiness(shiftDTO.getDemands()),convertListOfWorkersToBusiness(shiftDTO.getWorkers().get(QualificationsDTO.Cashier)),convertListOfWorkersToBusiness(shiftDTO.getWorkers().get(QualificationsDTO.Storekeeper)),convertListOfWorkersToBusiness(shiftDTO.getWorkers().get(QualificationsDTO.Arranger)),convertListOfWorkersToBusiness(shiftDTO.getWorkers().get(QualificationsDTO.Guard)),convertListOfWorkersToBusiness(shiftDTO.getWorkers().get(QualificationsDTO.Assistant)),convertWorkerToBusiness(shiftDTO.getShiftManager()),shiftDTO.getBranchID());
    }

    private ShiftDemandsDTO convertShiftDemandsToDTO(ShiftDemands shiftDemands){
        return new ShiftDemandsDTO(shiftDemands.getDate(),shiftDemands.getCashierAmount(),shiftDemands.getStoreKeeperAmount(),shiftDemands.getArrangerAmount(),shiftDemands.getGuardAmount(),shiftDemands.getAssistantAmount());
    }

    private ShiftDemands convertShiftDemandsToBusiness(ShiftDemandsDTO shiftDemandsDTO){
        return new ShiftDemands(shiftDemandsDTO.getDate(),shiftDemandsDTO.getCashierAmount(),shiftDemandsDTO.getStoreKeeperAmount(),shiftDemandsDTO.getArrangerAmount(),shiftDemandsDTO.getGuardAmount(),shiftDemandsDTO.getAssistantAmount());
    }

    private ShiftTypeDTO convertShiftTypeToDTO(ShiftType shiftType){
        for(ShiftTypeDTO shiftTypeDTO : ShiftTypeDTO.values()){
            if(shiftTypeDTO.name().equals(shiftType.name()))
                return shiftTypeDTO;
        }
        return null;
    }

    private ShiftType convertShiftTypeToBusiness(ShiftTypeDTO shiftTypeDTO){
        for(ShiftType shiftType : ShiftType.values()){
            if(shiftType.name().equals(shiftTypeDTO.name()))
                return shiftType;
        }
        return null;
    }

    private WorkerDTO convertWorkerToDTO(Worker worker){
        List<QualificationsDTO>list=new ArrayList<>();
        for(Qualifications q:worker.getQualifications()){
            list.add(convertQualificationsToDTO(q));
        }
        return new WorkerDTO(worker.getFirstName(),worker.getLastName(),worker.getID(),convertBankAccountToDTO(worker.getBankAccount()),worker.getStartWorkingDay(),convertHiringConditionsToDTO(worker.getHiringConditions()),convertAvailableWorkDaysToDTO(worker.getAvailableWorkDays()),list);
    }

    private Worker convertWorkerToBusiness(WorkerDTO workerDTO){
        List<Qualifications>list=new ArrayList<>();
        for(QualificationsDTO q:workerDTO.getQualifications()){
            list.add(convertQualificationsToBusiness(q));
        }
        return new Worker(workerDTO.getFirstName(),workerDTO.getLastName(),workerDTO.getID(),convertBankAccountToBusiness(workerDTO.getBankAccount()),workerDTO.getStartWorkingDay(),convertHiringConditionsToBusiness(workerDTO.getHiringConditions()),convertAvailableWorkDaysToBusiness(workerDTO.getAvailableWorkDays()),list);
    }

    private ShiftDemands[][] convertShiftDemandsToBusiness(ShiftDemandsDTO [][]shiftDemands){
        ShiftDemands[][] sd = new ShiftDemands[7][2];
        for(int i=0; i<7 ; i++){
            for(int j=0; j<2 ; j++){
               sd[i][j] = convertShiftDemandsToBusiness(shiftDemands[i][j]);
            }
        }
        return sd;
    }

    private List<QualificationsDTO> convertListQualificationsToDTO(List <Qualifications> qualifications) {
        List<QualificationsDTO> list = new ArrayList<>();
        for (Qualifications q : qualifications) {
            list.add(convertQualificationsToDTO(q));
        }
        return list;
    }

    private List<Qualifications> convertListQualificationsToBusiness(List <QualificationsDTO> qualifications) {
        List<Qualifications> list = new ArrayList<>();
        for (QualificationsDTO q : qualifications) {
            list.add(convertQualificationsToBusiness(q));
        }
        return list;
    }


    public void setAvailableWorkDays(int branchID,WorkerDTO workerDTO, AvailableWorkDaysDTO availableWorkDaysDTO) {
        try{
            findBusinessWorker(branchID,workerDTO.getID()).setAvailableWorkDays(convertAvailableWorkDaysToBusiness(availableWorkDaysDTO));
        }catch(Exception e){

        }
    }

    public void isLegalBranch(int branchID){
        branchController.isLegalBranch(branchID);
    }

    public void isLegalWorker(String workerID){
        branchController.isLegalWorker(workerID);
    }

    public Boolean isAManager(String ID) {
        return findBusinessWorkerByID(ID).getQualifications().contains(Qualifications.BranchManager);
    }
    public void addQualification(String ID, QualificationsDTO q){
        findBusinessWorkerByID(ID).getQualifications().add(convertQualificationsToBusiness(q));
    }

    public boolean isHRD(String id) {
        return findBusinessWorkerByID(id).getQualifications().contains(Qualifications.Human_Resources_Director);
    }
}