package PresentationLayer.Supplier.DataTransferObjects;

public class DiscountStepDTO {
    private int id = -1;
    private final int stepPrice;
    private final int percentage;
    private int qwid = -1;

    public DiscountStepDTO(int stepPrice, int percentage) {
        this.stepPrice = stepPrice;
        this.percentage = percentage;
    }

    public DiscountStepDTO(int id, int stepPrice, int percentage, int qwid) {
        this.id = id;
        this.stepPrice = stepPrice;
        this.percentage = percentage;
        this.qwid = qwid;
    }

    public int getId() { return id; }

    public int getQwid() { return qwid; }

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

    public void setQwid(int qwid) {
        if (this.qwid == -1)
            this.qwid = qwid;
    }
}
