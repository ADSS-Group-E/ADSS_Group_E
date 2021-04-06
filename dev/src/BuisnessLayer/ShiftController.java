package BuisnessLayer;

import javafx.util.Pair;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ShiftController {
    private static ShiftController instance = null;
    private HashMap<Integer,List<Shift[][]>> weeklyAssignmentsHistory;
    private HashMap<Integer,Shift[][]> weeklyAssignment;


    private ShiftController() {
        weeklyAssignmentsHistory = new HashMap<>();
        weeklyAssignment = new HashMap<>();
    }

    public static ShiftController getInstance() {
        if (instance == null) {
            instance=new ShiftController();
        }
        return instance;
    }

    public void addBranch(int branchID) {
        boolean isFound = false;
        isFound=weeklyAssignment.containsKey(branchID);

        if (!isFound) {
            Shift[][] shifts = new Shift[7][2];
            for(int i=0;i<7;i++){
                for(int j=0;j<2;j++){
                    shifts[i][j]=null;
                }
            }
            weeklyAssignment.put(branchID,shifts);
            weeklyAssignmentsHistory.put(branchID,new LinkedList<Shift[][]>());
        }
    }



    public void printWorkersAtShift(int branchID,LocalDate date,ShiftType shiftType){
        getShift(branchID,date,shiftType).printWorkersAtShift();
    }

    public void removeBranch(int branchID) {
        if(!weeklyAssignmentsHistory.get(branchID).contains(weeklyAssignment.get(branchID)))
            weeklyAssignmentsHistory.get(branchID).add(weeklyAssignment.get(branchID));
        weeklyAssignment.remove(branchID);
    }

    private List<Worker> createShiftAssignment(LocalDate date, ShiftType shiftType, int branchID, ShiftDemands shiftDemands, List<Worker> workerList, Worker branchManager) {

        if(branchManager==null)
            throw new IllegalArgumentException("The shiftAssignment must contains a branchManager");
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

        Shift shift = new Shift(date, shiftType, shiftDemands, cashiers, storeKeepers, arrangers, guards, assistants, branchManager);
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

    public Shift getShift(int branchID,LocalDate date,ShiftType shiftType){
        int shift=shiftType==ShiftType.Morning? 0 : 1;
        for(int i=0;i<7;i++){
                if(weeklyAssignment.get(branchID)[i][shift].getDate().equals(date))
                    return weeklyAssignment.get(branchID)[i][shift];
        }
        return null;
    }

    private Qualifications findWorkerJob(Worker worker,int branchID,LocalDate date,ShiftType shiftType){
        return getShift(branchID,date,shiftType).findWorkerJob(worker);
    }


    public void workerReplacement(int branchID,LocalDate date1, ShiftType shiftType1,LocalDate date2 ,ShiftType shiftType2,Worker worker1,Worker worker2, Worker branchManager){
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

    public void createWeeklyAssignment(int branchID, LocalDate startDate, ShiftDemands[][] shiftDemands,List<Worker>workers ,Worker branchManager) {

        updateHistory(branchID);
        ShiftType type;
        for(int i=0;i<7;i++){
            List<Worker>ableToWork=new LinkedList<>(workers);
            for(int j=0;j<2;j++){
                if(j==0)
                    type=ShiftType.Morning;
                else
                    type=ShiftType.Evening;
                ableToWork=createShiftAssignment(startDate,type,branchID,shiftDemands[i][j],ableToWork,branchManager);
            }
            startDate=startDate.plusDays(1);
        }

    }


}
