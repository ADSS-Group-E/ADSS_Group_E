package BusinessLayer.Workers_Transport.DriverPackage;

import BusinessLayer.Workers_Transport.WorkersPackage.BankAccount;
import BusinessLayer.Workers_Transport.WorkersPackage.Worker;
import PresentationLayer.Workers_Transport.WorkerDTO;
import BusinessLayer.Workers_Transport.WorkersPackage.WorkersFacade;
import java.util.*;

public class Driver extends Worker {

    private String licenseType;
    private Date expLicenseDate;
    private boolean busy;


    public Driver(Worker w,String licenseType, Date expLicenseDate) {
        super(w.getFirstName(), w.getLastName(), w.getID(), w.getBankAccount(), w.getHiringConditions(), w.getAvailableWorkDays(),w.getQualifications());
        this.licenseType = licenseType;
        this.expLicenseDate = expLicenseDate;
        this.busy = false;
    }

    public Driver(Worker w,String licenseType, Date expLicenseDate,boolean busy) {
        super(w.getFirstName(), w.getLastName(), w.getID(), w.getBankAccount(), w.getHiringConditions(), w.getAvailableWorkDays(),w.getQualifications());
        this.licenseType = licenseType;
        this.expLicenseDate = expLicenseDate;
        this.busy = busy;
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
                "licenseType='" + licenseType + '\'' +
                ", expLicenseDate=" + expLicenseDate +
                ", busy=" + busy +
                '}';
    }

}
