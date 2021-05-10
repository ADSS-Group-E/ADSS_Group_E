package PresentationLayer.Supplier.DataTransferObjects;

public class DiscountStepDTO {
    private int id = -1;
    private final int stepPrice;
    private final int precentage;
    private int qwid = -1;

    public DiscountStepDTO(int stepPrice, int precentage) {
        this.stepPrice = stepPrice;
        this.precentage = precentage;
    }

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

    public void setId(int id) {
        if (this.id == -1)
            this.id = id;
    }

    public void setQwid(int qwid) {
        if (this.qwid == -1)
            this.qwid = qwid;
    }
}
