package BuisnessLayer;



import jdk.internal.util.xml.impl.Pair;

import java.time.LocalDate;
import java.util.*;

public class Shift {
    private LocalDate date;
    private ShiftType type;
    private ShiftDemands demands;
    private EnumMap<Qualifications,List<Worker>> workers;
    private Worker shiftManager;
    private int branchID;


    public int getBranchID() {
        return branchID;
    }

    public EnumMap<Qualifications, List<Worker>> getWorkers() {
        return workers;
    }

    public void printWorkersByQualification(Qualifications qualifications){
       List<Worker>workerList= workers.get(qualifications);
       int index=1;
        System.out.println("The "+ qualifications.name()+ "of this shift are:");
        for(Worker w : workerList) {
            System.out.println(index + ") name:" + w.getFirstName() + " " + w.getLastName() + "ID:" + w.getID());
            index++;
        }
    }

    public void printWorkersAtShift(){
        int i=1;
        System.out.println("Date: "+ date);
        System.out.println("Shift Type "+ type.name());
        System.out.println("The manager of this shift is: "+shiftManager.getFirstName()+" "+shiftManager.getLastName()+ "and his Id is:"+shiftManager.getID());
        System.out.println("The workers of this shift are: ");
        for(Qualifications qualifications : Qualifications.values()){
            System.out.println("works as "+qualifications.name()+":");
            for( Worker worker : workers.get(qualifications)){
                System.out.println(i+") name:"+worker.getFirstName()+" "+worker.getLastName()+"ID:"+worker.getID());
                i++;
            }
        }
    }

    public void setWorkers(EnumMap<Qualifications, List<Worker>> workers) {
        this.workers = workers;
    }

    public List<Worker> getCashiers() {
        return workers.get(Qualifications.Cashier);
    }

    public void setCashiers(List<Worker> cashiers) {
         workers.remove(Qualifications.Cashier);
         workers.put(Qualifications.Cashier,cashiers);
    }

    public List<Worker> getStoreKeepers() {
        return workers.get(Qualifications.Storekeeper);
    }

    public void setStoreKeepers(List<Worker> storeKeepers) {
        workers.remove(Qualifications.Storekeeper);
        workers.put(Qualifications.Storekeeper,storeKeepers);
    }

    public List<Worker> getArrangers() {
        return workers.get(Qualifications.Arranger);
    }

    public void setArrangers(List<Worker> arrangers) {
        workers.remove(Qualifications.Arranger);
        workers.put(Qualifications.Arranger,arrangers);
    }

    public List<Worker> getGuards() {
        return workers.get(Qualifications.Guard);
    }

    public void setGuards(List<Worker> guards) {
        workers.remove(Qualifications.Guard);
        workers.put(Qualifications.Guard,guards);
    }

    public List<Worker> getAssistants() {
        return workers.get(Qualifications.Assistant);
    }

    public void setAssistants(List<Worker> assistants) {
        workers.remove(Qualifications.Assistant);
        workers.put(Qualifications.Assistant,assistants);
    }

    public Worker getShiftManager() {
        return shiftManager;
    }

    public void setShiftManager(Worker shiftManager) {
        this.shiftManager = shiftManager;
    }

    public Shift(LocalDate date, ShiftType type, ShiftDemands demands, List<Worker> cashiers, List<Worker> storeKeepers, List<Worker> arrangers, List<Worker> guards, List<Worker> assistants, Worker shiftManager, int branchID) {
        workers = new EnumMap<Qualifications, List<Worker>>(Qualifications.class);
        this.date = date;
        this.type = type;
        this.demands = demands;
        setArrangers(arrangers);
        setAssistants(assistants);
        setCashiers(cashiers);
        setGuards(guards);
        setStoreKeepers(storeKeepers);
        this.shiftManager = shiftManager;
        this.branchID = branchID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ShiftType getType() {
        return type;
    }

    public void setType(ShiftType type) {
        this.type = type;
    }

    public ShiftDemands getDemands() {
        return demands;
    }

    public void setDemands(ShiftDemands demands) {
        this.demands = demands;
    }

    public Qualifications findWorkerJob(Worker worker) {

        for(Qualifications qualifications : Qualifications.values()){
            if(workers.get(qualifications).contains(worker))
                return qualifications;
        }
        return null;
    }

    @Override
    public String toString() {
        String st = type + " shift at " + "date=" + date +
                ", shift demands=" + demands +
                ", shiftManager=" + shiftManager +
                ", branchID= " + branchID;
         int i =1;
        for(Qualifications qualifications : Qualifications.values()){
            st = st + "works as "+qualifications.name()+":";
            for( Worker worker : workers.get(qualifications)){
                st = st+ i+") name:"+worker.getFirstName()+" "+worker.getLastName()+"ID:"+worker.getID();
                i++;
            }
        }
    return st;
    }
}
