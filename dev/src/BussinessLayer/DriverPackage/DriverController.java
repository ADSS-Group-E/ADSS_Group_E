package BussinessLayer.DriverPackage;

import BussinessLayer.WorkersPackage.Worker;
import PresentationLayer.WorkerDTO;

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


    public Driver createDriver(Worker w, String licenseType, Date expLicenseDate) throws Exception {
        Date date = new Date();
        if(drivers.containsKey(w.getID()))
            throw new Exception("The Driver Already Exists");
        if(expLicenseDate.compareTo(date) < 0)
            throw new Exception("License Date Already Expired");
        Driver driver = new Driver(w, licenseType, expLicenseDate);
        return driver;
    }

    public void addDriver(Driver driver) throws Exception {
        if(drivers.containsKey(driver.getID()))
            throw new Exception("The Driver Already Exists");
        this.drivers.put(driver.getID(), driver);
    }

    public void removeDriver(Driver driver) throws Exception {
        if(!drivers.containsKey(driver.getID()))
            throw new Exception("The Driver Doesn't Exists");
        this.drivers.remove(driver.getID());
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

    public List<Driver> getDrivers()
    {
        List<Driver> driversList=new LinkedList<>();
        for (Driver d:
            drivers.values()) {
            driversList.add(d);
        }
        if (driversList.size()==0) return null;
        return driversList;
    }
}
