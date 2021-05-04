package PresentationLayer.Supplier.DataTransferObjects;

public class DiscountStepDTO {
    private final int stepPrice;
    private final int precentage;

    public DiscountStepDTO(int stepPrice, int precentage) {
        this.stepPrice = stepPrice;
        this.precentage = precentage;
    }

    public int getPrecentage() {
        return precentage;
    }

    public int getStepPrice() {
        return stepPrice;
    }
}
