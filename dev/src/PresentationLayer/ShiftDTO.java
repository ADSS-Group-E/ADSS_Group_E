package PresentationLayer;

import BuisnessLayer.Qualifications;
import BuisnessLayer.ShiftDemands;
import BuisnessLayer.ShiftType;
import BuisnessLayer.Worker;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;

public class ShiftDTO {
    private LocalDate date;
    private ShiftTypeDTO type;
    private ShiftDemandsDTO demands;
    private EnumMap<QualificationsDTO, List<WorkerDTO>> workers;
    private WorkerDTO shiftManager;
    private int branchID;

    public ShiftDTO(LocalDate date, ShiftTypeDTO type, ShiftDemandsDTO demands, EnumMap<QualificationsDTO, List<WorkerDTO>> workers, WorkerDTO shiftManager, int branchID) {
        this.date = date;
        this.type = type;
        this.demands = demands;
        this.workers = workers;
        this.shiftManager = shiftManager;
        this.branchID = branchID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ShiftTypeDTO getType() {
        return type;
    }

    public void setType(ShiftTypeDTO type) {
        this.type = type;
    }

    public ShiftDemandsDTO getDemands() {
        return demands;
    }

    public void setDemands(ShiftDemandsDTO demands) {
        this.demands = demands;
    }

    public EnumMap<QualificationsDTO, List<WorkerDTO>> getWorkers() {
        return workers;
    }

    public void setWorkers(EnumMap<QualificationsDTO, List<WorkerDTO>> workers) {
        this.workers = workers;
    }

    public WorkerDTO getShiftManager() {
        return shiftManager;
    }

    public void setShiftManager(WorkerDTO shiftManager) {
        this.shiftManager = shiftManager;
    }

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }
}
