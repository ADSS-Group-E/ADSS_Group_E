package BuisnessLayer;

//import javafx.util.converter.LocalDateTimeStringConverter;

import java.time.LocalDate;
import java.util.*;

public class ShiftController {

    private HashMap<Integer,List<Shift[][]>> weeklyAssignmentsHistory;
    private HashMap<Integer,Shift[][]> weeklyAssignment;
    private HashMap<Integer, ShiftDemands[][]> shiftDemandsHashMap;


    public ShiftController() {
        weeklyAssignmentsHistory = new HashMap<>();
        weeklyAssignment = new HashMap<>();
        shiftDemandsHashMap=new HashMap<>();
    }


    public void addShiftDemands(int branchID,LocalDate date,ShiftType shiftType,ShiftDemands shiftDemands){
        if(!shiftDemandsHashMap.containsKey(branchID))
            throw new IllegalArgumentException("Cant add shift demands to shift that isn't exist");
        LocalDate upperLimit=LocalDate.now().plusDays(7);
        if(date.compareTo(LocalDate.now())<0)
            throw new IllegalArgumentException("Can't create a shift demands to shift with date that already passed");
        if(date.compareTo(upperLimit)>0)
            throw new IllegalArgumentException("Can't create a shift demands that is so far. The system can create a shift demands that is within the next week");
        int type=shiftType==ShiftType.Morning ? 0 : 1;
        shiftDemandsHashMap.get(branchID)[date.getDayOfWeek().getValue()-1][type]=new ShiftDemands(shiftDemands);
    }

    public void resetShiftDemands(int branchID){
        for(int i=0;i<7;i++){
            for(int j=0;j<2;j++){
                shiftDemandsHashMap.get(branchID)[i][j]=null;
            }
        }
    }

    public void addBranch(int branchID) {
        boolean isFound ;
        isFound = weeklyAssignment.containsKey(branchID);
        if (isFound)
            throw new IllegalArgumentException("Can't add a branch that is already exists");
        Shift[][] shifts = new Shift[7][2];
        ShiftDemands[][] shiftDemands = new ShiftDemands[7][2];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                shifts[i][j] = null;
                shiftDemands[i][j] = null;
            }
        }
        weeklyAssignment.put(branchID, shifts);
        weeklyAssignmentsHistory.put(branchID, new LinkedList<Shift[][]>());
        shiftDemandsHashMap.put(branchID, shiftDemands);
    }

    public void printWorkersAtShift(int branchID,LocalDate date,ShiftType shiftType){
        if(getShift(branchID,date,shiftType)!=null)
            getShift(branchID,date,shiftType).printWorkersAtShift();
        else
            System.out.println("There is no "+shiftType.name().toLowerCase(Locale.ROOT)+" shift in this date");
    }

    public void removeBranch(int branchID) {
        if(!weeklyAssignment.containsKey(branchID))
            throw new IllegalArgumentException("cant remove the branch you entered from ShiftController because it is not exist");

        if(!weeklyAssignmentsHistory.get(branchID).contains(weeklyAssignment.get(branchID)))
            weeklyAssignmentsHistory.get(branchID).add(weeklyAssignment.get(branchID));
        weeklyAssignment.remove(branchID);
    }

    private List<Worker> createShiftAssignment(LocalDate date, ShiftType shiftType, int branchID,List<Worker> workerList, Worker branchManager) {
        int type = shiftType == ShiftType.Morning ? 0:1;
        if(branchManager==null)
            throw new IllegalArgumentException("The shiftAssignment must contains a branchManager");
        if(!shiftDemandsHashMap.containsKey(branchID))
            throw new IllegalArgumentException("The hash map of demands doesn't contains this branch's demands");
        if(!shiftDemandsHashMap.get(branchID)[date.getDayOfWeek().getValue()][type].getDate().equals(date))
            throw new IllegalArgumentException("The shift demands of this date is not exist in the system");
        ShiftDemands shiftDemands = shiftDemandsHashMap.get(branchID)[date.getDayOfWeek().getValue()][type];
        int dayAtWeek = date.getDayOfWeek().getValue();
        int arrangerAmount = shiftDemands.getArrangerAmount();
        int assistantAmount = shiftDemands.getAssistantAmount();
        int cashierAmount = shiftDemands.getCashierAmount();
        int guardAmount = shiftDemands.getGuardAmount();
        int storeKeeperAmount = shiftDemands.getStoreKeeperAmount();
        List<Worker> arrangers = new LinkedList<>();
        List<Worker> assistants = new LinkedList<>();
        List<Worker> cashiers = new LinkedList<>();
        List<Worker> guards = new LinkedList<>();
        List<Worker> storeKeepers = new LinkedList<>();
        int typeOfShift = shiftType == ShiftType.Morning ? 0 : 1;
        List<Worker> notWorkYet = new LinkedList<>(workerList);
        List<Worker> workingList=new LinkedList<>();
        for (Worker w : notWorkYet) {
            List<Qualifications> qualifications = w.getQualifications();

            if (arrangerAmount > 0 && qualifications.contains(Qualifications.Arranger)) {
                if (w.getAvailableWorkDays().getFavoriteShifts()[dayAtWeek][typeOfShift]) {
                    arrangerAmount--;
                    arrangers.add(w);
                    notWorkYet.remove(w);
                    workingList.add(w);
                }
            } else if (assistantAmount > 0 && qualifications.contains(Qualifications.Assistant)) {
                if (w.getAvailableWorkDays().getFavoriteShifts()[dayAtWeek][typeOfShift]) {
                    assistantAmount--;
                    assistants.add(w);
                    notWorkYet.remove(w);
                    workingList.add(w);
                }
            } else if (cashierAmount > 0 && qualifications.contains(Qualifications.Cashier)) {
                if (w.getAvailableWorkDays().getFavoriteShifts()[dayAtWeek][typeOfShift]) {
                    cashierAmount--;
                    cashiers.add(w);
                    notWorkYet.remove(w);
                    workingList.add(w);
                }
            } else if (guardAmount > 0 && qualifications.contains(Qualifications.Guard)) {
                if (w.getAvailableWorkDays().getFavoriteShifts()[dayAtWeek][typeOfShift]) {
                    guardAmount--;
                    guards.add(w);
                    notWorkYet.remove(w);
                    workingList.add(w);
                }
            } else if (storeKeeperAmount > 0 && qualifications.contains(Qualifications.Storekeeper)) {
                if (w.getAvailableWorkDays().getFavoriteShifts()[dayAtWeek][typeOfShift]) {
                    storeKeeperAmount--;
                    storeKeepers.add(w);
                    notWorkYet.remove(w);
                    workingList.add(w);
                }
            }

            if (arrangerAmount == 0 && assistantAmount == 0 && cashierAmount == 0 && guardAmount == 0 && storeKeeperAmount == 0)
                break;
        }

        Shift shift = new Shift(date, shiftType, shiftDemands, cashiers, storeKeepers, arrangers, guards, assistants, branchManager,branchID);
        weeklyAssignment.get(branchID)[dayAtWeek][typeOfShift]=shift;
        return workingList;
    }

    private void updateHistory(int branchID){
        for(int i=0;i<7;i++){
            for(int j=0;j<2;j++){
                if(weeklyAssignment.get(branchID)[i][j]!=null){
                    if(!weeklyAssignmentsHistory.get(branchID).contains(weeklyAssignment.get(branchID)))
                        weeklyAssignmentsHistory.get(branchID).add(weeklyAssignment.get(branchID));
                    return;
                }
            }
        }
    }

    public Shift getShift(int branchID,LocalDate date,ShiftType shiftType) {
        int shift = shiftType == ShiftType.Morning ? 0 : 1;
        for (int i = 0; i < 7; i++) {
            if (!weeklyAssignment.containsKey(branchID))
                throw new IllegalArgumentException("The branch ID:" + branchID + " is not exist in the system");
            if (weeklyAssignment.get(branchID)[i][shift] != null && weeklyAssignment.get(branchID)[i][shift].getDate().equals(date))
                return weeklyAssignment.get(branchID)[i][shift];
        }
        return null;
    }

    private Qualifications findWorkerJob(Worker worker,int branchID,LocalDate date,ShiftType shiftType){
        return getShift(branchID,date,shiftType).findWorkerJob(worker);
    }


    public void workerReplacement(int branchID,LocalDate date1, ShiftType shiftType1,LocalDate date2 ,ShiftType shiftType2,Worker worker1,Worker worker2, Worker branchManager){
        if(!weeklyAssignment.containsKey(branchID))
            throw new IllegalArgumentException("There is no branch id "+branchID);
        if(date1.isBefore(LocalDate.now())||date2.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("The shift is already happened");

        int type1=shiftType1==ShiftType.Morning? 0 : 1;
        int type2=shiftType2==ShiftType.Morning? 0 : 1;
        Qualifications qualifications1=findWorkerJob(worker1,branchID,date1,shiftType1);
        Qualifications qualifications2=findWorkerJob(worker2,branchID,date2,shiftType2);
        if(qualifications1==qualifications2){
            Shift shift1=getShift(branchID,date1,shiftType1);
            Shift shift2=getShift(branchID,date2,shiftType2);
            shift1.getWorkers().get(qualifications1).remove(worker1);
            shift2.getWorkers().get(qualifications2).add(worker1);
            shift2.getWorkers().get(qualifications2).remove(worker2);
            shift1.getWorkers().get(qualifications1).add(worker2);
        }

    }

    public void createWeeklyAssignment(int branchID, LocalDate startDate,List<Worker>workers ,Worker branchManager) {
        if(!weeklyAssignment.containsKey(branchID))
            throw new IllegalArgumentException("There is no such branch");
        updateHistory(branchID);
        ShiftDemands[][] shiftDemands = shiftDemandsHashMap.get(branchID);

        ShiftType type;
        for(int i=0;i<7;i++){
            List<Worker>ableToWork=new LinkedList<>(workers);
            for(int j=0;j<2;j++){
                if(j==0)
                    type=ShiftType.Morning;
                else
                    type=ShiftType.Evening;
                ableToWork=createShiftAssignment(startDate,type,branchID,ableToWork,branchManager);
            }
            startDate=startDate.plusDays(1);
        }
    }
    public ShiftDemands[][] getShiftDemands(int branchID, LocalDate date){
        if(!shiftDemandsHashMap.get(branchID)[0][0].getDate().equals(date)){
            System.out.println("there is no up to date demands");
            return null;
        }
        return shiftDemandsHashMap.get(branchID);
    }


    public void printWeeklyAssignment(int branchID, LocalDate date) {
        if(!weeklyAssignment.containsKey(branchID))
            throw new IllegalArgumentException("Cant print a branch that isn't exist");

        Shift[][] shifts = null;
        if(weeklyAssignment.get(branchID)[0][0].getDate().equals(date))
            shifts = weeklyAssignment.get(branchID);
        else{
            List<Shift[][]> s = weeklyAssignmentsHistory.get(branchID);
            for(Shift[][] shift : s ){
                if(shift[0][0].getDate().equals(date)){
                    shifts = shift;
                    break;
                }
            }
            if (shifts == null)
                throw new IllegalArgumentException("There is no weekly assignment in this date");
            else
                for (Shift[] sh : shifts)
                    System.out.println(sh.toString());
            }
        }
    }

