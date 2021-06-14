package PresentationLayer.Workers.DataTransferObjects;

import PresentationLayer.Workers.DataTransferObjects.WorkerDTO;

import java.util.Date;

public class DriverDTO extends WorkerDTO {

    private String licenseType;
    private Date expLicenseDate;
    private boolean busy;

    public DriverDTO(WorkerDTO w,String licenseType,Date expLicenseDate) {
        super(w.getFirstName(), w.getLastName(), w.getID(), w.getBankAccount(), w.getHiringConditions(), w.getAvailableWorkDays() );
        this.licenseType = licenseType;
        this.expLicenseDate=expLicenseDate;
        busy=false;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public Date getExpLicenseDate() {
        return expLicenseDate;
    }

    public boolean isBusy() {
        return busy;
    }


}
