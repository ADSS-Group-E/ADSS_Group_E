package BuisnessLayer;

import java.time.LocalDate;
import java.util.List;

public class Shift {
    private LocalDate date;
    private ShiftType type;
    private ShiftDemands demands;
    private List<Worker> cashiers;
    private List<Worker> storeKeepers;
    private List<Worker> arrangers;
    private List<Worker> guards;
    private List<Worker> assistants;
    private Worker shiftManager;

    public List<Worker> getCashiers() {
        return cashiers;
    }

    public void setCashiers(List<Worker> cashiers) {
        this.cashiers = cashiers;
    }

    public List<Worker> getStoreKeepers() {
        return storeKeepers;
    }

    public void setStoreKeepers(List<Worker> storeKeepers) {
        this.storeKeepers = storeKeepers;
    }

    public List<Worker> getArrangers() {
        return arrangers;
    }

    public void setArrangers(List<Worker> arrangers) {
        this.arrangers = arrangers;
    }

    public List<Worker> getGuards() {
        return guards;
    }

    public void setGuards(List<Worker> guards) {
        this.guards = guards;
    }

    public List<Worker> getAssistants() {
        return assistants;
    }

    public void setAssistants(List<Worker> assistants) {
        this.assistants = assistants;
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
        this.cashiers = cashiers;
        this.storeKeepers = storeKeepers;
        this.arrangers = arrangers;
        this.guards = guards;
        this.assistants = assistants;
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
}
