package PresentationLayer;

import java.time.LocalDate;

public class ShiftDemandsDTO {
    private LocalDate date;
    private int cashierAmount;
    private int storeKeeperAmount;

    @Override
    public String toString() {
        return "ShiftDemandsDTO{" +
                "date=" + date +
                ", cashierAmount=" + cashierAmount +
                ", storeKeeperAmount=" + storeKeeperAmount +
                ", arrangerAmount=" + arrangerAmount +
                ", guardAmount=" + guardAmount +
                ", assistantAmount=" + assistantAmount +
                '}';
    }

    private int arrangerAmount;
    private int guardAmount;
    private int assistantAmount;

    public ShiftDemandsDTO(LocalDate date, int cashierAmount, int storeKeeperAmount, int arrangerAmount, int guardAmount, int assistantAmount) {
        this.date = date;
        this.cashierAmount = cashierAmount;
        this.storeKeeperAmount = storeKeeperAmount;
        this.arrangerAmount = arrangerAmount;
        this.guardAmount = guardAmount;
        this.assistantAmount = assistantAmount;
    }
    public ShiftDemandsDTO(BuisnessLayer.ShiftDemands blShiftDemands) {
        this.date = blShiftDemands.getDate();
        this.cashierAmount = blShiftDemands.getCashierAmount();
        this.storeKeeperAmount = blShiftDemands.getStoreKeeperAmount();
        this.arrangerAmount = blShiftDemands.getArrangerAmount();
        this.guardAmount = blShiftDemands.getGuardAmount();
        this.assistantAmount = blShiftDemands.getAssistantAmount();
    }


    public LocalDate getDate() {
        return date;
    }

    public int getCashierAmount() {
        return cashierAmount;
    }

    public int getStoreKeeperAmount() {
        return storeKeeperAmount;
    }

    public int getArrangerAmount() {
        return arrangerAmount;
    }

    public int getGuardAmount() {
        return guardAmount;
    }

    public int getAssistantAmount() {
        return assistantAmount;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCashierAmount(int cashierAmount) {
        this.cashierAmount = cashierAmount;
    }

    public void setStoreKeeperAmount(int storeKeeperAmount) {
        this.storeKeeperAmount = storeKeeperAmount;
    }

    public void setArrangerAmount(int arrangerAmount) {
        this.arrangerAmount = arrangerAmount;
    }

    public void setGuardAmount(int guardAmount) {
        this.guardAmount = guardAmount;
    }

    public void setAssistantAmount(int assistantAmount) {
        this.assistantAmount = assistantAmount;
    }
}
