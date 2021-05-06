package PresentationLayer;

import java.util.Date;
import java.util.List;

public class DriverDTO extends WorkerDTO {

    private String licenseType;
    private Date expLicenseDate;
    private boolean busy;

    public DriverDTO(String firstName, String lastName, String ID, BankAccountDTO bankAccount, HiringConditionsDTO hiringConditions, AvailableWorkDaysDTO availableWorkDays, List<QualificationsDTO> qualifications, String licenseType,Date expLicenseDate) {
        super(firstName, lastName, ID, bankAccount, hiringConditions, availableWorkDays, qualifications);
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
