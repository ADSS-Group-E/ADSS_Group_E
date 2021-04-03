package BuisnessLayer;

import javafx.util.Pair;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ShiftController {
    private static ShiftController instance = null;
    private List<Pair<Integer, List<Shift[][]>>> weeklyAssignmentsHistory;
    private List<Pair<Integer, Shift[][]>> weeklyAssignment;

    private ShiftController() {
        weeklyAssignmentsHistory = new LinkedList<>();
        weeklyAssignment = new LinkedList<>();
    }

    public static ShiftController getInstance() {
        if (instance == null) {
            return new ShiftController();
        }
        return instance;
    }

    public void addBranch(int branchID) {
        boolean isFound = false;
        for (Pair pair : weeklyAssignment) {
            if ((int) pair.getKey() == branchID) {
                isFound = true;
                break;
            }
        }

        if (!isFound) {
            Shift[][] shifts = new Shift[7][2];
            for(int i=0;i<7;i++){
                for(int j=0;j<2;j++){
                    shifts[i][j]=null;
                }
            }
            Pair p = new Pair(branchID, shifts);
            weeklyAssignment.add(p);
            weeklyAssignmentsHistory.add(new Pair<>(branchID, new LinkedList<Shift[][]>()));
        }
    }

    public void removeBranch(int branchID) {
        for (Pair pair : weeklyAssignment) {
            if ((int) pair.getKey() == branchID)
                weeklyAssignment.remove(pair);
        }
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
        for (Pair pair : weeklyAssignment) {
            if ((int) pair.getKey() == branchID) {
                ((Shift[][]) (pair.getValue()))[dayAtWeek][typeOfShift] = shift;
            }
        }
        return workingList;
    }

    private void updateHistory(int branchID){
        for(Pair pair: weeklyAssignment){
            if((int)pair.getKey()==branchID){
                for(Pair p: weeklyAssignmentsHistory){
                    if((int)p.getKey()==branchID){
                       ((List<Shift[][]>)p.getValue()).add((Shift[][]) pair.getValue());
                       return ;
                    }
                }
            }
        }
    }

    public void createWeeklyAssignment(int branchID, LocalDate startDate, ShiftDemands[][] shiftDemands,List<Worker>workers ,Worker branchManager) {
        Boolean found = false;
        for(Pair pair : weeklyAssignment){
            if((int)pair.getKey()==branchID){
                for(int i=0;i<7&&!found;i++){
                    for(int j=0;j<2&&!found;j++){
                        if(((Shift[][]) (pair.getValue()))[i][j]!=null){
                            updateHistory(branchID);
                            found = true;
                        }
                    }
                }

            }
            if(found)
                break;
        }
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
