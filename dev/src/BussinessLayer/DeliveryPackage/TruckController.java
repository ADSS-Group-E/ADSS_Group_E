package BussinessLayer.DeliveryPackage;

import java.util.*;

public class TruckController {

    private Map<String, Truck> trucks;
    private static TruckController truckController = null;

    private TruckController() {

        this.trucks = new HashMap<>();
    }

    public static TruckController getInstance() {
        if (truckController == null)
            truckController = new TruckController();
        return truckController;
    }

    public Truck getTruck(String id) throws Exception {
        if (!trucks.containsKey(id))
            throw new Exception("The Truck Doesn't Exists");
        return trucks.get(id);
    }

    public Truck createTruck(String id, String model, double netoWeight, double totalWeight) throws Exception {
        if (trucks.containsKey(id))
            throw new Exception("The Truck Already Exists");
        if (netoWeight < 0 || totalWeight < 0)
            throw new Exception("Weight Cannot Be Lower Than 0");
        if (netoWeight > totalWeight)
            throw new Exception("Neto Cannot Be Bigger Than the Total");
        Truck truck = new Truck(id, model, netoWeight, totalWeight);
        return truck;
    }

    public void addTruck(Truck truck) throws Exception {
        if (trucks.containsKey(truck.getId()))
            throw new Exception("The Truck Already Exists");
        this.trucks.put(truck.getId(), truck);
    }

    public void removeTruck(Truck truck) throws Exception {
        if (!trucks.containsKey(truck.getId()))
            throw new Exception("The Truck Doesn't Exists");
        this.trucks.remove(truck.getId());
    }

    public void setTruckUsed(String id) throws Exception {
        if (!trucks.containsKey(id))
            throw new Exception("The Truck Doesn't Exists");
        trucks.get(id).setUsed();
    }

    public void setTruckNotUsed(String id) throws Exception {
        if (!trucks.containsKey(id))
            throw new Exception("The Truck Doesn't Exists");
        trucks.get(id).setNotUsed();
    }

    public Map<String, Truck> getTrucks() {
        return trucks;
    }
}
