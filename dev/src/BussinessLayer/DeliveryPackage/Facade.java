package BussinessLayer.DeliveryPackage;
import BussinessLayer.DriverPackage.Driver;
import BussinessLayer.DriverPackage.DriverController;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

public class Facade {
    private final Map<String, Double> licenseTypes = new HashMap<String, Double>() {
        {
            put("A", 5000.0);
            put("B", 10000.0);
            put("C", 15000.0);
            put("D", 20000.0);
        }
    };

    private Map<String, Delivery> deliveries;
    private static Facade facade = null;
    private DriverController driverController;
    private TruckController truckController;
    private LocationController locationController;
    private OrderController orderController;

    private Facade()
    {
        this.deliveries = new HashMap<>();
        driverController = DriverController.getInstance();
        truckController = TruckController.getInstance();
        locationController = LocationController.getInstance();
        orderController = OrderController.getInstance();
    }

    public static Facade getInstance()
    {
        if(facade == null)
            facade = new Facade();
        return facade;
    }


    public Delivery createDelivery(String id, Date deliveryDate, Time leavingTime, String driverId, String srcLocation, List<String> targets, String truckId, List<String> orders) throws Exception
    {
        double totalWeight = 0;
        for (String s : orders)
        {
            if(!this.getOrders().containsKey(s))
                throw new Exception("The Order Does Not Exist");
            totalWeight += orderController.getOrder(s).getWeight();
        }
        totalWeight += truckController.getTruck(truckId).getNetoWeight();
        Date date = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        Calendar cal = Calendar.getInstance();
        Time time = new Time(timeFormat.parse(timeFormat.format(cal.getTime())).getTime());
        if (deliveries.containsKey(id))
            throw new Exception("The Delivery Already Exists");
        if (deliveryDate.compareTo(date) < 0 )
            throw new Exception("The Delivery Date Must Be A Future Date");
        if(driverController.getDriver(driverId).getExpLicenseDate().compareTo(deliveryDate) < 0)
            throw new Exception("The Driver Cannot Drive the Delivery With Expired License");
        if (leavingTime.compareTo(time) < 0)
            throw new Exception("The Delivery Time Must Be a Future Time");
        if(totalWeight <= 0)
            throw new Exception("The Weight Must Be a Positive Number");
        if(totalWeight > truckController.getTruck(truckId).getTotalWeight())
            throw new Exception("The Weight of the Orders and the Truck Exceeds the Max Weight");
        if(truckController.getTruck(truckId).isUsed())
            throw new Exception("The Truck Is Used");
        if(driverController.getDriver(driverId).isBusy())
        throw new Exception("The Driver Is Busy");
        if(licenseTypes.get(driverController.getDriver(driverId).getLicenseType()) < truckController.getTruck(truckId).getTotalWeight())
            throw new Exception("The Driver Cannot Drive the Truck");
        for (Delivery d : deliveries.values())
        {
            if(d.getDeliveryDate().compareTo(deliveryDate) == 0)
                if(d.getTruckId().compareTo(truckId) == 0 || d.getDriverId().compareTo(driverId) == 0)
                    throw new Exception("The Driver or the Truck Already in Use");
        }
        if(!checkArea(targets))
            throw new Exception("Not All Deliveries Are From the Same Area");
        Delivery delivery = new Delivery(id, deliveryDate, leavingTime, driverId, srcLocation, targets, totalWeight, truckId, orders);
        facade.addDelivery(delivery);
        return delivery;
    }

    public Delivery getDelivery(String id) throws Exception {
        if (!deliveries.containsKey(id))
            throw new Exception("The Delivery Doesn't Exists");
        return deliveries.get(id);
    }

    public Location getLocation(String id) throws Exception {
        if (locationController.getLocation(id) == null)
            throw new Exception("The Location Doesn't Exists");
        return locationController.getLocation(id);
    }



    public boolean checkArea(List<String> locationAreas){
        try
        {
            for (String id : locationAreas)
                if(locationController.getLocation(id).getShippingArea().compareTo(locationController.getLocation(locationAreas.get(0)).getShippingArea()) != 0)
                    return false;
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public void addDelivery(Delivery delivery) throws Exception {
        if (deliveries.containsKey(delivery.getId()))
            throw new Exception("The Delivery Already Exists");
        this.deliveries.put(delivery.getId(), delivery);
    }

    public void removeDelivery(String id) throws Exception {
        if (!deliveries.containsKey(id))
            throw new Exception("The Delivery Doesn't Exists");
        if(deliveries.get(id).getStatus().equals(Delivery.Status.InProgress) || deliveries.get(id).getStatus().equals(Delivery.Status.Done))
            throw new Exception("Edit Delivery Details Only for Created Delivery");
        this.deliveries.remove(id);
    }

    public void updateDeliveryDate(String id, Date deliveryDay) throws Exception {
        Date date = new Date();
        if (!deliveries.containsKey(id))
            throw new Exception("The Delivery Doesn't Exists");
        if (deliveryDay.compareTo(date) < 0 )
            throw new Exception("Delivery Date Must Be Future Date");
        if(deliveries.get(id).getStatus().equals(Delivery.Status.InProgress) || deliveries.get(id).getStatus().equals(Delivery.Status.Done))
            throw new Exception("Edit Delivery Details Only for Created Delivery");
        deliveries.get(id).setDeliveryDate(deliveryDay);
    }

    public void updateLeavingTime(String id, Time leavingTime) throws Exception {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        Calendar cal = Calendar.getInstance();
        Time time = new Time(timeFormat.parse(timeFormat.format(cal.getTime())).getTime());
        if (!deliveries.containsKey(id))
            throw new Exception("The Delivery Doesn't Exists");
        if (leavingTime.compareTo(time) < 0)
            throw new Exception("Delivery Time Must Be Future Time");
        if(!deliveries.get(id).getStatus().equals(Delivery.Status.Created))
            throw new Exception("Edit Delivery Details Only for Created Delivery");
        deliveries.get(id).setLeavingTime(leavingTime);
    }

    public void updateDriverId(String id, String driverId) throws Exception {
        if (!deliveries.containsKey(id))
            throw new Exception("The Delivery Doesn't Exists");
        if(driverController.getDriver(driverId).getExpLicenseDate().compareTo(deliveries.get(id).getDeliveryDate()) < 0)
            throw new Exception("The Driver's License Is Expired At This Date");
        if(!deliveries.get(id).getStatus().equals(Delivery.Status.Created))
            throw new Exception("Edit Delivery Details Only for Created Delivery");
        for (Delivery d : deliveries.values())
        {
            if(d.getDeliveryDate().compareTo(deliveries.get(id).getDeliveryDate()) == 0 && d.getId().compareTo(id) != 0)
                if(d.getDriverId().compareTo(driverId) == 0)
                    throw new Exception("The Driver Already Busy");
        }
        try {
            Driver d = driverController.getDriver(driverId);
            if(licenseTypes.get(d.getLicenseType()) < truckController.getTruck(deliveries.get(id).getTruckId()).getTotalWeight())
                throw new Exception("The Driver Cannot Drive This Truck, Too Heavy");
            deliveries.get(id).setDriverId(driverId);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void removeOrderAndLocation(String id, String locationId, String orderId) throws Exception {
        if (!deliveries.containsKey(id))
            throw new Exception("The Delivery Doesn't Exists");
        if (!deliveries.get(id).getTargets().contains(locationId))
            throw new Exception("The Target Location Doesn't Exists in the Delivery");
        if (!deliveries.get(id).getOrders().contains(orderId))
            throw new Exception("The Order Doesn't Exists in the Delivery");
        if(!deliveries.get(id).getStatus().equals(Delivery.Status.Created) )
            throw new Exception("Edit Delivery Details Only for Created Delivery");
        try {
            Location l = locationController.getLocation(locationId);
            Order o = orderController.getOrder(orderId);
            deliveries.get(id).removeTargetLocation(locationId);
            deliveries.get(id).removeOrder(orderId);
        } catch (Exception e)
        {
            throw e;
        }
    }

    public void addOrderAndLocation(String id, String locationId, String orderId) throws Exception {
        if (!deliveries.containsKey(id))
            throw new Exception("The Delivery Doesn't Exists");
        if (deliveries.get(id).getOrders().contains(orderId))
            throw new Exception("the order already exists in the delivery");
        if (deliveries.get(id).getTargets().contains(locationId))
            throw new Exception("The Target Location Already Exists in the Delivery");
        if(locationId.compareTo(locationController.getLocation(deliveries.get(id).getTargets().get(0)).getShippingArea()) != 0)
            throw new Exception("The Location Is In Another Area");
        if(deliveries.get(id).getStatus().equals(Delivery.Status.InProgress) || deliveries.get(id).getStatus().equals(Delivery.Status.Done))
            throw new Exception("Edit Delivery Details Is Available Only for Created Delivery");
        try {
            if(deliveries.get(id).getWeight() + orderController.getOrder(orderId).getWeight() >
                    truckController.getTruck(deliveries.get(id).getTruckId()).getTotalWeight())
                throw new Exception("Cannot Add the Order to the Delivery, the Weight of the Delivery Exceeds the Maximal Total Weight of the Truck");
            Order o = orderController.getOrder(orderId);
            Location l = locationController.getLocation(locationId);
            deliveries.get(id).addTargetLocation(locationId);
            deliveries.get(id).addOrder(orderId);

        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void updateWeight(String id, double weight) throws Exception {
        if (!deliveries.containsKey(id))
            throw new Exception("The Delivery Doesn't Exists");
        if(weight <= 0)
            throw new Exception("The Weight Is Must Be a Positive Number");
        if(weight + truckController.getTruck(deliveries.get(id).getTruckId()).getNetoWeight() > truckController.getTruck(deliveries.get(id).getTruckId()).getTotalWeight())
            throw new Exception("The Weight of the Order and the Truck Exceeds the Maximal Weight");
        if(!deliveries.get(id).getStatus().equals(Delivery.Status.Created))
            throw new Exception("Edit Delivery Details Is Available Only for Created Delivery");
        deliveries.get(id).setWeight(weight);
    }

    public void updateTruckId(String id, String truckId) throws Exception {
        if (!deliveries.containsKey(id))
            throw new Exception("The Delivery Doesn't Exists");
        if(!deliveries.get(id).getStatus().equals(Delivery.Status.Created))
            throw new Exception("Edit Delivery Details Is Available Only for Created Delivery");
        for (Delivery d : deliveries.values())
        {
            if(d.getDeliveryDate().compareTo(deliveries.get(id).getDeliveryDate()) == 0 && d.getId().compareTo(id) != 0)
                if(d.getTruckId().compareTo(truckId) == 0)
                    throw new Exception("The Truck Already in Use");
        }
        try {
            Truck t = truckController.getTruck(truckId);
            if(licenseTypes.get(driverController.getDriver(deliveries.get(id).getDriverId()).getLicenseType()) < truckController.getTruck(truckId).getTotalWeight())
                throw new Exception("The Driver Cannot Drive the Truck, Too Heavy");
            deliveries.get(id).setTruckId(truckId);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void updateStatus(String id, String status) throws Exception {
        if (!deliveries.containsKey(id))
            throw new Exception("The Delivery Doesn't Exists");
        if(status.compareTo("InProgress") != 0 && status.compareTo("Done") != 0)
            throw new Exception("Status Can Be Changed Only to InProgress or Done");
        if(status.compareTo("InProgress") == 0 && deliveries.get(id).getWeight() <= truckController.getTruck(deliveries.get(id).getTruckId()).getTotalWeight())
        {
            deliveries.get(id).setStatus(Delivery.Status.InProgress);
            truckController.getTruck(deliveries.get(id).getTruckId()).setUsed();
            driverController.getDriver(deliveries.get(id).getDriverId()).setDriving();
        }
        else
            if(status.compareTo("InProgress") == 0)
                throw new Exception("The Weight of the Delivery Is Bigger Than the Total Weight Possible\n" +
                    "Please Rearrange the Delivery - Delivery Process Cannot Start");
        if(status.compareTo("Done") == 0)
        {
            deliveries.get(id).setStatus(Delivery.Status.Done);
            truckController.getTruck(deliveries.get(id).getTruckId()).setNotUsed();
            driverController.getDriver(deliveries.get(id).getDriverId()).setNotDriving();
        }
    }

    public Order createOrder(String id, Map<String, Integer> items, String supplierId, String locationId, double totalWeight) throws Exception
    {
        try
        {
            Order o = orderController.createOrder(id, items, supplierId, locationId, totalWeight);
            orderController.addOrder(o);
            return o;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public Map<String, Order> getOrders()
    {
        return orderController.getOrders();
    }

    public void addOrder(Order order) throws Exception
    {
        try
        {
            orderController.addOrder(order);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void removeOrder(String id) throws Exception
    {
        for (String deliveryId : deliveries.keySet())
        {
            if(deliveries.get(deliveryId).getOrders().contains(id) && deliveries.get(deliveryId).getStatus().equals(Delivery.Status.InProgress))
                throw new Exception("Cannot Remove an Order From a Delivery in Transit");
            if(deliveries.get(deliveryId).getOrders().contains(id) && deliveries.get(deliveryId).getStatus().equals(Delivery.Status.Done))
                throw new Exception("Cannot Remove an Order From a Delivery That Done Already");
        }
        try
        {
            orderController.removeOrder(orderController.getOrder(id));
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void addItem(String id, String item, int quantity) throws Exception
    {
        for (String deliveryId : deliveries.keySet())
        {
            if(deliveries.get(deliveryId).getOrders().contains(id) && deliveries.get(deliveryId).getStatus().equals(Delivery.Status.InProgress))
                throw new Exception("Cannot Add an Item to an Order of a Delivery in Transit");
            if(deliveries.get(deliveryId).getOrders().contains(id) && deliveries.get(deliveryId).getStatus().equals(Delivery.Status.Done))
                throw new Exception("Cannot Add an Item to an Order of a Delivery That Delivered Already");
        }
        try
        {
            orderController.addItem(id, item, quantity);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void removeItem(String id, String item) throws Exception
    {
        for (String deliveryId : deliveries.keySet())
        {
            if(deliveries.get(deliveryId).getOrders().contains(id) && deliveries.get(deliveryId).getStatus().equals(Delivery.Status.InProgress))
                throw new Exception("Cannot Remove an Item to an Order of a Delivery in Transit");
            if(deliveries.get(deliveryId).getOrders().contains(id) && deliveries.get(deliveryId).getStatus().equals(Delivery.Status.Done))
                throw new Exception("Cannot Remove an Item to an Order of a Delivery That Delivered Already");
        }
        try
        {
            orderController.removeItem(id, item);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void updateQuantity(String id, String item, int quantity) throws Exception
    {
        for (String deliveryId : deliveries.keySet())
        {
            if(deliveries.get(deliveryId).getOrders().contains(id) && deliveries.get(deliveryId).getStatus().equals(Delivery.Status.InProgress))
                throw new Exception("Cannot Change Quantity of an Item to an Order of a Delivery That in Transit");
            if(deliveries.get(deliveryId).getOrders().contains(id) && deliveries.get(deliveryId).getStatus().equals(Delivery.Status.Done))
                throw new Exception("Cannot Change Quantity of an Item to an Order of a Delivery That Delivered Already");
        }
        try
        {
            orderController.updateQuantity(id, item, quantity);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void updateTotalWeight(String id, double totalWeight) throws Exception
    {
        for (String deliveryId : deliveries.keySet())
        {
            if(deliveries.get(deliveryId).getOrders().contains(id) && deliveries.get(deliveryId).getStatus().equals(Delivery.Status.InProgress))
                throw new Exception("Cannot Change Weight of an Order That Belongs to a Delivery That in Transit");
            if(deliveries.get(deliveryId).getOrders().contains(id) && deliveries.get(deliveryId).getStatus().equals(Delivery.Status.Done))
                throw new Exception("Cannot Change Weight of an Order That Belongs to a Delivery That Delivered Already");
        }
        try
        {
            for (String deliveryId : deliveries.keySet())
            {
                if(deliveries.get(deliveryId).getOrders().contains(id) &&
                        deliveries.get(deliveryId).getWeight() + totalWeight <= truckController.getTruck(deliveries.get(deliveryId).getTruckId()).getTotalWeight())
                    deliveries.get(deliveryId).setWeight(deliveries.get(deliveryId).getWeight() + totalWeight);
                else
                    if(deliveries.get(deliveryId).getOrders().contains(id))
                        throw new Exception("The Weight of Delivery: " + deliveryId + " Passed the Max Weight Possible");
            }
            orderController.updateTotalWeight(id, totalWeight);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public Location createLocation(String id, String name, String address, String telNumber, String contactName, String shippingArea) throws Exception
    {
        try
        {
            Location l = locationController.createLocation(id, name, address, telNumber, contactName, shippingArea);
            locationController.addLocation(l);
            return l;
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void addLocation(Location location) throws Exception
    {
        try
        {
            locationController.addLocation(location);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void removeLocation(String id) throws Exception
    {
        try
        {
            locationController.removeLocation(locationController.getLocation(id));
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void updateTelNumber(String id, String telNumber) throws Exception
    {
        try
        {
            locationController.updateTelNumber(id, telNumber);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void updateContactName(String id, String contactName) throws Exception
    {
        try
        {
            locationController.updateContactName(id, contactName);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public Truck createTruck(String id, String model, double netoWeight, double totalWeight) throws Exception
    {
        try
        {
            Truck t = truckController.createTruck(id, model, netoWeight, totalWeight);
            truckController.addTruck(t);
            return t;
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void addTruck(Truck truck) throws Exception
    {
        try
        {
            truckController.addTruck(truck);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public Truck getTruck(String id) throws Exception {
        try
        {
            return truckController.getTruck(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void removeTruck(String id) throws Exception
    {
        try
        {
            truckController.removeTruck(truckController.getTruck(id));
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void setTruckUsed(String id) throws Exception
    {
        try
        {
            truckController.setTruckUsed(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void setTruckNotUsed(String id) throws Exception
    {
        try
        {
            truckController.setTruckNotUsed(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public Driver createDriver(String id, String name, String licenseType, Date expLicenseDate) throws Exception
    {
        try
        {
            if(!licenseTypes.containsKey(licenseType))
                throw new Exception("Not Valid License Type");
            Driver d = driverController.createDriver(id, name, licenseType, expLicenseDate);
            driverController.addDriver(d);
            return d;
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public Driver getDriver(String id) throws Exception {
        try
        {
            return driverController.getDriver(id);
        }
        catch (Exception e)
        {
            throw e;
        }

    }

    public void addDriver(Driver driver) throws Exception
    {
        try
        {
            driverController.addDriver(driver);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void removeDriver(String id) throws Exception
    {
        try
        {
            driverController.removeDriver(driverController.getDriver(id));
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void updateExpDate(String id, Date expLicenseDate) throws Exception
    {
        try
        {
            driverController.updateExpDate(id, expLicenseDate);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void updateLicenseType(String id, String licenseType) throws Exception
    {
        try
        {
            if(!licenseTypes.containsKey(licenseType))
                throw new Exception("Not Valid License Type");
            driverController.updateLicenseType(id, licenseType);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void setDriverToDrive(String id) throws Exception
    {
        try
        {
            driverController.setDriverToDrive(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void setDriverNotToDrive(String id) throws Exception
    {
        try
        {
            driverController.setDriverNotToDrive(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void displayDeliveries(){
        System.out.println("Deliveries:\n");
        for (Delivery delivery: deliveries.values()) {
            System.out.println(delivery.toString()+"\n");
        }
    }

    public Map<String, Delivery> getDeliveries()
    {
        return deliveries;
    }
    public Map<String, Driver> getDrivers()
    {
        return driverController.getDrivers();
    }
    public Map<String, Truck> getTrucks()
    {
        return truckController.getTrucks();
    }
    public Map<String, Location> getLocations()
    {
        return locationController.getLocations();
    }
}
