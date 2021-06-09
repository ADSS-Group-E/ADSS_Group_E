package PresentationLayer.Inventory.DataTransferObjects;

import BusinessLayer.Inventory.DomainObjects.Discount;
import BusinessLayer.Inventory.DomainObjects.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This class represents the DiscountDTO.
 * A DTO is an object that is used to encapsulate data and send it from one subsystem of an application to another.
 */

public class DiscountDTO extends DataTransferObject{
    private final String name;
    private final double discountPercent;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

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

    // Constructors
    public DiscountDTO(int did, String name, double discountPercent, LocalDateTime startDate, LocalDateTime endDate) {
        super(did);
        this.name = name;
        this.discountPercent = discountPercent;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public DiscountDTO(Discount other, ArrayList<Product> products) {
        super(other.getId());
        this.name = other.getName();
        this.discountPercent = other.getDiscountPercent();
        this.startDate = other.getStartDate();
        this.endDate = other.getEndDate();
    }

    // Print
    public String toString(){
        return  "DID:                " + id + "\n" +
                "Name:               " + name + "\n" +
                "Discount Percent:   " + String.format("%.2f",discountPercent*100) + "%\n" +
                "Start Date:         " + startDate.toLocalDate() + "\n" +
                "End Date:           " + endDate.toLocalDate() ;
    }
}
