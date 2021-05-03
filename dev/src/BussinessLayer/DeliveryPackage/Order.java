package BussinessLayer.DeliveryPackage;

import java.util.*;

public class Order {

    private String id;
    private Map<String, Integer> items;
    private String supplierId;
    private String locationId;
    private double weight;

    public Order(String id, Map<String, Integer> items, String supplierId, String locationId, double weight) {
        this.id = id;
        this.items = items;
        this.supplierId = supplierId;
        this.locationId = locationId;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void setItems(Map<String, Integer> items) {
        this.items = items;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Order {" +
                "id='" + id + '\'' +
                ", items=" + items +
                ", supplierId='" + supplierId + '\'' +
                ", locationId='" + locationId + '\'' +
                ", weight=" + weight +
                '}';
    }
}
