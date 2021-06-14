package BusinessLayer.Workers_Transport.DeliveryPackage;

import BusinessLayer.Workers_Transport.DriverPackage.Driver;
import BusinessLayer.Workers_Transport.DriverPackage.DriverController;
import BusinessLayer.Workers_Transport.Response;
import BusinessLayer.Workers_Transport.WorkersPackage.ShiftDemands;
import BusinessLayer.Workers_Transport.WorkersPackage.WorkersFacade;
import DataAccessLayer.Workers_Transport.Transports.DTO;
import DataAccessLayer.Workers_Transport.Transports.Drivers;
import DataAccessLayer.Workers_Transport.Workers.Shifts;

import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class DeliveryFacade {
    WorkersFacade wFacade=WorkersFacade.getInstance();
    private final Map<String, Double> licenseTypes = new HashMap<String, Double>() {
        {
            put("A", 5000.0);
            put("B", 10000.0);
            put("C", 15000.0);
            put("D", 20000.0);
        }
    };

    private Map<String, Delivery> deliveries;
    private static DeliveryFacade deliveryFacade = null;
    private DriverController driverController;
    private TruckController truckController;
    private LocationController locationController;
    private OrderController orderController;

    private DeliveryFacade()
    {
        this.deliveries = new HashMap<>();
        driverController = DriverController.getInstance();
        truckController = TruckController.getInstance();
        locationController = LocationController.getInstance();
        orderController = OrderController.getInstance();
    }

    public static DeliveryFacade getInstance()
    {
        if(deliveryFacade == null)
            deliveryFacade = new DeliveryFacade();
        return deliveryFacade;
    }


    public Delivery createDelivery(String id, Date deliveryDay, Time leavingTime, String driverId, int srcLocation, List<Integer> targetLocation,
                                   String truckId, List<Integer> orders) throws Exception
    {
        double weight = 0.0;
        for (Integer s : orders)
        {
            if(DataAccessLayer.Workers_Transport.Transports.Order.checkOrder(s)==null)
                throw new Exception("the order doesn't exist");
            weight += orderController.getOrder(s).getTotalWeight();
        }
        weight += truckController.getTruck(truckId).getNetoWeight();
        Date date = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        Calendar cal = Calendar.getInstance();
        Time time = new Time(timeFormat.parse(timeFormat.format(cal.getTime())).getTime());
        //if (DataAccessLayer.Workers_Transport.Transports.Delivery.checkDelivery(id)!=null)
            //throw new Exception("the delivery already exists");
        if (deliveryDay.compareTo(date) < 0 )
            throw new Exception("delivery date must be future date");
        if (leavingTime.compareTo(time) < 0)
            throw new Exception("delivery time must be future time");
        if(weight <= 0)
            throw new Exception("weight must be greater than 0");
        if(weight > truckController.getTruck(truckId).getTotalWeight())
            throw new Exception("the weight of the order and the truck bigger than the max weight");
        if(DataAccessLayer.Workers_Transport.Transports.Delivery.checkDTforDate(id, new java.sql.Date(deliveryDay.getTime()),driverId,truckId))
            throw new Exception("the truck or driver is used at the same date");
        if(locationController.getLocation(srcLocation)==null)
            throw new Exception("source location doesn't exists");
        if(!checkArea(targetLocation))
            throw new Exception("locations are not in the same area");
        if(shiftType(leavingTime)==null)
            throw new Exception("leaving time must be between 08:00-23:00");
        Delivery delivery = new Delivery(id, deliveryDay, leavingTime, driverId, srcLocation, targetLocation, weight, truckId, orders);
        deliveryFacade.addDelivery(delivery);
        updateShiftDemandsDeliveryRequired(deliveryDay,shiftType(leavingTime), srcLocation,true);
        return delivery;
    }
    public static String shiftType(Time time){
        if((time.after(Time.valueOf("08:00:00"))||time.toString().equals("08:00:00"))&&time.before(Time.valueOf("16:00:00")))
            return "Morning";
        if((time.after(Time.valueOf("16:00:00"))||time.toString().equals("16:00:00"))&&(time.before(Time.valueOf("23:00:00"))||time.toString().equals("23:00:00")))
            return "Evening";
        return null;
    }
    public void updateShiftDemandsDeliveryRequired(Date date, String shiftType, int branchID, boolean deliveryRequired) throws SQLException {
        ShiftDemands shiftDemand=Shifts.getShiftDemands(convertToLocalDateViaSqlDate(date),shiftType,branchID);
        Shifts.setDeliveryRequired(convertToLocalDateViaSqlDate(date),shiftType,branchID,deliveryRequired);
    }
    public static LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public Delivery getDelivery(String id) throws Exception {
        Delivery d= DataAccessLayer.Workers_Transport.Transports.Delivery.checkDelivery(id);
        if (d==null)
            throw new Exception("the delivery doesn't exists");
        return d;
    }


    public Location getLocation(int id) throws Exception {
        Location l=DataAccessLayer.Workers_Transport.Transports.Location.checkLocation(id);
        if(l==null)
            throw new Exception("the location doesn't exists");
        return l;
    }



    public boolean checkArea(List<Integer> locationAreas){
        try
        {
            for (Integer id : locationAreas)
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
        //if (DataAccessLayer.Workers_Transport.Transports.Delivery.checkDelivery(delivery.getId())!=null)
            //throw new Exception("the delivery already exists");
        //this.deliveries.put(delivery.getId(), delivery);
        DataAccessLayer.Workers_Transport.Transports.Delivery.insertDelivery(new DTO.Delivery(delivery.getId(),delivery.getDeliveryDate(),delivery.getLeavingTime(),delivery.getDriverId(),delivery.getSrcLocation(),delivery.getWeight(),delivery.getTruckId(),delivery.getStatus().toString()));
        for (int l:delivery.getTargets()
        ) {
            DataAccessLayer.Workers_Transport.Transports.Delivery.insertDeliveryTargetLocation(new DTO.DeliverytargetLocation(delivery.getId(),l));
        }
        for (int o: delivery.getOrders()
        ) {
            DataAccessLayer.Workers_Transport.Transports.Delivery.insertOrdersForDeliveries(new DTO.OrdersForDelivery(delivery.getId(),o));
        }
    }

    public void removeDelivery(String id) throws Exception {
        Delivery d=DataAccessLayer.Workers_Transport.Transports.Delivery.checkDelivery(id);
        if (d==null)
            throw new Exception("the delivery doesn't exists");
        if(d.getStatus().equals(Delivery.Status.InProgress) ||d.getStatus().equals(Delivery.Status.Done))
            throw new Exception("edit delivery details only for Created delivery");
        this.deliveries.remove(id);
        DataAccessLayer.Workers_Transport.Transports.Delivery.deleteDelivery(id);
    }

    public void updateDeliveryDate(String id, Date deliveryDay) throws Exception {
        Date date = new Date();
        Delivery d=DataAccessLayer.Workers_Transport.Transports.Delivery.checkDelivery(id);
        if (d==null)
            throw new Exception("the delivery doesn't exists");
        if (deliveryDay.compareTo(date) < 0 )
            throw new Exception("delivery date must be future date");
        if(d.getStatus().equals(Delivery.Status.InProgress) || d.getStatus().equals(Delivery.Status.Done))
            throw new Exception("edit delivery details only for Created delivery");
        deliveries.get(id).setDeliveryDate(deliveryDay);
        DataAccessLayer.Workers_Transport.Transports.Delivery.updateDeliveryDay(id,deliveryDay);
    }

    public void updateLeavingTime(String id, Time leavingTime) throws Exception {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        Calendar cal = Calendar.getInstance();
        Time time = new Time(timeFormat.parse(timeFormat.format(cal.getTime())).getTime());
        Delivery d=DataAccessLayer.Workers_Transport.Transports.Delivery.checkDelivery(id);
        if (d==null)
            throw new Exception("the delivery doesn't exists");
        if(leavingTime.after(Time.valueOf("22:00:00")) || leavingTime.before(Time.valueOf("7:00:00")))
            throw new Exception("delivery time must be future time");
        if(d.getStatus().equals(Delivery.Status.InProgress) || d.getStatus().equals(Delivery.Status.Done))
            throw new Exception("edit delivery details only for Created delivery");
        deliveries.get(id).setLeavingTime(leavingTime);
        DataAccessLayer.Workers_Transport.Transports.Delivery.updateLeavingTime(id,leavingTime);
    }

    public void updateDriverId(String id, String driverId) throws Exception {
        Delivery d=DataAccessLayer.Workers_Transport.Transports.Delivery.checkDelivery(id);
        if (d==null)
            throw new Exception("the delivery doesn't exists");

        if(d.getStatus().equals(Delivery.Status.InProgress) || d.getStatus().equals(Delivery.Status.Done))
            throw new Exception("edit delivery details only for Created delivery");
        if(DataAccessLayer.Workers_Transport.Transports.Delivery.checkDriverForDel(id,new java.sql.Date(d.getDeliveryDate().getTime()),driverId))
            throw new Exception("the driver already in use");

        try {

            DataAccessLayer.Workers_Transport.Transports.Delivery.updateDriverId(id,driverId);
        }
        catch (Exception e)
        {
            throw e;
        }
        }


    public void removeOrderAndLocation(String id, int locationId, int orderId) throws Exception {
        Delivery d= DataAccessLayer.Workers_Transport.Transports.Delivery.checkDelivery(id);
        if (d==null)
            throw new Exception("the delivery doesn't exists");
        if (!d.getTargets().contains(locationId))
            throw new Exception("the target location doesn't exists in the delivery");
        if (!d.getOrders().contains(orderId))
            throw new Exception("the order doesn't exists in the delivery");
        if(d.getStatus().equals(Delivery.Status.InProgress) || d.getStatus().equals(Delivery.Status.Done))
            throw new Exception("edit delivery details only for Created delivery");
        try {
            /*Location l = locationController.getLocation(locationId);
            Order o = orderController.getOrder(orderId);
            deliveries.get(id).removeTargetLocation(locationId);
            deliveries.get(id).removeOrder(orderId);*/
            DataAccessLayer.Workers_Transport.Transports.Delivery.removeOrderAndLocation(id,locationId,orderId);
        } catch (Exception e)
        {
            throw e;
        }
    }

    public void addOrderAndLocation(String id, int locationId, int orderId) throws Exception {
        Delivery d= DataAccessLayer.Workers_Transport.Transports.Delivery.checkDelivery(id);

        if (d==null)
            throw new Exception("the delivery doesn't exists");
        if (d.getTargets().contains(locationId))
            throw new Exception("the target location already exists in the delivery");
        if (d.getOrders().contains(orderId))
            throw new Exception("the order already exists in the delivery");
        if(locationController.getLocation(locationId).getShippingArea().compareTo(locationController.getLocation(d.getTargets().get(0)).getShippingArea()) != 0)
            throw new Exception("location in another area");
        if(d.getStatus().equals(Delivery.Status.InProgress) || d.getStatus().equals(Delivery.Status.Done))
            throw new Exception("edit delivery details only for Created delivery");
        try {
            if(d.getWeight() + orderController.getOrder(orderId).getTotalWeight() >
                    truckController.getTruck(d.getTruckId()).getTotalWeight())
                throw new Exception("cannot add the order to the delivery, the weight of the delivery passes the max capacity of the truck");
            DataAccessLayer.Workers_Transport.Transports.Delivery.addOrderAndLocation(id,locationId,orderId);
            double we=d.getWeight() + orderController.getOrder(orderId).getTotalWeight();
            DataAccessLayer.Workers_Transport.Transports.Delivery.updateDelWeight(id,we);

        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void updateWeight(String id, double weight) throws Exception {
        Delivery d= DataAccessLayer.Workers_Transport.Transports.Delivery.checkDelivery(id);
        if (d==null)
            throw new Exception("the delivery doesn't exists");
        if(weight <= 0)
            throw new Exception("the weight is lower than 0");
        if(weight + truckController.getTruck(d.getTruckId()).getNetoWeight() > truckController.getTruck(d.getTruckId()).getTotalWeight())
            throw new Exception("the weight of the order and the truck bigger than the max weight");
        if(d.getStatus().equals(Delivery.Status.InProgress) || d.getStatus().equals(Delivery.Status.Done))
            throw new Exception("edit delivery details only for Created delivery");
        deliveries.get(id).setWeight(weight);
        DataAccessLayer.Workers_Transport.Transports.Delivery.updateDelWeight(id,weight+truckController.getTruck(d.getTruckId()).getNetoWeight());

    }

    public void updateTruckId(String id, String truckId) throws Exception {
        Delivery d= DataAccessLayer.Workers_Transport.Transports.Delivery.checkDelivery(id);
        if (d==null)
            throw new Exception("the delivery doesn't exists");
        if(d.getStatus().equals(Delivery.Status.InProgress) || d.getStatus().equals(Delivery.Status.Done))
            throw new Exception("edit delivery details only for Created delivery");
        if(DataAccessLayer.Workers_Transport.Transports.Truck.checkTruck(truckId)==null)
            throw new Exception("the truck doesn't exists");
        if(d.getWeight()>truckController.getTruck(truckId).getTotalWeight())
            throw new Exception("weight of delivery is bigger then the truck's max weight");
        if(DataAccessLayer.Workers_Transport.Transports.Delivery.checkTruckForDel(id,new java.sql.Date(d.getDeliveryDate().getTime()),truckId))
            throw new Exception("the truck is already is used at the same");

        try {

            deliveries.get(id).setTruckId(truckId);
            DataAccessLayer.Workers_Transport.Transports.Delivery.updateTruckID(id,truckId);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public boolean updateStatus(String id, String status) throws Exception {
        Delivery d= DataAccessLayer.Workers_Transport.Transports.Delivery.checkDelivery(id);
        if (d==null)
            throw new Exception("the delivery doesn't exists");
        if(status.compareTo("InTransit") != 0 && status.compareTo("Delivered") != 0)
            throw new Exception("status can be changed only to InTransit or Delivered");
        /*if(d.getWeight()>truckController.getTruck(d.getTruckId()).getTotalWeight())
            throw new Exception("weight of delivery is bigger then the truck's max weight");*/
        if(status.compareTo("InTransit") == 0 && d.getWeight() <= truckController.getTruck(d.getTruckId()).getTotalWeight())
        {
            d.setStatus(Delivery.Status.InProgress);
            DataAccessLayer.Workers_Transport.Transports.Delivery.updateStatus(id,status);
            truckController.getTruck(d.getTruckId()).setUsed();
            DataAccessLayer.Workers_Transport.Transports.Truck.updateUsed(d.getTruckId(),true);
            driverController.getDriver(deliveries.get(id).getDriverId()).setDriving();
        }
        else
        if(status.compareTo("InTransit") == 0)
            throw new Exception("cannot start the delivery process, the weight of the delivery is bigger than the total weight possible\n" +
                    "please rearrange the delivery");
        if(status.compareTo("Delivered") == 0)
        {
            d.setStatus(Delivery.Status.Done);
            DataAccessLayer.Workers_Transport.Transports.Delivery.updateStatus(id,status);
            truckController.getTruck(d.getTruckId()).setNotUsed();
            DataAccessLayer.Workers_Transport.Transports.Truck.updateUsed(d.getTruckId(),false);
            driverController.getDriver(deliveries.get(id).getDriverId()).setNotDriving();
        }
        return true;
    }

    public Order createOrder(int id, Map<String, Integer> items, String supplierId, int locationId, double totalWeight) throws Exception
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

    public Map<Integer, Order> getOrders()
    {
        return orderController.getOrders();
    }

    public Order getOrder(int id) throws Exception {
        return orderController.getOrder(id);
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

    public void removeOrder(int id) throws Exception
    {
        if(DataAccessLayer.Workers_Transport.Transports.Delivery.checkOrder(id))
            throw new Exception("cant change delivery that is InTransit or Delivered");
        try
        {
            orderController.removeOrder(orderController.getOrder(id));
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void addItem(int id, String item, int quantity) throws Exception
    {
        if(DataAccessLayer.Workers_Transport.Transports.Delivery.checkOrder(id))
            throw new Exception("cant change delivery that is InTransit or Delivered");
        try
        {
            orderController.addItem(id, item, quantity);
        }
        catch (Exception e)
        {
            throw e;
        }
    }


    public void removeItem(int id, String item) throws Exception
    {
        if(DataAccessLayer.Workers_Transport.Transports.Delivery.checkOrder(id))
            throw new Exception("cant change delivery that is InTransit or Delivered");
        try
        {
            orderController.removeItem(id, item);
        }
        catch (Exception e)
        {
            throw e;
        }
    }


    public void updateQuantity(int id, String item, int quantity) throws Exception
    {
        if(DataAccessLayer.Workers_Transport.Transports.Delivery.checkOrder(id))
            throw new Exception("cant change delivery that is InTransit or Delivered");
        try
        {
            orderController.updateQuantity(id, item, quantity);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void updateTotalWeight(int id, double totalWeight) throws Exception
    {
        if(DataAccessLayer.Workers_Transport.Transports.Delivery.checkOrder(id))
            throw new Exception("cant change delivery that is InTransit or Delivered");
        try
        {
            /*for (String deliveryId : deliveries.keySet())
            {
                if(deliveries.get(deliveryId).getOrders().contains(id) &&
                        deliveries.get(deliveryId).getWeight() + totalWeight <= truckController.getTruck(deliveries.get(deliveryId).getTruckId()).getTotalWeight())
                    deliveries.get(deliveryId).setWeight(deliveries.get(deliveryId).getWeight() + totalWeight);
                else
                    if(deliveries.get(deliveryId).getOrders().contains(id))
                        throw new Exception("the weight of delivery: " + deliveryId + " passed the max weight possible");
            }*/
            orderController.updateTotalWeight(id, totalWeight);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public Location createLocation(int id, String name, String address, String telNumber, String contactName, String shippingArea) throws Exception
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

    public void removeLocation(int id) throws Exception
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

    public void updateTelNumber(int id, String telNumber) throws Exception
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

    public void updateContactName(int id, String contactName) throws Exception
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

    public Response createTruck(String id, String model, double netoWeight, double totalWeight) throws Exception
    {
        try
        {
            Truck t = truckController.createTruck(id, model, netoWeight, totalWeight);
            truckController.addTruck(t);
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
        return new Response();
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

    public Response removeTruck(String id) throws Exception
    {
        try
        {
            truckController.removeTruck(id);
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
        return new Response();
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

    public void createDriver(DTO.Driver driver, int branchID) throws Exception
    {
        try
        {
            //WorkerDTO worker=new WorkerDTO(driver.getFirstName(),driver.getLastName(),driver.getID(), driver.getBankAccount(), driver.getHiringConditions(), driver.getAvailableWorkDays());
            //Workers.insertWorker(wFacade.convertWorkerToBusiness(worker),branchID);
            //DTO.Driver driver1=new DTO.Driver(driver.getID(),driver.getLicenseType(),driver.getExpLicenseDate());
            Drivers.insertDriver(driver,branchID);
         /*   if(!licenseTypes.containsKey(licenseType))
                throw new Exception("Not Valid License Type");
            Driver d = driverController.createDriver(driver, licenseType, expLicenseDate);
            driverController.addDriver(d);
            return d;*/
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
    public void updateExpDate(String id, LocalDate expLicenseDate) throws Exception
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
    public List<Driver> getDrivers()
    {
        return driverController.getDrivers();
    }
    public Map<String, Truck> getTrucks()
    {
        return truckController.getTrucks();
    }
    public Map<Integer, Location> getLocations()
    {
        return locationController.getLocations();
    }

    public Date getDeliveryDate(String id) throws Exception {
        Delivery d= DataAccessLayer.Workers_Transport.Transports.Delivery.checkDelivery(id);
        if(d==null)
            throw new Exception("the delivery id does not exists");
        return d.getDeliveryDate();
    }

    public double getDeliveryTruckWeight(String id) throws Exception {
        Delivery d= DataAccessLayer.Workers_Transport.Transports.Delivery.checkDelivery(id);
        if(d==null)
            throw new Exception("the delivery id does not exists");

        return truckController.getTruck(d.getTruckId()).getTotalWeight();
    }

    public double getWeightForType(String type)
    {
        return licenseTypes.get(type);
    }

    public String getDeliveryDriverID(String id) throws Exception {
        Delivery d= DataAccessLayer.Workers_Transport.Transports.Delivery.checkDelivery(id);
        if(d==null)
            throw new Exception("the driver doesn't exists");
        return d.getDriverId();
    }
    public Time getDeliveryLeavingTime(String id) throws Exception {
        Delivery d= DataAccessLayer.Workers_Transport.Transports.Delivery.checkDelivery(id);
        if(d==null)
            throw new Exception("delivery doesn't exists");
        return d.getLeavingTime();

    }

    public void printDeliveries() throws SQLException { DataAccessLayer.Workers_Transport.Transports.Delivery.printDeliveries(); }

    public void printLocations() throws SQLException {  locationController.printLocations(); }

    public void printOrders() throws SQLException{ orderController.printOrders(); }

    public  void printTrucks() throws SQLException { truckController.printTrucks(); }




}
