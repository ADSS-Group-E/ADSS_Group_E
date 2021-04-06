package BuisnessLayer;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Worker {
    private String firstName;
    private String lastName;
    private String ID;
    private BankAccount bankAccount;
    private LocalDate startWorkingDay;
    //private Boolean isCurrentWorker;
    private HiringConditions hiringConditions;
    private AvailableWorkDays availableWorkDays;
    private List<Qualifications> qualifications;

    public List<Qualifications> getQualifications() {
        return qualifications;
    }

    public void setQualifications(List<Qualifications> qualifications) {
        this.qualifications = qualifications;
    }

    public HiringConditions getHiringConditions() {
        return hiringConditions;
    }

    public void setHiringConditions(HiringConditions hiringConditions) {
        this.hiringConditions = hiringConditions;
    }

    public AvailableWorkDays getAvailableWorkDays() {
        return availableWorkDays;
    }

    public void setAvailableWorkDays(AvailableWorkDays availableWorkDays) {
        this.availableWorkDays = availableWorkDays;
    }

    public Worker(String firstName, String lastName, String ID, BankAccount bankAccount, LocalDate startWorkingDay, HiringConditions hiringConditions, AvailableWorkDays availableWorkDays, List<Qualifications> qualifications) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ID = ID;
        this.bankAccount = bankAccount;
        this.startWorkingDay = startWorkingDay;
        this.hiringConditions = hiringConditions;
        this.availableWorkDays = availableWorkDays;
        this.qualifications = qualifications;
    }

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

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public LocalDate getStartWorkingDay() {
        return startWorkingDay;
    }

    public void setStartWorkingDay(LocalDate startWorkingDay) {
        this.startWorkingDay = startWorkingDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Worker)) return false;
        Worker worker = (Worker) o;
        return Objects.equals(getFirstName(), worker.getFirstName()) && Objects.equals(getLastName(), worker.getLastName()) && Objects.equals(getID(), worker.getID()) && Objects.equals(getBankAccount(), worker.getBankAccount()) && Objects.equals(getStartWorkingDay(), worker.getStartWorkingDay()) && Objects.equals(getHiringConditions(), worker.getHiringConditions()) && Objects.equals(getQualifications(), worker.getQualifications());
    }

    @Override
    public String toString() {
        return "Worker{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", ID='" + ID + '\'' +
                ", bankAccount=" + bankAccount +
                ", startWorkingDay=" + startWorkingDay +
                ", hiringConditions=" + hiringConditions +
                ", availableWorkDays=" + availableWorkDays +
                ", qualifications=" + qualifications +
                '}';
    }
}
