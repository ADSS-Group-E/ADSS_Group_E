package BussinessLayer.DeliveryPackage;

import java.util.*;

public class LocationController {

    private Map<String, Location> locations;
    private static LocationController locationController = null;

    private LocationController()
    {
        this.locations = new HashMap<>();
    }

    public static LocationController getInstance()
    {
        if(locationController == null)
            locationController = new LocationController();
        return locationController;
    }

    public Location getLocation(String id) throws Exception {
        if(!locations.containsKey(id))
            throw new Exception("The Location Doesn't Exists");
        return locations.get(id);
    }

    public Location createLocation(String id, String name, String address, String telNumber, String contactName, String shippingArea) throws Exception {
        if(!telNumber.matches("[0-9]+"))
            throw new Exception("The Telephone Number Contains Illegal Numbers");
        if(!contactName.matches("[a-zA-Z]+"))
            throw new Exception("The Contact Name Contains Illegal Characters");
        if(locations.containsKey(id))
            throw new Exception("The Location Already Exists");
        if(shippingArea.compareTo("North") != 0 && shippingArea.compareTo("South") != 0 && shippingArea.compareTo("Center") != 0)
            throw new Exception("The Location Area Doesn't Exist");
        Location location = new Location(id, name, address, telNumber, contactName, shippingArea);
        return location;
    }

    public void addLocation(Location location) throws Exception {
        if(!location.getTelNumber().matches("[0-9]+"))
            throw new Exception("The Telephone Number Contains Illegal Numbers");
        if(!location.getContactName().matches("[a-zA-Z]+"))
            throw new Exception("The Contact Name Contains Illegal Characters");
        if(locations.containsKey(location.getId()))
            throw new Exception("The Location Already Exists");
        if(location.getShippingArea().compareTo("North") != 0 && location.getShippingArea().compareTo("South") != 0 && location.getShippingArea().compareTo("Center") != 0)
            throw new Exception("The Location Area Doesn't Exist");
        this.locations.put(location.getId(), location);
    }

    public void removeLocation(Location location) throws Exception {
        if(!locations.containsKey(location.getId()))
            throw new Exception("The Location Doesn't Exists");
        this.locations.remove(location.getId());
    }

    public void updateTelNumber(String id, String telNumber) throws Exception {
        if(!telNumber.matches("[0-9]+"))
            throw new Exception("The Telephone Number Contains Illegal Numbers");
        if(!locations.containsKey(id))
            throw new Exception("The Location Doesn't Exists");
        locations.get(id).setTelNumber(telNumber);
    }

    public void updateContactName(String id, String contactName) throws Exception {
        if(!contactName.matches("[a-zA-Z]+"))
            throw new Exception("The Contact Name Contains Illegal Characters");
        if(!locations.containsKey(id))
            throw new Exception("The Location Doesn't Exists");
        locations.get(id).setContactName(contactName);
    }

    public Map<String, Location> getLocations() {
        return locations;
    }
}
