package BussinessLayer.DriverPackage;

import java.util.*;

public class DriverController {

    private Map<String, Driver> drivers;
    private static DriverController driverController = null;

    private DriverController()
    {
        this.drivers = new HashMap<>();
    }

    public static DriverController getInstance()
    {
        if(driverController == null)
            driverController = new DriverController();
        return driverController;
    }

    public Driver getDriver(String id) throws Exception {
        if(!drivers.containsKey(id))
            throw new Exception("The Driver Doesn't Exists");
        return drivers.get(id);
    }

    public Driver createDriver(String id, String name, String licenseType, Date expLicenseDate) throws Exception {
        Date date = new Date();
        if(drivers.containsKey(id))
            throw new Exception("The Driver Already Exists");
        if(expLicenseDate.compareTo(date) < 0)
            throw new Exception("License Date Already Expired");
        Driver driver = new Driver(id, name, licenseType, expLicenseDate);
        return driver;
    }

    public void addDriver(Driver driver) throws Exception {
        if(drivers.containsKey(driver.getId()))
            throw new Exception("The Driver Already Exists");
        this.drivers.put(driver.getId(), driver);
    }

    public void removeDriver(Driver driver) throws Exception {
        if(!drivers.containsKey(driver.getId()))
            throw new Exception("The Driver Doesn't Exists");
        this.drivers.remove(driver.getId());
    }

    public void updateExpDate(String id, Date expLicenseDate) throws Exception {
        Date date = new Date();
        if(expLicenseDate.compareTo(date) < 0)
            throw new Exception("License Date Already Expired");
        if(!drivers.containsKey(id))
            throw new Exception("The Driver Doesn't Exists");
        drivers.get(id).setExpLicenseDate(expLicenseDate);
    }

    public void updateLicenseType(String id, String licenseType) throws Exception {
        if(!drivers.containsKey(id))
            throw new Exception("The Driver Doesn't Exists");
        drivers.get(id).setLicenseType(licenseType);
    }

    public void setDriverToDrive(String id) throws Exception {
        if(!drivers.containsKey(id))
            throw new Exception("The Driver Doesn't Exists");
        drivers.get(id).setDriving();
    }

    public void setDriverNotToDrive(String id) throws Exception {
        if(!drivers.containsKey(id))
            throw new Exception("The Driver Doesn't Exists");
        drivers.get(id).setNotDriving();
    }

    public Map<String, Driver> getDrivers()
    {
        return drivers;
    }
}
