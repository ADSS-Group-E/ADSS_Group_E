package BussinessLayer.DriverPackage;

import BussinessLayer.WorkersPackage.Worker;

import java.util.*;

public class Driver /*extends Worker*/ {

    private String licenseType;
    private Date expLicenseDate;
    private boolean busy;

    public Driver(/*Worker baseWorker,*/ String licenseType, Date expLicenseDate) {
        //super(baseWorker.getFirstName(), baseWorker.getLastName(), baseWorker.getID(), baseWorker.getBankAccount(), baseWorker.getHiringConditions(), baseWorker.getAvailableWorkDays(), baseWorker.getQualifications());
        this.licenseType = licenseType;
        this.expLicenseDate = expLicenseDate;
        this.busy = false;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public Date getExpLicenseDate() {
        return expLicenseDate;
    }

    public void setExpLicenseDate(Date expLicenseDate) {
        this.expLicenseDate = expLicenseDate;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setDriving() {
        busy = true;
    }

    public void setNotDriving() {
        busy = false;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "firstName='" + super.getFirstName() + '\'' +
                ", lastName='" + super.getLastName() + '\'' +
                ", ID='" + super.getID() + '\'' +
                ", bankAccount=" + super.getBankAccount() +
                ", startWorkingDay=" + super.getStartWorkingDay() +
                ", hiringConditions=" + super.getHiringConditions() +
                ", availableWorkDays=" + super.getAvailableWorkDays() +
                ", qualifications=" + super.getQualifications() +
                "licenseType='" + licenseType + '\'' +
                ", expLicenseDate=" + expLicenseDate +
                ", busy=" + busy +
                '}';
    }
}
