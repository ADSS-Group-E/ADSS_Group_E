package BuisnessLayer;

import java.util.Date;

public class Worker {
    private String firstName;
    private String lastName;
    private String ID;
    private String bankAccount;
    private Date startWorkingDay;
    private Boolean isCurrentWorker;
    private HiringConditions hiringConditions;
    private AvailableWorkDays availableWorkDays;


    public Worker(String firstName, String lastName, String ID, String bankAccount, Date startWorkingDay, Boolean isCurrentWorker) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ID = ID;
        this.bankAccount = bankAccount;
        this.startWorkingDay = startWorkingDay;
        this.isCurrentWorker = isCurrentWorker;
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
