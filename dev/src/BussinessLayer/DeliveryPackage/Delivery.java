package BussinessLayer.DeliveryPackage;

import java.sql.Time;
import java.util.*;

public class Delivery {

    public enum Status {
        Created , InProgress, Done;
    }
    private String id;
    private Date deliveryDate;
    private Time leavingTime;
    private String driverId;
    private String truckId;
    private double weight;
    private int srcLocation;
    private List<Integer> targets;
    private List<Integer> orders;
    private Status status;

    public Delivery(String id, Date deliveryDate, Time leavingTime, String driverId, int srcLocation, List<Integer> targets, double weight, String truckId, List<Integer> orders) {
        this.id = id;
        this.deliveryDate = deliveryDate;
        this.leavingTime = leavingTime;
        this.driverId = driverId;
        this.srcLocation = srcLocation;
        this.targets = targets;
        this.weight = weight;
        this.truckId = truckId;
        this.orders = orders;
        this.status = Status.Created;
    }

    public Delivery(String id, Date deliveryDate, Time leavingTime, String driverId, int srcLocation, List<Integer> targets, double weight, String truckId, List<Integer> orders,String status) {
        this.id = id;
        this.deliveryDate = deliveryDate;
        this.leavingTime = leavingTime;
        this.driverId = driverId;
        this.srcLocation = srcLocation;
        this.targets = targets;
        this.weight = weight;
        this.truckId = truckId;
        this.orders = orders;
        this.status = Status.valueOf(status);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Time getLeavingTime() {
        return leavingTime;
    }

    public void setLeavingTime(Time leavingTime) {
        this.leavingTime = leavingTime;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public int getSrcLocation() {
        return srcLocation;
    }

    public void setSrcLocation(int srcLocation) {
        this.srcLocation = srcLocation;
    }

    public List<Integer> getTargets() {
        return targets;
    }

    public void removeTargetLocation(String targetLocation) {
        this.targets.remove(targetLocation);
    }

    public void addTargetLocation(int targetLocation) {
        this.targets.add(targetLocation);
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getTruckId() {
        return truckId;
    }

    public void setTruckId(String truckId) {
        this.truckId = truckId;
    }

    public List<Integer> getOrders() {
        return orders;
    }

    public void removeOrder(String orderId) {
        this.targets.remove(orderId);
    }

    public void addOrder(int orderId) {
        this.targets.add(orderId);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Delivery {" +
                "id='" + id + '\'' +
                ", deliveryDate=" + deliveryDate +
                ", leavingTime=" + leavingTime +
                ", driverId='" + driverId + '\'' +
                ", srcLocation='" + srcLocation + '\'' +
                ", targets=" + targets +
                ", weight=" + weight +
                ", truckId='" + truckId + '\'' +
                ", orders=" + orders +
                ", status=" + status +
                '}';
    }
}
