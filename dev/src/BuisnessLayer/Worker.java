package BuisnessLayer;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Worker {
    private String firstName;
    private String lastName;
    private String ID;
    private String bankAccount;
    private Date startWorkingDay;
    private Boolean isCurrentWorker;
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

    public Worker(String firstName, String lastName, String ID, String bankAccount, Date startWorkingDay, HiringConditions hiringConditions, AvailableWorkDays availableWorkDays, List<Qualifications> qualifications) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ID = ID;
        this.bankAccount = bankAccount;
        this.startWorkingDay = startWorkingDay;
        this.isCurrentWorker = true;
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

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Date getStartWorkingDay() {
        return startWorkingDay;
    }

    public void setStartWorkingDay(Date startWorkingDay) {
        this.startWorkingDay = startWorkingDay;
    }

    public Boolean getCurrentWorker() {
        return isCurrentWorker;
    }

    public void setCurrentWorker(Boolean currentWorker) {
        isCurrentWorker = currentWorker;
    }
}
