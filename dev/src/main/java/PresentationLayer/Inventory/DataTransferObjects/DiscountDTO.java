package PresentationLayer.Inventory.DataTransferObjects;

import BusinessLayer.Inventory.Discount;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This class represents the DiscountDTO.
 * A DTO is an object that is used to encapsulate data and send it from one subsystem of an application to another.
 */

public class DiscountDTO extends DataTransferObject{
    private final int did;
    private final String name;
    private final double discountPercent;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final ArrayList<Integer> pids;
    private final String type;

    // Getters
    public int getDid() {
        return did;
    }

    public String getName() {
        return name;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public ArrayList<Integer> getPids() {
        return pids;
    }

    public String getType() {
        return type;
    }

    // Constructors
    public DiscountDTO(int did, String name, double discountPercent, LocalDateTime startDate, LocalDateTime endDate, ArrayList<Integer> pids, String type) {
        super(did);
        this.did = did;
        this.name = name;
        this.discountPercent = discountPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pids = pids;
        this.type = type;
    }

    public DiscountDTO(Discount other) {
        super(other.getId());
        this.did = other.getId();
        this.name = other.getName();
        this.discountPercent = other.getDiscountPercent();
        this.startDate = other.getStartDate();
        this.endDate = other.getEndDate();
        this.type = other.getType();
        this.pids = new ArrayList<>();
        other.getProducts().forEach((product)-> pids.add(product.getId()));
    }

    // Print
    public String toString(){
        return  "DID:                " + did + "\n" +
                "Name:               " + name + "\n" +
                "Discount Percent:   " + String.format("%.2f",discountPercent*100) + "%\n" +
                "Start Date:         " + startDate.toLocalDate() + "\n" +
                "End Date:           " + endDate.toLocalDate()  + "\n" +
                "Type:               " + type + "\n" +
                "Applies to PIDs:    " + pids;
    }
}
