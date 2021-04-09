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
            shiftController.addShiftDemands(branchID, date, convertShiftTypeToBusiness(shiftType), convertShiftDemandsToBusiness(shiftDemands));
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
            branchController.addWorker(convertWorkerToBusiness(worker), branchID);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response removeWorker(WorkerDTO worker, int branchID) {
        try{
        branchController.removeWorker(convertWorkerToBusiness(worker), branchID);
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
        }catch(Exception e){
            return new ResponseT<ShiftDTO>(convertShiftToDTO(shiftController.getShift(branchID, date, convertShiftTypeToBusiness(shiftType))),e.getMessage());
        }
        return new ResponseT<ShiftDTO>(convertShiftToDTO(shiftController.getShift(branchID, date, convertShiftTypeToBusiness(shiftType))));
    }

    public Response createWeeklyAssignment(int branchID, LocalDate startDate, WorkerDTO branchManager) {
        try {
            shiftController.createWeeklyAssignment(branchID, startDate, branchController.getBranch(branchID).getWorkersList(), convertWorkerToBusiness(branchManager));
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response workerReplacement(int branchID, LocalDate date1, ShiftTypeDTO shiftType1, LocalDate date2, ShiftTypeDTO shiftType2, WorkerDTO worker1, WorkerDTO worker2, WorkerDTO branchManager) {
        try {
            shiftController.workerReplacement(branchID, date1, convertShiftTypeToBusiness(shiftType1), date2, convertShiftTypeToBusiness(shiftType2), convertWorkerToBusiness(worker1), convertWorkerToBusiness(worker2), convertWorkerToBusiness(branchManager));
        }catch (Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public ResponseT<WorkerDTO> findDTOWorker(int branchID, String workerID) {
        try {
            branchController.findWorker(branchID, workerID);
        }catch(Exception e){
            return new ResponseT<WorkerDTO>(convertWorkerToDTO(branchController.findWorker(branchID, workerID)),e.getMessage());
        }
        return new ResponseT<WorkerDTO>(convertWorkerToDTO(branchController.findWorker(branchID, workerID)));
    }
    private Worker findBusinessWorker(int branchID, String workerID) {
        return branchController.findWorker(branchID, workerID);
    }

    public ResponseT<WorkerDTO> findDTOWorkerByID(String workerID) {
        try{
            branchController.findWorkerByID(workerID);
        }catch(Exception e){
            return new ResponseT<WorkerDTO>(null,e.getMessage());
        }
        return new ResponseT<WorkerDTO>(convertWorkerToDTO(branchController.findWorkerByID(workerID)));
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
            branchController.showWorkers(branchID);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public ResponseT<List<QualificationsDTO>> getWorkerQualifications(WorkerDTO worker) {
        try{
            branchController.getWorkerQualifications(convertWorkerToBusiness(worker));
        }catch(Exception e){
            return new ResponseT<>(null,e.getMessage());
        }
        return new ResponseT<>(convertListQualificationsToDTO(branchController.getWorkerQualifications(convertWorkerToBusiness(worker))));
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
        }catch(Exception e){
            return new ResponseT<>(null,e.getMessage());
        }
        return new ResponseT<>(convertBranchToDTO(branchController.findBranchByWorker(worker.getID())));
    }

    public Response setWorkerFirstName(String WorkerID,String newFirstName) {
        try{
            findBusinessWorkerByID(WorkerID).setFirstName(newFirstName);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Response setWorkerLastName(String lastName, String ID) {
        try{
            findBusinessWorkerByID(ID).setLastName(lastName);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Response setBankAccount(BankAccountDTO bankAccount, String WorkerID) {
        try{
            findBusinessWorkerByID(WorkerID).setBankAccount(convertBankAccountToBusiness(bankAccount));
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

    public Response setHiringConditions(HiringConditionsDTO hiringConditions, String WorkerID) {
        try{
            findBusinessWorkerByID(WorkerID).setHiringConditions(convertHiringConditionsToBusiness(hiringConditions));
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
    public Response setWorkerQualifications(List<QualificationsDTO> qualifications, String ID) {
        try{
            findBusinessWorkerByID(ID).setQualifications(convertListQualificationsToBusiness(qualifications));
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public void isExistingWorker(String ID){
        if(findDTOWorkerByID(ID)!=null)
            throw new IllegalArgumentException("The worker: "+ ID + " is already exist in the system");
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

    public Response isLegalWorker(String workerID){
        try{
            branchController.isLegalWorker(workerID);
        }catch(Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public ResponseT<Boolean> isAManager(String ID) {
        try{
            findBusinessWorkerByID(ID).getQualifications().contains(Qualifications.BranchManager);
        }catch(Exception e){
            return new ResponseT<>(null,e.getMessage());
        }
        return new ResponseT<>(findBusinessWorkerByID(ID).getQualifications().contains(Qualifications.BranchManager));
    }
    public Response addQualification(String ID, QualificationsDTO q){
        try{
            findBusinessWorkerByID(ID).getQualifications().add(convertQualificationsToBusiness(q));
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
}