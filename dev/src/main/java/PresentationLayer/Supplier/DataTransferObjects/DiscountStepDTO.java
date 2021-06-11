package PresentationLayer.Supplier.DataTransferObjects;

import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

public class DiscountStepDTO extends DataTransferObject {
    private final int stepPrice;
    private final int percentage;
    private int quantityWriterId = -1;

    public DiscountStepDTO(int stepPrice, int percentage) {
        super(-1);
        this.stepPrice = stepPrice;
        this.percentage = percentage;
    }

    public DiscountStepDTO(int id, int stepPrice, int percentage, int quantityWriterId) {
        super(id);
        this.stepPrice = stepPrice;
        this.percentage = percentage;
        this.quantityWriterId = quantityWriterId;
    }

    public int getId() { return id; }

    public int getQuantityWriterId() { return quantityWriterId; }

    public int getPercentage() {
        return percentage;
    }

    public int getStepPrice() {
        return stepPrice;
    }

    public void setId(int id) {
        if (this.id == -1)
            this.id = id;
    }

    public void setQuantityWriterId(int quantityWriterId) {
        if (this.quantityWriterId == -1)
            this.quantityWriterId = quantityWriterId;
    }
}
