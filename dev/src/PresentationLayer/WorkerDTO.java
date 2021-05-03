package PresentationLayer;

import java.time.LocalDate;
import java.util.List;

public class WorkerDTO {
    private String firstName;
    private String lastName;
    private String ID;
    private BankAccountDTO bankAccount;
    private LocalDate startWorkingDay;
    //private Boolean isCurrentWorker;
    private HiringConditionsDTO hiringConditions;
    private AvailableWorkDaysDTO availableWorkDays;
    private List<QualificationsDTO> qualifications;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public BankAccountDTO getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountDTO bankAccount) {
        this.bankAccount = bankAccount;
    }

    public LocalDate getStartWorkingDay() {
        return startWorkingDay;
    }

    public void setStartWorkingDay(LocalDate startWorkingDay) {
        this.startWorkingDay = startWorkingDay;
    }

    public HiringConditionsDTO getHiringConditions() {
        return hiringConditions;
    }

    public void setHiringConditions(HiringConditionsDTO hiringConditions) {
        this.hiringConditions = hiringConditions;
    }

    public AvailableWorkDaysDTO getAvailableWorkDays() {
        return availableWorkDays;
    }

    public void setAvailableWorkDays(AvailableWorkDaysDTO availableWorkDays) {
        this.availableWorkDays = availableWorkDays;
    }

    public List<QualificationsDTO> getQualifications() {
        return qualifications;
    }

    public void setQualifications(List<QualificationsDTO> qualifications) {
        this.qualifications = qualifications;
    }

    public WorkerDTO(String firstName, String lastName, String ID, BankAccountDTO bankAccount, HiringConditionsDTO hiringConditions, AvailableWorkDaysDTO availableWorkDays, List<QualificationsDTO> qualifications) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ID = ID;
        this.bankAccount = bankAccount;
        this.startWorkingDay = LocalDate.now();
        this.hiringConditions = hiringConditions;
        this.availableWorkDays = availableWorkDays;
        this.qualifications = qualifications;
    }
}
