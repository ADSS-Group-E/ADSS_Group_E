package BusinessLayer.Inventory.DomainObjects;

import PresentationLayer.Inventory.DataTransferObjects.DiscountDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This class represents discounts for products.
 * Discounts Can be applied for specific products and/or categories.
 */
public class Discount extends DomainObject{
    private final String name;
    private final double discountPercent;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public Discount(DiscountDTO other) {
        super(other.getId());
        this.name = other.getName();
        this.discountPercent = other.getDiscountPercent();
        this.startDate = other.getStartDate();
        this.endDate = other.getEndDate();
    }

    // Getters

    public double getDiscountPercent() {
        return discountPercent;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Discount(int did, String name, double discountPercent, LocalDateTime startDate, LocalDateTime endDate) {
        super(did);
        this.name = name;
        this.discountPercent = discountPercent;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * this function checks if the dates are valid
     * @return true if the discount applies currently, false otherwise
     */
    public boolean discountValid(){
        return (startDate.isBefore(LocalDateTime.now()) && endDate.isAfter(LocalDateTime.now()));
    }

}
