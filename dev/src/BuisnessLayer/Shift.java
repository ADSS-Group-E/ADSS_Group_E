package BuisnessLayer;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;

public class Shift {
    private LocalDate date;
    private ShiftType type;
    private ShiftDemands demands;
    private EnumMap<Qualifications,List<Worker>> workers;
    private Worker shiftManager;

    public EnumMap<Qualifications, List<Worker>> getWorkers() {
        return workers;
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

    public Shift(LocalDate date, ShiftType type, ShiftDemands demands, List<Worker> cashiers, List<Worker> storeKeepers, List<Worker> arrangers, List<Worker> guards, List<Worker> assistants, Worker shiftManager) {
        this.date = date;
        this.type = type;
        this.demands = demands;
        setArrangers(arrangers);
        setAssistants(assistants);
        setCashiers(cashiers);
        setGuards(guards);
        setStoreKeepers(storeKeepers);
        this.shiftManager = shiftManager;
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
}
