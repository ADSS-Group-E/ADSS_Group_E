package PresentationLayer.Supplier.DataTransferObjects;

public class DiscountStepDTO {
    private final int id;
    private final int stepPrice;
    private final int precentage;
    private final int qwid;

    public DiscountStepDTO(int id, int stepPrice, int precentage, int qwid) {
        this.id = id;
        this.stepPrice = stepPrice;
        this.precentage = precentage;
        this.qwid = qwid;
    }

    public int getId() { return id; }

    public int getQwid() { return qwid; }

    public int getPrecentage() {
        return precentage;
    }

    public int getStepPrice() {
        return stepPrice;
    }
}
