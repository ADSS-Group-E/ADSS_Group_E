package BusinessLayer.Supplier.DomainObjects;

import BusinessLayer.Inventory.DomainObjects.DomainObject;
import PresentationLayer.Supplier.DataTransferObjects.DiscountStepDTO;

public class DiscountStep extends DomainObject {
    private final int stepPrice;
    private final int percentage;

    public DiscountStep(int stepPrice, int percentage) {
        super(-1);
        this.stepPrice = stepPrice;
        this.percentage = percentage;
    }

    public DiscountStep(int id, int stepPrice, int percentage) {
        super(id);
        this.stepPrice = stepPrice;
        this.percentage = percentage;
    }
    public DiscountStep( DiscountStepDTO other){
        super(other.getId());
        this.stepPrice = other.getStepPrice();
        this.percentage = other.getPercentage();
    }

    public int getStepPrice() {
        return stepPrice;
    }

    public int getPercentage() {
        return percentage;
    }
}
