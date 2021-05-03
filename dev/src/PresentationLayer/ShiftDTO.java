package PresentationLayer;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;

public class ShiftDTO {
    private LocalDate date;
    private ShiftTypeDTO type;
    private ShiftDemandsDTO demands;
    private EnumMap<QualificationsDTO,List<WorkerDTO>> workers;
    private WorkerDTO shiftManager;
    private int branchID;



//    public ShiftDTO(LocalDate date, ShiftTypeDTO type, ShiftDemandsDTO demands, List<WorkerDTO> cashiers, List<WorkerDTO> storeKeepers, List<WorkerDTO> arrangers, List<WorkerDTO> guards, List<WorkerDTO> assistants) {
//        workers = new EnumMap<QualificationsDTO, List<WorkerDTO>>(QualificationsDTO.class);
//        this.date = date;
//        this.type = type;
//        this.demands = demands;
//        setArrangers(arrangers);
//        setAssistants(assistants);
//        setCashiers(cashiers);
//        setGuards(guards);
//        setStoreKeepers(storeKeepers);
//        this.shiftManager = shiftManager;
//        this.branchID = branchID;
//    }

    public int getBranchID() {
        return branchID;
    }



    public EnumMap<QualificationsDTO, List<WorkerDTO>> getWorkers() {
        return workers;
    }

    public void printWorkersByQualification(QualificationsDTO qualifications){
        List<WorkerDTO>workerList= workers.get(qualifications);
        int index=1;
        System.out.println("The "+ qualifications.name()+ "of this shift are:");
        for(WorkerDTO w : workerList) {
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
        for(QualificationsDTO qualifications : QualificationsDTO.values()){
            System.out.println("works as "+qualifications.name()+":");
            for( WorkerDTO worker : workers.get(qualifications)){
                System.out.println(i+") name:"+worker.getFirstName()+" "+worker.getLastName()+"ID:"+worker.getID());
                i++;
            }
        }
    }

    public void setWorkers(EnumMap<QualificationsDTO, List<WorkerDTO>> workers) {
        this.workers = workers;
    }

    public List<WorkerDTO> getCashiers() {
        return workers.get(QualificationsDTO.Cashier);
    }

    public void setCashiers(List<WorkerDTO> cashiers) {
        workers.remove(QualificationsDTO.Cashier);
        workers.put(QualificationsDTO.Cashier,cashiers);
    }

    public List<WorkerDTO> getStoreKeepers() {
        return workers.get(QualificationsDTO.Storekeeper);
    }

    public void setStoreKeepers(List<WorkerDTO> storeKeepers) {
        workers.remove(QualificationsDTO.Storekeeper);
        workers.put(QualificationsDTO.Storekeeper,storeKeepers);
    }

    public List<WorkerDTO> getArrangers() {
        return workers.get(QualificationsDTO.Arranger);
    }

    public void setArrangers(List<WorkerDTO> arrangers) {
        workers.remove(QualificationsDTO.Arranger);
        workers.put(QualificationsDTO.Arranger,arrangers);
    }

    public List<WorkerDTO> getGuards() {
        return workers.get(QualificationsDTO.Guard);
    }

    public void setGuards(List<WorkerDTO> guards) {
        workers.remove(QualificationsDTO.Guard);
        workers.put(QualificationsDTO.Guard,guards);
    }

    public List<WorkerDTO> getAssistants() {
        return workers.get(QualificationsDTO.Assistant);
    }

    public void setAssistants(List<WorkerDTO> assistants) {
        workers.remove(QualificationsDTO.Assistant);
        workers.put(QualificationsDTO.Assistant,assistants);
    }

    public WorkerDTO getShiftManager() {
        return shiftManager;
    }

    public void setShiftManager(WorkerDTO shiftManager) {
        this.shiftManager = shiftManager;
    }

    public ShiftDTO(LocalDate date, ShiftTypeDTO type, ShiftDemandsDTO demands, List<WorkerDTO> cashiers, List<WorkerDTO> storeKeepers, List<WorkerDTO> arrangers, List<WorkerDTO> guards, List<WorkerDTO> assistants, WorkerDTO shiftManager, int branchID) {
        workers = new EnumMap<QualificationsDTO, List<WorkerDTO>>(QualificationsDTO.class);
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

    public QualificationsDTO findWorkerJob(WorkerDTO worker) {

        for(QualificationsDTO qualifications : QualificationsDTO.values()){
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
        for(QualificationsDTO qualifications : QualificationsDTO.values()){
            st = st + "works as "+qualifications.name()+":";
            for( WorkerDTO worker : workers.get(qualifications)){
                st = st+ i+") name:"+worker.getFirstName()+" "+worker.getLastName()+"ID:"+worker.getID();
                i++;
            }
        }
        return st;
    }
}
